package com.example.Member.Services;

import com.example.AuthDemo.AuthResponse.AuthPublicCert;
import com.example.AuthDemo.AuthResponse.ResponseModel;
import com.example.Member.Client.AuthClient.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class PublicKeyService
{
    @Autowired
    private AuthClient authClient;

    @Cacheable(value = "PublicKey")
    public AuthPublicCert getCachedPublicKey()
    {
        final AuthPublicCert[] authPublicCert = new AuthPublicCert[1];
        Call<ResponseModel<AuthPublicCert>> authClientPublicKey = authClient.getPublicKey();

        authClientPublicKey.enqueue(new Callback<ResponseModel<AuthPublicCert>>()
        {
            @Override
            public void onResponse(Call<ResponseModel<AuthPublicCert>> call, Response<ResponseModel<AuthPublicCert>> response)
            {
                assert response.body() != null;
                authPublicCert[0] = response.body().getData();
            }

            @Override
            public void onFailure(Call<ResponseModel<AuthPublicCert>> call, Throwable throwable)
            {
                try {
                    throw new Exception("Could not get Public Key");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        return authPublicCert[0];
    }
}

