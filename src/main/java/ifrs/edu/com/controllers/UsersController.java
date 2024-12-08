package ifrs.edu.com.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;

import ifrs.edu.com.Main;
import ifrs.edu.com.config.WebSocketConfig;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import ifrs.edu.com.services.UserDAO;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UsersController {
    private ObservableList<User> users;

    @FXML
    private TableColumn<User, Integer> columnId;

    @FXML
    private TableColumn<User, String> columnName;

    @FXML
    private TableColumn<User, String> columnUsername;

    @FXML
    private TableColumn<User, String> columnOnline;

    @FXML
    private TableView<User> usersTable;

    private void loadUserProprieties() {
        User selected = this.usersTable.getSelectionModel().getSelectedItem();
        // UserDAO service = new UserDAO();

        if (selected != null) {
            Stage popup = new Stage();
            popup.addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
                if (KeyCode.ESCAPE.equals(ev.getCode()))
                    popup.close();
            });

            Label label = new Label(selected.toString());
            // Button deleteButton = new Button("Delete");
            // deleteButton.setOnAction(ev -> {
            // if (!service.delete(selected.getUserId())) {
            // System.out.println("User Deleted!");
            // loadTable();
            // popup.close();
            // } else
            // System.out.println("Error on user delete!");
            // });
            Button chatButton = new Button("Chat Privado");
            chatButton.setOnAction(ev -> {
                PrivateChatController.targetUser = selected.getUsername();
                Main.loadView("privateChat");
                popup.close();
            });
            // VBox pane = new VBox(label, deleteButton, chatButton);
            VBox pane = new VBox(label, chatButton);
            pane.setAlignment(Pos.CENTER);
            pane.setSpacing(12);
            pane.minHeight(140);
            pane.minWidth(100);

            Scene window = new Scene(pane, 160, 180);
            popup.setScene(window);
            popup.setTitle("POPUP");
            popup.setResizable(false);

            popup.show();
        }
    }

    public void loadTable() {
        UserDAO service = new UserDAO();
        this.users = FXCollections.observableArrayList(service.list(100, 0));
        this.usersTable.setItems(users.filtered(user -> user.getUserId() != AuthProvider.getUser().getUserId()));

        usersTable.refresh();
    }

    @FXML
    private void initialize() {
        try {
            WebSocketConfig.startOnUsers(this);
        } catch (Exception err) {
            System.out.println("Erro ao connectar ao WebSocket Server!");
        }

        this.loadTable();

        this.usersTable.getSelectionModel().getTableView().setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && !ev.isConsumed()) {
                ev.consume();

                loadUserProprieties();
            }
        });
        this.usersTable.getSelectionModel().getTableView().setOnKeyTyped(ev -> {
            if (ev.getCharacter().getBytes()[0] == '\r' || ev.getCharacter().getBytes()[0] == '\n') {
                loadUserProprieties();
            }
        });

        columnId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnOnline.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell<User, String> call(TableColumn<User, String> arg0) {
                return new TableCell<User, String>() {
                    private Text text;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!isEmpty()) {
                            User user = getTableView().getItems().get(getIndex());

                            try {
                                text = new Text(
                                        WebSocketConfig.onlineids.contains(String.valueOf(user.getUserId())) ? "Sim"
                                                : "Não");
                            } catch (Exception exception) {
                                text = new Text("Err");
                            }

                            setGraphic(text);
                        } else {
                            text = new Text("");
                            setGraphic(text);
                        }
                    }
                };
            }
        });
        // columnOnline.setCellValueFactory(
        // new Callback<TableColumn.CellDataFeatures<User, String>,
        // ObservableValue<String>>() {
        // @Override
        // public ObservableValue<String> call(TableColumn.CellDataFeatures<User,
        // String> param) {
        // return new SimpleStringProperty(
        // WebSocketConfig.onlineids.contains(String.valueOf(param.getValue().getUserId()))
        // ? "Sim"
        // : "Não");
        // }
        // });
    }

    @FXML
    private void chatSceneHandler(ActionEvent ev) {
        Main.loadView("main");
    }

    @FXML
    private void profileSceneHandler(ActionEvent ev) {
        Main.loadView("profile");
    }
}
