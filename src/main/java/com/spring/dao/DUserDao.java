package com.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by imcts on 2017. 5. 14..
 * abstract를 이용하는 상속을 통하여 객체를 확장하기 시작하면 DUserDao가 상속을 구현하고 있을때나 상속이 필요할때 문제가 생길 수 있다.
 * 자바에서는 다중 상속을 허용하지 않고 있기 때문이다.
 * 그래서 이런 경우에는 ConnectionMaker를 이용해서 확장하는 편이 좋다.
 */

public class DUserDao extends UserDao {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "root");
    }
}
