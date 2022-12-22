package springbook.user.chap05.user.service;

import java.util.List;

import springbook.user.chap05.user.dao.UserDao;
import springbook.user.chap05.user.domain.Level;
import springbook.user.chap05.user.domain.User;
import springbook.user.chap05.user.policy.UserLevelUpgradePolicy;

public class UserService2 {

	
	// 추후 수정
	UserDao userDao;

	UserLevelUpgradePolicy userLevelUpgradePolicy;
	

	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void upgradeLevels() {
		 List<User> users = userDao.getAll();
		 for(User user : users) {
			 if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
				 userLevelUpgradePolicy.upgradeLevel(user);
			 }
		 }

	}

	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
