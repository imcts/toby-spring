package com.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by imcts on 2017. 5. 14..
 */
public class NUserDao extends UserDao {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "root");
    }
}
