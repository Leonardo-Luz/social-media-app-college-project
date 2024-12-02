package ifrs.edu.com.services;

import ifrs.edu.com.config.Database;
import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.Message;
import ifrs.edu.com.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements DAO<Message> {
    private static Connection db;

    public static boolean connection() {
        try {
            MessageDAO.db = Database.connect();
            return true;
        } catch (SQLException err) {
            System.out.println(err);
            return false;
        }
    }

    @Override
    public boolean insert(Message model) {
        try {
            String query;

            if (model.getMessageId() != 0)
                query = "INSERT INTO message (text, usersid, chatid, messageid) VALUES (?, ?, ?, ?)";
            else
                query = "INSERT INTO message (text, usersid, chatid) VALUES (?, ?, ?)";
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setString(1, model.getText());
            ps.setInt(2, model.getUser().getUserId());
            ps.setInt(3, model.getChat().getChatId());

            if (model.getMessageId() != 0)
                ps.setInt(4, model.getMessageId());

            return ps.execute();

        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            String query = "DELETE FROM message WHERE messageid= ?";
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            return ps.execute();
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public boolean update(Message model) {
        try {
            String query = """
                        UPDATE message SET text=?, usersid=?, chatid=?
                        WHERE messageid = ?
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setString(1, model.getText());
            ps.setInt(2, model.getUser().getUserId());
            ps.setInt(3, model.getChat().getChatId());

            ps.setInt(4, model.getMessageId());

            return ps.executeUpdate() > 0;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public List<Message> list(int limit, int offset) {
        try {
            UserDAO userService = new UserDAO();
            ChatDAO chatService = new ChatDAO();

            List<Message> list = new ArrayList<>();

            String query = """
                        SELECT messageid, text, usersid, chatid, createdat, updatedat FROM message
                        LIMIT ? OFFSET ?
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        userService.get(response.getInt("usersid")),
                        chatService.get(response.getInt("chatid")),
                        response.getDate("createdat"),
                        response.getDate("updatedat")));
            }

            return list;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    public List<Message> chatList(int chatId, int limit, int offset) {
        try {
            UserDAO userService = new UserDAO();
            ChatDAO chatService = new ChatDAO();

            List<Message> list = new ArrayList<>();

            String query = """
                        SELECT messageid, text, usersid, chatid, createdat, updatedat FROM message
                        WHERE chatid = ?
                        LIMIT ? OFFSET ?
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, chatId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        userService.get(response.getInt("usersid")),
                        chatService.get(response.getInt("chatid")),
                        response.getDate("createdat"),
                        response.getDate("updatedat")));
            }

            return list;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    @Override
    public Message get(int id) {
        try {
            UserDAO userService = new UserDAO();
            ChatDAO chatService = new ChatDAO();

            String query = """
                        SELECT messageid, text, usersid, chatid, createdat, updatedat FROM message
                        WHERE messageid=?
                        LIMIT 1
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                User user = userService.get(response.getInt("usersid"));
                Chat chat = chatService.get(response.getInt("chatid"));

                return new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        user,
                        chat,
                        response.getDate("createdat"),
                        response.getDate("updatedat"));
            }

            return null;

        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    public boolean clear(int chatId) {
        try {
            String query = "DELETE FROM message WHERE chatid=?";
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, chatId);

            return ps.execute();
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }
}
