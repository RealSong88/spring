package springbook.user.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import springbook.user.domain.User;

class UserDaoTest2 {

//	@Autowired ApplicationContext context;

	private UserDao2 dao;
	private User user1;
	private User user2;
	private User user3;
	private User user4;

	@BeforeEach
	void setUp() {
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		this.dao = context.getBean("userDao", UserDao.class);

		dao = new UserDao2();
		DataSource dataSource = new SingleConnectionDataSource("jdbc:h2:tcp://localhost/~/springbook", "sa", "sa", true);
		dao.setDataSource(dataSource);

		this.user1 = new User("test1", "테스트1", "spring1");
		this.user2 = new User("test2", "테스트2", "spring2");
		this.user3 = new User("test3", "테스트3", "spring3");
		this.user4 = new User("test4", "테스트4", "spring4");

		System.out.println("#####################");
//		System.out.println(this.context);
//		System.out.println(this);
		System.out.println("#####################");
	}

	@SuppressWarnings("resource")
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);

		dao.deleteAll();
		assertThat(dao.getCount()).isEqualTo(0);

		User user = new User();
		user.setId("aa");
		user.setName("에이");
		user.setPassword("비밀");


//		dao.del(user);
		dao.add(user);
		assertThat(dao.getCount()).isEqualTo(1);

		User user2 = dao.get(user.getId());

		assertThat(user2.getName()).isEqualTo(user.getName());
		assertThat(user2.getPassword()).isEqualTo(user.getPassword());

//		User user3 = new User("testUser3", "테스트유저3", "비밀3");
//		User user4 = new User("testUser4", "테스트유저4", "비밀4");

		dao.deleteAll();
		assertThat(dao.getCount()).isEqualTo(0);

		dao.add(user3);
		dao.add(user4);
		assertThat(dao.getCount()).isEqualTo(2);

		User userget3 = dao.get(user3.getId());
		assertThat(userget3.getName()).isEqualTo(user3.getName());
		assertThat(userget3.getPassword()).isEqualTo(user3.getPassword());

		User userget4 = dao.get(user4.getId());
		assertThat(userget4.getName()).isEqualTo(user4.getName());
		assertThat(userget4.getPassword()).isEqualTo(user4.getPassword());
	}

	@Test
	void count() throws SQLException, ClassNotFoundException {
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

//		UserDao dao = context.getBean("userDao", UserDao.class);
//		User user1 = new User("test1", "테스트1", "spring1");
//		User user2 = new User("test2", "테스트2", "spring2");
//		User user3 = new User("test3", "테스트3", "spring3");

		dao.deleteAll();

		assertThat(dao.getCount()).isEqualTo(0);

		dao.add(user1);
		assertThat(dao.getCount()).isEqualTo(1);

		dao.add(user2);
		assertThat(dao.getCount()).isEqualTo(2);

		dao.add(user3);
		assertThat(dao.getCount()).isEqualTo(3);
	}

	@Test
	void getUserFailure() throws SQLException, ClassNotFoundException {
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

//		UserDao dao = context.getBean("userDao", UserDao.class);

		dao.deleteAll();

		assertThat(dao.getCount()).isEqualTo(0);


		// get 메소드 변경전
//		assertThrows(JdbcSQLNonTransientException.class, () -> { dao.get("unknown_id"); });
		// get 메소드 변경 후
		assertThrows(EmptyResultDataAccessException.class, () -> { dao.get("unknown_id"); });
	}

}
