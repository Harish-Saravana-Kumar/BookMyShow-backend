package com.bookmyshow.repository;

import com.bookmyshow.model.TicketBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<TicketBooking, String> {
    List<TicketBooking> findByUserId(String userId);
    TicketBooking findByUserIdAndShowIdAndBookingStatus(String userId, String showId, String status);

}
