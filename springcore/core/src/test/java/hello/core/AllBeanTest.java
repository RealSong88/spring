package hello.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;

class AllBeanTest {

	@Test
	void findAllBean() {
		ApplicationContext ac =  new AnnotationConfigApplicationContext(AutoAppConfig.class, DisCountService.class);
		DisCountService disCountService = ac.getBean(DisCountService.class);
		Member member = new Member(1L, "userA", Grade.VIP);
		int discount = disCountService.discount(member, 10000, "rateDiscountPolicy");
//
		assertThat(disCountService).isInstanceOf(DisCountService.class);
		assertThat(discount).isEqualTo(1000);
	}

	static class DisCountService {
		private final Map<String, DiscountPolicy> policyMap;
		private final List<DiscountPolicy> policies;

		public DisCountService(Map<String, DiscountPolicy> policyMap,
				List<DiscountPolicy> policies) {
			this.policyMap = policyMap;
			this.policies = policies;
			System.out.println("policyMap = " + policyMap);
			System.out.println("policies = " + policies);

		}

		public int discount(Member member, int price, String discountCode) {

			DiscountPolicy discountPolicy = policyMap.get(discountCode);

			System.out.println("discountCode = " + discountCode);
			System.out.println("discountPolicy = " + discountPolicy);

			return discountPolicy.discount(member, price);
		}

	}
}
