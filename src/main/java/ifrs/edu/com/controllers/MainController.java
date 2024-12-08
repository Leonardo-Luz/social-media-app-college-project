package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.config.ChatConfig;
import ifrs.edu.com.models.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

    private ChatConfig chat;

    @FXML
    private TextField chatInput;

    @FXML
    private TableColumn<Message, String> columnMessage;

    @FXML
    private TableView<Message> messagesTable;

    @FXML
    private void initialize() {
        chat = new ChatConfig(1, messagesTable, chatInput, columnMessage);

        chat.chatStart();
    }

    @FXML
    private void sendMessageHandler() {
        this.chat.sendMessageHandler();
    }

    @FXML
    private void usersSceneHandler(ActionEvent ev) {
        Main.loadView("users");
    }

    @FXML
    private void profileSceneHandler(ActionEvent ev) {
        Main.loadView("profile");
    }
}
