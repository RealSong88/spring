package hello.servlet.web.frontcontroller.v3;

import java.util.Map;

import hello.servlet.web.frontcontroller.ModelView;

public class MemberFormControllerV3 implements ControllerV3 {

	public ModelView process(Map<String, String> paramMap) {
		return new ModelView("new-form");
	}

}
