package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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
//	@Bean
//	public UserDao userDao() {
//		return new UserDao(connectionMaker());
//	}

	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
//		userDao.setConnectionMaker(connectionMaker());
		userDao.setDataSource(dataSource());
		return userDao;
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
//		return new CountingMaker();
	}

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/springbook");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");

		return dataSource;
	}

}