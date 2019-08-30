package com.example.Member.MemberController;

import com.example.Member.MemberRequest.StudentRequest;
import com.example.Member.MemberRequest.TeacherRequest;
import com.example.Member.MemberResponse.ResponseModel;
import com.example.Member.MemberResponse.StudentResponse;
import com.example.Member.MemberResponse.TeacherResponse;
import com.example.Member.Services.StudentService;
import com.example.Member.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/MemberStaffs")
public class MemberController
{
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(path = "/addStudent", method = RequestMethod.POST, consumes = {"application/json"})
    public CompletableFuture<ResponseEntity<ResponseModel<StudentResponse>>> addStudent(@RequestBody StudentRequest studentRequest)
    {
        CompletableFuture<StudentResponse> studentResponse = studentService.addStudent(studentRequest);
        CompletableFuture<ResponseEntity<ResponseModel<StudentResponse>>> result = studentResponse.thenApply(t -> new ResponseEntity<ResponseModel<StudentResponse>>(new ResponseModel<StudentResponse>(t), HttpStatus.OK));
        return result;
    }

    @RequestMapping(path = "/enrollStudent/{studentId}/{topicId}", method = RequestMethod.GET, produces = {"application/json"})
    public CompletableFuture<ResponseEntity<ResponseModel<StudentResponse>>> enrollStudent(@PathVariable("topicId") String topicId, @PathVariable("studentId") String studentId)
    {
        CompletableFuture<StudentResponse> studentResponse = studentService.enrollStudent(studentId, topicId);
        CompletableFuture<ResponseEntity<ResponseModel<StudentResponse>>> result = studentResponse.thenApply(t -> new ResponseEntity<>(new ResponseModel(t), HttpStatus.OK));
        return result;
    }

    @RequestMapping(path = "/getStudent/{studentId}", method = RequestMethod.GET, produces = {"application/json"})
    public CompletableFuture<ResponseEntity<ResponseModel<StudentResponse>>> getStudent(@PathVariable("studentId") String studentId)
    {
        CompletableFuture<StudentResponse> studentResponse = studentService.getStudent(studentId);
        CompletableFuture<ResponseEntity<ResponseModel<StudentResponse>>> result = studentResponse.thenApply(t -> new ResponseEntity<>(new ResponseModel<>(t), HttpStatus.OK));
        return result;
    }

    @RequestMapping(path = "/addTeacher", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public CompletableFuture<ResponseEntity<ResponseModel<TeacherResponse>>> addTeacher(@RequestBody TeacherRequest teacherRequest)
    {
        CompletableFuture<TeacherResponse> teacherResponse = teacherService.addTeacher(teacherRequest);
        CompletableFuture<ResponseEntity<ResponseModel<TeacherResponse>>> result = teacherResponse.thenApply(t -> new ResponseEntity<>(new ResponseModel<>(t), HttpStatus.OK));
        return result;
    }
}
