package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.dbcp2.BasicDataSource;


// establish a connection to the database
public class TestConnect {
    static Dotenv dotenv = Dotenv.load();
    private final static String url = dotenv.get("DB_URL");
    private final static String user = dotenv.get("DB_USER");
    private final static String password = dotenv.get("DB_PASS");
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Successfully connected to the database!");
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException err) {
            System.out.println("SQL Exception: " + err.getMessage());
        }
    }
}


//REF: https://www.baeldung.com/java-connection-pooling