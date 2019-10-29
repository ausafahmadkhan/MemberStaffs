package com.example.Member.Client.AuthClient;

import com.example.AuthDemo.AuthResponse.AuthPublicCert;
import com.example.AuthDemo.AuthResponse.ResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthClient
{
    @GET("/auth/getPublicKey")
    Call<ResponseModel<AuthPublicCert>> getPublicKey();
}
