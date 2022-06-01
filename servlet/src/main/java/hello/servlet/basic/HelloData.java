package hello.servlet.basic;

import lombok.Data;

@Data
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
