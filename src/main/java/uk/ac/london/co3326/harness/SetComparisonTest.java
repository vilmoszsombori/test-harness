package uk.ac.london.co3326.harness;

import java.util.Set;

import com.google.common.collect.Sets;

public abstract class SetComparisonTest extends BinaryTest {
	
	public SetComparisonTest(String description) {
		super(description);
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
