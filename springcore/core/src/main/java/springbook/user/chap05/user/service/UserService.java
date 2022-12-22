package springbook.user.chap05.user.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import springbook.user.chap05.user.dao.UserDao;
import springbook.user.chap05.user.domain.Level;
import springbook.user.chap05.user.domain.User;

public class UserService {

	UserDao userDao;

	
	//v3
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
	//v4
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//v6
	private PlatformTransactionManager transactionManager;
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	protected void upgradeLevels() throws Exception{
		//v1
//		List<User> users = userDao.getAll();
//		for (User user : users) {
//			Boolean changed = null;
//			if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//				user.setLevel(Level.SILVER);
//				changed = true;
//			} else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//				user.setLevel(Level.GOLD);
//				changed = true;
//			} else if (user.getLevel() == Level.GOLD) {
//				changed = false;
//			} else {
//				changed = false;
//			}
//
//			if (changed) {
//				userDao.update(user);
//			}
//		}
		//v2, v3
//		 List<User> users = userDao.getAll();
//		 for(User user : users) {
//			 if (canUpgradeLevel(user)) {
//				 upgradelevel(user);
//			 }
//		 }
		
		//v4
//		TransactionSynchronizationManager.initSynchronization();
//		
//		Connection c = DataSourceUtils.getConnection(dataSource);
//		c.setAutoCommit(false);
//		
//		try {
//			List<User> users = userDao.getAll();
//			for (User user : users) {
//				if (canUpgradeLevel(user)) {
//					upgradeLevel(user);
//				}
//			}
//			
//			c.commit();
//		} catch (Exception e) {
//			c.rollback();
//			throw e;
//		} finally {
//			DataSourceUtils.releaseConnection(c, dataSource);
//			TransactionSynchronizationManager.unbindResource(this.dataSource);
//			TransactionSynchronizationManager.clearSynchronization();
//		}
		
		//v5
//		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
//		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

		//v6
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			List<User> users = userDao.getAll();
			for (User user : users) {
				if (canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			//v5
//			transactionManager.commit(status);
			//v6
			this.transactionManager.commit(status);
		} catch (RuntimeException e) {
			//v5
//			transactionManager.rollback(status);
			//v6
			this.transactionManager.rollback(status);
			throw e;
		}
			

	}

	protected void upgradeLevel(User user) {
		//v2
//		if (user.getLevel() == Level.BASIC) user.setLevel(Level.SILVER);
//		else if (user.getLevel() == Level.SILVER) user.setLevel(Level.GOLD);
//		userDao.update(user);
		//v3
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEmail(user);
	}

	private void sendUpgradeEmail(User user) {
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "mail.ksug.org");
//		Session s = Session.getInstance(props, null);
//		MimeMessage message = new MimeMessage(s);
//		
//		try {
//			message.setFrom(new InternetAddress("july2day@gmail.com"));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
//			
//			message.setSubject("Upgrade 안내");
//			message.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");
//			
//			Transport.send(message);
//		} catch (AddressException e) {
//			throw new RuntimeException(e);
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		} 
//		catch (UnsupportedEncodingException e) {
//			throw new RuntimeException(e);
//		}
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@ksug.org");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());
		
		this.mailSender.send(mailMessage);
		
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {
			case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD: return false;
			default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}
	
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}



}
