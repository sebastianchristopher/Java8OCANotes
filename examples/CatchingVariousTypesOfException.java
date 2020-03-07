class ACheckedException extends Exception {}
class AnUncheckedException extends RuntimeException {}

public class CatchingVariousTypesOfException {
	static void foo(int i) throws ACheckedException {
		if(i == 1) {
			throw new ACheckedException();
		} else if(i == 2) {
			throw new AnUncheckedException();
		}
	}
	
	static void test(int i) {
		try {
			// foo(i);
			throw new Exception();
		} catch(ACheckedException e) {
			System.out.println("Checked");
		} catch(AnUncheckedException e) {
			System.out.println("Unchecked");
		} finally {
			System.out.println("I run regardless");
		}
	}
	
	public static void main(String... args) {
		test(1); // -> Checked \n I run regardless
		test(2); // -> Unchecked \n I run regardless
		test(3); // -> I run regardless
	}
}