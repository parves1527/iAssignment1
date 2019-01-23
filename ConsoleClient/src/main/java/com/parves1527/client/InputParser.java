package com.parves1527.client;

import com.parves1527.cm.CmRequestInfo;
import com.parves1527.cm.Password;

public class InputParser
{
    public InputParser()
    {
    }
            
    public CmRequestInfo parse(String input, String configFile) {

        String parts[] = input.split(" ");
        
        if(parts.length == 0) {
            System.err.println("No uri found");
            return null;
        }
        
        CmRequestInfo.Builder builder = CmRequestInfo.Builder.newInstance(parts[0], configFile);
        
        if(parts.length > 1) {
            //userName setting is optional as it depend on protocol
            builder.setUserName(parts[1]);
        }
        if(parts.length > 2) {
            //password setting is optional as it depend on protocol
            builder.setPassword(new Password(parts[2]));
        }
        
        //Downloader Factory setting is optional. Otherwise default factory will be used
        builder.setDownloaderFactory(new DemoDownloaderFactory());
        
        return builder.build();
    }
}
