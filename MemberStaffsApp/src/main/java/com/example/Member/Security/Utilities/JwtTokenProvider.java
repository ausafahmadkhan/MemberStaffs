package com.example.Member.Security.Utilities;

import com.example.Member.Persistence.Models.Security.JwtToken;
import com.example.Member.Persistence.Models.Security.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider
{
    @Value("${app.secretKey}")
    private String secretKey;

    @Value("${app.expiration}")
    private Long expirationMilliS;

    public JwtToken generateToken(Authentication authentication)
    {
        JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();

        Date date = new Date();

        Date expiration = new Date(date.getTime() + expirationMilliS);

        Claims claims = Jwts.claims()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiration);

       String token =  Jwts.builder()
                            .setClaims(claims)
                            .signWith(SignatureAlgorithm.HS512, secretKey)
                            .compact();

        return new JwtToken(token);
    }

    public Boolean isVaildToken(String token)
    {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public String getUserNameFromJwt(String token)
    {
        return Jwts.parser()
                          .setSigningKey(secretKey)
                          .parseClaimsJws(token)
                          .getBody()
                          .getSubject();
    }

}
