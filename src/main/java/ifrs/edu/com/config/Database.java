package ifrs.edu.com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    private final static Dotenv dotenv = Dotenv.load();

    public static Connection connect() throws SQLException {
        String url = dotenv.get("POSTGRES_URL");
        String user = dotenv.get("POSTGRES_USER");
        String password = dotenv.get("POSTGRES_PASSWORD");

        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }
}
