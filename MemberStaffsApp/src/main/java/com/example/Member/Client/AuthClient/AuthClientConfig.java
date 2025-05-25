package com.example.Member.Client.AuthClient;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class AuthClientConfig
{
    private static final String BASE_URL = "http://localhost:8083";
    private final Retrofit retrofit;

    public AuthClientConfig()
    {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    request = request.newBuilder().addHeader("Authentication", "Bearer abcd...")
                            .build();
                    return chain.proceed(request);
                })
                .build();

        this.retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
    }

    @Bean
    public AuthClient getAuthClient()
    {
        return retrofit.create(AuthClient.class);
    }
}
