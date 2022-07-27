package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jpabook.jpashop.domain.Member;

@SpringBootTest
class MemberRepsitoryTest2 {

	@Autowired MemberRepository memberRepository;

	@Test
	@Transactional
	@Rollback(false)
	public void testMember() {
		Member member = new Member();
		member.setName("memberA");
		Long saveId = memberRepository.save(member);

		Member findMember = memberRepository.findOne(saveId);

		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getName()).isEqualTo(member.getName());
		assertThat(findMember).isEqualTo(member);
	}

}
