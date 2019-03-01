package com.parves1527.cm.test;

import com.parves1527.cm.CmRequestInfo;
import com.parves1527.cm.CmSyncDownloader;
import com.parves1527.cm.Config;
import com.parves1527.cm.DownloadDetail;
import com.parves1527.cm.DownloadStatus;
import com.parves1527.cm.Password;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpDownloaderTest
{   
    private static final Config CONFIG = new Config();
    
    public HttpDownloaderTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {                
        CONFIG.setTempDir("..");
        CONFIG.setDownloadDir(".");
    }    
    
    @Test
    public void smallFile() 
    {
        String address = "http://example.com/index.html";
        CmRequestInfo requestInfo = CmRequestInfo.Builder.newInstance(address, CONFIG).build();
        
        CmSyncDownloader syncDownloader = new CmSyncDownloader();
        DownloadDetail downloadDetail =  syncDownloader.download(requestInfo);
        
        Assert.assertEquals(downloadDetail.getDownloadStatus(), DownloadStatus.completed);
    }
    
    @Test
    public void nestedResourcePath() 
    {
        String address = "http://placehold.it/120x120&text=image1";
        CmRequestInfo requestInfo = CmRequestInfo.Builder.newInstance(address, CONFIG).build();
        
        CmSyncDownloader syncDownloader = new CmSyncDownloader();
        DownloadDetail downloadDetail =  syncDownloader.download(requestInfo);
        
        Assert.assertEquals(downloadDetail.getDownloadStatus(), DownloadStatus.completed);
    }
}
