package com.dynamo.repositories;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.dynamo.models.Movie;

@Repository
public class RepositoryMovie {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Movie save(Movie movie){
        dynamoDBMapper.save(movie);
        return movie;
    }

    public List<Movie> saveAll(List<Movie> movieList){
        dynamoDBMapper.batchSave(movieList);
        return movieList;
    }
    
    public Movie findById(String id){
       return dynamoDBMapper.load(Movie.class, id);
    }

    public List<Movie> findAll(){
        return dynamoDBMapper.scan(Movie.class, new DynamoDBScanExpression());
    }

    public String update(String id, Movie movie){
        dynamoDBMapper.save(movie,
                new DynamoDBSaveExpression()
        .withExpectedEntry("id",
                new ExpectedAttributeValue(
                        new AttributeValue().withS(id)
                )));
        return id;
    }

    public String delete(String id){
       Movie movie = dynamoDBMapper.load(Movie.class, id);
        dynamoDBMapper.delete(movie);
        return "Movie deleted successfully:: "+id;
    }

    //Filtering and returning as per requirement 1
    public List<Movie> findMovieByDirector(String director) {
        /*DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withIndexName(director)
                .withConsistentRead(false);
        List<Movie> movieList =  dynamoDBMapper.scan(Movie.class, scanExpression);*/

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(director));
        eav.put(":val2", new AttributeValue().withS("Year"));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("Year > :2010 and Year = :2020").withExpressionAttributeValues(eav);

        List<Movie> movieList =  dynamoDBMapper.scan(Movie.class, scanExpression);
        return movieList;
    }
    //Filtering and returning English titles with the highest reviews for the year as per requirement 2
    public Map<Object, Object> findEnglishTitlesByReviews(String reviews) {
        List<Movie> movieList = dynamoDBMapper.scan(Movie.class, new DynamoDBScanExpression());
        Map<Object, Object> movieMap = new HashMap<>();
        Map<Object, Object> reverseSortedMap = null;
        for (Movie movie : movieList
        ) {
            if (Integer.parseInt(movie.getReviews()) >= Integer.parseInt(reviews)) {
                if (movie.getLanguage().equals("English"))
                    movieMap.put(movie.getReviews(), movie.getTitle());
            }
            reverseSortedMap = new TreeMap<Object, Object>(Collections.reverseOrder());
            reverseSortedMap.putAll(movieMap);
        }
        return reverseSortedMap;
    }

    //Filtering and returning titles with the highest budget for the year as per requirement 3
    public Map<Object, Object> findMovieWithHighestBudget(String publishYear) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withIndexName(publishYear)
                .withConsistentRead(false);
        List<Movie> movieList =  dynamoDBMapper.scan(Movie.class, scanExpression);
        Map<Object, Object> movieMap = new HashMap<>();
        Map<Object, Object> reverseSortedMap = null;
        for (Movie movie:movieList
             ) {
            movieMap.put(movie.getBudget(), movie.getCountry());
        }
        reverseSortedMap = new TreeMap<Object, Object>(Collections.reverseOrder());
        reverseSortedMap.putAll(movieMap);
        return reverseSortedMap;
    }


}