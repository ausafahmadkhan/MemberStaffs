package com.example.Member.Services.Security;

import com.example.Member.MemberRequest.UserRequest;
import com.example.Member.MemberResponse.UserResponse;
import com.example.Member.Persistence.Models.Security.JwtUserDetails;
import com.example.Member.Persistence.Models.Security.UserDAO;
import com.example.Member.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<UserDAO> userDAO = userRepository.findByUserName(username);
        userDAO.orElseThrow(() -> new UsernameNotFoundException("User not present"));
        System.out.println(userDAO.get().getPassword());
        return new JwtUserDetails(userDAO.get());
    }

    public UserResponse createUser(UserRequest userRequest)
    {
        UserDAO userDAO = new UserDAO();
        userDAO.setUsername(userRequest.getUsername());
        userDAO.setPassword(userRequest.getPassword());
        userDAO.setIsActive(userRequest.getIsActive());
        userDAO.setRoles(userRequest.getRoles());
        userRepository.save(userDAO);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(userRequest.getUsername());
        userResponse.setPassword(userRequest.getPassword());
        userResponse.setIsActive(userRequest.getIsActive());
        userResponse.setRoles(userRequest.getRoles());

        return userResponse;
    }
}
