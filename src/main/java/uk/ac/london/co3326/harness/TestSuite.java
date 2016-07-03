package uk.ac.london.co3326.harness;

import java.util.ArrayList;
import java.util.List;

import uk.ac.london.co3326.Coursework;

public class TestSuite<T extends Coursework> {

	protected transient T etalon;
	protected List<TestCase<T>> tests;
	protected transient String input;

	public int evaluate() {
		return tests.stream().mapToInt(t -> t.evaluate(input)).sum();
	}

	public TestSuite(String input, String testInput) throws ClassNotFoundException {
		this.input = input;
		this.tests = new ArrayList<>();	
	}
	
	public List<TestResult> getResult() {
	    List<TestResult> result = new ArrayList<>();
	    tests.stream().forEach(r -> result.add(new TestResult(r)));
	    return result;
	}

}
