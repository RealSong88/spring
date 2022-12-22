package springbook.user.chap05.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.chap05.user.domain.Level;
import springbook.user.chap05.user.domain.User;



class UserDaoTest {

	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	
	private DataSource dataSource;
	
	@BeforeEach
	void setUp() throws Exception {
		ApplicationContext context = new GenericXmlApplicationContext("chap05-applicationContext.xml");
		this.dao = context.getBean("userDao", UserDao.class);
		this.dataSource = context.getBean("dataSource", DataSource.class);
		
		this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
		this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
		this.user3 = new User("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);
	}

	@Test
	void addAndGet() {
		dao.deleteAll();
		dao.add(user1);
		dao.add(user2);
		User userget1 = dao.get(user1.getId());
		checkSameUser(userget1, user1);
		
		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
	}
	
	@Test
	void update() {
		dao.deleteAll();
		
		dao.add(user1);	//수정할 사용자
		dao.add(user2);	// 수정하지 않을 사용자
		
		user1.setName("나다용");
		user1.setPassword("springno6");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		
		dao.update(user1);
		
		User user1update = dao.get(user1.getId());
		checkSameUser(user1, user1update);
		User user2same = dao.get(user2.getId());
		checkSameUser(user2, user2same);
	}
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId()).isEqualTo(user2.getId());
		assertThat(user1.getName()).isEqualTo(user2.getName());
		assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
		assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
		assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
		assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
	}

}
