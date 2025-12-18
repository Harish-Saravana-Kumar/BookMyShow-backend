package com.bookmyshow.repository;

import com.bookmyshow.model.ShowAndSeat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends MongoRepository<ShowAndSeat, String> {
    List<ShowAndSeat> findByMovieId(String movieId);

}
