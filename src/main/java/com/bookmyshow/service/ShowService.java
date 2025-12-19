package com.bookmyshow.service;

import com.bookmyshow.model.ShowAndSeat;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Cacheable(value = "shows", key = "#movieId")
    public List<ShowAndSeat> getShowsByMovieId(String movieId) {
        System.out.println("Fetching shows for movie " + movieId + " from database...");
        return showRepository.findByMovieId(movieId);
    }

    @Cacheable(value = "shows", key = "'show_' + #showId")
    public Optional<ShowAndSeat> getShowById(String showId) {
        System.out.println("Fetching show " + showId + " from database...");
        return showRepository.findById(showId);
    }

    @CacheEvict(value = "shows", key = "#showId")
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

    @CacheEvict(value = "shows", allEntries = true)
    public ShowAndSeat addShow(ShowAndSeat show) {
        return showRepository.save(show);
    }

}
