package control;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.Runner;
import usage.confirm.SceneChanger;

public class MainController {

    @FXML
    private Button workers;

    @FXML
    private Button myProfile;

    @FXML
    private Button allUsers;

    @FXML
    private Button allDepartments;


    @FXML
    private Button back;

    private final SceneChanger sceneChanger = SceneChanger.getInstance();

    @FXML
    private void initialize() {
        if (!"Администратор".equals(Runner.getStatus().getStatusName())) {
            allUsers.setVisible(false);
        }

        workers.setOnAction(event -> {
            workers.getScene().getWindow().hide();
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

        allDepartments.setOnAction(event -> {
            allDepartments.getScene().getWindow().hide();
            sceneChanger.changeScene("/fxml/cinemahall_list.fxml");
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