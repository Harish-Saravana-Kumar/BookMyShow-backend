package com.bookmyshow.service;

import com.bookmyshow.model.MovieSelection;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Cacheable(value = "movies")
    public List<MovieSelection> getAllMovies() {
        return movieRepository.findAll();
    }

    @Cacheable(value = "movie", key = "#movieId")
    public Optional<MovieSelection> getMovieById(String movieId) {
        return movieRepository.findById(movieId);
    }

    @Cacheable(value = "moviesByGenre", key = "#genre")
    public List<MovieSelection> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public MovieSelection addMovie(MovieSelection movie) {
        return movieRepository.save(movie);
    }

}
