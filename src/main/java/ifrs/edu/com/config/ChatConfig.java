package ifrs.edu.com.config;

import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.models.User;
import ifrs.edu.com.services.ChatDAO;
import ifrs.edu.com.services.MessageDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ChatConfig {
    private ObservableList<Message> messages;
    private int chatId;

    private TableView<Message> chatTable;
    private TextField chatInput;
    TableColumn<Message, String> messageColumn;

    public ChatConfig(int chatId, TableView<Message> chatTable, TextField chatInput,
            TableColumn<Message, String> messageColumn) {
        this.chatId = chatId;

        this.chatTable = chatTable;
        this.chatInput = chatInput;
        this.messageColumn = messageColumn;
    }

    public void loadTable() {
        MessageDAO service = new MessageDAO();

        this.messages = FXCollections.observableArrayList(service.chatList(chatId, 100, 0));

        chatTable.setItems(this.messages);
        chatTable.scrollTo(chatTable.getItems().size());
    }

    public void chatStart() {
        try {
            WebSocketConfig.start(this);
        } catch (Exception err) {
            System.out.println("Erro ao connectar ao WebSocket Server!");
        }

        chatInput.setOnKeyTyped(ev -> {
            if (ev.getCharacter().getBytes()[0] == '\r' || ev.getCharacter().getBytes()[0] == '\n') {
                sendMessageHandler();
            }
        });

        this.loadTable();

        ChatDAO service = new ChatDAO();

        Text placeholder = new Text("Nenhuma mensagem no Chat");
        chatTable.setPlaceholder(placeholder);

        chatTable.setLayoutX(340);
        messageColumn.setPrefWidth(337.3);
        messageColumn.setText("Chat " + service.get(this.chatId).getTitle());

        messageColumn.setCellFactory(new Callback<TableColumn<Message, String>, TableCell<Message, String>>() {
            @Override
            public TableCell<Message, String> call(TableColumn<Message, String> arg0) {
                return new TableCell<Message, String>() {
                    private Text text;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!isEmpty()) {
                            Message message = getTableView().getItems().get(getIndex());

                            User user = message.getUser();
                            String displayName = "Deleted";

                            if (user != null) {
                                String username = user.getUsername();
                                displayName = username.equals(AuthProvider.getUser().getUsername()) ? "Eu"
                                        : username;
                            }

                            String formattedText = "@" + displayName + ": " + message.getText();

                            text = new Text(formattedText);
                            text.setWrappingWidth(messageColumn.getWidth());
                            this.setWrapText(true);

                            setGraphic(text);
                        } else {
                            text = new Text("");
                            setGraphic(text);
                        }
                    }
                };
            }
        });

        messageColumn.setResizable(true);
    }

    public void clear() {
        MessageDAO messageService = new MessageDAO();

        messageService.clear(chatId);
        chatTable.setItems(null);
        chatInput.setText("");
        loadTable();
    }

    public void sendMessageHandler() {
        if (chatInput.getText().trim().length() < 1)
            return;

        MessageDAO messageService = new MessageDAO();

        ChatDAO chatService = new ChatDAO();

        Message newMessage = new Message(chatInput.getText(), AuthProvider.getUser(), chatService.get(this.chatId));

        if (chatInput.getText().equals("/clear")) {
            clear();
            WebSocketConfig.sendMessage(newMessage.getText());
            return;
        }

        messageService.insert(newMessage);

        WebSocketConfig.sendMessage(newMessage.getText());

        chatInput.setText("");
        loadTable();
    }
}
