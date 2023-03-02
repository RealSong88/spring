package springbook.user.chap06.user.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static springbook.user.chap05.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.chap05.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.chap06.user.dao.MockUserDao;
import springbook.user.chap06.user.dao.UserDao;
import springbook.user.chap06.user.domain.Level;
import springbook.user.chap06.user.domain.User;
import springbook.user.chap06.user.handler.TransactionHandler;
import springbook.user.chap06.user.service.TestUserService.TestUserServiceException;




@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/chap06-applicationContext.xml")
//@ContextConfiguration(locations = "file:src/main/resources/chap05-applicationContext.xml") // 두개다 됨
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    List<User> users;

    User user;

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    MailSender mailSender;

    @Autowired
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() throws Exception {

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

    @Test
    void upgradeLevels() throws Exception {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userServiceImpl.upgradeLevels();

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
        userServiceImpl.setMailSender(mockMailSender); // 메일 발송 결과를 테스트 할 수 있도록 목 오브젝트를 만들어 userService의 의존 오브젝트로 주입

        userServiceImpl.upgradeLevels();

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
    	// 다이나믹 프록시를 이용한 테스트
        TestUserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setMailSender(mailSender);
        
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(testUserService);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern("upgradeLevels");
        UserService txUserService = (UserService)Proxy.newProxyInstance(getClass().getClassLoader()
        							, new Class[] { UserService.class }
        							, txHandler);
//        txUserService.setTransactionManager(transactionManager);
//        txUserService.setUserService(testUserService);

        userDao.deleteAll();
        for(User user : users) {
            userDao.add(user);
        }

        try {
            txUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {
        }

        checkLevelUpgraded(users.get(1), false);
        
        
//        TestUserService testUserService = new TestUserService(users.get(3).getId());
//        testUserService.setUserDao(this.userDao);
//        testUserService.setMailSender(mailSender);
//
//        UserServiceTx txUserService = new UserServiceTx();
//        txUserService.setTransactionManager(transactionManager);
//        txUserService.setUserService(testUserService);
//
//        userDao.deleteAll();
//        for(User user : users) {
//            userDao.add(user);
//        }
//
//        try {
//            txUserService.upgradeLevels();
//            fail("TestUserServiceException expected");
//        } catch (TestUserServiceException e) {
//        }
//
//        checkLevelUpgraded(users.get(1), false);

    }

    // MockUserDao를 사용하는 고립된 테스트
    @Test
    public void upgradeLevesMock() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();

        assertThat(updated.size()).isEqualTo(2);
        checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
        checkUserAndLevel(updated.get(1), "madnite1", Level.GOLD);
        
        List<String> request = mockMailSender.getRequests();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expectedId);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    void mockUpgradeLevels() throws Exception {
    	UserServiceImpl userServiceImpl = new UserServiceImpl();
    	
    	UserDao mockUserDao = mock(UserDao.class);
    	when(mockUserDao.getAll()).thenReturn(this.users);
    	userServiceImpl.setUserDao(mockUserDao);
    	
    	MailSender mockMailSender = mock(MailSender.class);
    	userServiceImpl.setMailSender(mockMailSender);
    	
    	userServiceImpl.upgradeLevels();
    	
    	verify(mockUserDao, times(2)).update(any(User.class));
    	verify(mockUserDao, times(2)).update(any(User.class));
    	verify(mockUserDao).update(users.get(1));
    	assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
    	verify(mockUserDao).update(users.get(3));
    	assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);
    	
    	ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
    	verify(mockMailSender, times(2)).send(mailMessageArg.capture());
    	List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
    	
    	assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
    	assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
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
