package com.bookmyshow.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "shows")
public class ShowAndSeat {
    @Id
    private String showId;
    private String movieId;
    private String showTime;
    private String theatreId;
    private List<Seat> seats;

    public ShowAndSeat() {}

    public ShowAndSeat(String showId, String movieId, String showTime, String theatreId, List<Seat> seats) {
        this.showId = showId;
        this.movieId = movieId;
        this.showTime = showTime;
        this.theatreId = theatreId;
        this.seats = seats;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public static class Seat {
        private String seatNumber;
        private String status;

        public Seat() {}

        public Seat(String seatNumber, String status) {
            this.seatNumber = seatNumber;
            this.status = status;
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
