package com.example.Member.MemberRequest;

public enum Role
{
    ADMIN("ADMIN"),
    STUDENT("STUDENT");

    String value;

    Role(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
