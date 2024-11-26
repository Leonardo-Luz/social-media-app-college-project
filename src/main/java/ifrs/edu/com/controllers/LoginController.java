package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.services.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private void exitApplication(ActionEvent ev) {
        Platform.exit();
    }

    @FXML
    private void loginHandler(ActionEvent ev) {
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
            Main.loadView("main");
        } else
            System.err.println("Error on login!");
    }

    @FXML
    private void registerSceneHandler(ActionEvent ev) {
        Main.loadView("register");
    }
}
