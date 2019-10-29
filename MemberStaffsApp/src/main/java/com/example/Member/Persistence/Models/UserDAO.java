package com.example.Member.Persistence.Models;

import com.example.Member.Utils.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class UserDAO
{
    private String id;
    private String username;
    private String password;
    private String email;
    private List<Role> roles;
}
