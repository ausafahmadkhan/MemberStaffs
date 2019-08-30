package com.example.Member.Services;

import com.example.Material.MaterialResponses.TopicResponse;
import com.example.Member.MemberRequest.StudentRequest;
import com.example.Member.MemberResponse.ResponseModel;
import com.example.Member.MemberResponse.StudentResponse;
import com.example.Member.MemberResponse.TeacherResponse;
import com.example.Member.Persistence.Models.StudentDAO;
import com.example.Member.Persistence.Models.TeacherDAO;
import com.example.Member.Persistence.Repository.StudentRepository;
import com.example.Member.Persistence.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentServiceImpl implements StudentService
{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Async
    public CompletableFuture<StudentResponse> addStudent(StudentRequest studentRequest)
    {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.setName(studentRequest.getName());
        studentDAO.setStudentId(studentRequest.getStudentId());
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setName(studentRequest.getName());
        studentResponse.setStudentId(studentRequest.getStudentId());
        studentRepository.save(studentDAO);
        return CompletableFuture.completedFuture(studentResponse);
    }

    @Override
    @Async
    public CompletableFuture<StudentResponse> enrollStudent(String studentId, String topicId)
    {
        String url = "http://localhost:8080/StudyMaterials/getTopic/"+topicId;
        ResponseModel<TopicResponse>  topicResponseModel = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseModel<TopicResponse>>() {}).getBody();
        TopicResponse topicResponse = topicResponseModel.getData();
        System.out.println(topicResponse);
        StudentDAO studentDAO = studentRepository.findById(studentId).orElse(null);
        if (studentDAO.getTopicsEnrolled() == null) {
            List<TopicResponse> topics = new ArrayList<>();
            studentDAO.setTopicsEnrolled(topics);
        }
        studentDAO.getTopicsEnrolled().add(topicResponse);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStudentId(studentDAO.getStudentId());
        studentResponse.setName(studentDAO.getName());
        studentResponse.setTopicsEnrolled(studentDAO.getTopicsEnrolled());
        TeacherDAO teacherDAO = teacherRepository.searchByTopicId(topicId);
        if (studentDAO.getTeachersAssigned() == null) {
            List<TeacherDAO> teachers = new ArrayList<>();
            studentDAO.setTeachersAssigned(teachers);
        }
        studentDAO.getTeachersAssigned().add(teacherDAO);
        studentRepository.save(studentDAO);
        List<TeacherDAO> teachers = studentDAO.getTeachersAssigned();
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (TeacherDAO t : teachers)
        {
            TeacherResponse teacherResponse = new TeacherResponse();
            teacherResponse.setTeacherId(t.getTeacherId());
            teacherResponse.setName(t.getName());
            teacherResponse.setTopicId(t.getTopicId());
            teacherResponses.add(teacherResponse);
        }
        studentResponse.setTeachersAssigned(teacherResponses);
        return CompletableFuture.completedFuture(studentResponse);
    }

    @Override
    @Async
    public CompletableFuture<StudentResponse> getStudent(String studentId)
    {
        StudentResponse studentResponse = new StudentResponse();
        StudentDAO studentDAO = studentRepository.findById(studentId).orElse(null);
        studentResponse.setStudentId(studentDAO.getStudentId());
        studentResponse.setName(studentDAO.getName());
        studentResponse.setTopicsEnrolled(studentDAO.getTopicsEnrolled());
        List<TeacherDAO> teacherDAOS = studentDAO.getTeachersAssigned();
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (TeacherDAO teacherDAO : teacherDAOS)
        {
            TeacherResponse t = new TeacherResponse();
            t.setName(teacherDAO.getName());
            t.setTeacherId(teacherDAO.getTeacherId());
            t.setTopicId(teacherDAO.getTopicId());
            teacherResponses.add(t);
        }

        studentResponse.setTeachersAssigned(teacherResponses);

        return CompletableFuture.completedFuture(studentResponse);
    }
}
