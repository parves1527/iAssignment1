package com.parves1527.cm;

public class DownloadDetail
{
    private final DownloadStatus downloadStatus;    
    private final String failureReason;
    private long totalTime;

    public DownloadDetail(DownloadStatus downloadStatus)
    {
        this(downloadStatus, null);
    }
    
    public DownloadDetail(DownloadStatus downloadStatus, String failureReason)
    {
        this.downloadStatus = downloadStatus;
        this.failureReason = failureReason;
    }

    public DownloadStatus getDownloadStatus()
    {
        return downloadStatus;
    }

    public long getTotalTime()
    {
        return totalTime;
    }

    public String getFailureReason()
    {
        return failureReason;
    }

    public void setTotalTime(long totalTime)
    {
        this.totalTime = totalTime;
    }       
}
