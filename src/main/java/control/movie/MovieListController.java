package control.movie;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.attribute.MovieAttribute;
import entities.Movie;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Runner;
import usage.MapParser;
import usage.confirm.SceneChanger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class MovieListController {

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
    private Button addMovie;

    @FXML
    private Button deleteMovie;
//    @FXML
//    private Text movieName;

//    @FXML
//    private Text movieGenre;
//
    @FXML
    private Button back;

    @FXML
    private Button main;

    @FXML
    private Button saveToFile;


    private SceneChanger sceneChanger = SceneChanger.getInstance();
    private List<Movie> movies;
    private List<String> cinemaHalls = new ArrayList<>();
    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {
        if (!"Администратор".equals(Runner.getStatus().getStatusName())) {
            addMovie.setVisible(false);
            deleteMovie.setVisible(false);
        }

        fillMoviesTable();

        addMovie.setOnAction(event -> {
            sceneChanger.changeSceneAndWait("/fxml/add_movie.fxml");
            fillMoviesTable();
        });

        deleteMovie.setOnAction(event -> {
            deleteMovie();
            fillMoviesTable();
        });

        moviesTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                MovieAttribute movieAttribute = moviesTable.getSelectionModel().getSelectedItem();
                sceneChanger.setDataId(movieAttribute.getMovieId().getValue());
                sceneChanger.changeSceneAndWait("/fxml/edit_movie.fxml");
                fillMoviesTable();
            }
        });

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });
        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });


        this.saveToFile.setOnAction(event -> {

            try {
                FileWriter writer = new FileWriter("src/main/resources/workers" + ".txt");

                for (Movie movie : this.movies) {
                    String movieName = movie.getMovieName();
                    String movieGenre = movie.getMovieGenre();
                    String movieCountry = movie.getMovieCountry();
                    String movieDuration = movie.getMovieDuration();
                    String hallType = movie.getCinemaHall().getHallType();

                    String text =
                            " Название фильма: " + movieName
                            + " Жанр:   " + movieGenre
                            + " Страна производства  " + movieCountry
                            + " Длительность   " + movieDuration
                            + " Тип зала  " + hallType;

                    writer.write(text + System.getProperty("line.separator"));
                }
                writer.close();

                Alert alert = new Alert(INFORMATION, "Отчет успешно сохранен в файл!");
                alert.show();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(ERROR, "Произошла ошибка при записи файл!");
                alert.show();
            }


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
    }

    private void deleteMovie() {
        int movieId = moviesTable.getSelectionModel()
                .selectedItemProperty()
                .getValue()
                .getMovieId()
                .getValue();
        Map<String, Object> map = new HashMap<>();
        map.put("movieId", movieId);
        Runner.sendData(new ClientRequest("deleteMovie", map));
        ServerResponse response = Runner.getData();
        if (response.isError()) {
            Alert alert = new Alert(ERROR, "Произошла ошибка при удалении фильма!");
            alert.show();
        }
    }
//    private List<CinemaHall> getCinemaHalls() {
//        Runner.sendData(new ClientRequest("getAllCinemaHalls", new HashMap<>()));
//        ServerResponse response = Runner.getData();
//        if (!response.isError()) {
//            Map<String, Object> cinemaHallMap = response.getData();
//            List cinemaHallData = (List) cinemaHallMap.get("cinemaHalls");
//            return parser.cinemaHalls(cinemaHallData);
//        }
//        return new ArrayList<>();
//    }

}
