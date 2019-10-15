package com.example.Member.Persistence.Repository;

import com.example.Member.Persistence.Models.TeacherDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TeacherRepository extends MongoRepository<TeacherDAO, String>
{
    @Query(value = "{'topicId' : ?0}", fields = "{'topicId' : 0}")
    public TeacherDAO searchByTopicId(String topicId);
}
