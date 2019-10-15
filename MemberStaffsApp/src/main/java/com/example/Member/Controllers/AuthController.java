package com.example.Member.Controllers;

import com.example.Member.MemberRequest.UserRequest;
import com.example.Member.MemberResponse.ResponseModel;
import com.example.Member.MemberResponse.UserResponse;
import com.example.Member.Persistence.Models.Security.JwtToken;
import com.example.Member.Persistence.Models.Security.JwtUserDetails;
import com.example.Member.Security.Utilities.JwtTokenProvider;
import com.example.Member.Services.Security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/User")
public class AuthController
{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/auth/getToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CompletableFuture<ResponseEntity<ResponseModel<JwtToken>>> generateToken(@Valid @RequestBody UserRequest userRequest)
    {
        System.out.println("Before up auth");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequest.getUsername(),userRequest.getPassword());
        System.out.println(authenticationToken.toString());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        System.out.println("Authentication : " + authentication + "Authenticated ?? " + authentication.isAuthenticated());
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return CompletableFuture.completedFuture(ResponseEntity.ok(new ResponseModel<JwtToken>(jwtToken)));

    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CompletableFuture<ResponseEntity<ResponseModel<UserResponse>>> createUser(@Valid @RequestBody UserRequest userRequest)
    {
          UserResponse userResponse =  jwtUserDetailsService.createUser(userRequest);
          return CompletableFuture.completedFuture(ResponseEntity.ok(new ResponseModel<UserResponse>(userResponse)));
    }

}
