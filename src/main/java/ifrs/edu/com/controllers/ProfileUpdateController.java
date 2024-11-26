package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfileUpdateController {
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
    private void cancelHandler(ActionEvent ev) {
        Main.loadView("profile");
    }

    @FXML
    private void confirmHandler(ActionEvent ev) {
        User user = AuthProvider.getUser();
        user.setName(nameInput.getText());
        user.setUsername(usernameInput.getText());
        user.setPassword(passwordInput.getText());

        UserDAO service = new UserDAO();
        service.update(user);

        AuthProvider.logout();
        Main.loadView("login");
    }

    @FXML
    private void chatSceneHandler(ActionEvent ev) {
        Main.loadView("main");
    }

    @FXML
    private void usersSceneHandler(ActionEvent ev) {
        Main.loadView("users");
    }
}
