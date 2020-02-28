package statics;

public class A {
	public static final String TYPE = "A";
	public static final String DIFFERENT_SIGNATURE(String foo, String bar){
		return foo + bar;
	}
	public static final String SAME_SIGNATURE(String foo){
		return foo;
	}
}