package com.parves1527.web.controller;

import com.parves1527.web.model.FileMeta;
import com.parves1527.web.service.FileMetaService;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController
{
    @Autowired
    private FileMetaService fileMetaService;
    
    @RequestMapping(value = "/show", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String downloaded(
            @RequestParam(value = "start") int start, 
            @RequestParam(value = "end") int end)
    {
        List<FileMeta> fileMetas = fileMetaService.get(start, end);        
        
        JSONArray root = new JSONArray();
        
        for(FileMeta fileMeta : fileMetas)
        {
            JSONObject item = new JSONObject()
                .put("id", fileMeta.getId())
                .put("file", fileMeta.getFileName())
                .put("uri", fileMeta.getUri())
                .put("size", fileMeta.getSize())
                .put("type", fileMeta.getFileType())
                .put("status", fileMeta.getStatus());
         
            root.put(item);
        }        
        
        return root.toString();
    }
    
    @RequestMapping(value = "/update", params = {"state"}, method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String updateStatus(
            @RequestParam(value = "state") boolean state,
            @RequestParam(value = "id") int id)
    {
        System.out.println("id: " + id + " state: " + state);
        
        String status = state ? "Approved" : "Rejected";
        
        boolean flag = fileMetaService.update(status, id);
        
        System.out.println(flag ? "update success" : "update failed");
        
        return "{}";
    }
    
    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String get(@RequestParam(value = "id") int id)
    {
        System.out.println("id: " + id);
        
        FileMeta fileMeta = fileMetaService.get(id);
        
        String fileType = fileMeta.getFileType();
        String content;
        
        try
        {
            byte[] bytes = Files.readAllBytes(Paths.get(fileMeta.getDownloadPath()));
            if(fileType.startsWith("image/")) {
                content = "data:" + fileType + ";base64," + new String(Base64.getEncoder().encode(bytes));
            }
            else
            {
                content = new String(bytes);
            }
        } catch (IOException ex)
        {
          ex.printStackTrace();
          content = "";
        }                
        
        String json = new JSONObject()
            .put("id", fileMeta.getId())
            .put("type", fileType)
            .put("content", content).toString();
        
        return json;
    }        
}
