package com.bookmyshow.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "bookings")
public class TicketBooking {
    @Id
    private String bookingId;
    private String userId;
    private String movieId;
    private String showId;
    private List<String> selectedSeats;
    private double totalAmount;
    private String bookingStatus;

    public TicketBooking() {}

    public TicketBooking(String bookingId, String userId, String movieId, String showId, 
                        List<String> selectedSeats, double totalAmount, String bookingStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.showId = showId;
        this.selectedSeats = selectedSeats;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public List<String> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<String> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
