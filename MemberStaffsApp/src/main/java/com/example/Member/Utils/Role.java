package com.example.Member.Utils;

public enum Role
{
    STUDENT("STUDENT"),
    ADMIN("ADMIN");

    private String value;

    Role(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
