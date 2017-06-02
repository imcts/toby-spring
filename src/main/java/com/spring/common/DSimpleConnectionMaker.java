package com.spring.common;

import com.spring.di.ConnectionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by imcts on 2017. 5. 14..
 */
public class DSimpleConnectionMaker implements ConnectionMaker{
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {

        //D사의 독자적인 커넥션을 생성하는 코드

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "root");
    }
}
