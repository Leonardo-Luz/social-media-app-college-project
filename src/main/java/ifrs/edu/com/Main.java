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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;

    public static void runTests() {
        System.out.println("User Model Test: " + (UserModelTest.run() ? "PASS" : "FAIL"));
        System.out.println("User Service Test: " + (UserDAOTest.run() ? "PASS" : "FAIL"));

        System.out.println("Message Model Test: " + (MessageModelTest.run() ? "PASS" : "FAIL"));
        System.out.println("Message Service Test: " + (MessageDAOTest.run() ? "PASS" : "FAIL"));

        System.out.println("Chat Model Test: " + (ChatModelTest.run() ? "PASS" : "FAIL"));
        System.out.println("Chat Service Test: " + (ChatDAOTest.run() ? "PASS" : "FAIL"));
    }

    private void addQuitApplication(Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
            if (KeyCode.ESCAPE.equals(ev.getCode()))
                stage.close();
        });
    }

    public static void loadView(String route) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(String.format("/views/%s.fxml", route)));
            BorderPane page = loader.load();

            Scene scene = new Scene(page, 340, 480);

            Main.stage.setScene(scene);
            Main.stage.setTitle("Chat");
            Main.stage.show();
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    @Override
    public void start(Stage firstStage) {
        stage = firstStage;
        addQuitApplication(stage);

        // runTests();

        Font.font("Fira Code");

        Main.loadView("login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
