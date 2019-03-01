/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import static org.junit.Assert.*;

/**
 *
 * @author parves841
 */
public class SftpDownloaderTest
{
    private static final Config CONFIG = new Config();
    
    public SftpDownloaderTest()
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
        String address = "sftp://test.rebex.net/readme.txt";
        String userName = "demo";
        String password = "password";
        
        CmRequestInfo requestInfo = CmRequestInfo.Builder.newInstance(address, CONFIG).setUserName(userName).setPassword(new Password(password)).build();
                
        CmSyncDownloader syncDownloader = new CmSyncDownloader();
        DownloadDetail downloadDetail =  syncDownloader.download(requestInfo);
        
        Assert.assertEquals(downloadDetail.getDownloadStatus(), DownloadStatus.completed);
    }
}
