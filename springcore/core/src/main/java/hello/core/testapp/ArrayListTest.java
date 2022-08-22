package hello.core.testapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ArrayListTest {

	public static void main(String[] args) {

		List<String> list = Arrays.asList("a","b","c");
		System.out.println(list);
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("aa", "bb", "cc"));
		System.out.println(arrayList);

		Supplier<String> getString = () -> "test function";

		System.out.println(getString.get());
	}

}
