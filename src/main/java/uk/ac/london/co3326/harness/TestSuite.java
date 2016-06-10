package uk.ac.london.co3326.harness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.common.base.Objects;

import uk.ac.london.co3326.Coursework;

public class TestSuite<T extends Coursework> {

	public static String TEST_FILE = "test.txt";

	private List<TestCase<?>> tests;
	private transient static String file;

	public boolean evaluate(String input, Class<?> irrelevant) {
		return tests.stream().filter(t -> !Objects.equal(t.getClass(), equals(irrelevant)))
				.allMatch(t -> t.evaluate(input) > 0);
	}

	public boolean evaluate(String input) {
		return tests.stream().map(t -> t.evaluate(input)).filter(r -> r == 0).count() == 0;
	}

	public TestSuite(String path) {
		file = path + File.separator + TEST_FILE;
		this.tests = new ArrayList<>();
		// add test cases
		tests.add(new BinaryTest("Alice's private key") {
			@Override
			public Object expected() {
				return getEtalon().getAlice().getD();
			}

			@Override
			public Object actual() {
				return getObject().getAlice().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Bob's private key") {
			@Override
			public Object expected() {
				return getEtalon().getBob().getD();
			}

			@Override
			public Object actual() {
				return getObject().getBob().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Charlie's private key") {
			@Override
			public Object expected() {
				return getEtalon().getCharlie().getD();
			}

			@Override
			public Object actual() {
				return getObject().getCharlie().getUncomputedD();
			}
		});
		tests.add(new BinaryTest("Encoding") {
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
		tests.add(new SetComparisonTest("Encryption + decryption") {
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
		/*
		 * tests.add(new UnaryTest("Alice's p is prime") {
		 * 
		 * @Override public boolean pass() { return
		 * Util.isPrime(getObject().getAlice().getRsa().getP()); } });
		 */

	}

	public static String getFile() {
		return file;
	}

}
