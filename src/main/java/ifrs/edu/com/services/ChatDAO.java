package ifrs.edu.com.services;

import ifrs.edu.com.config.Database;
import ifrs.edu.com.context.AuthProvider;
import ifrs.edu.com.controllers.PrivateChatController;
import ifrs.edu.com.models.Chat;

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

            // insert into CHAT_USERS table

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
            UserDAO service = new UserDAO();

            List<Chat> list = new ArrayList<>();

            String query = """
                        SELECT chatid, title, adminid, createdat, updatedat FROM chat
                        LIMIT ? OFFSET ?
                    """;
            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new Chat(
                        response.getInt("chatid"),
                        service.get(response.getInt("adminid")),
                        response.getString("title"),
                        response.getDate("createdat"),
                        response.getDate("updatedat")));
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
            UserDAO service = new UserDAO();

            String query = """
                        SELECT chatid, title, adminid, createdat, updatedat FROM chat
                        WHERE chatid=?
                        LIMIT 1
                    """;
            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new Chat(
                        response.getInt("chatid"),
                        service.get(response.getInt("adminid")),
                        response.getString("title"),
                        response.getDate("createdat"),
                        response.getDate("updatedat"));
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
            UserDAO service = new UserDAO();

            String query = """
                        SELECT chatid, title, adminid, createdat, updatedat FROM chat
                        WHERE title=?
                        LIMIT 1
                    """;
            PreparedStatement ps = ChatDAO.db.prepareStatement(query);

            ps.setString(1, title);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new Chat(
                        response.getInt("chatid"),
                        service.get(response.getInt("adminid")),
                        response.getString("title"),
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
