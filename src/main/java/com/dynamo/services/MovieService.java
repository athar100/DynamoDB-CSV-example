package com.dynamo.services;

import com.dynamo.models.Movie;
import com.dynamo.repositories.RepositoryMovie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;



@Service
public class MovieService {

    private static Logger logger = LoggerFactory.getLogger(MovieService.class);

    // To Use CSVFileReaderService Data for all the services
    public ResponseEntity loadDataInDynamoDb() throws IOException {

        logger.info("saving movie " + this.getClass().getName());
        try{
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/Users/moathar/Downloads/movies.csv"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            String line="";
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] movie_row = line.split(",");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Movies updated in Database");
        }
        catch (IOException ioException){
            throw new IOException();
        }
    }

    @Autowired
    private RepositoryMovie repositoryMovie;

    public Movie save(Movie movie){
        logger.info("save movie " + this.getClass().getName());
        return repositoryMovie.save(movie);
    }

    public List<Movie> saveAll(List<Movie> movieList){
        logger.info("save movies " + this.getClass().getName());
        return  repositoryMovie.saveAll(movieList);
    }

    public Movie findById(String id){
        logger.info("find movie by id" + this.getClass().getName());
        return repositoryMovie.findById(id);
    }

    public List<Movie> findAll(){
        logger.info("findAll movies " + this.getClass().getName());
        return repositoryMovie.findAll();
    }

    public String update(String id, Movie movie){
        logger.info("update movie " + this.getClass().getName());
        return repositoryMovie.update(id, movie);
    }

    public String delete(String id){
        logger.info("Edit Configurations… movie " + this.getClass().getName());
        return repositoryMovie.delete(id);
    }

    public List<Movie> findMovieByDirector(String director) {
        logger.info("find movie by director" + this.getClass().getName());
        return repositoryMovie.findMovieByDirector(director);
    }
    public Map<Object, Object> findEnglishTitlesByReviews(String totalReviews) {
        logger.info("find movie by director" + this.getClass().getName());
        return repositoryMovie.findEnglishTitlesByReviews(totalReviews);
    }
    public Map<Object, Object> findMovieWithHighestBudget(String publishYear) {
        logger.info("find movie by director" + this.getClass().getName());
        return repositoryMovie.findMovieWithHighestBudget(publishYear);
    }


}
