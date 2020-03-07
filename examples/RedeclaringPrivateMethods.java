public class RedeclaringPrivateMethods {
	private void foo() {
		System.out.println("RedeclaringPrivateMethods");
	}
	
	public static void main(String... args) {
		RedeclaringPrivateMethods parentReference = new AChildClass();
		parentReference.foo(); // -> RedeclaringPrivateMethods
		
		AChildClass childReference = new AChildClass();
		childReference.foo(); // -> AChildClass
	}
}

class AChildClass extends RedeclaringPrivateMethods {
	public void foo() {
		System.out.println("AChildClass");
	}
}