package com.example.Member.Persistence.Repository;

import com.example.Member.Persistence.Models.StudentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<StudentDAO, String> {
}
