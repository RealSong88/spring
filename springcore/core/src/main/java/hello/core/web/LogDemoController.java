package hello.core.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.core.common.MyLogger;
import hello.core.logdemo.LogDemoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

	private final LogDemoService logDemoService;
//	private final ObjectProvider<MyLogger> myLoggerProvier;
	private final MyLogger myLogger;

	@RequestMapping("log-demo")
	@ResponseBody
	public String logDemo(HttpServletRequest request) {
		String requestURL = request.getRequestURL().toString();
//		MyLogger myLogger = myLoggerProvier.getObject();
		myLogger.setRequestURL(requestURL);

//		System.out.println("mylogger = " + myLogger.getClass());

		myLogger.log("controller-test");
		logDemoService.logic("testId");
		return "OK";
	}
}
