package uk.ac.london.co3326.harness;

import com.google.common.base.Objects;
import com.google.gson.Gson;

import uk.ac.london.co3326.Cw1;

public abstract class BinaryTest extends TestCase<Cw1> {

	public BinaryTest(String description) {
		setDescription(description);
	}

    @Override	
    protected void init(String input) {
        Gson gson = new Gson();
        // actual
        object = gson.fromJson(input, Cw1.class);
        if (object.getAlice() == null || object.getAlice().getRsa() == null
                || object.getBob() == null || object.getBob().getRsa() == null
                || object.getCharlie() == null || object.getCharlie().getRsa() == null)
            throw new RuntimeException("Key is empty");

        // expected
        etalon = gson.fromJson(input, Cw1.class);
        etalon.demonstrate();        
    }
	
	@Override
	public boolean evaluate(String input) {
		try {
		    init(input);
			setSuccessful(Objects.equal(expected(), actual()));
			if (!isSuccessful()) {
				setError(String.format("Missmatch: expected=%d, actual=%d", expected(), actual()));
			}
		} catch (Exception e) {
			setSuccessful(false);
			setError(e.getMessage());
		}
		return isSuccessful();
	}
	
	public abstract Object expected();
    public abstract Object actual();
}
