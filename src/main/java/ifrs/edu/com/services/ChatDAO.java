package ifrs.edu.com.services;

import ifrs.edu.com.config.Database;
import ifrs.edu.com.models.Chat;
import ifrs.edu.com.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements DAO<Chat> {
    private static Connection db;

    public static boolean connection() {
        try {
            ChatDAO.db = Database.connect();
            return true;
        } catch (SQLException err) {
            System.out.println(err);
            return false;
        }
    }

    @Override
    public boolean insert(Chat model) {
        try {

            String query;

            if (model.getChatId() != 0)
                query = "INSERT INTO chat(title, adminid, chatid) VALUES (?, ?, ?)";
            else
                query = "INSERT INTO chat(title, adminid) VALUES (?, ?)";

            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setString(1, model.getTitle());
            ps.setInt(2, model.getAdmin().getUserId());

            if (model.getChatId() != 0)
                ps.setInt(3, model.getChatId());

            return ps.execute();

        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    public boolean insertPrivate(Chat model) {
        try {

            String query;

            query = "INSERT INTO chat(title, adminid) VALUES (?, ?)";

            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setString(1, model.getTitle());
            ps.setInt(2, model.getAdmin().getUserId());

            if (model.getChatId() != 0)
                ps.setInt(3, model.getChatId());

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
            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            return ps.execute();
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public boolean update(Chat model) {
        try {
            String query = """
                        UPDATE chat SET title=?, adminid=?
                        WHERE chatid = ?
                    """;
            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setString(1, model.getTitle());
            ps.setInt(2, model.getAdmin().getUserId());
            ps.setInt(3, model.getChatId());

            // update CHAT_USERS table

            return ps.executeUpdate() > 0;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public List<Chat> list(int limit, int offset) {
        try {
            List<Chat> list = new ArrayList<>();

            String query = """
                        SELECT
                            c.chatid, c.title, c.adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            u.usersid, u.name, u.username, u.password, u.createdat AS u_createdat, u.updatedat AS u_updatedat
                        FROM chat c
                        INNER JOIN users u ON u.usersid=adminid
                        LIMIT ? OFFSET ?
                    """;
            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Chat(
                        response.getInt("chatid"),
                        new User(
                                response.getInt("usersid"),
                                response.getString("name"),
                                response.getString("username"),
                                response.getString("password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        response.getString("title"),
                        response.getDate("c_createdat"),
                        response.getDate("c_updatedat")));
            }

            // Get from CHAT_USERS table

            return list;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    @Override
    public Chat get(int id) {
        try {
            String query = """
                        SELECT
                            c.chatid, c.title, c.adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            u.usersid, u.name, u.username, u.password, u.createdat AS u_createdat, u.updatedat AS u_updatedat
                        FROM chat c
                        INNER JOIN users u ON u.usersid=adminid
                        WHERE chatid=?
                        LIMIT 1
                    """;

            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new Chat(
                        response.getInt("chatid"),
                        new User(
                                response.getInt("usersid"),
                                response.getString("name"),
                                response.getString("username"),
                                response.getString("password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        response.getString("title"),
                        response.getDate("c_createdat"),
                        response.getDate("c_updatedat"));
            }

            // Get from CHAT_USERS table

            return null;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    public Chat getPrivate(String title) {
        try {
            String query = """
                        SELECT
                            c.chatid, c.title, c.adminid, c.createdat AS c_createdat, c.updatedat AS c_updatedat,
                            u.usersid, u.name, u.username, u.password, u.createdat AS u_createdat, u.updatedat AS u_updatedat
                        FROM chat c
                        INNER JOIN users u ON u.usersid=adminid
                        WHERE title=?
                        LIMIT 1
                    """;
            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setString(1, title);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new Chat(
                        response.getInt("chatid"),
                        new User(
                                response.getInt("usersid"),
                                response.getString("name"),
                                response.getString("username"),
                                response.getString("password"),
                                response.getDate("u_createdat"),
                                response.getDate("u_updatedat")),
                        response.getString("title"),
                        response.getDate("c_createdat"),
                        response.getDate("c_updatedat"));
            }

            return null;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }
}
