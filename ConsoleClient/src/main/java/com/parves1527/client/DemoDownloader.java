package com.parves1527.client;

import com.parves1527.cm.CmDownloader;
import com.parves1527.cm.Config;
import com.parves1527.cm.Credential;
import com.parves1527.cm.DownloadDetail;
import com.parves1527.cm.DownloadStatus;
import com.parves1527.cm.Uri;

public class DemoDownloader implements CmDownloader
{
    @Override
    public DownloadDetail download(Uri uri, Credential credential, Config configuration)
    {
        System.out.println("This is a demo downloader to demonstrate how to extend cm downloader library");
        return new DownloadDetail(DownloadStatus.ongoing);
    }

    @Override
    public int getDefaultPort()
    {
        return 0;
    }
    
}
