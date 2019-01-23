package com.parves1527.cm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

class HttpDownloader implements CmDownloader {
    
    private static final int DEFAULT_PORT = 80;

    @Override
    public int getDefaultPort()
    {
        return DEFAULT_PORT;
    }
    
    @Override
    public DownloadDetail download(Uri uri, Credential credential, Config configuration) {
        try
        {
            String address = uri.getProtocol() + "://" + uri.getHost();
            int port = uri.getPort();
            if(port != DEFAULT_PORT)
            {
                address = address + ":" + port;
            }
            URL website = new URL(address);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());    
            FileOutputStream fos = new FileOutputStream(uri.getLocalResource());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (IOException ex) {
            return new DownloadDetail(DownloadStatus.failed, ex.getMessage());
        }
        
        return new DownloadDetail(DownloadStatus.completed);
    }
}
