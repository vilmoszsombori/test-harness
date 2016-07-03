package uk.ac.london.co3326.harness.cw2;

import com.google.common.base.Objects;

import uk.ac.london.co3326.Cw2;
import uk.ac.london.co3326.harness.TestCase;

public abstract class BinaryTest extends TestCase<Cw2> {

	public BinaryTest(String description, int weight, Cw2 etalon) {
	    super(description, weight, etalon);
	}

    @Override	
    protected void init(String input) {
        object = gson.fromJson(input, Cw2.class);
        if (object.getA() == null || object.getA().getRsa() == null
                || object.getB() == null || object.getB().getRsa() == null
                || object.getS() == null || object.getS().getRsa() == null)
            throw new RuntimeException("Key is empty");
    }
	
	@Override
	public int evaluate(String input) {
		try {
		    init(input);
			setScore(getWeight() * (Objects.equal(expected(), actual()) ? 1 : 0));
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
