package control.user;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.Movie;
import entities.attribute.MovieAttribute;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.Runner;
import usage.MapParser;
import usage.confirm.SceneChanger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class BookingController {

    @FXML
    private TableView<MovieAttribute> moviesTable;

    @FXML
    private TableColumn<MovieAttribute, String> movieNameColumn;

    @FXML
    private TableColumn<MovieAttribute, String> movieGenreColumn;

    @FXML
    private TableColumn<MovieAttribute, String> movieCountryColumn;

    @FXML
    private TableColumn<MovieAttribute, String> movieDurationColumn;

    @FXML
    private TableColumn<MovieAttribute, String> cinemaHallColumn;
    @FXML
    private TableColumn<MovieAttribute, Integer> moviePriceColumn;
    @FXML
    private Button booking;
    @FXML
    private Button back;

    @FXML
    private Button main;
    @FXML
    private Text name;
    @FXML
    private Text genre;
    @FXML
    private Text country;

    @FXML
    private ImageView image;


    private SceneChanger sceneChanger = SceneChanger.getInstance();
    private List<Movie> movies;
    private List<String> cinemaHalls = new ArrayList<>();
    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {

        fillMoviesTable();

        booking.setOnAction(event -> {
            bookingMovie();
        });

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });
        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });

    }

    private void getMovies() {
        Runner.sendData(new ClientRequest("getAllMovies", new HashMap<>()));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> productMap = response.getData();
            List productData = (List) productMap.get("movies");
            movies = parser.movies(productData);
        }
    }
private void bookingMovie(){
        int userId = Runner.getUserId();
        int movieId = moviesTable.getSelectionModel().getSelectedItem().getMovieId().intValue();
    Map<String, Object> data = new HashMap<>();
    data.put("userId", userId);
    data.put("movieId", movieId);
    Runner.sendData(new ClientRequest("booking", data));
    ServerResponse response = Runner.getData();
    if (!response.isError()) {
        booking.getScene().getWindow().hide();
        SceneChanger.getInstance().changeScene("/fxml/main.fxml");
    } else {
        Alert alert = new Alert(ERROR, "Ошибка бронирования");
        alert.show();
    }

}
    private void fillMoviesTable() {
        getMovies();
        List<MovieAttribute> movieAttributes = new ArrayList<>();
        movies.forEach(movie -> movieAttributes.add(new MovieAttribute(movie)));
        moviesTable.setItems(FXCollections.observableArrayList(movieAttributes));
        movieNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMovieName());
        movieGenreColumn.setCellValueFactory(cellData -> cellData.getValue().getMovieGenre());
        movieCountryColumn.setCellValueFactory(cellData -> cellData.getValue().getMovieCountry());
        movieDurationColumn.setCellValueFactory(cellData -> cellData.getValue().getMovieDuration());
        moviePriceColumn.setCellValueFactory(cellData -> {
            int moviePriceColumn = cellData.getValue().getMoviePrice().getValue().intValue();
            return new SimpleIntegerProperty(moviePriceColumn).asObject();
        });
        cinemaHallColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCinemaHall().getHallType()));
        showMovieDetails(null);
        moviesTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showMovieDetails(newValue));
   }
    private void showMovieDetails(MovieAttribute movieAttribute) {
        if (movieAttribute != null) {
            name.setText(movieAttribute.getMovieName().getValue());
            genre.setText(movieAttribute.getMovieGenre().getValue());
            country.setText(movieAttribute.getMovieCountry().getValue());
            image.setImage(new Image(movieAttribute.getImagePath().getValue()));

        } else {
            name.setText("");
            image.setImage(null);
        }
    }

}
