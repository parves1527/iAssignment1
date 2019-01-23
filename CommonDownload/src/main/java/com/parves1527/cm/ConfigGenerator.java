package com.parves1527.cm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class ConfigGenerator
{
        
    public Config generate(String fileName)
    {
        basicFileCheck(fileName);
        
        Properties prop = new Properties();

        Config config = new Config();
        
        try(InputStream input = new FileInputStream(fileName))
        {
            prop.load(input);
            
            basicPropertyCheck(prop, fileName);

            updateConfig(config, prop);            
            
        } catch (IOException ex)
        {
            throw new RequestException(fileName + " is not a vaild properties file");
        } 
        
        return config;
    }
    
    private String getTempDir(Properties prop) {
        String filePath = prop.getProperty(ConfigKey.temporaryDirectory);
        File file = new File(filePath);
        
        if(!file.exists()) {
            throw new RequestException(ConfigKey.temporaryDirectory + " has invalid file path");
        }
        
        return filePath;
    }
    
    private String getDownloadDir(Properties prop) {
        
        String filePath = prop.getProperty(ConfigKey.downloadDirectory);
        File file = new File(filePath);
        
        if(!file.exists()) {
            throw new RequestException(ConfigKey.downloadDirectory + " has invalid file path");
        }
        
        return filePath;
    }
    
    private int getTimeout(Properties prop) {
        
        int i = -1;
        try
        {
            i = Integer.parseInt(prop.getProperty(ConfigKey.requestTimeout));
        }
        catch(Exception ex){}
        
        if(i <= 0) {
            throw new RequestException(ConfigKey.requestTimeout + " value needed be postive integer");
        }
        
        return i;
    }
    
    private boolean getHostVerification(Properties prop) {
                
        try
        {
            return Boolean.parseBoolean(prop.getProperty(ConfigKey.hostVerification));
        }
        catch(Exception ex)
        {
            throw new RequestException(ConfigKey.hostVerification + " Supported value: true/false");
        }
    }        

    private void basicFileCheck(String fileName)
    {
        if(fileName == null || fileName.isEmpty()) {
            throw new RequestException("Config file name can't be null or empty");
        }
        
        File file = new File(fileName);
        
        if(!file.exists())
        {
            throw new RequestException("File " + fileName + " not found");
        }
    }

    private void updateConfig(Config config, Properties prop)
    {
        String tempDir = getTempDir(prop);
        String downloadDir = getDownloadDir(prop);
        
        if(tempDir.equals(downloadDir)) {
            throw new RequestException(ConfigKey.temporaryDirectory + " and " + ConfigKey.downloadDirectory + " can't be same");
        }
        
        config.setTempDir(tempDir);
        config.setDownloadDir(downloadDir);
        config.setTimeout(getTimeout(prop));
        config.setVerifyHost(getHostVerification(prop));
    }

    private void basicPropertyCheck(Properties prop, String fileName)
    {
        String propertieNames[] = {ConfigKey.temporaryDirectory, ConfigKey.downloadDirectory, ConfigKey.requestTimeout, ConfigKey.hostVerification};

        for(String propertyName : propertieNames)
        {
            if(!prop.containsKey(propertyName))
            {
                throw new RequestException(propertyName + " not found in " + fileName);
            }
        }
    }
}
