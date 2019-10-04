package com.example.Member.Client;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class StudyMaterialClientConfig
{
    private Retrofit retrofit;

    public StudyMaterialClientConfig()
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        retrofit = new Retrofit.Builder()
                                .baseUrl("http://localhost:8080")
                                .client(okHttpClient)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
    }

    @Bean
    public StudyMaterialClient getStudyMaterialClient() {
        return retrofit.create(StudyMaterialClient.class);
    }
}
