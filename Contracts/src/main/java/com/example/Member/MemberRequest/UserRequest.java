package com.example.Member.MemberRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest
{
    @NotBlank
    @Size(max = 10)
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    @Field("true")
    private Boolean isActive;

    @NotNull
    private List<Role> roles;
}
