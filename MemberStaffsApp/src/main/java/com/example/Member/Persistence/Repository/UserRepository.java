package com.example.Member.Persistence.Repository;

import com.example.Member.MemberResponse.UserResponse;
import com.example.Member.Persistence.Models.Security.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDAO, String>
{
    @Query("{username : ?0}")
    Optional<UserDAO> findByUserName(String userName);
}
