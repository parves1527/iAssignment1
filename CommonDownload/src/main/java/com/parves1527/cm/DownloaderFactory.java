package com.parves1527.cm;

import java.util.List;

public interface DownloaderFactory
{
    CmDownloader getDownloaderInstance(String protocol);
    
    List<String> getSupportedProtocol();
}
