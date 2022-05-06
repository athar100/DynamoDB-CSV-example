package com.dynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.dynamo.repositories.RepositoryMovie;

@SpringBootApplication
public class ApplicationService {

	@Autowired
    private RepositoryMovie repositoryMovie;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationService.class, args);
	}

}
