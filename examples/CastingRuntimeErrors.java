class Cat {}
class Tabby extends Cat {}

public class CastingRuntimeErrors {
	public static void main(String... args) {	
		Cat cat = new Cat();
		if(cat instanceof Tabby) {
			Tabby tabby = (Tabby)cat;
		} else {
			System.out.println("Cannot cast");
		}
	}
}
// output -> Cannot cast