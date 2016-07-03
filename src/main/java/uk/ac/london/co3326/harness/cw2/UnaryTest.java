package uk.ac.london.co3326.harness.cw2;

import com.google.gson.Gson;

import uk.ac.london.co3326.Cw2;
import uk.ac.london.co3326.harness.TestCase;

public abstract class UnaryTest extends TestCase<Cw2> {

    public UnaryTest(String description, int weight, Cw2 etalon) {
        super(description, weight, etalon);
    }
    
    @Override
    protected void init(String input) {
        Gson gson = new Gson();
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
            setScore(getWeight() * pass());
        } catch (Exception e) {
            setError(e.getMessage());
        }
        return getScore();
    }

    public abstract int pass();
}
