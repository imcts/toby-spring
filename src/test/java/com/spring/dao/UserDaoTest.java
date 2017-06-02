package com.spring.dao;

import com.spring.common.CountingConnectionMaker;
import com.spring.dto.User;
import com.spring.factory.CountingDaoFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by imcts on 2017. 6. 1..
 */
public class UserDaoTest {
    //모든 단위테스트는 각자 테스트에 영향을 주지 않는게 목표이며 서로 간섭받지 말아야 한다.

    @Test(expected = EmptyResultDataAccessException.class) //테스트 중에 발생할 것으로 기대하는 예외 클래스를 지정한다.
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        //get dao
        UserDao dao = context.getBean("userDao", UserDao.class); //싱글톤을 내부적으로 유지시켜준다.

        dao.deleteAll();

        assertThat(dao.getUserCount(), is(0));

        dao.getFindUser(new User("", ""));
    }


    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        //get dao
        UserDao dao = context.getBean("userDao", UserDao.class); //싱글톤을 내부적으로 유지시켜준다.

        User user1 = new User("dolen1", "password");
        User user2 = new User("dolen2", "password");

        //delete users
        dao.deleteAll();

        //add user
        dao.addUser(user1);
        dao.addUser(user2);

        //check count
        assertThat(dao.getUserCount(), is(2));

        User findUser1 = dao.getFindUser(user1);
        User findUser2 = dao.getFindUser(user2);

        assertThat(user1.getName(), is(findUser1.getName()));
        assertThat(user1.getPassword(), is(findUser1.getPassword()));

        assertThat(user2.getName(), is(findUser2.getName()));
        assertThat(user2.getPassword(), is(findUser2.getPassword()));
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        //get dao
        UserDao dao = context.getBean("userDao", UserDao.class); //싱글톤을 내부적으로 유지시켜준다.

        dao.deleteAll();

        assertThat(dao.getUserCount(), is(0));

        for(int i = 0; i < 3; i++) {
            dao.addUser(new User("dolen", "dolenPassword"));
            assertThat(dao.getUserCount(), is(i + 1));
        }
    }

    @Test
    public void checkAllDelete() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        //get dao
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll();

        assertThat(dao.getUserCount(), is(0));

        User addUser = new User("dolen", "dolenPassword");

        dao.addUser(addUser);

        assertThat(dao.getUserCount(), is(1));

        Map<String, String> map = new HashMap() {
            {
                this.put("test", "test");
                this.put("test2", "test2");
            }
        };

        System.out.println(map.toString());
    }

    @Test
    public void addAndGetUseFactory() throws SQLException, ClassNotFoundException {
        //어노테이션으로 클래스를 지정하여 사용할때는 이렇게 !
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        //get dao
        UserDao dao = context.getBean("userDao", UserDao.class); //싱글톤을 내부적으로 유지시켜준다.

        //add user dto
        User addUser = new User("dolen", "dolenPassword");

        //add user
        dao.addUserUseConnectionMaker(addUser);

        //get user
        User user = dao.getUsersUseConnectionMaker();

        CountingConnectionMaker countingConnectionMaker = (CountingConnectionMaker) dao.getConnectionMaker();

        assertThat(addUser.getName(), is(user.getName()));
        assertThat(addUser.getPassword(), is(user.getPassword()));
        assertThat(countingConnectionMaker.getCounter(), is(2));
    }
}
