package ifrs.edu.com.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button toRegisterButton;

    @FXML
    private void exitApplication(ActionEvent ev) throws IOException {
        Platform.exit();
    }

    @FXML
    private void loginHandler(ActionEvent ev) throws IOException {
        System.out.println("login...");
    }

    @FXML
    private void registerSceneHandler(ActionEvent ev) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
        BorderPane page = loader.load();

        Stage stage = (Stage) toRegisterButton.getScene().getWindow();
        Scene scene = new Scene(page, 340, 480);
        stage.setScene(scene);
        stage.setTitle("Chat");
        stage.show();
    }
}
