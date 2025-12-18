package com.bookmyshow.config;

import com.bookmyshow.model.MovieSelection;
import com.bookmyshow.model.ShowAndSeat;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear existing data and reseed
        movieRepository.deleteAll();
        showRepository.deleteAll();
        
        System.out.println("✓ Cleared existing data");
        
        // Seed fresh data
        seedMovies();
        seedShows();
    }

    private void seedMovies() {
        List<MovieSelection> movies = new ArrayList<>();

        // Movie 1: Inception
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "Inception",
                "Sci-Fi",
                148,
                "English"
        ));

        // Movie 2: The Dark Knight
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "The Dark Knight",
                "Action",
                152,
                "English"
        ));

        // Movie 3: Interstellar
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "Interstellar",
                "Sci-Fi",
                169,
                "English"
        ));

        // Movie 4: Dune
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "Dune",
                "Sci-Fi",
                156,
                "English"
        ));

        // Movie 5: Avatar
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "Avatar",
                "Sci-Fi",
                162,
                "English"
        ));

        // Movie 6: The Matrix
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "The Matrix",
                "Sci-Fi",
                136,
                "English"
        ));

        // Movie 7: Titanic
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "Titanic",
                "Romance",
                194,
                "English"
        ));

        // Movie 8: 3 Idiots
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "3 Idiots",
                "Comedy",
                170,
                "Hindi"
        ));

        // Movie 9: Dilwale Dulhania Le Jayenge
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "Dilwale Dulhania Le Jayenge",
                "Romance",
                182,
                "Hindi"
        ));

        // Movie 10: Avengers: Endgame
        movies.add(new MovieSelection(
                UUID.randomUUID().toString(),
                "Avengers: Endgame",
                "Action",
                181,
                "English"
        ));

        movieRepository.saveAll(movies);
        System.out.println("✓ Seeded " + movies.size() + " movies to database");
    }

    private void seedShows() {
        List<ShowAndSeat> shows = new ArrayList<>();
        List<MovieSelection> movies = movieRepository.findAll();

        // Theatres available
        String[] theatres = {
                "PVR Cinemas - Screen 1",
                "Inox - Screen 2",
                "Cinepolis - Screen 3",
                "Carnival Cinemas - Screen 1",
                "PVR Cinemas - Screen 4",
                "Inox - Screen 5"
        };

        // Show times for different slots
        String[] showTimes = {
                "2025-12-18 09:00",  // Morning
                "2025-12-18 13:00",  // Afternoon
                "2025-12-18 17:00",  // Evening
                "2025-12-18 21:00"   // Night
        };

        // Additional show times for popular movies
        String[] additionalTimes = {
                "2025-12-18 10:30",
                "2025-12-18 14:30",
                "2025-12-18 18:30",
                "2025-12-18 22:30"
        };

        // Create shows for ALL movies
        for (int i = 0; i < movies.size(); i++) {
            MovieSelection movie = movies.get(i);
            
            // Each movie gets at least 4 shows
            for (int j = 0; j < 4; j++) {
                shows.add(createShow(
                        movie.getMovieId(),
                        showTimes[j],
                        theatres[i % theatres.length]
                ));
            }

            // Popular movies (first 5) get additional shows
            if (i < 5) {
                for (int j = 0; j < 2; j++) {
                    shows.add(createShow(
                            movie.getMovieId(),
                            additionalTimes[j],
                            theatres[(i + 1) % theatres.length]
                    ));
                }
            }

            // Add weekend shows for top 3 movies
            if (i < 3) {
                shows.add(createShow(
                        movie.getMovieId(),
                        "2025-12-19 11:00",
                        theatres[(i + 2) % theatres.length]
                ));
                shows.add(createShow(
                        movie.getMovieId(),
                        "2025-12-19 15:00",
                        theatres[(i + 2) % theatres.length]
                ));
                shows.add(createShow(
                        movie.getMovieId(),
                        "2025-12-19 19:00",
                        theatres[(i + 2) % theatres.length]
                ));
            }
        }

        showRepository.saveAll(shows);
        System.out.println("✓ Seeded " + shows.size() + " shows with seats to database");
    }

    private ShowAndSeat createShow(String movieId, String showTime, String theatreId) {
        ShowAndSeat show = new ShowAndSeat();
        show.setShowId(UUID.randomUUID().toString());
        show.setMovieId(movieId);
        show.setShowTime(showTime);
        show.setTheatreId(theatreId);
        show.setSeats(generateSeats());
        return show;
    }

    private List<ShowAndSeat.Seat> generateSeats() {
        List<ShowAndSeat.Seat> seats = new ArrayList<>();
        
        // Generate seats in rows A-H, columns 1-12
        String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int seatsPerRow = 12;

        for (String row : rows) {
            for (int i = 1; i <= seatsPerRow; i++) {
                String seatNumber = row + i;
                String status = "Available"; // All seats available initially
                
                // Randomly book a few seats for demo
                if (Math.random() < 0.15) { // 15% seats booked
                    status = "Booked";
                }
                
                seats.add(new ShowAndSeat.Seat(seatNumber, status));
            }
        }

        return seats;
    }
}
