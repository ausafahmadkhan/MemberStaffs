package com.example.Member.Security.Enum;


public enum Role
{
    ADMIN("ADMIN"),
    STUDENT("STUDENT");

    private String value;

    public String getValue()
    {
        return value;
    }

    Role(String role)
    {
        this.value = role;
    }
}
