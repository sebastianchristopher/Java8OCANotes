public class StaticVsInstance {
	public static String stringOne = "One";
	public String stringTwo = "Two";
	public static void one(){
		System.out.println("Called one()");
	}
	public void two(){
		System.out.println("Called two()");
	}
	public static void main(String... args){
		one();
		two();  // DOES NOT COMPILE -> error: non-static method two() cannot be referenced from a static context    two();
		
		System.out.println(stringOne);
		System.out.println(stringTwo);  // -> error: non-static variable stringTwo cannot be referenced from a static context    System.out.println(stringTwo);
	}
}