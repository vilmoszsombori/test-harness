package uk.ac.london.co3326.harness;

import uk.ac.london.co3326.Cw1;
import uk.ac.london.co3326.Util;

public class EmptyTestSuite extends TestSuite<Cw1> {

	public EmptyTestSuite(String input, String testInput) throws ClassNotFoundException {
		super(input, testInput);

		tests.add(new UnaryTest("Alice's p is prime", null) {
			@Override
			public boolean pass() {
				System.out.println(getObject().getAlice().getRsa().p);
				return Util.isPrime(getObject().getAlice().getRsa().p);
			}
		});
	}

}
