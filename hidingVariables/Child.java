public class Child extends Parent {
	public String name = "Child";
	public int age = 20;
	
	public void sayChildName(){
		System.out.println("Child is " + name);
	}
	
	public void printAgeAndParentAge() {
		System.out.println("Age is " + age + "; Parent's age is " + super.age);
	}
}