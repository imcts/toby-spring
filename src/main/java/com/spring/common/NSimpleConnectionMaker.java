package com.spring.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by imcts on 2017. 5. 14..
 */
public class NSimpleConnectionMaker {
    public Connection getConnection() throws ClassNotFoundException, SQLException {

        //N사의 독자적인 커넥션을 생성하는 코드

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "root");
    }
}
