package entities.attribute;

import entities.CinemaHall;
import entities.Movie;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieAttribute {
    private IntegerProperty movieId;
    private StringProperty movieName;
    private StringProperty movieGenre;
    private StringProperty movieCountry;
    private StringProperty movieDuration;
    private IntegerProperty moviePrice;
    private CinemaHall cinemaHall;


    public MovieAttribute(Movie movie) {
        this.movieId = new SimpleIntegerProperty(movie.getMovieId());
        this.movieName = new SimpleStringProperty(movie.getMovieName());
        this.movieGenre = new SimpleStringProperty(movie.getMovieGenre());
        this.movieCountry = new SimpleStringProperty(movie.getMovieCountry());
        this.movieDuration = new SimpleStringProperty(movie.getMovieDuration());
        this.moviePrice = new SimpleIntegerProperty(movie.getMoviePrice());
        this.cinemaHall = movie.getCinemaHall();
    }
}
