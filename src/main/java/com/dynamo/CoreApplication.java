package com.dynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.dynamo.models.Movie;
import com.dynamo.repositories.MovieRepository;

@SpringBootApplication
public class CoreApplication{

	@Autowired
    private MovieRepository movieRepository;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
