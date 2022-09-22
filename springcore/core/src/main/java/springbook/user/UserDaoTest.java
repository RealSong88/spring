package springbook.user;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

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

		dao.del(user);
		dao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = dao.get(user.getId());
//		System.out.println(user2.getName());

		DaoFactory factory =  new DaoFactory();
		UserDao dao1 = factory.userDao();
		UserDao dao2 = factory.userDao();

		System.out.println(dao1);
		System.out.println(dao2);

		UserDao dao3 = ac.getBean("userDao", UserDao.class);
		UserDao dao4 = ac.getBean("userDao", UserDao.class);
		System.out.println(dao3);
		System.out.println(dao4);
		System.out.println(dao3 == dao4);

		user.setName("테스트 실패용 세팅");
		if (!user.getName().equals(user2.getName())) {
			System.out.println("테스트 실패 (name)");
		} else if (!user.getPassword().equals(user2.getPassword())) {
			System.out.println("테스트 실패 (password)");
		} else {
			System.out.println("조회 테스트 성공!");
		}

		//####################  xml을 통한 bean 등록
		ApplicationContext ac2 = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao22 = ac2.getBean("userDao", UserDao.class);
		User user22 = dao22.get("nadayong");
		System.out.println("user22 = " + user22.toString());
	}
}
