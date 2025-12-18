package com.bookmyshow.dto;

import java.util.List;

public class BookingRequest {
    private String userId;
    private String movieId;
    private String showId;
    private List<String> selectedSeats;
    private double pricePerSeat;

    public BookingRequest() {}

    public BookingRequest(String userId, String movieId, String showId, List<String> selectedSeats, double pricePerSeat) {
        this.userId = userId;
        this.movieId = movieId;
        this.showId = showId;
        this.selectedSeats = selectedSeats;
        this.pricePerSeat = pricePerSeat;
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

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }
}
