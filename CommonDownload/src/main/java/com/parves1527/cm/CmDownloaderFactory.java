package com.parves1527.cm;

import java.util.ArrayList;
import java.util.List;

public class CmDownloaderFactory implements DownloaderFactory
{
    @Override
    public CmDownloader getDownloaderInstance(String protocol)
    {
        try {
            NetworkProtocol networkProtocol = NetworkProtocol.valueOf(protocol);
            return getDownloader(networkProtocol);
        }
        catch(java.lang.IllegalArgumentException ex) {
            return null;
        }        
    }

    @Override
    public List<String> getSupportedProtocol()
    {        
        List<String> protocols = new ArrayList<>();
        
        for(NetworkProtocol protocol : NetworkProtocol.values())
        {
            protocols.add(protocol.name());
        }        
        
        return protocols;
    }
    
    private CmDownloader getDownloader(NetworkProtocol protocol) {
                
        CmDownloader downloader = null;
        
        switch(protocol)
        {
            case http:
            case https:    
                downloader = getHttpDownlader();
                break;
                
            case ftp:
                downloader = getFtpDownlader();
                break;
            case sftp:
                downloader = getSftpDownlader();
                break;
        }
        
        return downloader;
    }
    
    private HttpDownloader getHttpDownlader() {
        return new HttpDownloader();
    }
    
    private FtpDownloader getFtpDownlader() {
        return new FtpDownloader();
    }
    
    private SftpDownloader getSftpDownlader() {
        return new SftpDownloader();
    }        
}
