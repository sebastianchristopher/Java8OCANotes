class Mammal {
	public void info() {
		System.out.println("This mammal is " + getName());
	}
	
	public String getName() {
		return "undefined";
	}
}

class Cat extends Mammal {
	public String getName() {
		return "a cat";
	}
}

public class VirtualMethods {
	public static void main(String... args) {
		Mammal mammal = new Cat();
		mammal.info();
	}
}
// output -> This mammal is a cat