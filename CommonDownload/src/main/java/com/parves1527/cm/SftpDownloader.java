package com.parves1527.cm;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

class SftpDownloader implements CmDownloader {
    
    private static final int DEFAULT_PORT = 22;

    @Override
    public int getDefaultPort()
    {
        return DEFAULT_PORT;
    }
    
    @Override
    public DownloadDetail download(Uri uri, Credential credential, Config configuration) {
        JSch jsch = new JSch();
        Session session;
        try {
            String userName = credential.getUserName();
            String password = credential.getPassword().getPassString();
            session = jsch.getSession(userName, uri.getHost(), uri.getPort());
            session.setConfig("StrictHostKeyChecking", (configuration.isVerifyHost() ? "yes" : "no"));
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(uri.getRemoteResource(), uri.getLocalResource());
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException | SftpException ex) {
            return new DownloadDetail(DownloadStatus.failed, ex.getMessage());
        }
        
        return new DownloadDetail(DownloadStatus.completed);
    }
}
