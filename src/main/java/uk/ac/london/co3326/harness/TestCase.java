package uk.ac.london.co3326.harness;

import com.google.gson.Gson;

import uk.ac.london.co3326.Coursework;

public abstract class TestCase<T extends Coursework> {

	private int score = 0;
	private String error;
	private String description;
	protected transient T object, etalon;
	protected transient Gson gson = new Gson(); 

    protected abstract void init(String input);
	public abstract int evaluate(String input);
	
	public TestCase() {}
		
	public int getScore() {
		return score;
	}

	public String getError() {
		return error;
	}

	public String getDescription() {
		return description;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public T getObject() {
	    return this.object;
	}
	
	public T getEtalon() {
	    return this.etalon;
	}

}
