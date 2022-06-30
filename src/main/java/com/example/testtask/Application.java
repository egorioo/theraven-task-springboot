package com.example.testtask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application implements CommandLineRunner{

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String...args) throws Exception {
    }
}
