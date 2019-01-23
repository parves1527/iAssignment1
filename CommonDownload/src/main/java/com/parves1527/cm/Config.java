package com.parves1527.cm;

import java.io.File;

public class Config
{
    private int timeout;
    private boolean verifyHost = false;
    private String downloadDir = ".";
    private String tempDir = "." + File.separator + "temp";
    private int chunkSize = 1024*1024;

    public String getDownloadDir()
    {
        return downloadDir;
    }

    public void setDownloadDir(String downloadPath)
    {
        this.downloadDir = downloadPath;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    public boolean isVerifyHost()
    {
        return verifyHost;
    }

    public void setVerifyHost(boolean verifyHost)
    {
        this.verifyHost = verifyHost;
    }

    public void setTempDir(String tempDir)
    {
        this.tempDir = tempDir;
    }
    
    public String getTempDir()
    {
        return tempDir;
    }

    public int getChunkSize()
    {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize)
    {
        this.chunkSize = chunkSize;
    }        
}
