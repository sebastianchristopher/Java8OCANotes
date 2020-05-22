interface Shape {
	boolean test(int h, double l);
}

public class TestingFuncInterfaceWithMultipleArgs {
	public static void main(String[] args) {
		Shape square = (h, l) -> h == l;
		System.out.println(square.test(5, 5));
		System.out.println(square.test(3, 7));
		
		Shape rectangleHeightTwiceLength = (int h, double l) -> h == (l * 2);
		System.out.println(rectangleHeightTwiceLength.test(4, 2));
		System.out.println(rectangleHeightTwiceLength.test(1, 5));
		
		// Shape lengthIsOne = (int h, l) -> l == 1; // DOES NOT COMPILE - if one argument has a type, all others have to as well
	}
}