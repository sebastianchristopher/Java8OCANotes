public class Child extends Parent {
	public static String name(){return "Child";}
	public static void sayChildName(){
		System.out.println("Parent is" + name);
	}
}