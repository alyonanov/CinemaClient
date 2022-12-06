package control;

import cooper.ClientRequest;
import cooper.ServerResponse;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Runner;
import usage.MapParser;
import usage.confirm.SceneChanger;

import usage.hash.PasswordHashKeeper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class IndexController {

    @FXML
    private Button signIn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signUp;

    @FXML
    private Button forgotPassword;

    private MapParser parser = MapParser.getInstance();

    private PasswordHashKeeper hashKeeper = PasswordHashKeeper.getInstance();

    @FXML
    private void initialize() {
        signIn.setOnAction(event -> processSignIn());
        signUp.setOnAction(event -> {
            signIn.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/sign-up.fxml");
        });
        forgotPassword.setOnAction(event -> {
            forgotPassword.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/restore-password.fxml");
        });
    }

    private void processSignIn() {
        String usernameText = username.getText();
        String passwordText = password.getText();
        if (Objects.nonNull(usernameText) && Objects.nonNull(passwordText)) {
            String encoded = hashKeeper.generateHash(usernameText, passwordText);
            Map<String, Object> map = new HashMap<>();
            map.put("username", usernameText);
            map.put("password", encoded);
            Runner.sendData(new ClientRequest("signIn", map));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                User user = parser.user((Map<String, Object>) response.getData().get("user"));
                Runner.setUserId(user.getUserId());
                Runner.setUsername(user.getUsername());
                Runner.setStatus(user.getUserStatus());
                signIn.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/fxml/main.fxml");
                Alert alert = new Alert(INFORMATION, "Вход выполнен успешно!");
                alert.show();
            } else {
                Alert alert = new Alert(ERROR, response.getErrorMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(ERROR, "Информация некорректна:\n" +
                    "1) логин должен состоять из 6-15 символов: латинских букв, дефисов (-) и нижних подчеркиваний (_)\n" +
                    "2) пароль должен состоять из 8-30 символов: латинских букв, дефисов (-) и нижних подчеркиваний (_)");
            alert.show();
        }
    }
}
