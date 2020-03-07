class Cat {}
class Canary {}
public class CastingUnrelatedTypes {
	public static void main(String... args) {
		Cat cat = new Cat();
		Canary canary = (Canary)cat; // DOES NOT COMPILE -> error: incompatible types: Cat cannot be converted to Canary
	}
}