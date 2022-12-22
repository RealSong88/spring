package springbook.user.chap05.user.policy;

import springbook.user.chap05.user.domain.User;

public interface UserLevelUpgradePolicy {

	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user);
}
