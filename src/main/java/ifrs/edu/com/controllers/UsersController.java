package ifrs.edu.com.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;

import ifrs.edu.com.Main;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.models.User;
import ifrs.edu.com.services.UserDAO;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UsersController {
    private ObservableList<User> users;

    @FXML
    private TableColumn<User, Integer> columnId;

    @FXML
    private TableColumn<User, String> columnName;

    @FXML
    private TableColumn<User, String> columnUsername;

    @FXML
    private TableView<User> usersTable;

    private void loadUserProprieties() {
        User selected = this.usersTable.getSelectionModel().getSelectedItem();
        UserDAO service = new UserDAO();

        if (selected != null) {
            Stage popup = new Stage();
            popup.addEventHandler(KeyEvent.KEY_RELEASED, ev -> {
                if (KeyCode.ESCAPE.equals(ev.getCode()))
                    popup.close();
            });

            Label label = new Label(selected.toString());
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(ev -> {
                if (!service.delete(selected.getUserId())) {
                    System.out.println("User Deleted!");
                    loadTable();
                    popup.close();
                } else
                    System.out.println("Error on user delete!");
            });
            Button chatButton = new Button("Chat Privado");
            chatButton.setOnAction(ev -> {
                PrivateChatController.targetUser = selected.getUsername();
                Main.loadView("privateChat");
                popup.close();
            });
            VBox pane = new VBox(label, deleteButton, chatButton);
            pane.setAlignment(Pos.CENTER);
            pane.setSpacing(12);
            pane.minHeight(140);
            pane.minWidth(100);

            Scene window = new Scene(pane, 100, 100);
            popup.setScene(window);
            popup.setTitle("POPUP");

            popup.show();
        }
    }

    private void loadTable() {
        UserDAO service = new UserDAO();
        this.users = FXCollections.observableArrayList(service.list(100, 0));
        this.usersTable.setItems(users.filtered(user -> user.getUserId() != AuthProvider.getUser().getUserId()));
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
    }

    @FXML
    private void initialize() {
        this.loadTable();

        columnId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
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
