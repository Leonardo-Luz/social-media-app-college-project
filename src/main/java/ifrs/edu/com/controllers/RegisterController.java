package ifrs.edu.com.controllers;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;
import ifrs.edu.com.controllers.SceneController;

public class RegisterController {
    SceneController sceneController = new SceneController();

    @FXML
    private Button toLoginButton;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private PasswordField confirmPasswordInput;

    @FXML
    private void exitApplication(ActionEvent ev) throws IOException {
        Platform.exit();
    }

    @FXML
    private void registerHandler(ActionEvent ev) throws IOException {
        if (nameInput.getText().length() < 3) {
            System.err.println("Name has to be at least 3 letters");

            return;
        } else if (usernameInput.getText().length() < 3) {
            System.err.println("Username has to be at least 3 letters");

            return;
        } else if (passwordInput.getText().length() < 3) {
            System.err.println("Password has to be at least 3 letters");

            return;
        } else if (!passwordInput.getText().equals(confirmPasswordInput.getText())) {
            System.err.println("Passwords dont match");

            return;
        }

        UserDAO service = new UserDAO();

        String name = nameInput.getText();
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (AuthProvider.register(service, name, username, password)) {
            System.out.println("New user succefully inserted.");
            System.out.println("AA: " + AuthProvider.getUser());
            sceneController.changeScene("/views/main.fxml", toLoginButton);
        } else
            System.err.println("Error on user insert!");
    }

    @FXML
    private void loginSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/login.fxml", toLoginButton);
    }
}
