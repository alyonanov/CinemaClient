package control.cinemahall;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.attribute.CinemaHallAttribute;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Runner;
import usage.MapParser;
import usage.confirm.SceneChanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class CinemaHallListController {

    @FXML
    private TableView<CinemaHallAttribute> table;

    @FXML
    private TableColumn<CinemaHallAttribute, String> hallType;

    @FXML
    private TableColumn<CinemaHallAttribute, String> hallSeatsNumber;

    @FXML
    private Button addCinemaHall;

    @FXML
    private Button deleteCinemaHall;

    @FXML
    private Button back;

    @FXML
    private Button main;

    private SceneChanger sceneChanger = SceneChanger.getInstance();
    //private List<CinemaHall> cinemaHalls = new ArrayList<>();
    private MapParser parser = MapParser.getInstance();


    @FXML
    private void initialize() {
        if (!"Администратор".equals(Runner.getStatus().getStatusName())) {
            addCinemaHall.setVisible(false);
            deleteCinemaHall.setVisible(false);
        } else {
            table.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    CinemaHallAttribute cinemaHallAttribute = table.getSelectionModel().getSelectedItem();
                    sceneChanger.setDataId(cinemaHallAttribute.getHallId().getValue());
                    sceneChanger.changeSceneAndWait("/fxml/add_cinemahall.fxml");
                    fillMoviesTable();
                }
            });
        }

        fillMoviesTable();


        addCinemaHall.setOnAction(event -> {
            sceneChanger.changeSceneAndWait("/fxml/add_cinemahall.fxml");
            fillMoviesTable();
        });

        deleteCinemaHall.setOnAction(event -> {
            delete();
            fillMoviesTable();
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

    private void fillMoviesTable() {
        getCinemaHalls();
        List<CinemaHallAttribute> cinemaHallAttributes = new ArrayList<>();

        table.setItems(FXCollections.observableArrayList(cinemaHallAttributes));

        hallType.setCellValueFactory(cellData -> cellData.getValue().getHallType());
        //hallSeatsNumber.setCellValueFactory(cellData -> cellData.intValue().gethallSeatsNumber();
    }
    private void delete() {
        int movieId = table.getSelectionModel()
                .selectedItemProperty()
                .getValue()
                .getHallId()
                .getValue();

            Map<String, Object> map = new HashMap<>();
            map.put("hallId", movieId);
            Runner.sendData(new ClientRequest("deleteCinemaHall", map));
            ServerResponse response = Runner.getData();
            System.out.println(response.isError());
            System.out.println(response.getErrorMessage());
            if (response.isError()) {
                Alert alert = new Alert(ERROR, "Произошла ошибка при удалении кафедры!");
                alert.show();
            }
        }
    private void getCinemaHalls() {

        Runner.sendData(new ClientRequest("getAllCinemaHalls", new HashMap<>()));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> data = response.getData();
            List productData = (List) data.get("cinemaHalls");
            //cinemaHalls = parser.cinemaHalls(productData);

        }
    }
}

