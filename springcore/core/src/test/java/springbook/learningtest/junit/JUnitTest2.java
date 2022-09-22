package springbook.learningtest.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class JUnitTest2 {


	static Set<JUnitTest2> testObjects = new HashSet<>();

	@Test
	void test1() {
		assertThat(testObjects).isNotEqualTo(hasItem(this));
		testObjects.add(this);

		System.out.println(this);
		System.out.println("테스트1 : " + testObjects.toString());
	}
	@Test
	void test2() {
		assertThat(testObjects).isNotEqualTo(hasItem(this));
		testObjects.add(this);

		System.out.println("테스트2 : " + testObjects.toString());
	}
	@Test
	void test3() {
		assertThat(testObjects).isNotEqualTo(hasItem(this));
		testObjects.add(this);

		System.out.println("테스트3 : " + testObjects.toString());
	}



}
