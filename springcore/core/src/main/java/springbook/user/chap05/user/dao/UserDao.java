package springbook.user.chap05.user.dao;

import java.util.List;

import springbook.user.chap05.user.domain.User;


public interface UserDao {

	void add(User user);
	User get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
	void update(User user);
	
}
