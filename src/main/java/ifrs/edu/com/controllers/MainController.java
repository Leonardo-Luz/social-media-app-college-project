
package ifrs.edu.com.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    SceneController sceneController = new SceneController();

    @FXML
    private Button chat;

    @FXML
    private void usersSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/users.fxml", chat);
    }

    @FXML
    private void profileSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/profile.fxml", chat);
    }
}
