package uk.ac.london.co3326.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import uk.ac.london.co3326.Cw1;

public class TestSuite {

	public static String TEST_FILE = "test.txt";

	private List<TestCase> tests;
	private transient String file;
	private transient Cw1 object, etalon;

	public void evaluate(String input) {
		init(input);
		tests.stream().forEach(t -> t.evaluate());
	}

	private void init(String input) {
		Gson gson = new Gson();
		etalon = gson.fromJson(input, Cw1.class);
		etalon.demonstrate();

		object = gson.fromJson(input, Cw1.class);
		
		tests.add(new PrivateKeyTest(object, etalon));
	}

	public TestSuite(String path) {
		this.file = path + File.separator + TEST_FILE;
		this.tests = new ArrayList<>();
	}

	public String getFile() {
		return this.file;
	}

}
