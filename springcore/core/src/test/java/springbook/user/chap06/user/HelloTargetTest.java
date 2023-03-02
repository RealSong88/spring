package springbook.user.chap06.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

class HelloTargetTest {

	@Test
	void simpleProxy() {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Song")).isEqualTo("Hello Song");
		assertThat(hello.sayHi("Song")).isEqualTo("Hi Song");
		assertThat(hello.sayThankYou("Song")).isEqualTo("Thank You Song");
		
	}
	
	@Test
	void uppercaseTest() {
		Hello proxiedHello = new HelloUppercase(new HelloTarget());
		assertThat(proxiedHello.sayHello("Song")).isEqualTo("HELLO SONG");
		assertThat(proxiedHello.sayHi("Song")).isEqualTo("HI SONG");
		assertThat(proxiedHello.sayThankYou("Song")).isEqualTo("THANK YOU SONG");
		
	}
	
	@Test
	void dynamicProxyTest() {
		Hello proxiedHello = (Hello)Proxy.newProxyInstance(getClass().getClassLoader()
				, new Class[] {Hello.class}
				, new UppercaseHandler(new HelloTarget()));
		
		String sayHello = proxiedHello.sayHello("Song");
		System.out.println(sayHello);
		
	}

}
