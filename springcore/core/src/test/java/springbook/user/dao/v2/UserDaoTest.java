package springbook.user.dao.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.dao.v1.UserDao4;
import springbook.user.domain.User;

class UserDaoTest {

//	@Autowired ApplicationContext context;

	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	private User user4;

	@BeforeEach
	void setUp() {
		ApplicationContext context = new GenericXmlApplicationContext("v1-applicationContext.xml");
		this.dao = context.getBean("userDao", UserDao.class);

//		dao = new UserDao3();
//		DataSource dataSource = new SingleConnectionDataSource("jdbc:h2:tcp://localhost/~/springbook", "sa", "sa", true);
//		dao.setDataSource(dataSource);

		this.user1 = new User("test1", "테스트1", "spring1");
		this.user2 = new User("test2", "테스트2", "spring2");
		this.user3 = new User("test3", "테스트3", "spring3");
		this.user4 = new User("test4", "테스트4", "spring4");

		System.out.println("#####################");
//		System.out.println(this.context);
//		System.out.println(this);
		System.out.println("#####################");
	}


	@Test
	void getAll() throws SQLException, ClassNotFoundException {
		dao.deleteAll();

		List<User> users0 = dao.getAll();

		assertThat(users0.size()).isEqualTo(0);
		System.out.println("[users0] = " + users0);
		System.out.println(users0 != null);

		dao.add(user1); // id:test1
		List<User> users1 = dao.getAll();
		assertThat(users1.size()).isEqualTo(1);
		checkSameUser(user1, users1.get(0));

		dao.add(user2); // id:test2
		List<User> users2 = dao.getAll();
		assertThat(users2.size()).isEqualTo(2);
		checkSameUser(user2, users2.get(1));

		dao.add(user3); // id:test3
		List<User> users3 = dao.getAll();
		assertThat(users3.size()).isEqualTo(3);
		checkSameUser(user3, users3.get(2));

		assertThat(dao.getCount()).isEqualTo(3);
	}


	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId()).isEqualTo(user2.getId());
		assertThat(user1.getName()).isEqualTo(user2.getName());
		assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
		System.out.println("!!!!!!!!!!!!!!!!!!!");
		System.out.println("user class = " + user1 +" user.getId = " + user1.getId());
		System.out.println("!!!!!!!!!!!!!!!!!!!");

	}


}
