package com.example.Member.Security.Filter;

import com.example.Member.Persistence.Models.UserDAO;
import com.example.Member.Services.JwtTokenService;
import com.example.Member.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        String token = jwtTokenService.getToken(bearerToken);
        try {
            if (jwtTokenService.isValidToken(token))
            {
                String username = jwtTokenService.getUsernameFromJwt(token);
                UserDAO userDAO = userService.getUserByUserName(username);
                List<GrantedAuthority> authorities = userDAO.getRoles()
                                                    .stream()
                                                    .map(role -> new SimpleGrantedAuthority(role.getValue()))
                                                    .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDAO.getUsername(), userDAO.getPassword(), authorities);
                authenticationToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            else
            {
                System.out.println("Invalid token");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
