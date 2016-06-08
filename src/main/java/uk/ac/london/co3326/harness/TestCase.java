package uk.ac.london.co3326.harness;

public abstract class TestCase {

	private boolean successful;
	private String error;
	private String description;

	public abstract void evaluate();
	
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

}
