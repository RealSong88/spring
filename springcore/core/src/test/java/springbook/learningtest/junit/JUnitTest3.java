package springbook.learningtest.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class JUnitTest3 {

	@Autowired ApplicationContext context;

	static Set<JUnitTest3> testObjects = new HashSet<>();
	static List<JUnitTest3> lists = new ArrayList<>();
	static ApplicationContext contextObject = null;
	private Object first;
	private Object second;
	private List<Object> list;

	@BeforeEach
	void setUp() {
		first = new Object();
		second = new Object();
		list = Arrays.asList(first, second);
	}

	@Test
	void test() {
		org.hamcrest.MatcherAssert.assertThat(list, hasItem(first));
//		lists = new ArrayList<>(Arrays.asList(this));
		System.out.println("#### lists1 = " + lists);
		lists.add(this);
		System.out.println("#### lists2 = " + lists);
		org.hamcrest.MatcherAssert.assertThat(lists, hasItem(this));

	}
	@Test
	void test1() {
		System.out.println("#### testObjects : " + testObjects);
		System.out.println("#### this : " + this);

		// Collection의 하부 객체를 비교하지않음
//		assertThat(testObjects).isNotEqualTo(hasItem(this));
		org.hamcrest.MatcherAssert.assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);

		assertThat(contextObject == null || contextObject == this.context).isEqualTo(true);
		System.out.println(contextObject == this.context);

		System.out.println("#### testObjects : " + testObjects);
		System.out.println("#### this : " + this);
		org.hamcrest.MatcherAssert.assertThat(testObjects, hasItem(this));
	}

	@Test
	void test2() {
//		assertThat(testObjects).isNotEqualTo(hasItem(this));
		org.hamcrest.MatcherAssert.assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		assertTrue(contextObject == null || contextObject == this.context);
		System.out.println("!!!!!!!! this.context = " + this.context);
		contextObject = this.context;
	}

	@Test
	void test3() {
		testObjects = null;
//		assertThat(testObjects).isNotEqualTo(hasItem(this));ㅅ		org.hamcrest.MatcherAssert.assertThat(testObjects, not(hasItem(this)));
		System.out.println("@@@@@@@ testObjects = " + testObjects);
		org.hamcrest.MatcherAssert.assertThat(testObjects, not(hasItem(this)));
//		testObjects.add(this);

//		org.hamcrest.MatcherAssert.assertThat(testObjects, either(nullValue()).or(is(this.context)));
		contextObject = this.context;
	}




}
