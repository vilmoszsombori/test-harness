package uk.ac.london.co3326.harness.cw2;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.common.collect.Sets;

import uk.ac.london.co3326.Cw2;
import uk.ac.london.co3326.harness.TestSuite;
import uk.ac.london.co3326.harness.cw2.SetComparisonTest;

public class FullySpecifiedTestSuiteCw2 extends TestSuite<Cw2> {

	public FullySpecifiedTestSuiteCw2(String input, String testInput) throws ClassNotFoundException {
	    super(input, testInput);

        // set up the etalon
        if (testInput != null && !testInput.isEmpty()) {
            Gson gson = new Gson();
            etalon = gson.fromJson(testInput, Cw2.class);
            etalon.demonstrate();
        }
        
        // add test cases
		tests.add(new BinaryTest("A's private key", 5, etalon) {
			@Override
			public Object expected() {
				return getEtalon().getA().getD();
			}

			@Override
			public Object actual() {
				return getObject().getA().getRsa().d;
			}
		});
		tests.add(new BinaryTest("B's private key", 5, etalon) {
			@Override
			public Object expected() {
				return getEtalon().getB().getD();
			}

			@Override
			public Object actual() {
				return getObject().getB().getRsa().d;
			}
		});
		tests.add(new BinaryTest("S's private key", 5, etalon) {
			@Override
			public Object expected() {
				return getEtalon().getS().getD();
			}

			@Override
			public Object actual() {
				return getObject().getS().getRsa().d;
			}
		});
		/*
		tests.add(new BinaryTest("Encoding", 5, etalon) {
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
		*/
		tests.add(new SetComparisonTest("Encryption", 1, etalon) {
			@Override
			public Object expected() {
				Set<Long> result = Arrays.stream(getEtalon().getB().encrypt(getEtalon().getA().getNonce().toString()).getEncrypted()).boxed().collect(Collectors.toSet());
				return result;
			}

			@Override
			public Object actual() {
				Set<Long> result = getObject().getCommunication()
				    .stream()
				    .filter(m -> m.getText().contains(getObject().getA().getNonce().toString()))
				    .findFirst()
				    .map(m -> Arrays.stream(m.getEncrypted()).boxed().collect(Collectors.toSet()))
				    .orElse(Sets.newHashSet());
				return result;
			}
		});
	}

}
