package com.bookmyshow.service;

import com.bookmyshow.model.MovieSelection;
import com.bookmyshow.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Cacheable(value = "movies", key = "'allMovies'")
    public List<MovieSelection> getAllMovies() {
        logger.warn("❌ [CACHE MISS] Fetching all movies from database...");
        List<MovieSelection> movies = movieRepository.findAll();
        logger.info("✅ [CACHE STORED] All movies cached successfully");
        return movies;
    }

    @Cacheable(value = "movie", key = "#movieId")
    public Optional<MovieSelection> getMovieById(String movieId) {
        logger.warn("❌ [CACHE MISS] Fetching movie {} from database...", movieId);
        return movieRepository.findById(movieId);
    }

    @Cacheable(value = "movies", key = "'genre_' + #genre")
    public List<MovieSelection> getMoviesByGenre(String genre) {
        logger.warn("❌ [CACHE MISS] Fetching movies by genre {} from database...", genre);
        return movieRepository.findByGenre(genre);
    }

    @CacheEvict(value = {"movies", "movie"}, allEntries = true)
    public MovieSelection addMovie(MovieSelection movie) {
        logger.info("🗑️ [CACHE EVICTED] Clearing movie caches");
        return movieRepository.save(movie);
    }

}
