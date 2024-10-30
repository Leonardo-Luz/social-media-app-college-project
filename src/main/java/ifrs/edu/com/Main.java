package ifrs.edu.com;

import java.sql.Connection;
import java.sql.SQLException;

import ifrs.edu.com.config.Database;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.service.MessageDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Message test = null;
        
        try{
            Connection db = Database.connect();
    
            MessageDAO service = new MessageDAO(db);

            test = service.get(1);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        Label l = new Label(test != null ? test.getText() : "Error");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}