package com.example.Member.Security.Entity;

import com.example.Member.Security.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "UserDAO")
public class UserDAO
{
    @Id
    private String id;
    private String userName;
    private String password;
    private Boolean isActive;
    private Role role;
}