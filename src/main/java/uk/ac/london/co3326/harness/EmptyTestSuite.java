package uk.ac.london.co3326.harness;

import uk.ac.london.co3326.Cw1;
import uk.ac.london.co3326.Util;

public class EmptyTestSuite extends TestSuite<Cw1> {

	public EmptyTestSuite(String input, String testInput) throws ClassNotFoundException {
		super(input, testInput);

		tests.add(new UnaryTest("p is prime", null) {
			@Override
			public boolean pass() {
				boolean result = Util.isPrime(getObject().getAlice().getRsa().p);
				return result;
			}
		});
		
		tests.add(new UnaryTest("q is prime", null) {
			@Override
			public boolean pass() {
				boolean result = Util.isPrime(getObject().getAlice().getRsa().q);
				return result;
			}
		});

		tests.add(new BinaryTest("gcd(e, r) = 1", null) {
			@Override
			public Object expected() {
				return 1L;
			}

			@Override
			public Object actual() {
				return Util.gcd(getObject().getAlice().getRsa().e, getObject().getAlice().getRsa().r);
			}
		});

		tests.add(new BinaryTest("d = modInv(e, r) = 1", null) {
			@Override
			public Object expected() {
				return Util.modInverse(getObject().getAlice().getRsa().e, getObject().getAlice().getRsa().r);
			}

			@Override
			public Object actual() {
				return getObject().getAlice().getRsa().d;
			}
		});

	}

}
