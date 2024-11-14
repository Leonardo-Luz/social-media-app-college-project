package ifrs.edu.com.service;

import ifrs.edu.com.config.Database;
import ifrs.edu.com.models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements DAO<Message> {
    private Connection db;

    public MessageDAO() {
        try {
            this.db = Database.connect();
        } catch (SQLException err) {
            System.out.println(err);
        }
    }

    @Override
    public boolean insert(Message model) {
        try {
            String query = "INSERT INTO message (text, usersid, chatid) VALUES (?, ?, ?)";
            PreparedStatement ps = this.db.prepareStatement(query);

            ps.setString(1, model.getText());
            ps.setInt(2, model.getUser().getUserId());
            ps.setInt(3, model.getChat().getChatId());

            return ps.execute();

        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            String query = "DELETE FROM chat WHERE chatid = ?";
            PreparedStatement ps = this.db.prepareStatement(query);

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
            PreparedStatement ps = this.db.prepareStatement(query);

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
            List<Message> list = new ArrayList<>();

            String query = """
                        SELECT messageid, text, usersid, chatid, createdat, updatedat FROM message
                        LIMIT ? OFFSET ?
                    """;
            PreparedStatement ps = this.db.prepareStatement(query);

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        response.getInt("usersid"),
                        response.getInt("chatid"),
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
            String query = """
                        SELECT messageid, text, usersid, chatid, createdat, updatedat FROM message
                        WHERE messageid=?
                        LIMIT 1
                    """;
            PreparedStatement ps = this.db.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        response.getInt("usersid"),
                        response.getInt("chatid"),
                        response.getDate("createdat"),
                        response.getDate("updatedat"));
            }

            return null;

        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }
}
