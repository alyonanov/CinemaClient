package control.user;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.User;
import entities.UserStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.Runner;
import usage.MapParser;
import usage.confirm.SceneChanger;

import java.util.HashMap;
import java.util.Map;

public class ProfileController {

    @FXML
    private Text name;

    @FXML
    private Text username;

    @FXML
    private Text status;

    @FXML
    private Text email;

    @FXML
    private Button changePassword;

    @FXML
    private Button changeEmail;

    @FXML
    private Button back;

    @FXML
    private Button main;

    private MapParser parser = MapParser.getInstance();
    private User user;

    @FXML
    private void initialize() {
        fillInformation();
        changePassword.setOnAction(event -> SceneChanger.getInstance().changeScene("/fxml/change-password.fxml"));
        changeEmail.setOnAction(event -> {
            SceneChanger.getInstance().changeSceneAndWait("/fxml/change-email.fxml");
            fillInformation();
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

    private void getUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Runner.getUserId());
        Runner.sendData(new ClientRequest("getUser", map));
        ServerResponse response = Runner.getData();
        if (!response.isError()) {
            Map<String, Object> userMap = response.getData();
            user = parser.user((Map<String, Object>) userMap.get("user"));
        }
    }

    private void fillInformation() {
        getUser();
        name.setText(user.getFirstName() + " " + user.getLastName());
        username.setText(user.getUsername());
        UserStatus userStatus = user.getUserStatus();
        status.setText(userStatus.getStatusName());
        email.setText(user.getEmail());
    }
}

