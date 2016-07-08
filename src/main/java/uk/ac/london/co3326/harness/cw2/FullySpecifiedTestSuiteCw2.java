package uk.ac.london.co3326.harness.cw2;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.common.collect.Sets;

import uk.ac.london.co3326.Cw2;
import uk.ac.london.co3326.Util;
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
        tests.add(new SetComparisonTest("Encoding", 1, etalon) {            
            @Override
            public Object expected() {
                Set<Long> result = 
                    Arrays.stream(Util.toByteArray(getEtalon().getA().getNonce().toString()))
                    .boxed()
                    .collect(Collectors.toSet());
                return result;
            }

            @Override
            public Object actual() {
                Set<Long> result = getObject().getCommunication()
                    .stream()
                    .filter(m -> m.getText().contains(getObject().getA().getNonce().toString()))
                    .map(m -> Arrays.stream(m.getEncoded()).boxed().collect(Collectors.toSet()))
                    .reduce((t, u) -> {
                        t.addAll(u);
                        return t;
                    })
                    .orElse(Sets.newHashSet());
                return result;
            }
        });
        tests.add(new SetComparisonTest("Encryption (A and B's nonce)", 1, etalon) {
            @Override
            public Object expected() {
                Set<Long> result = 
                    Sets.union(
                        Arrays.stream(getEtalon().getA().encrypt(getEtalon().getA().getNonce().toString()).getEncrypted())
                        .boxed()
                        .collect(Collectors.toSet()),
                        Arrays.stream(getEtalon().getA().encrypt(getEtalon().getB().getNonce().toString()).getEncrypted())
                        .boxed()
                        .collect(Collectors.toSet())
                    );
                return result;
            }

            @Override
            public Object actual() {
                Set<Long> result = getObject().getCommunication()
                    .stream()
                    .filter(m -> m.getText().contains(getObject().getA().getNonce().toString()) && m.getText().contains(getObject().getB().getNonce().toString()))
                    .map(m -> Arrays.stream(m.getEncrypted()).boxed().collect(Collectors.toSet()))
                    .reduce((t, u) -> {
                        t.addAll(u);
                        return t;
                    })
                    .orElse(Sets.newHashSet());
                return result;
            }
        });
	}

}
