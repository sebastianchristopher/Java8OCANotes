public class SecondCheckedException {
	static void foo() {
		try {
			System.out.println("2");
			throw new Exception();
		} catch(Exception e) {
			System.out.println("3");
			throw e;
		} finally {
			System.out.println("4");
		}
	}
	
	public static void main(String args[]) {
		System.out.println("1");
		foo();
		System.out.println("5");
	}
}