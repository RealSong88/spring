package hello.core;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest
class CoreApplicationTests {

//	@Test
//	void contextLoads() {
//
//		String createAuthNumber = createAuthNumber(6);
//		System.out.println("createAuthNumber : " + createAuthNumber);
//
//		char[] otp = OTP(6);
//		String str = String.valueOf(otp);
//		System.out.println("OTP 6 number : " + str);
//	}
//
//
//	static String createAuthNumber(int certNumLength) {
//		Random random = new Random(System.currentTimeMillis());
//
//        int range = (int)Math.pow(10, certNumLength);
//        int trim = (int)Math.pow(10, certNumLength - 1);
//        int result = random.nextInt(range) + trim;
//
//        if(result > range){
//            result = result - trim;
//        }
//
//        return String.valueOf(result);
//	}
//
//	static char[] OTP(int len)
//    {
//
//        // Using numeric values
//        String numbers = "0123456789";
//
//        // Using random method
//        Random rndm_method = new Random();
//
//        char[] otp = new char[len];
//
//        for (int i = 0; i < len; i++)
//        {
//            // Use of charAt() method : to get character value
//            // Use of nextInt() as it is scanning the value as int
//            otp[i] =
//             numbers.charAt(rndm_method.nextInt(numbers.length()));
//        }
//        return otp;
//    }
//
//	@Test
//	void 스플릿테스트() {
//		String receiveNumber = "+83 01066681002";
//		// sms 국내 구분
//		if (receiveNumber.split(" ")[0].equals("+82")) {
//			receiveNumber = receiveNumber.split(" ")[1];
//			if (!receiveNumber.substring(0, 1).equals("0")) {
//				receiveNumber = "0" + receiveNumber;
//			}
//		} else {
//			receiveNumber = "002" + receiveNumber.replaceAll("[^0-9]", " ").replaceAll(" ", "");
//		}
//
//		System.out.println("번호 테스트 : " + receiveNumber);
//	}

	@Test
	void compareTo() {
		int ii = 1;
		int jj = 1;
		Integer valueOf = Integer.valueOf(ii);
		Integer a = 20220109;
		Integer b = 20220101;
		String c = Integer.toString(a);
		String d = Integer.toString(b);
		System.out.println("########### compare start ###########");
		System.out.println(c.compareTo(d));
		System.out.println(a.compareTo(b));
		if ("20220824".compareTo("20220825") <= 0) {
			System.out.println("0보다 작음");
			System.out.println("\"20220824\".compareTo(\"20220825\") = " + "20220824".compareTo("20220825"));
		} else {
			System.out.println("0보다 큼");
		}
		//////////////// date

		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		System.out.println("time = " + time);
		cal.setTimeInMillis(0);
		cal.set(2022, 8, 24);
		Date time2 = cal.getTime();
		System.out.println("time2 = " + time2);

		System.out.println("########### compare end   ###########");
	}

	@Test
	void emailMasking() {
		String email = "j23@gmail.com";
		String emailMask = email.replaceAll("(?<=.{2}).(?=.*@)", "*");
		System.out.println("email : " + emailMask);

		System.out.println("test@test.com".charAt(0));
	}

	@Test
	void 숫자테스트() {
		boolean isNumber = true;
		String str = "11a";
		for (char c : str.toCharArray()) {
			if (c >= 48 && c <= 57) {
				continue;
			} else {
				isNumber = false;
				break;
			}
		}
		System.out.println("str은 숫자? : " + isNumber);
	}

}
