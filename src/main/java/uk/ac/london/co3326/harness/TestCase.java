package uk.ac.london.co3326.harness;

import uk.ac.london.co3326.Coursework;

public abstract class TestCase<T extends Coursework> {

	private boolean successful = false;
	private String error;
	private String description;
	protected transient T object, etalon;

    protected abstract void init(String input);
	public abstract boolean evaluate(String input);
	
	public TestCase() {}
		
	public boolean isSuccessful() {
		return successful;
	}

	public String getError() {
		return error;
	}

	public String getDescription() {
		return description;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
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
