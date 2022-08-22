package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//public class DaoFactory {
//	public UserDao userDao() {
//		return new UserDao(connectionMaker());
//	}
//
////	public AccountDao accountDao() {
////		return new AccountDao(connectionMaker());
////	}
//
//	public ConnectionMaker connectionMaker() {
//		return new DConnectionMaker();
//	}
//}


@Configuration
public class DaoFactory {
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}

}