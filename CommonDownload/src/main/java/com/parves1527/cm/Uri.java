package com.parves1527.cm;

public class Uri
{
    private final static int DEFAULT_PORT = -1;    
    
    private final String protocol;
    private final String host;    
    private final String remoteResource;
    private final String localResource;
    
    private int port = DEFAULT_PORT;    

    public Uri(String protocol, String host, String remoteResource, String localResource)
    {     
        this.protocol = protocol;
        this.host = host;
        this.remoteResource = remoteResource;
        this.localResource = localResource;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public String getRemoteResource()
    {
        return remoteResource;
    }

    public String getLocalResource()
    {
        return localResource;
    }    

    public void setPort(int port)
    {
        this.port = port;
    }
    
    public static int getDEFAULT_PORT()
    {
        return DEFAULT_PORT;
    }
    
    @Override
    public String toString()
    {
        return "Uri{" + "protocol=" + protocol + ", host=" + host + ", port=" + port + ", remoteResource=" + remoteResource + ", localResource=" + localResource + '}';
    }
}
