package ifrs.edu.com.models;

import java.sql.Date;

public class Message {
    private int messageId;
    private String text;
    private User user;
    private Chat chat;
    private Date createdAt;
    private Date updatedAt;

    public Message() {
    }

    public Message(int messageId, String text, User user, Chat chat, Date createdAt, Date updatedAt) {
        this.setMessageId(messageId);
        this.setText(text);

        this.setUser(user);
        this.setChat(chat);

        this.setUpdatedAt(createdAt);
        this.setCreatedAt(updatedAt);
    }

    // Request Body
    public Message(String text, User user, Chat chat) {
        this.setText(text);

        this.setUser(user);
        this.setChat(chat);
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

    @Override
    public String toString() {
        return "\nMessage " + this.messageId +
                "\nSender: " + this.user.getUsername() +
                "\nChat: " + this.chat.getTitle() +
                "\nText: " + this.text;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Message &&
                ((Message) obj).getText().equals(this.getText()) &&
                ((Message) obj).getUser().equals(this.getUser()) &&
                ((Message) obj).getChat().equals(this.getChat());
    }
}
