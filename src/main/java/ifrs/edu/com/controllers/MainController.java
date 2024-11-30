package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.services.ChatDAO;
import ifrs.edu.com.services.MessageDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField chatInput;

    @FXML
    private void sendMessageHandler(ActionEvent ev) {
        if (chatInput.getText().trim() == "")
            return;

        ChatDAO chatService = new ChatDAO();
        MessageDAO messageService = new MessageDAO();

        Message newMessage = new Message(chatInput.getText(), AuthProvider.getUser(), chatService.get(1));

        messageService.insert(newMessage);

        chatInput.setText("");
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
