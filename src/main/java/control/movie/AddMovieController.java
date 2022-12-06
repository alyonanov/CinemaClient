package control.movie;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.CinemaHall;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Runner;
import usage.MapParser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class AddMovieController {

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
    private TextField moviePrice;
    @FXML
    private ChoiceBox<CinemaHall> cinemaHall;

    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {
        fillCinemaHallsBox();
        addMovie.setOnAction(event -> addMovie());
        cancel.setOnAction(event -> cancel.getScene().getWindow().hide());
    }


    private void addMovie() {
        String movieNameText = this.movieName.getText();
        String movieGenreText = this.movieGenre.getText();
        String movieCountryText = this.movieCountry.getText();
        String movieDurationText = this.movieDuration.getText();
        String moviePriceText = this.moviePrice.getText();

        Map<String, Object> data = new HashMap<>();

        data.put("movieName", movieNameText);
        data.put("movieGenre", movieGenreText);
        data.put("movieCountry", movieCountryText);
        data.put("movieDuration", movieDurationText);
        data.put("moviePrice", Integer.parseInt(moviePriceText));
        data.put("cinemaHallId", cinemaHall.getValue().getHallId());

            Runner.sendData(new ClientRequest("addMovie", data));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                addMovie.getScene().getWindow().hide();

            } else {
                Alert alert = new Alert(ERROR, "Информация некорректна!");
                alert.show();
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

