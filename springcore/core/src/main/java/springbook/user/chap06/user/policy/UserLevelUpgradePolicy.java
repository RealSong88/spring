package springbook.user.chap06.user.policy;

import springbook.user.chap06.user.domain.User;

public interface UserLevelUpgradePolicy {

	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user);
}
