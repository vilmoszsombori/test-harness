package uk.ac.london.co3326.harness.cw2;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.google.gson.Gson;

import uk.ac.london.co3326.Cw2;
import uk.ac.london.co3326.Util;
import uk.ac.london.co3326.harness.TestSuite;
import uk.ac.london.co3326.harness.cw2.SetComparisonTest;

public class EmptyTestSuiteCw2 extends TestSuite<Cw2> {

    public EmptyTestSuiteCw2(String input, String testInput) throws ClassNotFoundException {
        super(input, testInput);

        tests.add(new UnaryTest("p is prime", 5, null) {
            @Override
            public int pass() {
                boolean result = 
                        Util.isPrime(getObject().getA().getRsa().p) && 
                        Util.isPrime(getObject().getB().getRsa().p) && 
                        Util.isPrime(getObject().getS().getRsa().p);
            	setDescription(
            	        (result ? "All " : "One of ") + 
            	        getObject().getA().getRsa().p + ", " +
                        getObject().getB().getRsa().p + ", " +
                        getObject().getS().getRsa().p +             	                
            	        (result ? " primes" : " not prime"));
                return result ? 1 : 0;
            }
        });

        tests.add(new UnaryTest("q is prime", 5, null) {
            @Override
            public int pass() {
                boolean result = 
                        Util.isPrime(getObject().getA().getRsa().q) && 
                        Util.isPrime(getObject().getB().getRsa().q) && 
                        Util.isPrime(getObject().getS().getRsa().q);
                setDescription(
                        (result ? "All " : "One of ") + 
                        getObject().getA().getRsa().q + ", " +
                        getObject().getB().getRsa().q + ", " +
                        getObject().getS().getRsa().q +                                 
                        (result ? " primes" : " not prime"));
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

        tests.add(new BinaryTest("gcd(e, r) = 1", 4, null) {
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

        tests.add(new BinaryTest("d = modInv(e, r)", 4, null) {
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

        // set up the etalon
        if (input != null && !input.isEmpty()) {
            Gson gson = new Gson();
            etalon = gson.fromJson(input, Cw2.class);
            if (etalon != null) {
                etalon.demonstrate();

                
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

    }

}
