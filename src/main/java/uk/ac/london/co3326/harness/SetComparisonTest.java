package uk.ac.london.co3326.harness;

import java.util.Set;

import com.google.common.collect.Sets;

import uk.ac.london.co3326.Cw1;

public abstract class SetComparisonTest extends BinaryTest {
	
	public SetComparisonTest(String description, Cw1 etalon) {
	    super(description, etalon);
	}
	
	@Override
    protected void init(String input) {
	    super.init(input);
        if (object.getAlice().getUncomputedD() == 0 || object.getBob().getUncomputedD() == 0
                || object.getCharlie().getUncomputedD() == 0)
            throw new RuntimeException("Private key not computed");         
    }
	
	@Override
	public int evaluate(String input) {
		try {
		    init(input);
		    Set<?> intersect = Sets.intersection((Set<?>)expected(), (Set<?>)actual());
			setScore(intersect.size() - 1);
			if (getScore() == 0) {
				setError(String.format("Missmatch: expected=%s, actual=%s", expected(), actual()));
			}
		} catch (Exception e) {
			setScore(0);
			setError(e.getMessage());
		}
		return getScore();
	}
	
}
