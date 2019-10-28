package com.example.Member.MemberRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequest
{
    @NotBlank
    private String teacherId;

    @NotBlank
    private String name;

    @NotBlank
    private String topicId;
}
