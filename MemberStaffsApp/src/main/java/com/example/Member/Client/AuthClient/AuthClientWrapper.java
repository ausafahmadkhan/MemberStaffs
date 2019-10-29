package com.example.Member.Client.AuthClient;

import com.example.AuthDemo.AuthResponse.AuthPublicCert;
import com.example.AuthDemo.AuthResponse.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

@Component
public class AuthClientWrapper
{
    @Autowired
    private AuthClient authClient;

    @Cacheable(value = "publicKey")
    public AuthPublicCert getCachedPublicKey() throws Exception
    {
        try {
            Call<ResponseModel<AuthPublicCert>> responseModelCall = authClient.getPublicKey();
            Response<ResponseModel<AuthPublicCert>> authPublicCertResponse = responseModelCall.execute();
            if (authPublicCertResponse.isSuccessful()) {
                ResponseModel<AuthPublicCert> authPublicCertResponseModel = authPublicCertResponse.body();
                return authPublicCertResponseModel.getData();
            } else {
                throw new InternalError("Could not get public key");
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

