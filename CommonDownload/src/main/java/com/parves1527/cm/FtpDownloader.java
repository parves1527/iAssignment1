package com.parves1527.cm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

class FtpDownloader implements CmDownloader
{    
    private static final int DEFAULT_PORT = 21;

    @Override
    public int getDefaultPort()
    {
        return DEFAULT_PORT;
    }
    
    @Override
    public DownloadDetail download(Uri uri, Credential credential, Config configuration)
    {
        String server = uri.getHost();
        int port = uri.getPort();
        String userName = credential.getUserName();
        String password = credential.getPassword().getPassString();

        FTPClient ftpClient = new FTPClient();
        try
        {
            ftpClient.connect(server, port);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFile = uri.getRemoteResource();
            File downloadFile = new File(uri.getLocalResource());
            InputStream inputStream;
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile)))
            {
                inputStream = ftpClient.retrieveFileStream(remoteFile);
                byte[] bytesArray = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(bytesArray)) != -1)
                {
                    outputStream.write(bytesArray, 0, bytesRead);
                }
                
                ftpClient.completePendingCommand();
            }
            inputStream.close();

        } catch (IOException ex)
        {
            return new DownloadDetail(DownloadStatus.failed, ex.getMessage());
        } finally
        {
            try
            {
                if (ftpClient.isConnected())
                {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        return new DownloadDetail(DownloadStatus.completed);
    }
}
