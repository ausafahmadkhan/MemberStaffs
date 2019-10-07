package com.example.Member.Security.Service;

import com.example.Member.Persistence.Models.UserDAO;
import com.example.Member.Persistence.Repository.UserRepository;
import com.example.Member.Security.Entity.MyUserDetails;
import com.example.Member.Security.Enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService
{
    private static Logger logger = LogManager.getLogger(MyUserDetailService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        logger.info("userName : {}", userName);
        Optional<UserDAO> userDAO = userRepository.findByUserName(userName);
        logger.info("User Fetched from db : {}", userDAO);
        userDAO.orElseThrow(() -> new UsernameNotFoundException("User not present"));
        return new MyUserDetails(userDAO.get());
    }
}
