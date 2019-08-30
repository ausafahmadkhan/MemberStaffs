package com.example.Member.MemberRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequest
{
    private String teacherId;
    private String name;
    private String topicId;
}
