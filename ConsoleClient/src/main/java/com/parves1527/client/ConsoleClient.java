package com.parves1527.client;

import com.parves1527.cm.CmAsyncDownloader;
import com.parves1527.cm.CmRequestInfo;
import com.parves1527.cm.ResponseNotifier;
import com.parves1527.cm.DownloadDetail;
import com.parves1527.cm.DownloadStatus;
import com.parves1527.cm.RequestException;
import com.parves1527.cm.Uri;
import java.util.Scanner;

public class ConsoleClient implements ResponseNotifier
{
    @Override
    public void notify(Uri uri, DownloadDetail downloadDetail)
    {
        DownloadStatus downloadStatus = downloadDetail.getDownloadStatus();
        StringBuilder sb = new StringBuilder();
        
        sb.append(uri);
        sb.append(System.lineSeparator());
        sb.append("Download Status: ");
        sb.append(downloadStatus.name());
        sb.append(System.lineSeparator());
        sb.append("Total time in milisecond: ");
        sb.append(downloadDetail.getTotalTime());
        sb.append(" ms");
        sb.append(System.lineSeparator());
        if(downloadStatus != DownloadStatus.completed) {
            sb.append(downloadDetail.getFailureReason());
        }
        
        System.out.println(sb);
    }
    
    public void start(String fileName)
    {
        InputParser inputParser = new InputParser();
        Scanner scanner = new Scanner(System.in);
        CmAsyncDownloader downloader = new CmAsyncDownloader();
        
        System.out.println("Simple console based file downlader");
        System.out.println("Downoad input format:");
        System.out.println(CmRequestInfo.getHelpMessage());
        System.out.println("Enter q to quit");
        
        while(scanner.hasNextLine()) {
            String cmd = scanner.nextLine();
            if(cmd.equals("q")) {
                break;
            }
                        
            try 
            {
                CmRequestInfo cmRequestInfo = inputParser.parse(cmd, fileName);
                downloader.download(cmRequestInfo, this);
                System.out.println("Request has been sent");
            }
            catch(RequestException ex)
            {
                System.err.println(ex.getMessage());
            }            
        }
    }
}
