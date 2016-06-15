package uk.ac.london.co3326.harness;

import com.google.common.base.Objects;

import uk.ac.london.co3326.Cw1;

public abstract class BinaryTest extends TestCase<Cw1> {

	public BinaryTest(String description, int weight, Cw1 etalon) {
	    super(description, weight, etalon);
	}

    @Override	
    protected void init(String input) {
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
