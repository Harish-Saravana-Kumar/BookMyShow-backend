package com.bookmyshow.controller;

import com.bookmyshow.dto.ApiResponse;
import com.bookmyshow.model.ShowAndSeat;
import com.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowAndSeat>>> getShowsByMovieId(@PathVariable String movieId) {
        try {
            List<ShowAndSeat> shows = showService.getShowsByMovieId(movieId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Shows fetched successfully", shows));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ApiResponse<ShowAndSeat>> getShowById(@PathVariable String showId) {
        try {
            ShowAndSeat show = showService.getShowById(showId)
                    .orElseThrow(() -> new RuntimeException("Show not found"));
            return ResponseEntity.ok(new ApiResponse<>(true, "Show fetched successfully", show));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{showId}/available-seats")
    public ResponseEntity<ApiResponse<List<ShowAndSeat.Seat>>> getAvailableSeats(@PathVariable String showId) {
        try {
            List<ShowAndSeat.Seat> seats = showService.getAvailableSeats(showId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Available seats fetched", seats));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ShowAndSeat>> addShow(@RequestBody ShowAndSeat show) {
        try {
            ShowAndSeat savedShow = showService.addShow(show);
            return ResponseEntity.ok(new ApiResponse<>(true, "Show added successfully", savedShow));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

}
