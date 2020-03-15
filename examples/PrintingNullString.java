public class PrintingNullString {
	static String s1 = "Hello";
	static String s2;
	public static void main(String[] args) {
		System.out.println(s1 + " " + s2); // Hello null
	}
}
		