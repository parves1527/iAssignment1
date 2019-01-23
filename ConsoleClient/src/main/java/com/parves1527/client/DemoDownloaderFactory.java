package com.parves1527.client;

import com.parves1527.cm.CmDownloader;
import com.parves1527.cm.CmDownloaderFactory;
import java.util.List;

public class DemoDownloaderFactory extends CmDownloaderFactory
{
    private final String demo = "demo";
    
    @Override
    public List<String> getSupportedProtocol()
    {
        List<String> protocols = super.getSupportedProtocol();
        protocols.add(demo);        
        return protocols;
    }

    @Override
    public CmDownloader getDownloaderInstance(String protocol)
    {
        CmDownloader downloader = super.getDownloaderInstance(protocol);
        
        if(downloader == null && protocol.equals(demo))
        {
            downloader = new DemoDownloader();
        }
        
        return downloader;
    }    
}
