package db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {

            Properties props = new Properties();
            props.load(new FileInputStream(".env")); // loads .env

            String url = props.getProperty("DB_URL");
            String user = props.getProperty("DB_USER");
            String pass = props.getProperty("DB_PASS");

            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }
}
