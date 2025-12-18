package com.bookmyshow.controller;

import com.bookmyshow.dto.ApiResponse;
import com.bookmyshow.dto.BookingRequest;
import com.bookmyshow.model.TicketBooking;
import com.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TicketBooking>> createBooking(@RequestBody BookingRequest request) {
        try {
            if (bookingService.isShowAlreadyBooked(request.getUserId(), request.getShowId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "You already have a booking for this show", null));
            }

            double totalAmount = request.getSelectedSeats().size() * request.getPricePerSeat();
            TicketBooking booking = bookingService.createBooking(
                    request.getUserId(),
                    request.getMovieId(),
                    request.getShowId(),
                    request.getSelectedSeats(),
                    totalAmount
            );
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", booking));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<TicketBooking>>> getBookingsByUserId(@PathVariable String userId) {
        try {
            List<TicketBooking> bookings = bookingService.getBookingsByUserId(userId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Bookings fetched successfully", bookings));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<TicketBooking>> getBookingById(@PathVariable String bookingId) {
        try {
            TicketBooking booking = bookingService.getBookingById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking fetched successfully", booking));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

}
