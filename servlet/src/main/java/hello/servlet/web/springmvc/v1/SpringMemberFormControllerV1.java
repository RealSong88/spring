package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberFormControllerV1 {

	@RequestMapping("/springmvc/v1/members/new-form")
	public ModelAndView process() {
		return new ModelAndView("new-form");
	}
	
}

//@Component //컴포넌트 스캔을 통해 스프링 빈으로 등록
//@RequestMapping
//public class SpringMemberFormControllerV1 {
//
//	@RequestMapping("/springmvc/v1/members/new-form")
//	public ModelAndView process() {
//		return new ModelAndView("new-form");
//	}
//}

/*
 * @RequestMapping public class SpringMemberFormControllerV1 {
 * 
 * @RequestMapping("/springmvc/v1/members/new-form") public ModelAndView
 * process() { return new ModelAndView("new-form"); } }
 */