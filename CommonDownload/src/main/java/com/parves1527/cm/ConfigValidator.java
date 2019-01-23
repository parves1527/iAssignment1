package com.parves1527.cm;

import java.io.File;

class ConfigValidator
{
    private final Config config;

    public ConfigValidator(Config config)
    {
        this.config = config;
    }
    
    public void validate()
    {
        checkTempDir();
        checkDownloadDir();
        checkTimeout();
    }
    
    private void checkTempDir() {
        String filePath = config.getTempDir();
        File file = new File(filePath);
        
        if(!file.exists()) {
            throw new RequestException(ConfigKey.temporaryDirectory + " has invalid file path");
        }
    }
    
    private void checkDownloadDir() {
        
        String filePath = config.getDownloadDir();
        File file = new File(filePath);
        
        if(!file.exists()) {
            throw new RequestException(ConfigKey.downloadDirectory + " has invalid file path");
        }        
    }
    
    private void checkTimeout() {
        
        if(config.getTimeout() <= 0) {
            throw new RequestException(ConfigKey.requestTimeout + " value needed be postive integer");
        }
    }
}
