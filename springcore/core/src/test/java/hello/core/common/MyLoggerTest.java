package hello.core.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyLoggerTest {

	@Autowired
	MyLogger myLogger;
	@Test
	void test() {

		myLogger.setRequestURL("testURL");
		System.out.println("mylogger = " + myLogger.getClass());
		myLogger.log("log start");

		assertThat(myLogger).isInstanceOf(MyLogger.class);
	}

}
