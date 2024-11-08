
package ifrs.edu.com.controllers;

import java.sql.SQLException;

import ifrs.edu.com.service.MessageDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button button;

    @FXML
    private void initialize() {

        // Initialize the button action
        button.setOnAction(event -> {
            try {
                MessageDAO service = new MessageDAO();

                System.out.println(service.get(1).getText());
            } catch (SQLException err) {
                System.out.println(err);
            }
        });

    }
}
