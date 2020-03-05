public class PrintingAnException {
	static void foo() {
		throw new RuntimeException("Whoops");
	}
	
	public static void main(String... args) {
		try {
			foo();
		} catch(RuntimeException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
/*
* output:
* java.lang.RuntimeException: Whoops
* Whoops
* java.lang.RuntimeException: Whoops
*         at PrintingAnException.foo(PrintingAnException.java:3)
*         at PrintingAnException.main(PrintingAnException.java:8)
*/