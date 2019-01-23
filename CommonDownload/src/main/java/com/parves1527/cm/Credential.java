package com.parves1527.cm;

public class Credential
{
    private final String userName;
    private final Password password;

    public Credential(String userName, Password password)
    {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public Password getPassword()
    {
        return password;
    }
    
    @Override
    public String toString()
    {
        return "Credential{" + "userName=" + userName + ", password=" + password + '}';
    }        
}
