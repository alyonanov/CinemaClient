package control;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Runner;
import usage.confirm.SceneChanger;

public class MainController {

    @FXML
    private Button movies;

    @FXML
    private Button myProfile;

    @FXML
    private Button allUsers;

    @FXML
    private Button booking;

    @FXML
    private Button bookingList;

    @FXML
    private Button allCinemaHalls;


    @FXML
    private Button back;

    private final SceneChanger sceneChanger = SceneChanger.getInstance();

    @FXML
    private void initialize() {
        if (!"Администратор".equals(Runner.getStatus().getStatusName())) {
            allUsers.setVisible(false);
            movies.setVisible(false);
            bookingList.setVisible(false);
        }
        if ("Администратор".equals(Runner.getStatus().getStatusName())) {
            booking.setVisible(false);
        }

        movies.setOnAction(event -> {
            movies.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/movies-list.fxml");
        });
        myProfile.setOnAction(event -> {
            myProfile.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/profile.fxml");
        });
        allUsers.setOnAction(event -> {
            allUsers.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/users.fxml");
        });

        allCinemaHalls.setOnAction(event -> {
            allCinemaHalls.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/cinemahall_list.fxml");
        });
        booking.setOnAction(event -> {
            booking.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/booking.fxml");
        });

        bookingList.setOnAction(event -> {
            booking.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/booking-list.fxml");
        });

        back.setOnAction(event -> {
            Runner.setStatus(null);
            Runner.setUserId(-1);
            Runner.setUsername(null);
            back.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/index.fxml");
        });
    }


}