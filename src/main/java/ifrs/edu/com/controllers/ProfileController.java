
package ifrs.edu.com.controllers;

import java.io.IOException;

import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProfileController {
    SceneController sceneController = new SceneController();

    @FXML
    private Button chat;

    @FXML
    private Label name;

    @FXML
    private Label username;

    @FXML
    private void initialize() {
        User user = AuthProvider.getUser();

        name.setText(user.getName());
        username.setText(user.getUsername());
    }

    @FXML
    private void deleteHandler(ActionEvent ev) {
        UserDAO service = new UserDAO();
        AuthProvider.deleteAccount(service);
    }

    @FXML
    private void chatSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/main.fxml", chat);
    }

    @FXML
    private void usersSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/users.fxml", chat);
    }
}
