package com.spring.factory;

import com.spring.common.DSimpleConnectionMaker;
import com.spring.dao.UserDao;
import com.spring.di.ConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by imcts on 2017. 5. 14..
 */

@Configuration //어플리케이션 컨텍스트 또는 빈 팩토리가 사용하는 설정이라는 의미
public class DaoFactory {

    //만약 두개의 @Configuration이 존재하고, 그 안에 같은 이름의 @Bean이 존재한다면 제일 마지막에 주입된 메소드가 오버라이딩 한다.
    @Bean //오브젝트 생성을 담당하는 IoC용 메소드라는 표시 싱글톤으로 관리된다
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(getConnectionMaker());
        return userDao;
    }

    @Bean
    public ConnectionMaker getConnectionMaker() {
        return new DSimpleConnectionMaker();
    }
}
