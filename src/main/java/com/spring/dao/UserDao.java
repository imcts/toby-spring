package com.spring.dao;

import com.spring.di.ConnectionMaker;
import com.spring.dto.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by imcts on 2017. 5. 12..
 */

public class UserDao {
    //데이터 소스를 사용
    private DataSource dataSource;

    //초기 공부용
    private ConnectionMaker connectionMaker;

    public UserDao() {}


    public void addUserUseConnectionMaker(User user) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO USER(name, password) VALUES(?, ?)";

        Connection con = this.connectionMaker.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getPassword());

        pstmt.executeUpdate();

        pstmt.close();
        con.close();
    }

    public User getUsersUseConnectionMaker() throws ClassNotFoundException, SQLException {
        User user = new User();

        String sql = "SELECT * FROM USER";

        Connection con = this.connectionMaker.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        while(resultSet.next()) {
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }

        pstmt.close();
        con.close();

        return user;
    }

    public void addUser(User user) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO USER(name, password) VALUES(?, ?)";

        Connection con = this.dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getPassword());

        pstmt.executeUpdate();

        pstmt.close();
        con.close();
    }

    public User getUsers() throws ClassNotFoundException, SQLException {
        User user = new User();

        String sql = "SELECT * FROM USER";

        Connection con = this.dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        while(resultSet.next()) {
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }

        pstmt.close();
        con.close();

        return user;
    }

    public User getFindUser(User user) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM USER WHERE name = ?";

        Connection con = this.dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, user.getName());

        ResultSet resultSet = pstmt.executeQuery();

        User resultUser = null;

        if(resultSet.next()) {
            resultUser = new User();
            resultUser.setId(resultSet.getString("id"));
            resultUser.setName(resultSet.getString("name"));
            resultUser.setPassword(resultSet.getString("password"));
        }

        pstmt.close();
        con.close();


        if(resultUser == null)
            throw new EmptyResultDataAccessException(1);

        return resultUser;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM USER";

        Connection con = this.dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.executeUpdate();

        pstmt.close();
        con.close();
    }

    public int getUserCount() throws ClassNotFoundException, SQLException {
        String sql = "SELECT COUNT(*) FROM USER";

        Connection con = this.dataSource.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        resultSet.next();

        int count = resultSet.getInt(1);

        resultSet.close();
        pstmt.close();
        con.close();

        return count;
    }

    public UserDao setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public UserDao setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
        return this;
    }

    public ConnectionMaker getConnectionMaker() {
        return connectionMaker;
    }
}