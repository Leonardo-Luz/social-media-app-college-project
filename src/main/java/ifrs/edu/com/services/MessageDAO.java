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
            List<Message> list = new ArrayList<>();

            String query = """
                        SELECT
                            m.messageid, m.text, m.createdat AS m_createdat, m.updatedat AS m_updatedat,
                            u.usersid AS u_usersid, u.name AS u_name, u.username AS u_username, u.password AS u_password, u.createdat AS u_createdat,u.updatedat AS u_updatedat,
                            c.chatid AS c_chatid, c.title AS c_title, c.adminid AS c_adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            a.usersid AS a_usersid, a.name AS a_name, a.username AS a_username, a.password AS a_password, a.createdat AS a_createdat,a.updatedat AS a_updatedat
                        FROM message AS m
                        INNER JOIN users AS u ON u.usersid = m.usersid
                        INNER JOIN chat AS c ON c.chatid = m.chatid
                        INNER JOIN users AS a ON a.usersid = c.adminid
                        LIMIT ? OFFSET ?
                        ORDER BY m.messageid ASC
                    """;

            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        new User(
                                response.getInt("u_usersid"),
                                response.getString("u_name"),
                                response.getString("u_username"),
                                response.getString("u_password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        new Chat(
                                response.getInt("c_chatid"),
                                new User(
                                        response.getInt("a_usersid"),
                                        response.getString("a_name"),
                                        response.getString("a_username"),
                                        response.getString("a_password"),
                                        response.getDate("a_createdat"),
                                        response.getDate("a_updatedat")),
                                response.getString("c_title"),
                                response.getDate("c_createdat"),
                                response.getDate("c_updatedat")),
                        response.getDate("m_createdat"),
                        response.getDate("m_updatedat")));
            }

            return list;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    public List<Message> chatList(int chatId) {
        try {
            List<Message> list = new ArrayList<>();

            String query = """
                        SELECT
                            m.messageid, m.text, m.createdat AS m_createdat, m.updatedat AS m_updatedat,
                            u.usersid AS u_usersid, u.name AS u_name, u.username AS u_username, u.password AS u_password, u.createdat AS u_createdat,u.updatedat AS u_updatedat,
                            c.chatid AS c_chatid, c.title AS c_title, c.adminid AS c_adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            a.usersid AS a_usersid, a.name AS a_name, a.username AS a_username, a.password AS a_password, a.createdat AS a_createdat,a.updatedat AS a_updatedat
                        FROM message AS m
                        INNER JOIN users AS u ON u.usersid = m.usersid
                        INNER JOIN chat AS c ON c.chatid = m.chatid
                        INNER JOIN users AS a ON a.usersid = c.adminid
                        WHERE m.chatid=?
                        ORDER BY m.messageid ASC
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, chatId);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        new User(
                                response.getInt("u_usersid"),
                                response.getString("u_name"),
                                response.getString("u_username"),
                                response.getString("u_password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        new Chat(
                                response.getInt("c_chatid"),
                                new User(
                                        response.getInt("a_usersid"),
                                        response.getString("a_name"),
                                        response.getString("a_username"),
                                        response.getString("a_password"),
                                        response.getDate("a_createdat"),
                                        response.getDate("a_updatedat")),
                                response.getString("c_title"),
                                response.getDate("c_createdat"),
                                response.getDate("c_updatedat")),
                        response.getDate("m_createdat"),
                        response.getDate("m_updatedat")));
            }

            return list;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    public List<Message> listByUserId(int usersId, int limit, int offset) {
        try {
            List<Message> list = new ArrayList<>();

            String query = """
                        SELECT
                            m.messageid, m.text, m.createdat AS m_createdat, m.updatedat AS m_updatedat,
                            u.usersid AS u_usersid, u.name AS u_name, u.username AS u_username, u.password AS u_password, u.createdat AS u_createdat,u.updatedat AS u_updatedat,
                            c.chatid AS c_chatid, c.title AS c_title, c.adminid AS c_adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            a.usersid AS a_usersid, a.name AS a_name, a.username AS a_username, a.password AS a_password, a.createdat AS a_createdat,a.updatedat AS a_updatedat
                        FROM message AS m
                        INNER JOIN users AS u ON u.usersid = m.usersid
                        INNER JOIN chat AS c ON c.chatid = m.chatid
                        INNER JOIN users AS a ON a.usersid = c.adminid
                        WHERE m.usersid=?
                        LIMIT ? OFFSET ?
                        ORDER BY m.messageid ASC
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, usersId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        new User(
                                response.getInt("u_usersid"),
                                response.getString("u_name"),
                                response.getString("u_username"),
                                response.getString("u_password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        new Chat(
                                response.getInt("c_chatid"),
                                new User(
                                        response.getInt("a_usersid"),
                                        response.getString("a_name"),
                                        response.getString("a_username"),
                                        response.getString("a_password"),
                                        response.getDate("a_createdat"),
                                        response.getDate("a_updatedat")),
                                response.getString("c_title"),
                                response.getDate("c_createdat"),
                                response.getDate("c_updatedat")),
                        response.getDate("m_createdat"),
                        response.getDate("m_updatedat")));
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
                        SELECT
                            m.messageid, m.text, m.createdat AS m_createdat, m.updatedat AS m_updatedat,
                            u.usersid AS u_usersid, u.name AS u_name, u.username AS u_username, u.password AS u_password, u.createdat AS u_createdat,u.updatedat AS u_updatedat,
                            c.chatid AS c_chatid, c.title AS c_title, c.adminid AS c_adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            a.usersid AS a_usersid, a.name AS a_name, a.username AS a_username, a.password AS a_password, a.createdat AS a_createdat,a.updatedat AS a_updatedat
                        FROM message AS m
                        INNER JOIN users AS u ON u.usersid = m.usersid
                        INNER JOIN chat AS c ON c.chatid = m.chatid
                        INNER JOIN users AS a ON a.usersid = c.adminid
                        WHERE m.messageid=?
                        LIMIT 1
                        ORDER BY m.messageid ASC
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        new User(
                                response.getInt("u_usersid"),
                                response.getString("u_name"),
                                response.getString("u_username"),
                                response.getString("u_password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        new Chat(
                                response.getInt("c_chatid"),
                                new User(
                                        response.getInt("a_usersid"),
                                        response.getString("a_name"),
                                        response.getString("a_username"),
                                        response.getString("a_password"),
                                        response.getDate("a_createdat"),
                                        response.getDate("a_updatedat")),
                                response.getString("c_title"),
                                response.getDate("c_createdat"),
                                response.getDate("c_updatedat")),
                        response.getDate("m_createdat"),
                        response.getDate("m_updatedat"));
            }

            return null;

        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    public boolean clearAll(int chatId) {
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

    public boolean clear(int chatId, int userId) {
        try {
            String query = "DELETE FROM message WHERE chatid=? AND usersid=?";
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, chatId);
            ps.setInt(2, userId);

            return ps.execute();
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    public List<Message> listUserMessages(int chatId, String username) {
        try {
            List<Message> list = new ArrayList<>();

            String query = """
                        SELECT
                            m.messageid, m.text, m.createdat AS m_createdat, m.updatedat AS m_updatedat,
                            u.usersid AS u_usersid, u.name AS u_name, u.username AS u_username, u.password AS u_password, u.createdat AS u_createdat,u.updatedat AS u_updatedat,
                            c.chatid AS c_chatid, c.title AS c_title, c.adminid AS c_adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            a.usersid AS a_usersid, a.name AS a_name, a.username AS a_username, a.password AS a_password, a.createdat AS a_createdat,a.updatedat AS a_updatedat
                        FROM message AS m
                        INNER JOIN users AS u ON u.usersid = m.usersid
                        INNER JOIN chat AS c ON c.chatid = m.chatid
                        INNER JOIN users AS a ON a.usersid = c.adminid
                        WHERE m.chatid=? AND u.username=?
                        ORDER BY m.messageid ASC
                    """;
            PreparedStatement ps = MessageDAO.db.prepareStatement(query);

            ps.setInt(1, chatId);
            ps.setString(2, username);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Message(
                        response.getInt("messageid"),
                        response.getString("text"),
                        new User(
                                response.getInt("u_usersid"),
                                response.getString("u_name"),
                                response.getString("u_username"),
                                response.getString("u_password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        new Chat(
                                response.getInt("c_chatid"),
                                new User(
                                        response.getInt("a_usersid"),
                                        response.getString("a_name"),
                                        response.getString("a_username"),
                                        response.getString("a_password"),
                                        response.getDate("a_createdat"),
                                        response.getDate("a_updatedat")),
                                response.getString("c_title"),
                                response.getDate("c_createdat"),
                                response.getDate("c_updatedat")),
                        response.getDate("m_createdat"),
                        response.getDate("m_updatedat")));
            }

            return list;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }
}
