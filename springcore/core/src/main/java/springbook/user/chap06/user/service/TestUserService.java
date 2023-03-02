package springbook.user.chap06.user.service;

import springbook.user.chap06.user.domain.User;

public class TestUserService extends UserServiceImpl {

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
