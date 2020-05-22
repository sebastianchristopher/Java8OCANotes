interface Age {
	boolean tooOld(Integer age);
}
public class TestingFunctionalInterface {
	public static void main(String[] args){
		checkAge(i -> i > 50, 25); // Just right
		checkAge( (Integer i) -> i > 45, 55); // Too old
		// checkAge( (int i) -> i > 1, 100); // Does not compile - type is Integer, not int - predicates can't autobox!
		
		Age a = i -> i == 0;
	}
	public static void checkAge(Age age, int years){
		if(age.tooOld(years))
			System.out.println("Too old");
		else
			System.out.println("Just right");
	}
}