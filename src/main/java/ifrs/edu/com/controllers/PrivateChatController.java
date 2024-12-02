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

    private ChatConfig chatConfig;

    private Chat chat;

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

        chat = service.getPrivate(chatTitle);

        if (chat == null) {
            chatTitle = targetUser + " - " + AuthProvider.getUser().getUsername();
            chat = service.getPrivate(chatTitle);
        }

        if (chat == null) {
            service.insertPrivate(new Chat(chatTitle, AuthProvider.getUser()));
            chat = service.getPrivate(chatTitle);
        }
        chatId = chat.getChatId();

        chatConfig = new ChatConfig(chatId, messagesTable, chatInput, columnMessage);

        chatConfig.chatStart();
    }

    @FXML
    private void sendMessageHandler() {
        chatConfig.sendMessageHandler();
    }

    @FXML
    private void deleteChatHandler() {
        ChatDAO service = new ChatDAO();

        service.delete(chat.getChatId());
        Main.loadView("users");
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
