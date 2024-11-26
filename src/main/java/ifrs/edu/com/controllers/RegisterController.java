package ifrs.edu.com.controllers;

import javafx.fxml.FXML;
import javafx.application.Platform;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.services.UserDAO;

public class RegisterController {

    @FXML
    private TextField nameInput;

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private PasswordField confirmPasswordInput;

    @FXML
    private void exitApplication(ActionEvent ev) {
        Platform.exit();
    }

    @FXML
    private void registerHandler(ActionEvent ev) {
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

        AuthProvider.register(service, name, username, password);

        if (AuthProvider.isLogged()) {
            System.out.println("New user succefully inserted.");
            System.out.println("AA: " + AuthProvider.getUser());
            Main.loadView("main");
        } else
            System.err.println("Error on user insert!");
    }

    @FXML
    private void loginSceneHandler(ActionEvent ev) {
        Main.loadView("login");
    }
}
