package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.domain.User;

public class UserDaoConnectionCountingTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

		UserDao dao = ac.getBean("userDao", UserDao.class);

		CountingConnectionMaker ccm = ac.getBean("connectionMaker", CountingConnectionMaker.class);

		ConnectionMaker bean = ac.getBean("connectionMaker", ConnectionMaker.class);
//		bean.makeConnection();
//		bean.makeConnection();
//		bean.makeConnection();
//		ccm.makeConnection();

		User user = dao.get("nadayong");
		System.out.println(user.getId() + " " + user.getName());

		System.out.println("Connection counter : " + ccm.getCounter());
	}
}
