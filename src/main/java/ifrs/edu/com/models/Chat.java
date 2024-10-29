package ifrs.edu.com.models;

import java.sql.Date;
import java.util.List;

public class Chat {
    private int chatId;
    private String title;
    private List<User> users;
    private User admin;
    private Date createdAt;
    private Date updatedAt;

    Chat(){}
    Chat(int chatId, String title, List<User> users, User admin, Date createdAt, Date updatedAt){
        this.setChatId(chatId);
        this.setTitle(title);

        this.setAdmin(admin);
        this.setUsers(users);

        this.setUpdatedAt(createdAt);
        this.setCreatedAt(updatedAt);
    }

    public int getChatId() {
        return chatId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getAdmin() {
        return admin;
    }
    private void setAdmin(User admin) {
        this.admin = admin;
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
    
}
