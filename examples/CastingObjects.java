class Mammal {
	public boolean isWarmBlooded() {
		return true;
	}
}

interface Swims {
	void swim();
}

class Dog extends Mammal implements Swims {
	public void swim() {
		System.out.println("Swimming!");
	}
	public int age = 10;
}
public class CastingObjects {
	public static void main(String... args) {
		Dog dog = new Dog();
		Mammal m = dog; // doesn't require cast
		// Dog dog2 = m; // DOES NOT COMPILE -> error: incompatible types: Mammal cannot be converted to Dog
		Dog dog3 = (Dog)m; // cast back to dog
		System.out.println(dog3.age); // now has access to Dog methods
		System.out.println(( (Dog)m).age) ; // or use a temporary cast to gain access to Dog methods
		// must be in parentheses, or the compiler will look for m.age rather than ((Dog)m).age
	}
}