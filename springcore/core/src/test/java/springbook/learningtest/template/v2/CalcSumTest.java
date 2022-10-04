package springbook.learningtest.template.v2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalcSumTest {
	Calculator calculator;
	String numbFilepath;

	@BeforeEach
	public void setUp() {
		this.calculator = new Calculator();
		this.numbFilepath = getClass().getResource("numbers.txt").getPath();

		System.out.println("파일 경로 : " + this.numbFilepath);
	}

	@Test
	void sumOfNumbers() throws IOException {
		Calculator cal = new Calculator();
		int sum = cal.calcSum(getClass().getResource("numbers.txt").getPath());
		int sum2 = cal.calcSum(getClass().getResource("numbers2.txt").getPath());

		assertThat(sum).isEqualTo(10);
		assertThat(sum2).isEqualTo(18);
	}


	@Test
	void multiplyOfNumbers() throws IOException {
		assertThat(calculator.calcMultiply(numbFilepath)).isEqualTo(24);
	}

	@Test
	void concatenateStrings() throws IOException {
		assertThat(calculator.concatenate(this.numbFilepath)).isEqualTo("1234");
	}


}