package springbook.user.chap06.user.service;

import springbook.user.chap06.user.domain.User;

public interface UserService {

	void add(User user);
	void upgradeLevels();
}
