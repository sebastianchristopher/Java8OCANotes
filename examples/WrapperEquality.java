public class WrapperEquality {
	public static void main(String[] args) {
		Integer num1 = Integer.valueOf(1);
		Integer num2 = Integer.valueOf(1);
		Short num3 = Short.valueOf((short)1);
		
		System.out.println(num1.equals(num2)); // true
		System.out.println(num1.equals(num3)); // false
	}
}
