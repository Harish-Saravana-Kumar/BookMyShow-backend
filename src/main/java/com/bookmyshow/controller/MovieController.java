package com.bookmyshow.controller;

import com.bookmyshow.dto.ApiResponse;
import com.bookmyshow.model.MovieSelection;
import com.bookmyshow.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @GetMapping
    @Cacheable(value = "movies", key = "'allMovies'")
    public ResponseEntity<ApiResponse<List<MovieSelection>>> getAllMovies() {
        try {
            logger.warn("❌ [CONTROLLER CACHE MISS] Fetching all movies...");
            List<MovieSelection> movies = movieService.getAllMovies();
            logger.info("✅ [CONTROLLER CACHE STORED] Cached all movies");
            return ResponseEntity.ok(new ApiResponse<>(true, "Movies fetched successfully", movies));
        } catch (Exception e) {
            logger.error("❌ Error fetching movies: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{movieId}")
    @Cacheable(value = "movie", key = "#movieId")
    public ResponseEntity<ApiResponse<MovieSelection>> getMovieById(@PathVariable String movieId) {
        try {
            logger.warn("❌ [CONTROLLER CACHE MISS] Fetching movie {}...", movieId);
            MovieSelection movie = movieService.getMovieById(movieId)
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            logger.info("✅ [CONTROLLER CACHE STORED] Cached movie {}", movieId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movie fetched successfully", movie));
        } catch (Exception e) {
            logger.error("❌ Error fetching movie: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<ApiResponse<List<MovieSelection>>> getMoviesByGenre(@PathVariable String genre) {
        try {
            List<MovieSelection> movies = movieService.getMoviesByGenre(genre);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movies fetched by genre", movies));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieSelection>> addMovie(@RequestBody MovieSelection movie) {
        try {
            MovieSelection savedMovie = movieService.addMovie(movie);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movie added successfully", savedMovie));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

}
