package com.gnaderi.interview.cro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CroApplication {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(CroApplication.class, args);
    }
}
