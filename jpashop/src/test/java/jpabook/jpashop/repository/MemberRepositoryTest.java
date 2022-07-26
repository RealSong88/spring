package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import jpabook.jpashop.domain.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;

	@Test
	@Transactional
	@Rollback(false)
	public void testMember() {
		Member member = new Member();
		member.setUsername("memberA");
		Long saveId = memberRepository.save(member);

		Member findMember = memberRepository.find(saveId);

		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
	}

}
