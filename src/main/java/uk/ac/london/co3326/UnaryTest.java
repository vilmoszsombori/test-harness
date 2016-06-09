package uk.ac.london.co3326;

import com.google.gson.Gson;

import uk.ac.london.co3326.harness.TestCase;

public abstract class UnaryTest extends TestCase<Cw1> {

    public UnaryTest(String description) {
        setDescription(description);
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
    public boolean evaluate(String input) {
        try {
            init(input);
            setSuccessful(pass());
        } catch (Exception e) {
            setSuccessful(false);
            setError(e.getMessage());
        }
        return isSuccessful();
    }

    public abstract boolean pass();
}
