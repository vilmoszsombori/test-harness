package uk.ac.london.co3326.harness.cw2;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import uk.ac.london.co3326.Cw2;
import uk.ac.london.co3326.Util;
import uk.ac.london.co3326.harness.TestSuite;
import uk.ac.london.co3326.harness.cw2.SetComparisonTest;

public class EmptyTestSuiteCw2 extends TestSuite<Cw2> {

    public EmptyTestSuiteCw2(String input, String testInput) throws ClassNotFoundException {
        super(input, testInput);

        tests.add(new UnaryTest("p is prime", 2, null) {
            @Override
            public int pass() {
                boolean result = Util.isPrime(getObject().getA().getRsa().p);
            	setDescription(getObject().getA().getRsa().p + (result ? " is " : " is not ") + "prime");
                return result ? 1 : 0;
            }
        });

        tests.add(new UnaryTest("q is prime", 2, null) {
            @Override
            public int pass() {
                boolean result = Util.isPrime(getObject().getA().getRsa().q);
            	setDescription(getObject().getA().getRsa().q + (result ? " is " : " is not ") + "prime");
                return result ? 1 : 0;
            }
        });

        tests.add(new UnaryTest("n = p * q", 1, null) {
            @Override
            public int pass() {
                boolean result = getObject().getA().getRsa().p
                        * getObject().getA().getRsa().q == getObject().getA().getRsa().n;
                return result ? 1 : 0;
            }
        });

        tests.add(new UnaryTest("r = (p-1)*(q-1)", 1, null) {
            @Override
            public int pass() {
                boolean result = (getObject().getA().getRsa().p - 1)
                        * (getObject().getA().getRsa().q - 1) == getObject().getA()
                                .getRsa().r;
                return result ? 1 : 0;
            }
        });

        tests.add(new BinaryTest("gcd(e, r) = 1", 2, null) {
            @Override
            public Object expected() {
                return 1L;
            }

            @Override
            public Object actual() {
                return Util.gcd(getObject().getA().getRsa().e,
                        getObject().getA().getRsa().r);
            }
        });

        tests.add(new BinaryTest("d = modInv(e, r)", 2, null) {
            @Override
            public Object expected() {
                return Util.modInverse(getObject().getA().getRsa().e,
                        getObject().getA().getRsa().r);
            }

            @Override
            public Object actual() {
                return getObject().getA().getRsa().d;
            }
        });

        /*
        tests.add(new UnaryTest("RSA text", 5, null) {
            @Override
            public int pass() {
                if (getObject().getCommunication() == null
                        || getObject().getCommunication().isEmpty())
                    throw new IllegalArgumentException("No communication");
                String etalon = getObject().getCommunication().stream().map(m -> m.getText())
                        .filter(m -> m != null).findFirst().orElse("##");
                Set<String> communication = getObject().getCommunication().stream()
                        .map(m -> m.getText()).filter(m -> m != null)
                        .collect(Collectors.toCollection(TreeSet::new));
                int occurence = communication.stream().mapToInt(
                        m -> m.trim().startsWith(etalon) && m.length() > etalon.length() ? 1 : 0).sum();
                return Math.min(occurence, 2);
            }
        });

        // set up the etalon
        if (input != null && !input.isEmpty()) {
            Gson gson = new Gson();
            etalon = gson.fromJson(input, Cw2.class);
            if (etalon != null) {
                etalon.demonstrate();

                tests.add(new SetComparisonTest("Encryption + decryption", 5, etalon) {
                    @Override
                    public Object expected() {
                        Set<Long> result =
                                getEtalon().getCommunication().stream()
                                        .filter(m -> m.getEncoded() != null
                                                && m.getEncoded().length > 0)
                                        .map(m -> m.getEncoded()[0])
                                        .collect(Collectors.toCollection(TreeSet::new));
                        return result;
                    }

                    @Override
                    public Object actual() {
                        Set<Long> result =
                                getObject().getCommunication().stream()
                                        .filter(m -> m.getEncoded() != null
                                                && m.getEncoded().length > 0)
                                        .map(m -> m.getEncoded()[0])
                                        .collect(Collectors.toCollection(TreeSet::new));
                        return result;
                    }
                });
            }
        }
        */

    }

}
