package abstractMethodsAndMultipleInheritance.sameNameAndReturn;
interface Foo {
	void foo();
}

interface Bar {
	public abstract void foo();
}

public class SameNameAndReturn implements Foo, Bar {
	public void foo(){
		System.out.println("Foo!");
	}
	public static void main(String... args){
		SameNameAndReturn test =  new SameNameAndReturn();
		test.foo();
	}
}