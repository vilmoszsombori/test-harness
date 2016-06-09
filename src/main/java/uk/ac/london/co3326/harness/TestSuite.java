package uk.ac.london.co3326.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import uk.ac.london.co3326.Coursework;
import uk.ac.london.co3326.UnaryTest;
import uk.ac.london.co3326.Util;

public class TestSuite<T extends Coursework> {

    public static String TEST_FILE = "test.txt";

    private List<TestCase<?>> tests;
    private transient String file;

    public boolean evaluate(String input, Class<?> irrelevant) {
        return tests.parallelStream().filter(t -> !Objects.equal(t.getClass(), equals(irrelevant)))
                .allMatch(t -> t.evaluate(input));
    }

    public boolean evaluate(String input) {
        return tests.parallelStream().allMatch(t -> t.evaluate(input));
    }

    public TestSuite(String path) {
        this.file = path + File.separator + TEST_FILE;
        this.tests = new ArrayList<>();
        // add test cases
        tests.add(new BinaryTest("Alice's private key") {
            @Override
            public Object expected() {
                return getEtalon().getAlice().getD();
            }

            @Override
            public Object actual() {
                return getObject().getAlice().getD();
            }
        });
        tests.add(new BinaryTest("Bob's private key") {
            @Override
            public Object expected() {
                return getEtalon().getBob().getD();
            }

            @Override
            public Object actual() {
                return getObject().getBob().getD();
            }
        });
        tests.add(new BinaryTest("Charlie's private key") {
            @Override
            public Object expected() {
                return getEtalon().getCharlie().getD();
            }

            @Override
            public Object actual() {
                return getObject().getCharlie().getD();
            }
        });
        tests.add(new UnaryTest("Alice's p is prime") {
            @Override
            public boolean pass() {
                return Util.isPrime(getObject().getAlice().getRsa().getP());
            }
        });
        
    }

    public String getFile() {
        return this.file;
    }
    
}
