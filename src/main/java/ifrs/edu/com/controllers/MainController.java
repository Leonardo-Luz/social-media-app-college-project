
package ifrs.edu.com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button button;

    @FXML
    private void initialize() {
        // Initialize the button action
        button.setOnAction(event -> {
            System.out.println("Button clicked!");
        });
    }
}
