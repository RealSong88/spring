package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HelloData {
	
	public HelloData() {
		/**
		 * 생성자
		 * 2022. 5. 31.
		 */
	}
	private String username;
	private int age;
}
