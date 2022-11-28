package control;

import cooperation.ClientRequest;
import cooperation.ServerResponse;
import entities.User;
import main.Runner;
import usage.MapParser;
import usage.hasher.PasswordHashKeeper;
import usage.validator.SceneChanger;
import usage.validator.UserInformationValidator;

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


    private UserInformationValidator validator = UserInformationValidator.getInstance();
    private PasswordHashKeeper hashKeeper = PasswordHashKeeper.getInstance();
    private MapParser parser = MapParser.getInstance();

    @FXML
    private void initialize() {
        signIn.setOnAction(event -> processSignIn());
        signUp.setOnAction(event -> {
            signIn.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/sign-up.fxml");
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
            }
        }
    }
}