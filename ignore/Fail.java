public class Fail {
	void fail() {
		throw new Exception(); // DOES NOT COMPILE
		// -> error: unreported exception Exception; must be caught or declared to be thrown
	}
}