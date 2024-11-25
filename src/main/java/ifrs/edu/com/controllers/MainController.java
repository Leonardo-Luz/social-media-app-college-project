package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
    @FXML
    private void usersSceneHandler(ActionEvent ev) {
        Main.loadView("users");
    }

    @FXML
    private void profileSceneHandler(ActionEvent ev) {
        Main.loadView("profile");
    }
}
