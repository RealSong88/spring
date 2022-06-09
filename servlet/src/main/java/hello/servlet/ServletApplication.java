package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import hello.servlet.web.springmvc.v1.SpringMemberFormControllerV1;

@ServletComponentScan
@SpringBootApplication
public class ServletApplication {
	/* SpringMemberFormControllerV1 @RequestMapping 만 있을 경우 
	 * //스프링 빈 직접 등록
	 * 
	 * @Bean SpringMemberFormControllerV1 springMemberFormControllerV1() { return
	 * new SpringMemberFormControllerV1(); }
	 */

	public static void main(String[] args) {
		
		SpringApplication.run(ServletApplication.class, args);
	}

}
