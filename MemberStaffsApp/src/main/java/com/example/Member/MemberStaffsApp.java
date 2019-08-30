package com.example.Member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
public class MemberStaffsApp
{
    @Bean
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }

    public static void main(String args[])
    {
        SpringApplication.run(MemberStaffsApp.class, args);
    }
}
