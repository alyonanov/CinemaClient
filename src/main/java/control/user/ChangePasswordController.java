package control.user;

import cooper.ClientRequest;
import cooper.ServerResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import main.Runner;
import usage.hash.PasswordHashKeeper;
import usage.confirm.UserInfoConfirm;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class ChangePasswordController {

    private UserInfoConfirm validator = UserInfoConfirm.getInstance();
    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();

    @FXML
    private PasswordField currentPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField confirmedPassword;

    @FXML
    private Button save;

    @FXML
    private Button cancel;

    @FXML
    private void initialize() {
        save.setOnAction(event -> saveChanges());
        cancel.setOnAction(event -> cancel.getScene().getWindow().hide());
    }

    private void saveChanges() {
        if (validator.validatePasswords(currentPassword.getText(), newPassword.getText(), confirmedPassword.getText())) {
            String currentEncoded = keeper.generateHash(Runner.getUsername(), currentPassword.getText());
            String newEncoded = keeper.generateHash(Runner.getUsername(), newPassword.getText());
            Map<String, Object> map = new HashMap<>();
            map.put("userId", Runner.getUserId());
            map.put("currentPassword", currentEncoded);
            map.put("newPassword", newEncoded);
            Runner.sendData(new ClientRequest("changePassword", map));
            ServerResponse response = Runner.getData();
            if (!response.isError()) {
                save.getScene().getWindow().hide();
                Alert alert = new Alert(INFORMATION, "Пароль изменён");
                alert.show();
            } else {
                Alert alert = new Alert(ERROR, "Ошибка при изменении пароля");
                alert.show();
            }
        } else {
            Alert alert = new Alert(ERROR, "Пароль некорректный (не менее 4 символов)");
            alert.show();
        }
    }
}
