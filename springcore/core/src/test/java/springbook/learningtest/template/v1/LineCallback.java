package springbook.learningtest.template.v1;

public interface LineCallback<T> {

	Integer doSomethingWithLine(String line, Integer value);
//	Integer doSomethingWithLine(String line, T value);
}
