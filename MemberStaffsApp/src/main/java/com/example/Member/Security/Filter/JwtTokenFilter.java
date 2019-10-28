package com.example.Member.Security.Filter;

import com.example.Member.Persistence.Models.Security.JwtUserDetails;
import com.example.Member.Security.Utilities.JwtTokenProvider;
import com.example.Member.Services.Security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        String path = request.getServletPath();
        return path.startsWith("/User");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try
        {
            String bearerToken = httpServletRequest.getHeader("Authorization");
            String jwt = getJwtFromRequest(bearerToken);

            if (jwt == null || !jwtTokenProvider.isVaildToken(jwt))
                throw new IllegalArgumentException("Not a valid Token");
            else
            {
                String username = jwtTokenProvider.getUserNameFromJwt(jwt);
                JwtUserDetails jwtUserDetails = userDetailsService.loadUserByUsername(username);
                System.out.println(jwtUserDetails.getAuthorities());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtUserDetails, jwtUserDetails.getPassword(), jwtUserDetails.getAuthorities());
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                /*After setting the Authentication in the context, we'll now be able to check
                if the current user is authenticated – using securityContext.getAuthentication().isAuthenticated()*/
            }
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong : " + e.getMessage());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(String bearerToken)
    {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }
}
