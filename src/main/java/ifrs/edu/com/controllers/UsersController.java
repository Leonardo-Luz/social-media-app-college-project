package ifrs.edu.com.controllers;

import javafx.fxml.FXML;
import java.io.IOException;

import ifrs.edu.com.models.User;
import ifrs.edu.com.service.UserDAO;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersController {
    SceneController sceneController = new SceneController();
    private ObservableList<User> users;

    @FXML
    private Button chat;

    @FXML
    private TableColumn<User, Integer> columnId;

    @FXML
    private TableColumn<User, String> columnName;

    @FXML
    private TableColumn<User, String> columnUsername;

    @FXML
    private TableView<User> usersTable;

    private void loadTable() {
        UserDAO service = new UserDAO();
        this.users = FXCollections.observableArrayList(service.list(100, 0));
        this.usersTable.setItems(users);
    }

    @FXML
    private void initialize() {
        this.loadTable();

        columnId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    @FXML
    private void chatSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/main.fxml", chat);
    }

    @FXML
    private void profileSceneHandler(ActionEvent ev) throws IOException {
        sceneController.changeScene("/views/profile.fxml", chat);
    }
}
