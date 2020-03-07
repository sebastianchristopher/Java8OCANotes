package abstractMethodsAndMultipleInheritance.sameNameDifferentReturn;
interface Foo {
	void foo();
}

interface Bar {
	public abstract int foo();
}

public class SameNameDifferentReturn implements Foo, Bar {
	public void foo(){
		System.out.println("Foo!");
	}
	public int foo(){
		return 0;
	}
	public static void main(String... args){
		SameNameDifferentReturn test =  new SameNameDifferentReturn();
		test.foo();
	}
}