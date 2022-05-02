package com.dynamo.controllers;

import java.util.List;

import com.dynamo.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dynamo.models.Movie;
import com.dynamo.repositories.MovieRepository;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private static Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieService movieService;

    @PostMapping
    public Movie save(@RequestBody Movie movie){
        logger.info("save movie " + this.getClass().getName());
        return movieService.save(movie);
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable(value = "id") String id){
        logger.info("find movie by id" + this.getClass().getName());
        return movieService.findById(id);
    }

    @GetMapping
    public List<Movie> findAll(){
        logger.info("findAll movies " + this.getClass().getName());
        return movieService.findAll();
    }

    @PutMapping("/{id}")
    public String update(@PathVariable(value = "id") String id,
    @RequestBody Movie movie){
        logger.info("update movie " + this.getClass().getName());
        return movieService.update(id, movie);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") String id){
        logger.info("Edit Configurations… movie " + this.getClass().getName());
        return movieService.delete(id);
    }
}