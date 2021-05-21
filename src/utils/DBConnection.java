package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
//    JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String serverName = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07rsq";

//    JDBC URL
    private static final String jdbcURL = protocol + vendorName + serverName + dbName;


    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver"; //JDBC Driver reference

    private static Connection connection = null;

    private static final String username = "U07rsq";
    private static final String password = "53689113579";

    public static Connection connect() {
        try {
            Class.forName(MYSQLJDBCDriver);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("--- DATABASE CONNECTION SUCCESSFUL ---");
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("--- DATABASE CONNECTION CLOSED ---");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
