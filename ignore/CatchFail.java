public class CatchFail  {
	void fail() throws Exception {
		throw new Exception(); // DOES NOT COMPILE
	}
}