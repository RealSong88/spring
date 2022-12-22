package springbook.user.chap05.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static springbook.user.chap05.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.chap05.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springbook.user.chap05.user.dao.UserDao;
import springbook.user.chap05.user.domain.Level;
import springbook.user.chap05.user.domain.User;
import springbook.user.chap05.user.policy.UserLevelUpgradePolicy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/chap05-applicationContext.xml")
class UserService2Test {

	@Autowired
	UserService2 userService;
	@Autowired
	UserDao userDao;
	@Autowired 
	UserLevelUpgradePolicy userLevelUpgradePolicy;
	
	List<User> users;
	
	//v3
	User user;
	
	@BeforeEach
	void setUp() throws Exception {
		
		users = Arrays.asList(new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
				new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
				new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1),
				new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
				new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE));
		
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
	void upgradeLevels() {
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
	void upgradeLevels2() {
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		userService.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), false); 
		checkLevelUpgraded(users.get(1), true); 
		checkLevelUpgraded(users.get(2), false); 
		checkLevelUpgraded(users.get(3), true); 
		checkLevelUpgraded(users.get(4), false); 
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

}
