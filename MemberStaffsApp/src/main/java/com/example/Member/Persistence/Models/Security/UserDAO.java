package com.example.Member.Persistence.Models.Security;

import com.example.Member.MemberRequest.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("User")
public class UserDAO
{
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Field(value = "true")
    private Boolean isActive;

    @NotNull
    private List<Role> roles;
}
