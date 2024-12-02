package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.config.ChatConfig;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.services.ChatDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PrivateChatController {
    public static String targetUser;

    private ChatConfig chat;

    @FXML
    private TextField chatInput;

    @FXML
    private TableColumn<Message, String> columnMessage;

    @FXML
    private TableView<Message> messagesTable;

    @FXML
    private void initialize() {
        ChatDAO service = new ChatDAO();

        Integer chatId = null;

        String chatTitle = AuthProvider.getUser().getUsername() + " - " + targetUser;

        Chat aux = service.getPrivate(chatTitle);

        if (aux == null) {
            service.insertPrivate(new Chat(chatTitle, AuthProvider.getUser()));
            aux = service.getPrivate(chatTitle);
        }
        chatId = aux.getChatId();

        ChatConfig chat = new ChatConfig(chatId, messagesTable, chatInput, columnMessage);

        chat.chatStart();
    }

    @FXML
    private void sendMessageHandler() {
        chat.sendMessageHandler();
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
