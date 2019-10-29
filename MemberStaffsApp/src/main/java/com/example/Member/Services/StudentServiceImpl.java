package com.example.Member.Services;

import com.example.Material.MaterialResponses.TopicResponse;
import com.example.Member.Client.StudyMaterialClient.StudyMaterialClient;
import com.example.Member.MemberRequest.StudentRequest;
import com.example.Member.MemberResponse.ResponseModel;
import com.example.Member.MemberResponse.StudentResponse;
import com.example.Member.MemberResponse.TeacherResponse;
import com.example.Member.Persistence.Models.StudentDAO;
import com.example.Member.Persistence.Models.TeacherDAO;
import com.example.Member.Persistence.Repository.StudentRepository;
import com.example.Member.Persistence.Repository.TeacherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentServiceImpl implements StudentService
{
    private static Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudyMaterialClient studyMaterialClient;

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
        logger.info("Added student : {}", studentDAO);
        return CompletableFuture.completedFuture(studentResponse);
    }

    @Override
    @Async
    public CompletableFuture<StudentResponse> enrollStudent(String studentId, String topicId) throws Exception
    {
        StudentDAO studentDAO = studentRepository.findById(studentId).orElse(null);
        if (studentDAO.getTopicsEnrolled() == null) {
            List<TopicResponse> topics = new ArrayList<>();
            studentDAO.setTopicsEnrolled(topics);
        }
        TeacherDAO teacherDAO = teacherRepository.searchByTopicId(topicId);
        logger.info("TeacherDAO : {}", teacherDAO);
        if (studentDAO.getTeachersAssigned() == null) {
            List<TeacherDAO> teachers = new ArrayList<>();
            studentDAO.setTeachersAssigned(teachers);
        }
        studentDAO.getTeachersAssigned().add(teacherDAO);

        Call<ResponseModel<TopicResponse>> topicResponseModelCall = studyMaterialClient.getTopic(topicId);
        topicResponseModelCall.enqueue(
                new Callback<ResponseModel<TopicResponse>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<TopicResponse>> call, Response<ResponseModel<TopicResponse>> response) {
                        try
                        {
                            ResponseModel<TopicResponse> responseModel =  response.body();
                            logger.info("Response Model: {}", responseModel);
                            TopicResponse topicResponse = responseModel.getData();
                            List<TopicResponse> topicsEnrolled = studentDAO.getTopicsEnrolled();

                            if (topicsEnrolled == null || topicsEnrolled.isEmpty())
                            {
                                studentDAO.setTopicsEnrolled(new ArrayList<TopicResponse>());
                            }

                            studentDAO.getTopicsEnrolled().add(topicResponse);
                            logger.info("Student DAO : {}", studentDAO);
                            studentRepository.save(studentDAO);
                        }
                        catch(Exception e)
                        {
                            logger.error("Exception occured : {}", e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseModel<TopicResponse>> call, Throwable throwable) {

                    }
                }
        );

        logger.info("StudentDAO : {}", studentDAO);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStudentId(studentDAO.getStudentId());
        studentResponse.setName(studentDAO.getName());
        studentResponse.setTopicsEnrolled(studentDAO.getTopicsEnrolled());
        logger.info("Enrolled Student : {}", studentDAO);
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
    public CompletableFuture<StudentResponse> getStudent(String studentId) throws IllegalAccessException {
        StudentResponse studentResponse = new StudentResponse();
        StudentDAO studentDAO = studentRepository.findById(studentId).orElse(null);
        studentResponse.setStudentId(studentDAO.getStudentId());
        studentResponse.setName(studentDAO.getName());
        if (studentDAO.getTopicsEnrolled() == null) {
            logger.error("Student not enrolled in any course");
            throw new IllegalAccessException("NOT ENROLLED IN ANY COURSE");
        }
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
        logger.info("Student info : {}", studentResponse);

        return CompletableFuture.completedFuture(studentResponse);
    }
}
