package com.bookmyshow.service;

import com.bookmyshow.model.MovieSelection;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Cacheable(value = "movies", key = "'allMovies'")
    public List<MovieSelection> getAllMovies() {
        System.out.println("Fetching all movies from database...");
        return movieRepository.findAll();
    }

    @Cacheable(value = "movie", key = "#movieId")
    public Optional<MovieSelection> getMovieById(String movieId) {
        System.out.println("Fetching movie " + movieId + " from database...");
        return movieRepository.findById(movieId);
    }

    @Cacheable(value = "movies", key = "'genre_' + #genre")
    public List<MovieSelection> getMoviesByGenre(String genre) {
        System.out.println("Fetching movies by genre " + genre + " from database...");
        return movieRepository.findByGenre(genre);
    }

    @CacheEvict(value = {"movies", "movie"}, allEntries = true)
    public MovieSelection addMovie(MovieSelection movie) {
        return movieRepository.save(movie);
    }

}
