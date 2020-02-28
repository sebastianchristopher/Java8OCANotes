package statics;

public class B {
	public static final String TYPE = "B";
	public static final String DIFFERENT_SIGNATURE(String bar){
		return bar;
	}
	public static final String SAME_SIGNATURE(String foo){
		return foo;
	}
}