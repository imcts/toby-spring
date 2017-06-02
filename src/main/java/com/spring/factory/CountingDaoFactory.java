package com.spring.factory;

import com.spring.common.CountingConnectionMaker;
import com.spring.common.DSimpleConnectionMaker;
import com.spring.dao.UserDao;
import com.spring.di.ConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

/**
 * Created by imcts on 2017. 5. 14..
 */

@Configuration //어플리케이션 컨텍스트 또는 빈 팩토리가 사용하는 설정이라는 의미
public class CountingDaoFactory {

    @Bean //오브젝트 생성을 담당하는 IoC용 메소드라는 표시 싱글톤으로 관리된다
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(this.getDataSource());
        userDao.setConnectionMaker(this.getCountingConnectionMaker());
        return userDao;
    }

    @Bean
    public DataSource getDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        simpleDriverDataSource.setUrl("jdbc:mysql://localhost:3306/spring");
        simpleDriverDataSource.setUsername("root");
        simpleDriverDataSource.setPassword("root");

        return simpleDriverDataSource;
    }

    @Bean //어차피 싱글톤으로 유지되기 때문에 기존의 DSimpleCojnnectionMaker를 손댈필요 없이 새로운 싱글톤 객체를 만들고 그 객체에서 유기적으로 움직일 수 있도록 수정한다.
    public ConnectionMaker getCountingConnectionMaker() {
        return new CountingConnectionMaker(this.getDSimpleConnectionMaker());
    }

    @Bean
    public ConnectionMaker getDSimpleConnectionMaker() {
        return new DSimpleConnectionMaker();
    }
}
