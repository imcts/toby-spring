package com.spring.common;

import com.spring.di.ConnectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by imcts on 2017. 5. 26..
 */
public class CountingConnectionMaker implements ConnectionMaker {
    private int counter = 0;
    private ConnectionMaker connectionMaker;

    public CountingConnectionMaker() {
    }

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return this.connectionMaker.getConnection();
    }

    public CountingConnectionMaker setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
        return this;
    }

    public ConnectionMaker getConnectionMaker() {
        return connectionMaker;
    }
}
