package ifrs.edu.com;

import java.io.IOException;

import ifrs.edu.com.context.AuthProvider;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        AuthProvider auth = new AuthProvider();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root, 640, 480);
            stage.setScene(scene);
            stage.setTitle("Chat");
            stage.show();
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
