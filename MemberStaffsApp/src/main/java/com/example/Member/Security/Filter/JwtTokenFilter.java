package com.example.Member.Security.Filter;

import com.example.Member.Persistence.Models.UserRoleDAO;
import com.example.Member.Services.JwtTokenService;
import com.example.Member.Services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        String token = jwtTokenService.getToken(bearerToken);
        try {
            Claims claims = jwtTokenService.validateToken(token);
            if (claims != null)
            {
                String username = claims.get("username").toString();
                UserRoleDAO userRoleDAO = userService.getUserByUserName(username);
                List<GrantedAuthority> authorities = userRoleDAO.getRoles()
                                                    .stream()
                                                    .map(role -> new SimpleGrantedAuthority(role.getValue()))
                                                    .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRoleDAO.getUsername(),null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            else
            {
                System.out.println("Invalid token");
            }
        }
        catch (BadCredentialsException e)
        {
            System.err.println("User not present");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
