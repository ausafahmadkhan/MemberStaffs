package com.example.Member.MemberResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse
{
    private String teacherId;
    private String name;
    private String topicId;
}
