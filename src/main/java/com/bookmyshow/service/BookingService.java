package com.bookmyshow.service;

import com.bookmyshow.model.TicketBooking;
import com.bookmyshow.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowService showService;

    public TicketBooking createBooking(String userId, String movieId, String showId, 
                                       List<String> selectedSeats, double totalAmount) {
        TicketBooking booking = new TicketBooking();
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setUserId(userId);
        booking.setMovieId(movieId);
        booking.setShowId(showId);
        booking.setSelectedSeats(selectedSeats);
        booking.setTotalAmount(totalAmount);
        booking.setBookingStatus("Confirmed");

        showService.bookSeats(showId, selectedSeats);
        return bookingRepository.save(booking);
    }

    public List<TicketBooking> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<TicketBooking> getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public boolean isShowAlreadyBooked(String userId, String showId) {
        TicketBooking existing = bookingRepository.findByUserIdAndShowIdAndBookingStatus(userId, showId, "Confirmed");
        return existing != null;
    }

}
