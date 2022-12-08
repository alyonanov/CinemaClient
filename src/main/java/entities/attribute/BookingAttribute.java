package entities.attribute;

import entities.Booking;
import entities.CinemaHall;
import entities.Movie;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookingAttribute {
    private IntegerProperty bookingId;
    private IntegerProperty movieId;
    private IntegerProperty userId;

    private StringProperty movieName;
    private StringProperty movieGenre;
    private StringProperty movieCountry;
    private StringProperty movieDuration;
    private StringProperty userName;
    private StringProperty userSurname;
    private StringProperty userEmail;
    private IntegerProperty moviePrice;
    private CinemaHall cinemaHall;


    public BookingAttribute(Booking booking) {
        this.bookingId = new SimpleIntegerProperty(booking.getBookingId());
        this.movieId = new SimpleIntegerProperty(booking.getMovieId().getMovieId());
        this.userId = new SimpleIntegerProperty(booking.getUserId().getUserId());
        this.movieName = new SimpleStringProperty(booking.getMovieId().getMovieName());
        this.movieGenre = new SimpleStringProperty(booking.getMovieId().getMovieGenre());
        this.movieCountry = new SimpleStringProperty(booking.getMovieId().getMovieCountry());
        this.movieDuration = new SimpleStringProperty(booking.getMovieId().getMovieDuration());
        this.moviePrice = new SimpleIntegerProperty(booking.getMovieId().getMoviePrice());
        this.userName = new SimpleStringProperty(booking.getUserId().getFirstName());
        this.userSurname = new SimpleStringProperty(booking.getUserId().getLastName());
        this.userEmail = new SimpleStringProperty(booking.getUserId().getEmail());
        this.cinemaHall = booking.getMovieId().getCinemaHall();
    }

    public int getBookingId() {
        return bookingId.get();
    }

    public IntegerProperty bookingIdProperty() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId.set(bookingId);
    }

    public int getMovieId() {
        return movieId.get();
    }

    public IntegerProperty movieIdProperty() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId.set(movieId);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getMovieName() {
        return movieName.get();
    }

    public StringProperty movieNameProperty() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public String getMovieGenre() {
        return movieGenre.get();
    }

    public StringProperty movieGenreProperty() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre.set(movieGenre);
    }

    public String getMovieCountry() {
        return movieCountry.get();
    }

    public StringProperty movieCountryProperty() {
        return movieCountry;
    }

    public void setMovieCountry(String movieCountry) {
        this.movieCountry.set(movieCountry);
    }

    public String getMovieDuration() {
        return movieDuration.get();
    }

    public StringProperty movieDurationProperty() {
        return movieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        this.movieDuration.set(movieDuration);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getUserSurname() {
        return userSurname.get();
    }

    public StringProperty userSurnameProperty() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname.set(userSurname);
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public StringProperty userEmailProperty() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail.set(userEmail);
    }

    public int getMoviePrice() {
        return moviePrice.get();
    }

    public IntegerProperty moviePriceProperty() {
        return moviePrice;
    }

    public void setMoviePrice(int moviePrice) {
        this.moviePrice.set(moviePrice);
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }
}
