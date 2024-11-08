package ifrs.edu.com.service;

import ifrs.edu.com.config.Database;
import ifrs.edu.com.models.Chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements DAO<Chat> {
    private Connection db;

    public ChatDAO() throws SQLException {
        this.db = Database.connect();
    }

    @Override
    public boolean insert(Chat model) throws SQLException {
        String query = "INSERT INTO chat(title, adminid) VALUES (?, ?)";
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setString(1, model.getTitle());
        ps.setInt(2, model.getAdmin().getUserId());

        // insert into CHAT_USERS table

        return ps.execute();
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM chat WHERE chatid = ?";
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, id);

        return ps.execute();
    }

    @Override
    public boolean update(Chat model) throws SQLException {
        String query = """
                    UPDATE chat SET title=?, adminid=?
                    WHERE chatid = ?
                """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setString(1, model.getTitle());
        ps.setInt(2, model.getAdmin().getUserId());
        ps.setInt(3, model.getChatId());

        // update CHAT_USERS table

        return ps.executeUpdate() > 0;
    }

    @Override
    public List<Chat> list(int limit, int offset) throws SQLException {
        List<Chat> list = new ArrayList<>();

        String query = """
                    SELECT chatid, title, adminid, createdat, updatedat FROM chat
                    LIMIT ? OFFSET ?
                """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, limit);
        ps.setInt(2, offset);

        ResultSet response = ps.executeQuery();

        while (response.next()) {
            list.add(new Chat(
                    response.getInt("chatid"),
                    response.getString("title"),
                    response.getDate("createdat"),
                    response.getDate("updatedat")));
        }

        // Get from CHAT_USERS table

        return list;
    }

    @Override
    public Chat get(int id) throws SQLException {
        String query = """
                    SELECT chatid, title, adminid, createdat, updatedat FROM chat
                    WHERE chatid = ?
                    LIMIT 1
                """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, id);

        ResultSet response = ps.executeQuery();

        if (response.next()) {
            return new Chat(
                    response.getInt("chatid"),
                    response.getString("title"),
                    response.getDate("createdat"),
                    response.getDate("updatedat"));
        }

        // Get from CHAT_USERS table

        return null;
    }
}
