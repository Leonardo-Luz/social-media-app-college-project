package ifrs.edu.com.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class SceneController {
    public void changeScene(String route, Button button) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
        BorderPane page = loader.load();

        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(page, 340, 480);
        stage.setScene(scene);
        stage.setTitle("Chat");
        stage.show();
    }
}
