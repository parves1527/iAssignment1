package com.parves1527.client;

public class Main
{
    public static void main(String[] args)
    {
        String fileName = ((args.length == 0) ? "config.properties" : args[1]);
        
        ConsoleClient client = new ConsoleClient();
        
        client.start(fileName);
    }          
}
