package ifrs.edu.com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final static String url = "jdbc:postgresql://localhost:5432/projeto";
    private final static String user = "postgres";
    private final static String password = "postgres";

    public static Connection connect() throws SQLException{
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }
}
