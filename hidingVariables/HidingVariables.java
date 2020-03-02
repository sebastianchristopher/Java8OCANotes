public class HidingVariables {
	public static void main(String... args) {
		Child child = new Child();
		child.sayChildName();
		child.sayParentName();
		
		child.printAge();
		child.printAgeAndParentAge();
	}
}