public class Child extends Parent {
	public static String name(){
		return "Child";
	}
	
	public void sayChildName(){
		System.out.println("Child is " + name());
	}
}