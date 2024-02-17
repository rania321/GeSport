package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static Connection cnx;
    private String url = "jdbc:mysql://localhost:3306/gesport";
    private String login = "root";
    private String pwd = "";
    private static DataSource instance;

    private DataSource() {
        try {
            cnx = DriverManager.getConnection(this.url, this.login, this.pwd);
            System.out.println("succes");
        } catch (SQLException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }

        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}