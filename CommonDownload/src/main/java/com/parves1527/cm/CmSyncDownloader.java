package com.parves1527.cm;

import com.parves1527.cm.CmRequestInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CmSyncDownloader
{                            
    public DownloadDetail download(CmRequestInfo cmRequestInfo) {
        Uri uri = cmRequestInfo.getUri();
        
        Credential credential = cmRequestInfo.getCredential();
        String protocol = uri.getProtocol();
        DownloaderFactory downloaderFactory = cmRequestInfo.getDownloaderFactory();
        CmDownloader downloader = downloaderFactory.getDownloaderInstance(protocol);
        
        if(uri.getPort() == -1)
        {
            uri.setPort(downloader.getDefaultPort());
        }
        
        Config configuration = cmRequestInfo.getConfig();
        
        long start = System.currentTimeMillis();
        
        DownloadDetail downloadDetail = downloader.download(uri, credential, configuration);
        
        long end = System.currentTimeMillis();
        
        downloadDetail.setTotalTime(end - start);
        
        if(downloadDetail.getDownloadStatus() == DownloadStatus.completed) {
            moveToDownloadDir(cmRequestInfo);
        }
        return downloadDetail;
    }
    
    private void moveToDownloadDir(CmRequestInfo cmRequestInfo) {
        String absoluteTempPath = cmRequestInfo.getUri().getLocalResource();
        File file = new File(absoluteTempPath);
        String tempFileName = file.getName();
        Path tempPath = Paths.get(absoluteTempPath);
        String absoluteDownloadPath = cmRequestInfo.getConfig().getDownloadDir() + File.separator + tempFileName;        
        Path downloadPath = Paths.get(absoluteDownloadPath);
        
        try {
            Files.move(tempPath, downloadPath,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
