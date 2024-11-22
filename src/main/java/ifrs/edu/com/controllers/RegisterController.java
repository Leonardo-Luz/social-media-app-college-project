package ifrs.edu.com.controllers;

import java.io.IOException;

import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RegisterController {
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
        UserDAO service = new UserDAO();

        User newUser = new User(nameInput.getText(), usernameInput.getText(), passwordInput.getText());

        if (!service.insert(newUser)) {
            System.out.println("New user succefully inserted.");
            changeScene("/views/main.fxml");
        } else
            System.err.println("Error on user insert!");
    }

    @FXML
    private void loginSceneHandler(ActionEvent ev) throws IOException {
        changeScene("/views/login.fxml");
    }

    private void changeScene(String route) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
        BorderPane page = loader.load();

        Stage stage = (Stage) toLoginButton.getScene().getWindow();
        Scene scene = new Scene(page, 340, 480);
        stage.setScene(scene);
        stage.setTitle("Chat");
        stage.show();
    }
}
