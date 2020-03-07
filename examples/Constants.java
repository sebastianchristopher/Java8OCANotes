public class Constants {
	static final int MAX = 100;
	static final int[] MIN_MAX = {0, 100};
	
	static final String HELLO_STRING = "Hello";
	static final StringBuilder HELLO_BUILDER = new StringBuilder("Hello");
	public static void main(String... args){
		MAX = 5; // DOES NOT COMPILE -> cannot assign a value to final variable MAX
		MIN_MAX[0] = 5; // compiles - value changed but didn't reassign object
		MIN_MAX = new int[]{5, 50}; // DOES NOT COMPILE -> cannot assign a value to final variable MIN_MAX
		
		HELLO_STRING += "World"; // DOES NOT COMPILE -> cannot assign a value to final variable HELLO_STRING
		HELLO_BUILDER.append("World"); // -> value of HELLO_BUILDER is now "HelloWorld"
		HELLO_BUILDER = new StringBuilder("HelloWorld");  // DOES NOT COMPILE -> cannot assign a value to final variable HELLO_BUILDER
	}
}