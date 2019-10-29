package com.example.Member.Services;

import com.example.AuthDemo.AuthResponse.AuthPublicCert;
import com.example.Member.Client.AuthClient.AuthClientWrapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class JwtTokenService
{
    @Autowired
    private AuthClientWrapper authClientWrapper;

    public String getToken(String bearerToken)
    {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    public Claims validateToken(String token) throws Exception {
        if (StringUtils.hasText(token))
        {
            AuthPublicCert authPublicCert = authClientWrapper.getCachedPublicKey();
            String pubKey = authPublicCert.getPublicKey();
            byte[] bytes = Base64.getDecoder().decode(pubKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            try
            {
                return Jwts
                            .parser()
                            .setSigningKey(publicKey)
                            .parseClaimsJws(token)
                            .getBody();
            }
            catch (Exception e)
            {
                return null;
            }
        }
        return null;
    }
}
