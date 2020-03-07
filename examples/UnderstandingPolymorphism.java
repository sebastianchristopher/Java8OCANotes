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
public class UnderstandingPolymorphism {
	public static void main(String... args) {
		Dog dog = new Dog();
		System.out.println(dog.age); // -> 10
		
		Swims swims = dog;
		swims.swim(); // -> Swimming!
		
		Mammal mammal = dog;
		System.out.println(mammal.isWarmBlooded()); // -> true
	}
}