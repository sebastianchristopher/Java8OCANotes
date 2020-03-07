interface Mammal {
	default void info() {
		System.out.println("This is " + getName());
	}
	
	public String getName();
}

class Cat implements Mammal {
	public String getName() {
		return "a cat";
	}
}

class Dog implements Mammal {
	public String getName() {
		return "a dog";
	}
}

class Mouse implements Mammal {
	public String getName() {
		return "a mouse";
	}
}

public class PolymorphicParameters {
	public static void mammalInfo(Mammal m) {
		m.info();
	}
	
	public static void main(String... args) {
		mammalInfo(new Cat()); // -> This is a cat
		mammalInfo(new Dog()); // -> This is a dog
		mammalInfo(new Mouse()); // ->This is a mouse	
	}
}