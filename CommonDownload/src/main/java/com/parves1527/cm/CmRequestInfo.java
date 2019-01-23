package com.parves1527.cm;

public class CmRequestInfo
{
    private final Credential credential;
    private final Uri uri;
    private final Config config;
    private final DownloaderFactory downloaderFactory;

    private CmRequestInfo(Uri uri, Config config, String userName, Password password, DownloaderFactory downloaderFactory)
    {
        this.uri = uri;
        this.config = config;
        this.credential = new Credential(userName, password);        
        this.downloaderFactory = downloaderFactory;
    }

    public Credential getCredential()
    {
        return credential;
    }

    public Uri getUri()
    {
        return uri;
    }

    public Config getConfig()
    {
        return config;
    }
    
    public static String getHelpMessage() {
        String msg = "<protocol>://<host-name>/<file-name> <username(optional)> <password(optional)>";
        
        return msg;
    }

    public DownloaderFactory getDownloaderFactory()
    {
        return downloaderFactory;
    }
    
    public static class Builder {
        
        private String userName;
        private Password password;
        private final String address;
        private final String configFile;
        private Config config;
        private DownloaderFactory downloaderFactory;
        
        public static Builder newInstance(String address, String configFile) {            
            return new Builder(address, configFile, null);
        }
        
        public static Builder newInstance(String address, Config config) {            
            return new Builder(address, null, config);
        }
        
        private Builder(String address, String configFile, Config config) {
            this.address = address;
            this.configFile = configFile;
            this.config = config;
            downloaderFactory = null;
        }

        public Builder setUserName(String userName)
        {
            this.userName = userName;
            return this;
        }

        public Builder setPassword(Password password)
        {
            this.password = password;
            return this;
        }

        public Builder setDownloaderFactory(DownloaderFactory downloaderFactory)
        {
            this.downloaderFactory = downloaderFactory;
            return this;
        }                
        
        public CmRequestInfo build()
        {
            if(downloaderFactory == null) {
                downloaderFactory = new CmDownloaderFactory();
            }
            
            if(config == null) {
                ConfigGenerator configGenerator = new ConfigGenerator();
                config = configGenerator.generate(configFile);
            }
            else {
                ConfigValidator configValidator = new ConfigValidator(this.config);
                configValidator.validate();
            }
            
            UriGenerator uriGenerator = new UriGenerator(downloaderFactory);            
            Uri uri = uriGenerator.generate(address, config.getDownloadDir());                        
            
            return new CmRequestInfo(uri, config, userName, password, downloaderFactory);
        }
    
    }
}
