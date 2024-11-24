package ifrs.edu.com.controllers;

import java.io.IOException;
import java.util.List;

import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UsersController {
    SceneController sceneController = new SceneController();
    @FXML
    private Button chat;

    @FXML
    private void initialize() {
        UserDAO service = new UserDAO();

        service.list(10, 0).forEach(user -> {
            if (user != null)
                System.out.println(user);
        });
    }

    @FXML
    private void chatSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/main.fxml", chat);
    }

    @FXML
    private void profileSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/profile.fxml", chat);
    }
}
