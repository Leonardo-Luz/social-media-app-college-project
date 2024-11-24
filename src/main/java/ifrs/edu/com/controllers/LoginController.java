package ifrs.edu.com.controllers;

import java.io.IOException;

import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.service.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    SceneController sceneController = new SceneController();

    @FXML
    private Button toRegisterButton;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private void exitApplication(ActionEvent ev) throws IOException {
        Platform.exit();
    }

    @FXML
    private void loginHandler(ActionEvent ev) throws IOException {
        if (usernameInput.getText().length() < 3) {
            System.err.println("Username has to be at least 3 letters");

            return;
        } else if (passwordInput.getText().length() < 3) {
            System.err.println("Password has to be at least 3 letters");

            return;
        }
        UserDAO service = new UserDAO();

        String username = usernameInput.getText();
        String password = passwordInput.getText();

        AuthProvider.login(service, username, password);

        if (AuthProvider.isLogged()) {
            System.out.println("User succefully logged.");
            sceneController.changeScene("/views/main.fxml", toRegisterButton);
        } else
            System.err.println("Error on login!");
    }

    @FXML
    private void registerSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/register.fxml", toRegisterButton);
    }
}
