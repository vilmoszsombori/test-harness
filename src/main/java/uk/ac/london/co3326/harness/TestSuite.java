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
		tests.add(new TestCase() {
			@Override
			public void evaluate() {
				setDescription("Private key");
				try {
					setSuccessful(object.getAlice().getD() == etalon.getAlice().getD());
					if (!isSuccessful()) {
						setError(String.format("Private key missmatch: Alice [expected=%d, actual=%d]",
								etalon.getAlice().getD(), object.getAlice().getD()));
					}
				} catch (Exception e) {
					setSuccessful(false);
					setError(e.getMessage());
				}
			}
		});
	}

	private void init(String input) {
		Gson gson = new Gson();
		etalon = gson.fromJson(input, Cw1.class);
		etalon.demonstrate();

		object = gson.fromJson(input, Cw1.class);
	}

	public TestSuite(String path) {
		this.file = path + File.separator + TEST_FILE;
		this.tests = new ArrayList<>();
	}

	public String getFile() {
		return this.file;
	}

}
