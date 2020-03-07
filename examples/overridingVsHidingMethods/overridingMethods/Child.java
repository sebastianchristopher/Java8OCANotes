public class Child extends Parent {
	public String name(){
		return "Child";
	}
	
	public void sayChildName(){
		System.out.println("Child is " + name());
	}
}