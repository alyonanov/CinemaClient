package entities;

public class Booking {
    int bookingId;
    Movie movieId;
    User userId;

    public Booking(int bookingId, Movie movieId, User userId) {
        this.bookingId = bookingId;
        this.movieId = movieId;
        this.userId = userId;
    }

    public Booking() {

    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public void setMovieId(Movie movieId) {
        this.movieId = movieId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
