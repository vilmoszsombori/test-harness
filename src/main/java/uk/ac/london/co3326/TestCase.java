package uk.ac.london.co3326;

import com.google.gson.Gson;

public class TestCase {
	
	private String file;
	private boolean successful;
	private String error;
	private String description;
	
	public void evaluate(String input) {
		try {
			Gson gson = new Gson();
			Cw1 etalon = gson.fromJson(input, Cw1.class);
			etalon.demonstrate();
			
			Cw1 object = gson.fromJson(input, Cw1.class);
			
			if (etalon.getAlice().getE() == object.getAlice().getE()) {
				successful = true;
			}
		} catch(Exception e) {
			setError(e.getMessage(), "Input: " + input);
		}
	}
	
	public TestCase(String file) {
		this.file = file;
	}
	
	public void setError(String error, String description) {
		setError(error);
		setDescription(description);
		setSuccessful(false);
	}
	
	public String getFile() {
		return this.file;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public String getError() {
		return error;
	}

	public String getDescription() {
		return description;
	}

	public void setFile(String file) {
		this.file = file;
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
