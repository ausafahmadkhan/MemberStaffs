package com.example.Member.Services;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class JwtTokenService
{
    @Autowired
    private PublicKeyService publicKeyService;

    public String getToken(String bearerToken)
    {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    public boolean isValidToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (StringUtils.hasText(token))
        {
            String pubKey = publicKeyService.getCachedPublicKey().getPublicKey();
            byte[] bytes = Base64.getDecoder().decode(pubKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            try {
                Jwts
                        .parser()
                        .setSigningKey(publicKey)
                        .parseClaimsJws(token);
            }
            catch (Exception e)
            {
                return false;
            }
            return  true;
        }
        return false;
    }

    public String getUsernameFromJwt(String token) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String pubKey = publicKeyService.getCachedPublicKey().getPublicKey();
        byte[] bytes = Base64.getDecoder().decode(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        String username = "";
        try {
                username = Jwts
                                    .parser()
                                    .setSigningKey(publicKey)
                                    .parseClaimsJws(token)
                                    .getBody()
                                    .get("username").toString();
        }
        catch (Exception e)
        {
            System.out.println("Not a valid Jwt");
        }

        return username;
    }
}
