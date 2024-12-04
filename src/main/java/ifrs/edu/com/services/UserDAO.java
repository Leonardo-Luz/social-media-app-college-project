package ifrs.edu.com.services;

import ifrs.edu.com.config.Database;
import ifrs.edu.com.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAO implements DAO<User> {
    private static Connection db;

    public static boolean connection() {
        try {
            UserDAO.db = Database.connect();
            return true;
        } catch (SQLException err) {
            System.out.println(err);
            return false;
        }
    }

    @Override
    public boolean insert(User model) {
        try {
            // FIX: Search a better way to do it!

            String query;

            if (model.getUserId() != 0)
                query = "INSERT INTO users (name, username, password, usersid) VALUES (?, ?, ?, ?)";
            else
                query = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";

            PreparedStatement ps = UserDAO.db.prepareStatement(query);

            ps.setString(1, model.getName());
            ps.setString(2, model.getUsername());
            ps.setString(3, model.getPassword());

            if (model.getUserId() != 0)
                ps.setInt(4, model.getUserId());

            return ps.execute();
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            String query = "DELETE FROM users WHERE usersid = ?";
            PreparedStatement ps = UserDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            return ps.execute();
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public boolean update(User model) {
        try {
            String query = """
                        UPDATE users SET name=?, username=?, password=?
                        WHERE usersid = ?
                    """;
            PreparedStatement ps = UserDAO.db.prepareStatement(query);

            ps.setString(1, model.getName());
            ps.setString(2, model.getUsername());
            ps.setString(3, model.getPassword());

            ps.setInt(4, model.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return false;
    }

    @Override
    public List<User> list(int limit, int offset) {
        try {
            List<User> list = new ArrayList<>();

            String query = """
                        SELECT * FROM users
                        LIMIT ? OFFSET ?
                    """;
            PreparedStatement ps = UserDAO.db.prepareStatement(query);

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet response = ps.executeQuery();

            while (response.next()) {
                list.add(new User(
                        response.getInt("usersid"),
                        response.getString("name"),
                        response.getString("username"),
                        response.getString("password"),
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
    public User get(int id) {
        try {
            String query = """
                        SELECT usersid, name, username, password, createdat, updatedat FROM users
                        WHERE usersid=?
                        LIMIT 1
                    """;
            PreparedStatement ps = UserDAO.db.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new User(
                        response.getInt("usersid"),
                        response.getString("name"),
                        response.getString("username"),
                        response.getString("password"),
                        response.getDate("createdat"),
                        response.getDate("updatedat"));
            }

            return null;
        } catch (SQLException err) {
            System.out.println(err);
        }

        return null;
    }

    public User login(String username, String password) {
        try {
            String query = """
                        SELECT usersid, name, username, password, createdat, updatedat FROM users
                        WHERE username=? AND password=?
                        LIMIT 1
                    """;
            PreparedStatement ps = UserDAO.db.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet response = ps.executeQuery();

            if (response.next()) {
                return new User(
                        response.getInt("usersid"),
                        response.getString("name"),
                        response.getString("username"),
                        response.getString("password"),
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
