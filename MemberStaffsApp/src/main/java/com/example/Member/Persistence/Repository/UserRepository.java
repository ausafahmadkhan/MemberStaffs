package com.example.Member.Persistence.Repository;

import com.example.Member.Persistence.Models.UserRoleDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserRoleDAO, String>
{
    @Query("{'username' : ?0}")
    Optional<UserRoleDAO> findUserByUserName(String username);
}
