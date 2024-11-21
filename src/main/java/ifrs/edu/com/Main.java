package ifrs.edu.com;

import java.io.IOException;

import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.tests.models.ChatModelTest;
import ifrs.edu.com.tests.models.MessageModelTest;
import ifrs.edu.com.tests.models.UserModelTest;
import ifrs.edu.com.tests.services.ChatDAOTest;
import ifrs.edu.com.tests.services.MessageDAOTest;
import ifrs.edu.com.tests.services.UserDAOTest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void tests() {
        System.out.println("User Model Test: " + (UserModelTest.run() ? "PASS" : "DIDNT PASS"));
        System.out.println("User Service Test: " + (UserDAOTest.run() ? "PASS" : "DIDNT PASS"));

        System.out.println("Message Model Test: " + (MessageModelTest.run() ? "PASS" : "DIDNT PASS"));
        System.out.println("Message Service Test: " + (MessageDAOTest.run() ? "PASS" : "DIDNT PASS"));

        System.out.println("Chat Model Test: " + (ChatModelTest.run() ? "PASS" : "DIDNT PASS"));
        System.out.println("Chat Service Test: " + (ChatDAOTest.run() ? "PASS" : "DIDNT PASS"));
    }

    @Override
    public void start(Stage stage) {
        AuthProvider auth = new AuthProvider();

        // tests();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root, 340, 480);
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
