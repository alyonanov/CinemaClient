package control.movie;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.CinemaHall;
import entities.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import main.Runner;
import usage.MapParser;
import usage.confirm.MovieInfoConfirm;
import usage.confirm.SceneChanger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class EditMovieController {

    @FXML
    private Button addMovie;

    @FXML
    private Button cancel;

    @FXML
    private TextField movieName;

    @FXML
    private TextField movieGenre;

    @FXML
    private TextField movieCountry;

    @FXML
    private TextField movieDuration;

    @FXML
    private ChoiceBox<CinemaHall> cinemaHall;


    private MapParser parser = MapParser.getInstance();
    private MovieInfoConfirm confirm = MovieInfoConfirm.getInstance();
    private Movie movie;

    @FXML
    private void initialize() {
        fillCinemaHallsBox();
        addMovie.setOnAction(event -> updateMovie());
        cancel.setOnAction(event -> cancel.getScene().getWindow().hide());


        this.initForm(SceneChanger.getInstance().getDataId());
    }

    private void initForm(int movieId) {
        getMovie(movieId);
        this.initForm();

        if (!"Администратор".equals(Runner.getStatus().getStatusName())) {
            addMovie.setVisible(false);

            movieName.setEditable(false);
            movieGenre.setEditable(false);
            movieCountry.setEditable(false);
            movieDuration.setEditable(false);
            cinemaHall.setDisable(true);
        }
    }

    private void initForm() {
        this.movieName.setText(this.movie.getMovieName());
        this.movieGenre.setText(this.movie.getMovieGenre());
        this.movieCountry.setText(this.movie.getMovieCountry());
        this.movieDuration.setText(this.movie.getMovieDuration());
        this.cinemaHall.getSelectionModel().select(this.movie.getCinemaHall());
    }

    private void getMovie(int movieId) {
        Map<String, Object> data = new HashMap<>();
        data.put("movieId", movieId);
        Runner.sendData(new ClientRequest("getMovieById", data));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> movieMap = response.getData();
            movie = parser.movie((Map<String, Object>) movieMap.get("movie"));
        }
    }

    private void updateMovie() {
        String movieNameText = this.movieName.getText();
        String movieGenreText = this.movieGenre.getText();
        String movieCountryText = this.movieCountry.getText();
        String movieDurationText = this.movieDuration.getText();
        if (confirm.validate(movieNameText, movieGenreText, movieCountryText, movieDurationText) &&
                !cinemaHall.getSelectionModel().isEmpty()) {
            Map<String, Object> data = new HashMap<>();

            data.put("movieId", this.movie.getMovieId());
            data.put("movieName", movieNameText);
            data.put("movieGenre", movieGenreText);
            data.put("movieCountry", movieCountryText);
            data.put("movieDuration", movieDurationText);
            data.put("hallId", cinemaHall.getValue().getHallId());

            Runner.sendData(new ClientRequest("editMovie", data));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                addMovie.getScene().getWindow().hide();

            } else {
                Alert alert = new Alert(ERROR, "Информация некорректна! " + response.getErrorMessage());
                alert.show();
            }
        }
    }
    private void fillCinemaHallsBox() {
        Runner.sendData(new ClientRequest("getAllCinemaHalls", new HashMap<>()));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> cinemaHallMap = response.getData();
            List cinemaHallData = (List) cinemaHallMap.get("cinemaHalls");
            List<CinemaHall> cinemaHalls = parser.cinemaHalls(cinemaHallData);
            cinemaHall.setItems(FXCollections.observableArrayList(cinemaHalls));
        }
    }
}