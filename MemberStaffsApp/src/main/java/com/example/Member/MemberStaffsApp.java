package com.example.Member;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class MemberStaffsApp
{
    private static Logger logger = LogManager.getLogger(MemberStaffsApp.class);
    public static void main(String args[])
    {
        logger.info("Starting MemberStaff App");
        SpringApplication.run(MemberStaffsApp.class, args);
    }
}
