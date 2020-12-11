package com.sgs.assign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AssignApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignApplication.class, args);
    }

}
