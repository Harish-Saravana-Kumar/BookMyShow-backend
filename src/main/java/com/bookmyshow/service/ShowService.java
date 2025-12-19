package com.bookmyshow.service;

import com.bookmyshow.model.ShowAndSeat;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<ShowAndSeat> getShowsByMovieId(String movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public Optional<ShowAndSeat> getShowById(String showId) {
        return showRepository.findById(showId);
    }

    public ShowAndSeat bookSeats(String showId, List<String> selectedSeats) {
        Optional<ShowAndSeat> show = showRepository.findById(showId);
        if (show.isPresent()) {
            ShowAndSeat updatedShow = show.get();
            for (ShowAndSeat.Seat seat : updatedShow.getSeats()) {
                if (selectedSeats.contains(seat.getSeatNumber())) {
                    seat.setStatus("Booked");
                }
            }
            return showRepository.save(updatedShow);
        }
        throw new RuntimeException("Show not found");
    }

    public List<ShowAndSeat.Seat> getAvailableSeats(String showId) {
        Optional<ShowAndSeat> show = showRepository.findById(showId);
        if (show.isPresent()) {
            return show.get().getSeats().stream()
                    .filter(seat -> "Available".equals(seat.getStatus()))
                    .collect(java.util.stream.Collectors.toList());
        }
        throw new RuntimeException("Show not found");
    }

    public ShowAndSeat addShow(ShowAndSeat show) {
        return showRepository.save(show);
    }

}
