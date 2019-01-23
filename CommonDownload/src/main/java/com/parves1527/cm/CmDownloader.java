package com.parves1527.cm;

import com.parves1527.cm.DownloadDetail;
import com.parves1527.cm.DownloadDetail;
import com.parves1527.cm.Config;
import com.parves1527.cm.Credential;
import com.parves1527.cm.Uri;

public interface CmDownloader
{
    DownloadDetail download(Uri uri, Credential credential, Config configuration);
    
    int getDefaultPort();
}
