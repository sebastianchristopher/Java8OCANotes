public class ExceptionInitializerErrorExample {
	static int age;
	static {
		int[] ages = {1, 17, 28, 32};
		age = ages[ages.length];
	}
	public static void main(String[] args){
		System.out.println(age);
	}
}
/*
* Exception in thread "main" java.lang.ExceptionInInitializerError
* Caused by: java.lang.ArrayIndexOutOfBoundsException: 4
*         at ExceptionInitializerErrorExample.<clinit>(ExceptionInitializerErrorExample.java:5)
*/