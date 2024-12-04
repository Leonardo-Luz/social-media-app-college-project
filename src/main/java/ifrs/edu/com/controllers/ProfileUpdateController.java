package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import ifrs.edu.com.services.UserDAO;
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
    private PasswordField confirmPasswordInput;

    @FXML
    private void initialize() {
        User user = AuthProvider.getUser();

        nameInput.setText(user.getName());
        usernameInput.setText(user.getUsername());
        passwordInput.setText(user.getPassword());
        confirmPasswordInput.setText(user.getPassword());
    }

    @FXML
    private void cancelHandler(ActionEvent ev) {
        Main.loadView("profile");
    }

    @FXML
    private void confirmHandler(ActionEvent ev) {
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

        User user = AuthProvider.getUser();
        user.setName(nameInput.getText());
        user.setUsername(usernameInput.getText());
        user.setPassword(passwordInput.getText());

        UserDAO service = new UserDAO();
        service.update(user);

        AuthProvider.login(user.getUsername(), user.getPassword());
        Main.loadView("profile");
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
