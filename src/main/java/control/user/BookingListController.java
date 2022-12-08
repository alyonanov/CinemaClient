package control.user;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.Booking;
import entities.Movie;
import entities.attribute.BookingAttribute;
import entities.attribute.MovieAttribute;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class BookingListController {

    @FXML
    private TableView<BookingAttribute> bookingTable;

    @FXML
    private TableColumn<BookingAttribute, String> movieNameColumn;

    @FXML
    private TableColumn<BookingAttribute, String> movieGenreColumn;

    @FXML
    private TableColumn<BookingAttribute, String> movieCountryColumn;

    @FXML
    private TableColumn<BookingAttribute, String> movieDurationColumn;

    @FXML
    private TableColumn<BookingAttribute, String> cinemaHallColumn;
    @FXML
    private TableColumn<BookingAttribute, Integer> moviePriceColumn;
    @FXML
    private TableColumn<BookingAttribute, String> userNameColumn;

    @FXML
    private TableColumn<BookingAttribute, String> userSurnameColumn;
    @FXML
    private TableColumn<BookingAttribute, String> userEmailColumn;
    //
    @FXML
    private Button back;

    @FXML
    private Button main;


    private SceneChanger sceneChanger = SceneChanger.getInstance();
    private List<Booking> bookings;
    private List<String> cinemaHalls = new ArrayList<>();
    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {

        fillBookingTable();

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });
        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });

    }

    private void getBookings() {
        Runner.sendData(new ClientRequest("getAllBookings", new HashMap<>()));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> productMap = response.getData();
            List productData = (List) productMap.get("bookings");
            bookings = parser.bookings(productData);
        }
    }
    private void fillBookingTable() {
        getBookings();
        List<BookingAttribute> bookingAttributes = new ArrayList<>();
        bookings.forEach(booking -> bookingAttributes.add(new BookingAttribute(booking)));
        bookingTable.setItems(FXCollections.observableArrayList(bookingAttributes));
        movieNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieName()));
        movieGenreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieGenre()));
        movieCountryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieCountry()));
        movieDurationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieDuration()));
        moviePriceColumn.setCellValueFactory(cellData -> {
            int moviePriceColumn = cellData.getValue().getMoviePrice();
            return new SimpleIntegerProperty(moviePriceColumn).asObject();
        });
        cinemaHallColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCinemaHall().getHallType()));
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
        userSurnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserSurname()));
        userEmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserEmail()));
    }

}
