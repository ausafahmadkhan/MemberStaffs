package com.example.Member.Services;

import com.example.Member.MemberRequest.StudentRequest;
import com.example.Member.MemberResponse.StudentResponse;

import java.util.concurrent.CompletableFuture;

public interface StudentService
{
    public CompletableFuture<StudentResponse> addStudent(StudentRequest studentRequest);
    public CompletableFuture<StudentResponse> enrollStudent(String studentId, String topicId);
    public CompletableFuture<StudentResponse> getStudent(String studentId);
}
