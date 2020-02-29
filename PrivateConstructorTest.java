class PrivateConstructor {
	private PrivateConstructor(){};
}
public class PrivateConstructorTest {
	public static void main(String... args) {
		PrivateConstructor foo = new PrivateConstructor();
	}
}