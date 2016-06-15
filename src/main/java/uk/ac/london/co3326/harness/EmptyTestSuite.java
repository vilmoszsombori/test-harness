package uk.ac.london.co3326.harness;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import uk.ac.london.co3326.Cw1;
import uk.ac.london.co3326.Util;

public class EmptyTestSuite extends TestSuite<Cw1> {

	public EmptyTestSuite(String input, String testInput) throws ClassNotFoundException {
		super(input, testInput);

		tests.add(new UnaryTest("p is prime", 1, null) {
			@Override
			public int pass() {
				boolean result = Util.isPrime(getObject().getAlice().getRsa().p);
				return result ? 1 : 0;
			}
		});
		
		tests.add(new UnaryTest("q is prime", 1, null) {
			@Override
			public int pass() {
				boolean result = Util.isPrime(getObject().getAlice().getRsa().q);
				return result ? 1 : 0;
			}
		});

		tests.add(new BinaryTest("gcd(e, r) = 1", 1, null) {
			@Override
			public Object expected() {
				return 1L;
			}

			@Override
			public Object actual() {
				return Util.gcd(getObject().getAlice().getRsa().e, getObject().getAlice().getRsa().r);
			}
		});

		tests.add(new BinaryTest("d = modInv(e, r) = 1", 1, null) {
			@Override
			public Object expected() {
				return Util.modInverse(getObject().getAlice().getRsa().e, getObject().getAlice().getRsa().r);
			}

			@Override
			public Object actual() {
				return getObject().getAlice().getRsa().d;
			}
		});

        tests.add(new UnaryTest("RSA text", 1, null) {
            @Override
            public int pass() {
                if (getObject().getCommunication() == null || getObject().getCommunication().isEmpty())
                    throw new IllegalArgumentException("No communication");
                String etalon = getObject().getCommunication().stream().map(m -> m.getText()).filter(m -> m != null).findFirst().orElse("##");
                Set<String> communication = getObject().getCommunication().stream().map(m -> m.getText()).filter(m -> m != null).collect(Collectors.toCollection(TreeSet::new));
                int occurence = communication.stream().mapToInt(m -> m.trim().startsWith(etalon) && m.length() > etalon.length() ? 1 : 0).sum();
                System.out.println(occurence + " --> " + communication);
                return Math.min(occurence, 2);
            }
        });
		
	}

}
