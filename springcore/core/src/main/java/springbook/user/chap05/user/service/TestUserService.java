package springbook.user.chap05.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import springbook.user.chap05.user.domain.User;

public class TestUserService extends UserService {

	private String id;
	
	TestUserService(String id) {
		this.id = id;
	}
	
	protected void upgradeLevel(User user) {
		if (user.getId().equals(this.id)) throw new TestUserServiceException();
		super.upgradeLevel(user);
	}
	
	static class TestUserServiceException extends RuntimeException{}
	
	
}
