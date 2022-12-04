package control.user;

import cooper.ClientRequest;
import cooper.ServerResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Runner;
import usage.confirm.SceneChanger;
import usage.hash.PasswordHashKeeper;
import usage.confirm.UserInfoConfirm;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class SignUpController {

    private UserInfoConfirm validator = UserInfoConfirm.getInstance();
    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();

    @FXML
    private TextField firstName;

    @FXML
    private TextField username;

    @FXML
    private Button signUp;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmedPassword;

    @FXML
    private Button index;

    @FXML
    private Button previous;

    @FXML
    private Button next;

    @FXML
    private Button back;

    @FXML
    private Button exit;

    @FXML
    private void initialize() {
        previous.setVisible(false);
        signUp.setOnAction(event -> processSignUp());
        index.setOnAction(event -> {
            index.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/index.fxml");
        });
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/fxml/index.fxml");
        });
        exit.setOnAction(event -> exit.getScene().getWindow().hide());
    }

    private void processSignUp() {
        String usernameText = username.getText();
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        String emailText = email.getText();
        String passwordText = password.getText();
        String confirmedPasswordText = confirmedPassword.getText();
        if (validator.validate(usernameText, firstNameText, lastNameText, emailText, passwordText, confirmedPasswordText)) {
            String encoded = keeper.generateHash(usernameText, passwordText);
            Map<String, Object> map = new HashMap<>();
            map.put("username", usernameText);
            map.put("firstName", firstNameText);
            map.put("lastName", lastNameText);
            map.put("email", emailText);
            map.put("password", encoded);
            Runner.sendData(new ClientRequest("signUp", map));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                signUp.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/fxml/index.fxml");
                Alert alert = new Alert(INFORMATION, "Регистрация прошла успешно! Теперь вы можете войти в систему.");
                alert.show();
            } else {
                Alert alert = new Alert(ERROR, response.getErrorMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(ERROR, "Информация некорректна:\n" +
                    "1) логин должен состоять из 6-15 символов: латинских букв, дефисов (-) и нижних подчеркиваний (_)\n" +
                    "2) имя и фамилия должны состоять из 2-30 символов\n" +
                    "3) пароль должен состоять из 8-30 символов: латинских букв, дефисов (-) и нижних подчеркиваний (_)\n" +
                    "4) пароли должны совпадать");
            alert.show();
        }
    }
}
