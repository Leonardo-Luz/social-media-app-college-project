
package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileController {

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
    private void updateSceneHandler(ActionEvent ev) {
        Main.loadView("profileUpdate");
    }

    @FXML
    private void logoutHandler(ActionEvent ev) {
        AuthProvider.logout();

        Main.loadView("login");
    }

    @FXML
    private void deleteHandler(ActionEvent ev) {
        AuthProvider.deleteAccount();

        Main.loadView("login");
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
