package com.example.Member.Services;

import com.example.Member.MemberRequest.TeacherRequest;
import com.example.Member.MemberResponse.TeacherResponse;

import java.util.concurrent.CompletableFuture;

public interface TeacherService
{
    public CompletableFuture<TeacherResponse> addTeacher(TeacherRequest teacherRequest);
    public CompletableFuture<TeacherResponse> getTeacher(String teacherId);
}
