package springbook.user;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.dao.ConnectionMaker;
import springbook.user.dao.DConnectionMaker;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class UserDaoTest {

//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
////		ConnectionMaker connectionMaker = new DConnectionMaker();
//		DaoFactory daoFactory = new DaoFactory();
//		UserDao dao = new DaoFactory().userDao();
//
//		User user = new User();
//		user.setId("nadayong");
//		user.setName("송");
//		user.setPassword("song");
//
////		dao.add(user);
//
//		System.out.println(user.getId() + " 등록 성공");
//
//		User user2 = dao.get(user.getId());
//		System.out.println(user2.getName());
//	}


	// 애플리케이션 컨텍스트를 적용
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = ac.getBean("userDao", UserDao.class);
		User user = new User();
		user.setId("nadayong");
		user.setName("송");
		user.setPassword("song");

//		dao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
	}
}
