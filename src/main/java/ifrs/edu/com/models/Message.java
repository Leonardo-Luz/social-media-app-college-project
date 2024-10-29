package ifrs.edu.com.models;

import java.sql.Date;

public class Message {
    private int messageId;
    private String text;
    private User user;
    private Chat chat;
    private Date createdAt;
    private Date updatedAt;

    Message(){}
    Message(int messageId, String text, int userId, int chatId, Date createdAt, Date updatedAt){
        this.setMessageId(messageId);
        this.setText(text);

        this.setUser(user);
        this.setChat(chat);

        this.setUpdatedAt(createdAt);
        this.setCreatedAt(updatedAt);
    }
    
    public Chat getChat() {
        return chat;
    }
    private void setChat(Chat chat) {
        this.chat = chat;
    }

    public int getMessageId() {
        return messageId;
    }
    private void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }
    private void setUser(User user) {
        this.user = user;
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
