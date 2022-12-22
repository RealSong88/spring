package springbook.user.chap05.user.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static springbook.user.chap05.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.chap05.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.chap05.user.dao.UserDao;
import springbook.user.chap05.user.domain.Level;
import springbook.user.chap05.user.domain.User;
import springbook.user.chap05.user.service.TestUserService.TestUserServiceException;




@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/chap05-applicationContext.xml")
//@ContextConfiguration(locations = "file:src/main/resources/chap05-applicationContext.xml") // 두개다 됨
class UserServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	
	List<User> users;
	
	//v3
	User user;
	
	//v4
	@Autowired
	DataSource dataSource;
	
	//v6
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	MailSender mailSender;
	
	@BeforeEach
	void setUp() throws Exception {
		
//		users = Arrays.asList(new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
//				new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
//				new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1),
//				new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
//				new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE));
		users = Arrays.asList(new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0, "bumjin@test.com"),
				new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "joytouch@test.com"),
				new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1, "erwins@test.com"),
				new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD, "madnite1@test.com"),
				new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "green@test.com"));
		
		user = new User();
	}

	@Test
	void bean() {
		assertNotNull(userService);
	}
	
//	@Test
//	void upgradeLevels() {
//		List<User> users = userDao.getAll();
//		for(User user : users) {
//			Boolean changed = null;
//			if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//				user.setLevel(Level.SILVER);
//				changed = true;
//			} else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//				user.setLevel(Level.GOLD);
//				changed = true;
//			} else if ( user.getLevel() == Level.GOLD) { changed = false; }
//			else { changed = false; }
//			
//			if (changed) {userDao.update(user);}
//		}
//	}
	
	@Test	
	void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		userService.upgradeLevels();
		
		checkLevel(users.get(0), Level.BASIC); 
		checkLevel(users.get(1), Level.SILVER); 
		checkLevel(users.get(2), Level.SILVER); 
		checkLevel(users.get(3), Level.GOLD); 
		checkLevel(users.get(4), Level.GOLD); 
	}
	@Test
	@DirtiesContext //컨텍스트의 DI 설정을 변경하는 테스트라는 것을 알려줌
	void upgradeLevels2() throws Exception {
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		MockMailSender mockMailSender = new MockMailSender();
		userService.setMailSender(mockMailSender); // 메일 발송 결과를 테스트 할 수 있도록 목 오브젝트를 만들어 userService의 의존 오브젝트로 주입
		
		userService.upgradeLevels();
		
		// 업그레이드 테스트, 메일발송이 일어나면 MockMailSender 오브젝트에 결과가 저장
		checkLevelUpgraded(users.get(0), false); 
		checkLevelUpgraded(users.get(1), true); 
		checkLevelUpgraded(users.get(2), false); 
		checkLevelUpgraded(users.get(3), true); 
		checkLevelUpgraded(users.get(4), false); 
		
		
		//목 오브젝트에 저장된 메일 수신자 목록을 가져와 업그레이드 대상과 일치 여부 확인
		List<String> request = mockMailSender.getRequests();
		assertThat(request.size()).isEqualTo(2);
		assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
		assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if (upgraded) {
			assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
		} else {
			assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
		}
	}
	
	@Test
	void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(4); // GOLD 레벨
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
		assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
	}
	
	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
	}

	//v3
	void upgradeLevel() {
		Level[] levels = Level.values();
		for(Level level : levels) {
			if (level.nextLevel() ==null) continue;
			user.setLevel(level);;
			user.upgradeLevel();
			assertThat(user.getLevel()).isEqualTo(level.nextLevel());
		}
	}
	
	@Test
	void cannotUpgradeLevel() {
		Level[] levels = Level.values();
		for(Level level : levels) {
			if (level.nextLevel() != null) continue;
			user.setLevel(level);
			assertThrows(IllegalStateException.class, () -> user.upgradeLevel());
		}
	}
	
	@Test
	void upgradeAllOrNothing() throws Exception {
		System.out.println(users.get(3).getId());
		UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		//v5
//		testUserService.setDataSource(dataSource);
		//v6
		testUserService.setTransactionManager(transactionManager);
		
		testUserService.setMailSender(mailSender);
		
		List<User> all = userDao.getAll();
		
		for(User a : all) {
			System.out.println("###############" + a.getName());
			
		}
		
		userDao.deleteAll();
		for(User user : users) {
			userDao.add(user);
		}
		
		try {
			testUserService.upgradeLevels();
//			Assertions.fail("TestUserServiceException expected");
//			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
		}
		
		checkLevelUpgraded(users.get(1), false);

	}
	
	// 인스턴스에 값 set, 정적필드 값 set
	// 내가 테스트해본것
	@Test 
	void num() {
		
//		int a = 1;
//		sum(a);
//		
//		assertThat(a).isEqualTo(2);
		
		User user = users.get(0);
		user.setLevel(null);
		user.setRecommend(10);
		setLevel(user);
		
		assertThat(user.getLevel()).isEqualTo(Level.BASIC);
		
		
		assertThat(user.getRecommend()).isEqualTo(9);
	}
	
	private void setLevel(User test) {
		test.setLevel(Level.BASIC);
		test.setRecommend(test.getRecommend() - 1);
	}
	private void sum(int num) {
		num++;
	}
	
	static class MockMailSender implements MailSender {
		
		private List<String> requests = new ArrayList<String>();
		public List<String> getRequests() {
			return requests;
		}
		
		@Override
		public void send(SimpleMailMessage simpleMessage) throws MailException {
			requests.add(simpleMessage.getTo()[0]);
		}

		@Override
		public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		}
		
	}
}
