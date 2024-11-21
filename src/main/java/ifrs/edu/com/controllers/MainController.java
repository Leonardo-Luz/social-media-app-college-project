
package ifrs.edu.com.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button button;

    @FXML
    private void loginSceneHandler(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        BorderPane page = loader.load();

        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(page, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Chat");
        stage.show();
    }

    @FXML
    private void registerSceneHandler(ActionEvent ev) throws IOException {
        System.out.println("CHANGE SCENE");
    }
}
