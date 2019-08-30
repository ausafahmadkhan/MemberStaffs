package com.example.Member.Persistence.Models;

import com.example.Material.MaterialResponses.TopicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class StudentDAO
{
    @Id
    private String studentId;
    private String name;
    private List<TopicResponse> topicsEnrolled;

    @DBRef
    private List<TeacherDAO> teachersAssigned;
}
