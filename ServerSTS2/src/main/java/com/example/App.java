package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class App  {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}


// src/mail/resourceのschema.sql に自動でテーブルを作る仕組みあり。