package com.example.Member.Security.Repository;

import com.example.Member.Security.Entity.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDAO, String>
{
    @Query(value = "{'userName' : ?0}")
    Optional<UserDAO> findByUserName(String userName);
}
