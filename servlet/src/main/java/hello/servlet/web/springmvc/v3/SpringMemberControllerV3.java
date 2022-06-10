package hello.servlet.web.springmvc.v3;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.servlet.basic.domain.member.Member;
import hello.servlet.basic.domain.member.MemberRepository;

/**
 * v3
 * Model 도입
 * ViewName 직접 반환
 * @RequestParam 사용
 * @RequestMapping -> @GetMapping, @PostMapping
 */

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

	private MemberRepository memberRepository = MemberRepository.getInstance();

	@GetMapping("/new-form")
	public String newForm() {
		return "new-form";
	}
	
	@PostMapping("save")
	public String save(@RequestParam String username, @RequestParam int age, Model model) {
		
		Member member = new Member(username, age);
		System.out.println("member = " + member);
		memberRepository.save(member);

//		메소드가 논리뷰명을 리턴할땐 모델을 사용..
//		ModelAndView mv = new ModelAndView("save-result");
//		mv.addObject("member", member);
		model.addAttribute("member", member);
		return "save-result";
	}
	
	@GetMapping
	public String members(Model model) {
		
		List<Member> members = memberRepository.findAll();
		
		model.addAttribute("members", members);
		return "members";
	}
}
