package com.bookmyshow.service;

import com.bookmyshow.model.MovieSelection;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<MovieSelection> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<MovieSelection> getMovieById(String movieId) {
        return movieRepository.findById(movieId);
    }

    public List<MovieSelection> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public MovieSelection addMovie(MovieSelection movie) {
        return movieRepository.save(movie);
    }

}
