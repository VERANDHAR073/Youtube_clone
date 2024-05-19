/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author TDX
 */
public class MySql {
    public static Connection connection;
    
    public static void createConnection()  throws Exception{
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fuel-station", "root", "Charuka@0606");
        }
    }
    
    public static ResultSet execute(String quary) throws Exception {
        createConnection();
        Statement statement = connection.createStatement();
        if (quary.startsWith("SELECT")) {
            return statement.executeQuery(quary);
        } else {
            statement.executeUpdate(quary);
            return null;
        }
    }
}
