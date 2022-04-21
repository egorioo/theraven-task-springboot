package com.example.testtask;

import com.example.testtask.entity.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();
		var a = localDateTime.toEpochSecond(ZoneOffset.UTC);
	}
}
