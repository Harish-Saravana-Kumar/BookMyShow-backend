package com.bookmyshow.controller;

import com.bookmyshow.dto.ApiResponse;
import com.bookmyshow.model.MovieSelection;
import com.bookmyshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieSelection>>> getAllMovies() {
        try {
            List<MovieSelection> movies = movieService.getAllMovies();
            return ResponseEntity.ok(new ApiResponse<>(true, "Movies fetched successfully", movies));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse<MovieSelection>> getMovieById(@PathVariable String movieId) {
        try {
            MovieSelection movie = movieService.getMovieById(movieId)
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            return ResponseEntity.ok(new ApiResponse<>(true, "Movie fetched successfully", movie));
        } catch (Exception e) {
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
