package springbook.learningtest.junit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

class JUnitTest {

	static JUnitTest testObject;


	@Test
	void test1() {
		assertThat(this).isNotEqualTo(testObject);
//		assertThat(this).isEqualTo(testObject);
		testObject = this;
	}
	@Test
	void test2() {
		assertThat(this).isNotEqualTo(testObject);
//		assertThat(this).isEqualTo(testObject);
		testObject = this;
	}
	@Test
	void test3() {
		assertThat(this).isNotEqualTo(testObject);
//		assertThat(this).isEqualTo(testObject);
		testObject = this;
	}



}
