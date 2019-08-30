package com.example.Member.MemberResponse;

import com.example.Material.MaterialResponses.TopicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse
{
    private String studentId;
    private String name;
    private List<TopicResponse> topicsEnrolled;
    private List<TeacherResponse> teachersAssigned;
}
