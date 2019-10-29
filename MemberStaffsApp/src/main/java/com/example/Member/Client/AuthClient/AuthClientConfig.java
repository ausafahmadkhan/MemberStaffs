package com.example.Member.Client.AuthClient;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class AuthClientConfig
{
    private Retrofit retrofit;

    public AuthClientConfig()
    {
        this.retrofit = new Retrofit.Builder()
                            .baseUrl("http://localhost:8083")
                            .client(new OkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
    }

    @Bean
    public AuthClient getAuthClient()
    {
        return retrofit.create(AuthClient.class);
    }
}
