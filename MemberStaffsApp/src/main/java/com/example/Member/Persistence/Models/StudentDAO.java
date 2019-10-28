package com.example.Member.Persistence.Models;

import com.example.Material.MaterialResponses.TopicResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class StudentDAO
{
    @Id
    @NotBlank
    private String studentId;

    @NotBlank
    private String name;
    private List<TopicResponse> topicsEnrolled;

    @DBRef
    private List<TeacherDAO> teachersAssigned;
}
