package com.example.testtask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application{

//    @Autowired
//    ApplicationContextProvider applicationContextProvider;

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public Test test() {
//        return new Test();
//    }
//
//    @Override
//    public void run(String...args) throws Exception {
//    }
}
