package com.example.Member.Services;

import com.example.Member.Persistence.Models.UserRoleDAO;
import com.example.Member.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public UserRoleDAO getUserByUserName(String username) throws BadCredentialsException
    {
        Optional<UserRoleDAO> userDAO = userRepository.findUserByUserName(username);
        userDAO.orElseThrow(() -> new BadCredentialsException("User not present"));
        return userDAO.get();
    }
}
