package springbook.learningtest.template.v1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	public Integer calcSum(String filepath) throws IOException {
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(filepath));
			Integer sum = 0;
			String line = null;
			while ((line = br.readLine()) != null) {
				sum += Integer.valueOf(line);
			}
			return sum;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}

	public Integer calcSum2(String filepath) throws IOException {
		BufferedReaderCallback sumCallback =
				new BufferedReaderCallback() {
					public Integer doSomethingWithReader(BufferedReader br) throws IOException {
						Integer sum = 0;
						String line = null;
						while((line = br.readLine()) != null) {
							sum += Integer.valueOf(line);
						}
						return sum;
					}
				};
		return fileReadTemplate(filepath, sumCallback);
	}

	public Integer calcSum3(String filepath) throws IOException {
		LineCallback sumCallback =
				new LineCallback() {
					public Integer doSomethingWithLine(String line, Integer value) {
						return value + Integer.valueOf(line);
					}
				};
		return lineReadTemplate(filepath, sumCallback, 0);
	}

	public Integer calcMultiply(String filepath) throws IOException {
		BufferedReaderCallback mulCallback =
				new BufferedReaderCallback() {

					@Override
					public Integer doSomethingWithReader(BufferedReader br) throws IOException {
						Integer mul = 1;
						String line = null;
						while((line = br.readLine()) != null) {
							mul *= Integer.valueOf(line);
						}
						return mul;
					}
				};
		return fileReadTemplate(filepath, mulCallback);
	}
	public Integer calcMultiply3(String filepath) throws IOException {
		LineCallback multiplyCallback =
				new LineCallback() {

					public Integer doSomethingWithLine(String line, Integer value) {
						return value * Integer.valueOf(line);
					}
				};
			return lineReadTemplate(filepath, multiplyCallback, 1);
	}

	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			int ret = callback.doSomethingWithReader(br);
			return ret;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public Integer lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			Integer res = initVal;
			String line = null;
			while((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
