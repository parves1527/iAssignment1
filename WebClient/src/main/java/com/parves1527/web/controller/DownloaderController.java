package com.parves1527.web.controller;

import com.parves1527.cm.CmRequestInfo;
import com.parves1527.cm.CmSyncDownloader;
import com.parves1527.cm.Config;
import com.parves1527.cm.DownloadDetail;
import com.parves1527.cm.DownloadStatus;
import com.parves1527.cm.Password;
import com.parves1527.cm.RequestException;
import com.parves1527.cm.Uri;
import com.parves1527.web.WebConfig;
import com.parves1527.web.model.FileMeta;
import com.parves1527.web.service.FileMetaService;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DownloaderController
{
    @Autowired
    private FileMetaService fileMetaService;
    
    @Autowired
    private WebConfig webconfig;
    
    @RequestMapping(value = "/download", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String download(
            @RequestParam(value = "address") String address, 
            @RequestParam(value = "username") String userName,
            @RequestParam(value = "password") String password
    ) 
    {                                                        
        Config config = getConfig();
        
        System.out.println(config.getDownloadDir());
        
        CmRequestInfo.Builder builder = CmRequestInfo.Builder.newInstance(address, config);
        CmSyncDownloader downloader = new CmSyncDownloader();
        
        if(!userName.isEmpty()) {
            builder.setUserName(userName);
        }
        if(!password.isEmpty()) {
            builder.setPassword(new Password(password));
        }
        
        String msg;
        String status;
        
        try {
            CmRequestInfo cmRequestInfo = builder.build();        
            DownloadDetail downloadDetail = downloader.download(cmRequestInfo);            
            if(downloadDetail.getDownloadStatus() != DownloadStatus.completed)
            {
                msg = downloadDetail.getFailureReason();
            }
            else
            {
                msg = "file saved";

                dbInsert(address, cmRequestInfo.getUri());
            }
            
            status = downloadDetail.getDownloadStatus().name();
        }
        catch(RequestException ex)
        {
            status = "failed";
            msg = ex.getMessage();
        }
        
        System.out.println(status + " " + msg);
        
        String json = new JSONObject()
            .put("msg", msg)
            .put("status", status).toString();
        
        return json;   
    }    
    
    private String getFileType(String filePath, String remoteResource) 
    {
        String fileType = "";
        System.out.println(filePath);
        try(InputStream is = new BufferedInputStream(new FileInputStream(filePath)))
        {
            String type = URLConnection.guessContentTypeFromStream(is);
            System.out.println(type);
            if(type != null && !type.isEmpty())
            {
                fileType = type;
            }
            else if(remoteResource.endsWith(".txt")) {
                fileType = "text/plain";
            }
            else if(remoteResource.endsWith(".htm") || remoteResource.endsWith(".html")) {
                fileType = "text/html";
            }
            
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        return fileType;
    }
    
    private long getFileSize(String filePath) {
        File file = new File(filePath);
        return file.length();
    }
    
    private Config getConfig()
    {
        Config config = new Config();
        config.setTempDir(webconfig.getTemporaryDirectory());
        config.setDownloadDir(webconfig.getDownloadDirectory());
        config.setTimeout(webconfig.getRequestTimeout());
        config.setVerifyHost(webconfig.isHostVerification());        
        
        return config;
    }

    private void dbInsert(String address, Uri uri)
    {
        String filePath = uri.getLocalResource();
        String remoteResource = uri.getRemoteResource();
        FileMeta fileMeta = new FileMeta();

        fileMeta.setUri(address);
        fileMeta.setStatus("ready for processing");                                
        fileMeta.setSize(getFileSize(filePath));
        fileMeta.setFileType(getFileType(filePath, remoteResource));
        fileMeta.setFileName(remoteResource);
        fileMeta.setDownloadPath(filePath);        

        boolean flag = fileMetaService.add(fileMeta);

        System.out.println(flag ? "insert success" : "insert failed");
    }
}
