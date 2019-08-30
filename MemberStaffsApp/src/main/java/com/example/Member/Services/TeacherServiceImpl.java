package com.example.Member.Services;

import com.example.Member.MemberRequest.TeacherRequest;
import com.example.Member.MemberResponse.TeacherResponse;
import com.example.Member.Persistence.Models.TeacherDAO;
import com.example.Member.Persistence.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;

@Service
public class TeacherServiceImpl implements TeacherService
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    @Async
    public CompletableFuture<TeacherResponse> addTeacher(TeacherRequest teacherRequest)
    {
        TeacherDAO teacherDAO = new TeacherDAO();
        teacherDAO.setName(teacherRequest.getName());
        teacherDAO.setTeacherId(teacherRequest.getTeacherId());
        teacherDAO.setTopicId(teacherRequest.getTopicId());
        teacherRepository.save(teacherDAO);
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setTeacherId(teacherDAO.getTeacherId());
        teacherResponse.setName(teacherDAO.getName());
        teacherResponse.setTopicId(teacherDAO.getTopicId());

        return CompletableFuture.completedFuture(teacherResponse);

    }

    @Override
    @Async
    public CompletableFuture<TeacherResponse> getTeacher(String teacherId)
    {
        TeacherResponse teacherResponse = new TeacherResponse();
        TeacherDAO teacherDAO = teacherRepository.findById(teacherId).orElse(null);
        teacherResponse.setTeacherId(teacherDAO.getTeacherId());
        teacherResponse.setName(teacherDAO.getName());
        teacherResponse.setTopicId(teacherDAO.getTopicId());

        return CompletableFuture.completedFuture(teacherResponse);
    }
}
