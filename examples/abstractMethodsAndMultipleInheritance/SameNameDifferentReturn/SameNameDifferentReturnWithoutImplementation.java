package abstractMethodsAndMultipleInheritance.sameNameDifferentReturn;
interface Foo {
	void foo();
}

interface Bar {
	public abstract int foo();
}

abstract class AbstractClassSameNameDifferentReturn implements Foo, Bar {}

interface InterfaceSameNameDifferentReturn extends Foo, Bar {}