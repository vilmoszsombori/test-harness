package uk.ac.london.co3326.harness;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;
import com.google.gson.Gson;

import uk.ac.london.co3326.Coursework;

public class TestSuite<T extends Coursework> {

	protected transient T etalon;
	protected List<TestCase<?>> tests;
	protected transient String input;
	
	public boolean evaluate(Class<?> irrelevant) {
		return tests.stream().filter(t -> !Objects.equal(t.getClass(), equals(irrelevant)))
				.allMatch(t -> t.evaluate(input) > 0);
	}

	public boolean evaluate() {
		return tests.stream().map(t -> t.evaluate(input)).filter(r -> r == 0).count() == 0;
	}

	public TestSuite(String input, String testInput) throws ClassNotFoundException {
		this.input = input;
		this.tests = new ArrayList<>();
		
        // set up the etalon
		if (testInput != null && testInput.isEmpty()) {			
	        String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
	        @SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) Class.forName(className);
			Gson gson = new Gson();
	        etalon = (T) gson.fromJson(testInput, clazz);
	        etalon.demonstrate();
	        System.out.println(etalon);
		}
	}

}
