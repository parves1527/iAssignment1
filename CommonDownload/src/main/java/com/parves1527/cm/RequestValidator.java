package com.parves1527.cm;

import java.net.MalformedURLException;
import java.net.URL;

class RequestValidator
{

    public RequestValidator()
    {
    }
    
    public String getValidProtocol(String address, DownloaderFactory downloaderFactory)
    {
        String protocol = getProtocol(address);
        
        if(protocol == null || protocol.isEmpty())
        {
            throw new RequestException("No protocol found in address");
        }
        
        if(!downloaderFactory.getSupportedProtocol().contains(protocol))
        {
            throw new RequestException(protocol + " is not supported protcol");
        }
        
        String modifiedAddress = address.replaceAll(protocol + "://", "http://");
        
        try
        {
            URL url = new URL(modifiedAddress);
        } catch (MalformedURLException ex)
        {
            throw new RequestException("Address is not in url format");
        }
        
        return protocol;
    }    
    
    private String getProtocol(String address)
    {
        int index = address.indexOf(':');
        if(index <= 0) {
            return null;
        }
        
        return address.substring(0, index);
    }                
}
