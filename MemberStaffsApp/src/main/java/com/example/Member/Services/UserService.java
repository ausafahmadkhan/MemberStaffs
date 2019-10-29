package com.example.Member.Services;

import com.example.Member.Persistence.Models.UserDAO;
import com.example.Member.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public UserDAO getUserByUserName(String username)
    {
        return userRepository.getUserByUserName(username)
                                     .orElseThrow(() -> new BadCredentialsException("Invalid Username/Email"));
    }
}
