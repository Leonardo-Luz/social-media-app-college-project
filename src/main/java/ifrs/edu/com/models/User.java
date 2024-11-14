package ifrs.edu.com.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ifrs.edu.com.service.UserDAO;

public class User {
    private int userId;
    private String name;
    private String username;
    private String password;
    private List<User> friends;
    // private List<User> blocks;
    private Date createdAt;
    private Date updatedAt;

    public User() {
    }

    public User(int userId, String name, String username, String password, int[] friendsId, UserDAO service,
            Date createdAt, Date updatedAt) {
        friends = new ArrayList<>();

        this.setUserId(userId);
        this.setPassword(password);
        this.setName(name);
        this.setUsername(username);

        for (int friendId : friendsId) {
            addFriend(friendId, service);
        }

        this.setUpdatedAt(createdAt);
        this.setCreatedAt(updatedAt);
    }

    public User(int userId, String name, String username, String password, Date createdAt, Date updatedAt) {
        this.setUserId(userId);
        this.setPassword(password);
        this.setName(name);
        this.setUsername(username);

        this.setUpdatedAt(createdAt);
        this.setCreatedAt(updatedAt);
    }

    public User(String name, String username, String password) { // User Request Body
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
    }

    public int getUserId() {
        return userId;
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String aux = name.trim();

        if (aux.length() <= 3)
            throw new Error("Nome precisa ter ao menos 3 caracteres");

        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        String aux = username.trim();

        if (aux.length() <= 3)
            throw new Error("Usuário precisa ter ao menos 3 caracteres");

        this.username = username;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void addFriend(int friendId, UserDAO service) {
        friends.add(service.get(friendId));
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "\nUser " + this.userId +
                "\nName: " + this.name +
                "\nUsername: " + this.username +
                "\nPassword: " + this.password;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User &&
                ((User) obj).getUserId() == this.getUserId() &&
                ((User) obj).getUsername().equals(this.getUsername());
    }
}
