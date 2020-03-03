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
public class MisunderstandingPolymorphism {
	public static void main(String... args) {
		Dog dog = new Dog();
		
		Swims swims = dog;
		System.out.println(swims.age); // DOES NOT COMPILE -> error: cannot find symbol
		System.out.println(swims.isWarmBlooded()); // DOES NOT COMPILE -> error: cannot find symbol
		
		Mammal mammal = dog;
		System.out.println(mammal.age); // DOES NOT COMPILE -> error: cannot find symbol
		mammal.swim(); // DOES NOT COMPILE -> error: cannot find symbol
	}
}