package springbook.user.dao.v3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import springbook.user.domain.User;

class UserDaoTest {

//	@Autowired ApplicationContext context;

	private UserDao dao;
	private User user1;
	
	private DataSource dataSource;
	

	@BeforeEach
	void setUp() {
		ApplicationContext context = new GenericXmlApplicationContext("v3-applicationContext.xml");
		this.dao = context.getBean("userDao", UserDao.class);
		this.dataSource = context.getBean("dataSource", DataSource.class);
		
		this.user1 = new User("testid", "nadayong", "is good");
		
	}

	@Test
	void sqlExceptionTranslate() {
		dao.deleteAll();
		
		try {
			dao.add(user1);
			dao.add(user1);
		} catch(DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException) ex.getRootCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
			
			assertThat(set.translate(null, null, sqlEx)).isExactlyInstanceOf(DuplicateKeyException.class);
		
		}
	}
	
	
	@Test
	public void dupciateKey() {
		dao.deleteAll();
		
		
		dao.add(user1);
		
		// then
		assertThrows(DataAccessException.class, () -> dao.add(user1));
//		assertThrows(DuplicateKeyException.class, () -> dao.add(user1));
		
		
	}

}
