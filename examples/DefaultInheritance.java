interface DefaultMethod {
	default void foo() {
		System.out.println("Foo in DefaultMethod");
	}
}

interface RedeclaresAbstract extends DefaultMethod {
	void foo(); // redeclares foo() as abstract
}

interface OverridesBackToDefault extends RedeclaresAbstract{
	default void foo() { // overrides abstract foo() as default method
		System.out.println("Foo in OverridesBackToDefault");
	}
}

class ImplementsDefaultMethod implements DefaultMethod {} // inherits default method so doesn't need to implement foo()

class ImplementsRedeclaresAbstract implements RedeclaresAbstract {  // inherits abstract method so needs to implement foo()
	public void foo() { // follows overriding rules so must be as accessible as parent method - which is assumed public
		System.out.println("Foo in ImplementsDefaultMethod");
	}
}

class ImplementsOverridesBackToDefault implements OverridesBackToDefault { // inherits default method so doesn't need to implement foo() but it is allowed to so we will
	public void foo() { // follows overriding rules so must be as accessible as parent method - which is assumed public
		System.out.println("Foo in ImplementsOverridesBackToDefault");
	}
}

public class DefaultInheritance {
	public static void main(String... args) {
		ImplementsDefaultMethod obj1 =  new ImplementsDefaultMethod();
		ImplementsRedeclaresAbstract obj2 = new ImplementsRedeclaresAbstract();
		ImplementsOverridesBackToDefault obj3 = new ImplementsOverridesBackToDefault();
		
		obj1.foo(); // -> Foo in DefaultMethod
		obj2.foo(); // -> Foo in ImplementsDefaultMethod
		obj3.foo(); // -> Foo in ImplementsOverridesBackToDefault
	}
}