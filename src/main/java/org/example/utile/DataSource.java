package org.example.utile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataSource {

    private String url="jdbc:mysql://localhost:3306/gesportweb";
    private String user="root";
    private String pwd= "";
    private Connection cnx;
    private static DataSource instance;

    private DataSource() {
        try {
            cnx = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connection établie");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static DataSource getInstance() {
        if(instance==null)
            instance = new DataSource();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
