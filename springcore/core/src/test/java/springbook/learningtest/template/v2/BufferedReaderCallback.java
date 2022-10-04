package springbook.learningtest.template.v2;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {

	Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
