package uk.ac.london.co3326.harness;

import com.google.gson.Gson;

import uk.ac.london.co3326.Coursework;

public abstract class TestCase<T extends Coursework> extends TestResult {

	protected transient T object, etalon;
	protected transient Gson gson = new Gson(); 

    protected abstract void init(String input);
	public abstract int evaluate(String input);
	
    public TestCase(String description, T etalon) {
        super(description);
		this.etalon = etalon;
	}
			
	public T getObject() {
	    return this.object;
	}
	
	public T getEtalon() {
	    return this.etalon;
	}

}
