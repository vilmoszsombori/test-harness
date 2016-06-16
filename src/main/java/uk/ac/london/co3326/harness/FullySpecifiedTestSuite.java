package uk.ac.london.co3326.harness;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import uk.ac.london.co3326.Cw1;

public class FullySpecifiedTestSuite extends TestSuite<Cw1> {

	public FullySpecifiedTestSuite(String input, String testInput) throws ClassNotFoundException {
	    super(input, testInput);

        // set up the etalon
        if (testInput != null && !testInput.isEmpty()) {
            Gson gson = new Gson();
            etalon = gson.fromJson(testInput, Cw1.class);
            etalon.demonstrate();
        }
        
        // add test cases
		tests.add(new BinaryTest("Alice's private key", 2, etalon) {
			@Override
			public Object expected() {
				return getEtalon().getAlice().getD();
			}

			@Override
			public Object actual() {
				return getObject().getAlice().getRsa().d;
			}
		});
		tests.add(new BinaryTest("Bob's private key", 2, etalon) {
			@Override
			public Object expected() {
				return getEtalon().getBob().getD();
			}

			@Override
			public Object actual() {
				return getObject().getBob().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Charlie's private key", 2, etalon) {
			@Override
			public Object expected() {
				return getEtalon().getCharlie().getD();
			}

			@Override
			public Object actual() {
				return getObject().getCharlie().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Encoding", 3, etalon) {
			@Override
			public Object expected() {
				return getEtalon().getCommunication().get(0).getEncoded()[0];
			}

			@Override
			public Object actual() {
				return getObject().getCommunication().stream()
						.filter(m -> m.getEncoded() != null && m.getEncoded().length > 0).findFirst()
						.map(m -> m.getEncoded()[0]).orElse(null);
			}
		});
		tests.add(new SetComparisonTest("Encryption + decryption", 3, etalon) {
			@Override
			public Object expected() {
				Set<Long> result = getEtalon().getCommunication().stream()
						.filter(m -> m.getEncoded() != null && m.getEncoded().length > 0)
						.map(m -> m.getEncoded()[0]).collect(Collectors.toCollection(TreeSet::new));
				return result;
			}

			@Override
			public Object actual() {
				Set<Long> result = getObject().getCommunication().stream()
						.filter(m -> m.getEncoded() != null && m.getEncoded().length > 0)
						.map(m -> m.getEncoded()[0]).collect(Collectors.toCollection(TreeSet::new));
				return result;
			}
		});
	}

}
