package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	void 회원가입() throws Exception {

		//Given
		Member member = new Member();
		member.setName("kim");

		//When
		Long saveId = memberService.join(member);

		//Then
		assertEquals(member, memberRepository.findOne(saveId));

		System.out.println("################################");
		System.out.println("member = " + member);
		System.out.println("memberRepository = " + memberRepository.findOne(saveId));
	}

	@Test
	void 중복_회원_예외() throws Exception {
		//Given
		Member member1 = new Member();
		member1.setName("kim");

		Member member2 = new Member();
		member2.setName("kim");

		//When
		memberService.join(member1);


		//Then
		assertThrows(IllegalStateException.class, () -> {
			memberService.join(member2); //예외가 발생해야함
		});
//		fail("예외가 발생해야 함");
	}
}
