public class RecursiveConstructorInvocation {
	RecursiveConstructorInvocation() {
		this();
	}
	
	RecursiveConstructorInvocation(int i){
		this("foo");
	}
	
	RecursiveConstructorInvocation(String s){
		this(7);
	}
}