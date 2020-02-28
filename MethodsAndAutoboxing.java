public class MethodsAndAutoboxing {
	public static void main(String... args){
		foo(7);
	}
	
	// public static void foo(short x){ // WON'T MATCH THIS - can't demote to short
		// System.out.println("short");
	// }
	// public static void foo(int x){
		// System.out.println("int");
	// }
	// public static void foo(long x){
		// System.out.println("long");
	// }
	// public static void foo(double x){
		// System.out.println("double");
	// }
	// public static void foo(Integer x){
		// System.out.println("Integer");
	// }
	// public static void foo(Double x){ // WON'T MATCH THIS - can't promote to double then autobox to Double
		// System.out.println("Double");
	// }
	// public static void foo(Number x){
		// System.out.println("Number");
	// }
	// public static void foo(Object x){
		// System.out.println("Object");
	// }
	// public static void foo(int... x){
		// System.out.println("int...");
	// }
	public static void foo(double... x){
		System.out.println("double...");
	}
	public static void foo(Integer... x){
		System.out.println("Integer...");
	}
	public static void foo(Object... x){
		System.out.println("Object...");
	}
}