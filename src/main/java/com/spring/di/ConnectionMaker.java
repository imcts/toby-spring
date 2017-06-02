package com.spring.di;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by imcts on 2017. 5. 14..
 */
public interface ConnectionMaker {
    Connection getConnection() throws ClassNotFoundException, SQLException;
}
