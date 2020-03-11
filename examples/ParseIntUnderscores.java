public class ParseIntUnderscores {
	public static void main(String... args) {
		int x = 12_34;
		int y = Integer.parseInt("12_34");
	}
}