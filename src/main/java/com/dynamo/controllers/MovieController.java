package com.dynamo.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ErrorManager;

import com.dynamo.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Timed;
import org.springframework.web.bind.annotation.*;

import com.dynamo.models.Movie;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/movie")
public class MovieController<authTokenValue> {

    private static Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieService movieService;

    @GetMapping("/save-from-csv")
    public ResponseEntity<List<Movie>> saveAll(HttpServletResponse res) throws IOException {

        long startTime = System.currentTimeMillis();
        logger.info("save movies from csv to dynamoDb");
        List<Movie> result= (List<Movie>) movieService.loadDataInDynamoDb();
        long endTime = System.currentTimeMillis();

        calculateExecutionTime(startTime,endTime,res);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    private  void calculateExecutionTime(Long startTime, Long endTime, HttpServletResponse response){

        Long time=endTime-startTime;
        logger.debug("Execution Time {} ms ",time);
        response.addHeader("X-TIME-TO-EXECUTE",time.toString()+" ms");
    }

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
    @Timed(millis = 0L)
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

    @GetMapping
    public List<Movie> findMovieByDirector(@PathVariable(value = "director") String director , @RequestHeader(value = "X-Auth-Token", required = false) String authTokenValue, HttpServletResponse response){
        if (authenticateToken(authTokenValue)) {
        logger.info("find movie by id" + this.getClass().getName());
        return movieService.findMovieByDirector("director");
        } else {
            Map<Object,Object> exception = new HashMap<Object,Object>();
            exception.put("code", "401");
            exception.put("reason", "UNAUTHORIZED");
            return (List<Movie>) exception;
        }
    }
    @GetMapping
    public Map<Object, Object> findEnglishTitlesByReviews(@PathVariable(value = "totalReviews") String totalReviews , @RequestHeader(value = "X-Auth-Token", required = false) String authTokenValue, HttpServletResponse response) {
        if (authenticateToken(authTokenValue)) {
        logger.info("find English title movies with more than" + totalReviews+ "Reviews" + this.getClass().getName());
        return movieService.findEnglishTitlesByReviews("totalReviews");
        } else {
            Map<Object,Object> exception = new HashMap<Object,Object>();
            exception.put("code", "401");
            exception.put("reason", "UNAUTHORIZED");
            return exception;
        }
    }
    @GetMapping
    public Map<Object,Object> findMovieWithHighestBudget(@PathVariable(value = "publishYear") String publishYear, @RequestHeader(value = "X-Auth-Token", required = false) String authTokenValue, HttpServletResponse response) {
        if (authenticateToken(authTokenValue)) {
            logger.info("find movie with highest budget" + this.getClass().getName());
            return movieService.findMovieWithHighestBudget("publishYear");
        } else {
            Map<Object,Object> exception = new HashMap<Object,Object>();
            exception.put("code", "401");
            exception.put("reason", "UNAUTHORIZED");
            return exception;
        }
    }

    private boolean authenticateToken(String token) {
        logger.info("Authenticating token...");
        if(token!=null && token.equals("HashedIn")){
            logger.info("Token Authentication Successful.");
            return true;
        }
        else {
            logger.info("Token Authentication Failed, rejecting the request.");
            return false;
        }
    }
}









