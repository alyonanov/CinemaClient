package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private int movieId;

    private String movieName;
    private String movieGenre;
    private String movieCountry;
    private String movieDuration;
    private CinemaHall cinemaHall;

    private int moviePrice;
}