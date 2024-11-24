package ifrs.edu.com.controllers;

import java.io.IOException;

import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfileUpdateController {
    SceneController sceneController = new SceneController();

    @FXML
    private Button chat;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private void initialize() {
        User user = AuthProvider.getUser();

        nameInput.setText(user.getName());
        usernameInput.setText(user.getUsername());
        passwordInput.setText(user.getPassword());
    }

    @FXML
    private void cancelHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/profile.fxml", chat);
    }

    @FXML
    private void confirmHandler(ActionEvent ev) throws IOException {
        User user = AuthProvider.getUser();

        UserDAO service = new UserDAO();
        service.update(
                new User(user.getUserId(), nameInput.getText(), usernameInput.getText(), passwordInput.getText()));

        sceneController.changeScene("/views/profile.fxml", chat);
    }

    @FXML
    private void chatSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/main.fxml", chat);
    }

    @FXML
    private void usersSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/users.fxml", chat);
    }
}
