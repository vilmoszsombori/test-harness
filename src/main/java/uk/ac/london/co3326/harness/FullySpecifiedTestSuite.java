package uk.ac.london.co3326.harness;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import uk.ac.london.co3326.Cw1;

public class FullySpecifiedTestSuite extends TestSuite<Cw1> {

	public FullySpecifiedTestSuite(String input, String testInput) throws ClassNotFoundException {
		super(input, testInput);

		// add test cases
		tests.add(new BinaryTest("Alice's private key", etalon) {
			@Override
			public Object expected() {
				return getEtalon().getAlice().getD();
			}

			@Override
			public Object actual() {
				return getObject().getAlice().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Bob's private key", etalon) {
			@Override
			public Object expected() {
				return getEtalon().getBob().getD();
			}

			@Override
			public Object actual() {
				return getObject().getBob().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Charlie's private key", etalon) {
			@Override
			public Object expected() {
				return getEtalon().getCharlie().getD();
			}

			@Override
			public Object actual() {
				return getObject().getCharlie().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Encoding", etalon) {
			@Override
			public Object expected() {
				return getEtalon().getCommunication().get(0).getEncoded()[2];
			}

			@Override
			public Object actual() {
				return getObject().getCommunication().stream()
						.filter(m -> m.getEncoded() != null && m.getEncoded().length > 0).findFirst()
						.map(m -> m.getEncoded()[2]).orElse(null);
			}
		});
		tests.add(new SetComparisonTest("Encryption + decryption", etalon) {
			@Override
			public Object expected() {
				Set<Long> result = getEtalon().getCommunication().stream()
						.filter(m -> m.getEncoded() != null && m.getEncoded().length > 0)
						.map(m -> m.getEncoded()[2]).collect(Collectors.toCollection(TreeSet::new));
				System.out.println("Etalon: " + result);
				return result;
			}

			@Override
			public Object actual() {
				Set<Long> result = getObject().getCommunication().stream()
						.filter(m -> m.getEncoded() != null && m.getEncoded().length > 0)
						.map(m -> m.getEncoded()[2]).collect(Collectors.toCollection(TreeSet::new));
				System.out.println("Actual: " + result);
				return result;
			}
		});
	}

}
