package uk.ac.london.co3326.harness;

import com.google.gson.Gson;

import uk.ac.london.co3326.Cw1;

public abstract class UnaryTest extends TestCase<Cw1> {

    public UnaryTest(String description, Cw1 etalon) {
        super(description, etalon);
    }

    @Override
    protected void init(String input) {
        Gson gson = new Gson();
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
            setScore(pass() ? 1 : 0);
        } catch (Exception e) {
            setError(e.getMessage());
        }
        return getScore();
    }

    public abstract boolean pass();
}
