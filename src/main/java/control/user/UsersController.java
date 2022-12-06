package control.user;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.User;
import entities.attribute.UserAttribute;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class UsersController {

    @FXML
    private TableView<UserAttribute> userTable;

    @FXML
    private TableColumn<UserAttribute, String> usernameColumn;

    @FXML
    private TableColumn<UserAttribute, String> nameColumn;

    @FXML
    private TableColumn<UserAttribute, String> surnameColumn;

    @FXML
    private TableColumn<UserAttribute, String> statusColumn;

    @FXML
    private TableColumn<UserAttribute, String> emailColumn;

    @FXML
    private Button changeToAdmin;

    @FXML
    private Button changeToUser;

    @FXML
    private Button back;

    @FXML
    private Button main;

    private List<User> users;
    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {
        fillUserTable();

//        changeToAdmin.setOnAction(event -> {
//            changeUserStatus(1);
//            fillUserTable();
//        });
//        changeToUser.setOnAction(event -> {
//            changeUserStatus(2);
//            fillUserTable();
//        });
        main.setOnAction(event -> {
            main.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/main.fxml");
        });
    }

    private void getUsers() {
        Runner.sendData(new ClientRequest("getAllUsers", new HashMap<>()));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> userMap = response.getData();
            List userData = (List) userMap.get("users");
            users = parser.users(userData);
        }
    }

    private void fillUserTable() {
        getUsers();
        List<UserAttribute> userAttributes = new ArrayList<>();
        users.forEach(user -> userAttributes.add(new UserAttribute(user)));
        userTable.setItems(FXCollections.observableArrayList(userAttributes));
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserStatus().getStatusName()));
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        //viewActionsForUser(null);
        userTable.getSelectionModel()
                .selectedItemProperty();
               // .addListener((observable, oldValue, newValue) -> viewActionsForUser(newValue));
    }

//    private void changeUserStatus(int status) {
//        UserAttribute user = userTable.getSelectionModel().getSelectedItem();
//        int userId = user.getUserId();
//        Map<String, Object> data = new HashMap<>();
//        data.put("userId", userId);
//        data.put("statusId", status);
//        Runner.sendData(new ClientRequest("changeUserStatus", data));
//        ServerResponse response = Runner.getData();
//        if (!response.isError()) {
//            getUsers();
//            Alert alert = new Alert(INFORMATION, "Статус пользователя изменен!");
//            alert.show();
//        } else {
//            Alert alert = new Alert(ERROR, "Произошла ошибка!");
//            alert.show();
//        }
//    }

//    private void viewActionsForUser(UserAttribute user) {
//        if (user != null) {
//
//            if ("Администратор".equals(user.getUserStatus().getStatusName())) {
//                changeToAdmin.setVisible(false);
//                changeToUser.setVisible(false);
//            } else {
//                changeToAdmin.setVisible(true);
//                changeToUser.setVisible(false);
//            }
//        } else {
//            changeToAdmin.setVisible(false);
//            changeToUser.setVisible(false);
//        }
//    }
}

