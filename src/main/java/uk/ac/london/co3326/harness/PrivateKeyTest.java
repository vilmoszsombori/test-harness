package uk.ac.london.co3326.harness;

import uk.ac.london.co3326.Cw1;

public class PrivateKeyTest extends TestCase {

	private transient Cw1 object, etalon;

	public PrivateKeyTest(Cw1 object, Cw1 etalon) {
		super();
		this.object = object;
		this.etalon = etalon;
		setDescription("Private key : Alice");
	}
	
	@Override
	public void evaluate() {
		try {
			setSuccessful(object.getAlice().getD() == etalon.getAlice().getD());
			if (!isSuccessful()) {
				setError(String.format("Private key missmatch: Alice [expected=%d, actual=%d]",
						etalon.getAlice().getD(), object.getAlice().getD()));
			}
		} catch (Exception e) {
			setSuccessful(false);
			setError(e.getMessage());
		}
	}
}
