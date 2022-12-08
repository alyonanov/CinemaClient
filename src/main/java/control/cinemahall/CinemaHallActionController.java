package control.cinemahall;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.CinemaHall;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Runner;
import usage.MapParser;
import usage.confirm.SceneChanger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class CinemaHallActionController {

    @FXML
    private Button save;

    @FXML
    private Button cancel;

    @FXML
    private TextField hallType;

   @FXML
   private TextField hallSeatsNumber;

    @FXML
    private Label label;

    private CinemaHall cinemaHall;

    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {

        if (Objects.nonNull(SceneChanger.getInstance().getDataId())) {
            this.label.setText("Изменение кафедры");
            save.setText("Изменить");
            this.getCinemaHall(SceneChanger.getInstance().getDataId());
            this.hallType.setText(this.cinemaHall.getHallType());
            save.setOnAction(event -> update());
        } else {
            this.label.setText("Добавление кафедры");
            save.setText("Сохранить");
            save.setOnAction(event -> add());
        }
        cancel.setOnAction(event -> {
                    clear();
                    cancel.getScene().getWindow().hide();
                }
        );
    }


    private void add() {
        String hallType = this.hallType.getText();
        String hallSeatsNumberText = this.hallSeatsNumber.getText();

        if (Objects.nonNull(hallType) && !hallType.isEmpty() && hallType.length() > 3 && hallType.length() <= 50) {
            Map<String, Object> data = new HashMap<>();

            data.put("hallType", hallType);
            data.put("hallSeatsNumber", Integer.parseInt(hallSeatsNumberText));
            Runner.sendData(new ClientRequest("addCinemaHall", data));
            ServerResponse response = Runner.getData();
            if (response.isError()) {
                Alert alert = new Alert(ERROR, "Информация некорректна!");
                alert.show();
            } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Hall добавлена!");
            alert.show();
            clear();
            save.getScene().getWindow().hide();
        }
        } else {
            Alert alert = new Alert(ERROR, "Информация некорректна:\n" +
                    "Название кафедры должно быть более 3 символов но менее 50!");
            alert.show();
        }
    }

    private void clear() {
        SceneChanger.getInstance().setDataId(null);
        this.hallType.setText("");
    }

    private void update() {
        String hallType = this.hallType.getText();

        if (Objects.nonNull(hallType) && !hallType.isEmpty() && hallType.length() > 3 && hallType.length() <= 50) {
            Map<String, Object> data = new HashMap<>();

            data.put("hallId", this.cinemaHall.getHallId());
            data.put("hallType", hallType);

            Runner.sendData(new ClientRequest("updateCinemaHall", data));
            ServerResponse response = Runner.getData();
            if (response.isError()) {
                Alert alert = new Alert(ERROR, "Информация некорректна!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Кафедра обновлена!");
                alert.show();

                clear();
                save.getScene().getWindow().hide();
            }
        } else {
            Alert alert = new Alert(ERROR, "Информация некорректна:\n" +
                    "Название кинозала должно быть более 3 символов но менее 50!");
            alert.show();
        }
    }


    private void getCinemaHall(int hallId) {
        Map<String, Object> data = new HashMap<>();
        data.put("hallId", hallId);
        Runner.sendData(new ClientRequest("getCinemaHallById", data));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> workerMap = response.getData();
            cinemaHall = parser.cinemaHall((Map<String, Object>) workerMap.get("cinemaHall"));
        }
    }

}
