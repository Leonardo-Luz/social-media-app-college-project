package ifrs.edu.com.service;

import ifrs.edu.com.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {
    private Connection db;

    public UserDAO(Connection db) {
        this.db = db;
    }

    @Override
    public boolean insert(User model) throws SQLException {
        String query = "INSERT INTO user (name, username, password) VALUES (?, ?, ?)";
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setString(1, model.getName());
        ps.setString(2, model.getUsername());
        ps.setString(3, model.getPassword());

        // insert into USER_FRIENDS table

        return ps.execute();
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM user WHERE userid = ?";
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, id);

        return ps.execute();
    }

    @Override
    public boolean update(User model) throws SQLException {
        String query = """
                    UPDATE user SET name=?, username=?, password=?
                    WHERE userid = ?
                """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setString(1, model.getName());
        ps.setString(2, model.getUsername());
        ps.setString(3, model.getPassword());

        ps.setInt(4, model.getUserId());

        // update USER_FRIENDS table

        return ps.executeUpdate() > 0;
    }

    @Override
    public List<User> list(int limit, int offset) throws SQLException {
        List<User> list = new ArrayList<>();

        String query = """
                    SELECT userid, name, username, password, createdat, updatedat FROM user
                    LIMIT ? OFFSET ?
                """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, limit);
        ps.setInt(2, offset);

        ResultSet response = ps.executeQuery();

        while (response.next()) {
            list.add(new User(
                    response.getInt("userid"),
                    response.getString("name"),
                    response.getString("username"),
                    response.getString("password"),
                    response.getDate("createdat"),
                    response.getDate("updatedat")));
        }

        // Get from CHAT_USERS table

        return list;
    }

    @Override
    public User get(int id) throws SQLException {
        String query = """
                    SELECT userid, name, username, password, createdat, updatedat FROM user
                    WHERE id=?
                    LIMIT 1
                """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, id);

        ResultSet response = ps.executeQuery();

        if (response.next()) {
            return new User(
                    response.getInt("userid"),
                    response.getString("name"),
                    response.getString("username"),
                    response.getString("password"),
                    response.getDate("createdat"),
                    response.getDate("updatedat"));
        }

        // Get from CHAT_USERS table

        return null;
    }

    public User login(String username, String password) throws SQLException {
        String query = """
                    SELECT userid, name, username, password, createdat, updatedat FROM user
                    WHERE username=? AND password=?
                    LIMIT 1
                """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet response = ps.executeQuery();

        if (response.next()) {
            return new User(
                    response.getInt("userid"),
                    response.getString("name"),
                    response.getString("username"),
                    response.getString("password"),
                    response.getDate("createdat"),
                    response.getDate("updatedat"));
        }

        // Get from CHAT_USERS table

        return null;
    }
}
