package ifrs.edu.com.controllers;

import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.models.User;
import ifrs.edu.com.services.ChatDAO;
import ifrs.edu.com.services.MessageDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class MainController {
    private ObservableList<Message> messages;

    @FXML
    private TextField chatInput;

    @FXML
    private TableColumn<Message, String> columnUsername;

    @FXML
    private TableColumn<Message, String> columnMessage;

    @FXML
    private TableView<Message> messagesTable;

    private void loadMessages() {
        MessageDAO service = new MessageDAO();

        this.messages = FXCollections.observableArrayList(service.list(100, 0));
        this.messagesTable.setItems(messages);
    }

    @FXML
    private void initialize() {
        chatInput.setOnKeyTyped(ev -> {
            if (ev.getCharacter().getBytes()[0] == '\r' || ev.getCharacter().getBytes()[0] == '\n') {
                sendMessageHandler();
            }
        });

        this.loadMessages();

        columnUsername.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Message, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Message, String> param) {
                        return new SimpleStringProperty(
                                param.getValue().getUser().getUsername().equals(AuthProvider.getUser().getUsername())
                                        ? "Eu"
                                        : param.getValue().getUser().getUsername());
                    }
                });
        columnMessage.setCellValueFactory(new PropertyValueFactory<>("text"));
    }

    @FXML
    private void sendMessageHandler() {
        if (chatInput.getText().trim().length() < 1)
            return;

        MessageDAO messageService = new MessageDAO();

        if (chatInput.getText().equals("/clear")) {
            messageService.clear();
            chatInput.setText("");
            loadMessages();
            return;
        }

        ChatDAO chatService = new ChatDAO();

        Message newMessage = new Message(chatInput.getText(), AuthProvider.getUser(), chatService.get(1));

        messageService.insert(newMessage);

        chatInput.setText("");
        loadMessages();
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
