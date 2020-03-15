public class ReturnValues {
	static int foo() {
		byte b = 1;
		return b;
	}
	static Parent parent() {
		Child c = new Child();
		return c;
	}
}
class Parent {}
class Child extends Parent {}