package com.parves1527.cm;

public class CmAsyncDownloader
{    
    public void download(CmRequestInfo cmRequestInfo, ResponseNotifier notifier)
    {
        TaskHandler handler = new TaskHandler(cmRequestInfo, notifier);
        Thread thread = new Thread(handler);
        thread.start();
    }        
    
    private class TaskHandler implements Runnable
    {
        private final CmRequestInfo cmRequestInfo;
        private final ResponseNotifier notifier;

        public TaskHandler(CmRequestInfo cmRequestInfo, ResponseNotifier notifier)
        {
            this.cmRequestInfo = cmRequestInfo;
            this.notifier = notifier;
        }                
        
        @Override
        public void run()
        {
            CmSyncDownloader syncDownader = new CmSyncDownloader();
            DownloadDetail downloadDetail = syncDownader.download(cmRequestInfo);
            notifier.notify(cmRequestInfo.getUri(), downloadDetail);
        }        
    }
}
