package uk.ac.london.co3326.harness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.base.Objects;

import uk.ac.london.co3326.Cw1;

public abstract class BinaryTest extends TestCase<Cw1> {

	public BinaryTest(String description) {
		setDescription(description);
		// set up the etalon
        try (BufferedReader in = new BufferedReader(new FileReader(TestSuite.getFile()))) {
            String line;
            while ((line = in.readLine()) != null) {
                try {
                    // convert the JSON string back to object
                    etalon = gson.fromJson(line, Cw1.class);
                    etalon.demonstrate();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }		
	}

    @Override	
    protected void init(String input) {
        // actual
        object = gson.fromJson(input, Cw1.class);
        if (object.getAlice() == null || object.getAlice().getRsa() == null
                || object.getBob() == null || object.getBob().getRsa() == null
                || object.getCharlie() == null || object.getCharlie().getRsa() == null)
            throw new RuntimeException("Key is empty");
    }
	
	@Override
	public int evaluate(String input) {
		try {
		    init(input);
			setScore(Objects.equal(expected(), actual()) ? 1 : 0);
			if (getScore() == 0) {
				setError(String.format("Missmatch: expected=%s, actual=%s", expected(), actual()));
			}
		} catch (Exception e) {
			setError(e.getMessage());
		}
		return getScore();
	}
	
	public abstract Object expected();
    public abstract Object actual();
}
