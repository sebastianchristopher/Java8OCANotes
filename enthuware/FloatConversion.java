public class FloatConversion {
	public static void main(String... args) {
		float f = 1; // compiles fine
		float f2 = 1.1; // DOES NOT COMPILE -> double literal can't convert downwards to float
	}
}
	