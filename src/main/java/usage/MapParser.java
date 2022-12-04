package usage;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapParser {

    private static final MapParser INSTANCE = new MapParser();

    public static MapParser getInstance() {
        return INSTANCE;
    }

    private MapParser() {
    }

    private ObjectMapper mapper = new ObjectMapper();

    //USERS
    public List<User> users(List userData) {
        List<User> users = new ArrayList<>();
        for (Map<String, Object> user : (List<Map<String, Object>>) userData) {
            users.add(user(user));
        }
        return users;
    }

    public User user(Map<String, Object> map) {
        return new User((int) map.get("userId"),
                (String) map.get("username"),
                (String) map.get("firstName"),
                (String) map.get("lastName"),
                userStatus((Map<String, Object>) map.get("userStatus")),
                (String) map.get("email"));
    }

    public UserStatus userStatus(Map<String, Object> map) {
        return new UserStatus((int) map.get("statusId"), (String) map.get("statusName"));
    }

    //MOVIE
    public List<Movie> movies(List moviesData) {
        List<Movie> movies = new ArrayList<>();
        for (Map<String, Object> movie : (List<Map<String, Object>>) moviesData) {
            movies.add(movie(movie));
        }
        return movies;
    }

    public Movie movie(Map<String, Object> map) {

        Movie movie = new Movie();

        movie.setMovieId((int) map.get("movieId"));
        movie.setMovieName((String) map.get("movieName"));
        movie.setMovieGenre((String) map.get("movieGenre"));
        movie.setMovieCountry((String) map.get("movieCountry"));
        movie.setMovieDuration((String) map.get("movieDuration"));
        movie.setMoviePrice((int) map.get("moviePrice"));

        movie.setCinemaHall(cinemaHall((Map<String, Object>) map.get("cinemaHall")));

        return movie;
    }

    //CINEMAHALL
    public List<CinemaHall> cinemaHalls(List cinemaHallsData) {
        List<CinemaHall> cinemaHalls = new ArrayList<>();
        for (Map<String, Object> cinemaHall : (List<Map<String, Object>>) cinemaHallsData) {
            cinemaHalls.add(cinemaHall(cinemaHall));
        }
        return cinemaHalls;
    }

    public CinemaHall cinemaHall(Map<String, Object> map) {
        return new CinemaHall((int) map.get("hallId"), (String) map.get("hallType"), (int) map.get("hallSeatsNumber"));
    }



}