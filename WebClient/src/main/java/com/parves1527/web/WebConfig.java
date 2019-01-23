package com.parves1527.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(value= {"classpath:config.properties"})
public class WebConfig
{    
    @Value("${temporaryDirectory}")
    private String temporaryDirectory;
    
    @Value("${downloadDirectory}")
    private String downloadDirectory;
    
    @Value("${requestTimeout}")
    private int requestTimeout;
    
    @Value("${hostVerification}")
    private boolean hostVerification;

    public String getTemporaryDirectory()
    {
        return temporaryDirectory;
    }

    public String getDownloadDirectory()
    {
        return downloadDirectory;
    }

    public int getRequestTimeout()
    {
        return requestTimeout;
    }

    public boolean isHostVerification()
    {
        return hostVerification;
    }    
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() 
    {
        return new PropertySourcesPlaceholderConfigurer();
    }    
}
