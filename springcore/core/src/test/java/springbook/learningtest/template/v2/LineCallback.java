package springbook.learningtest.template.v2;

public interface LineCallback<T> {

//	Integer doSomethingWithLine(String line, Integer value);
	T doSomethingWithLine(String line, T value);
}
