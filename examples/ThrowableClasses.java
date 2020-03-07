public class ThrowableClasses {
	public static void main(String[] args) {
		try {
			throw new Throwable();
		} catch(Throwable e) {
			System.out.println(e);
		}
		
		try {
			throw new Error();
		} catch(Error e) {
			System.out.println(e);
		}
		
		try {
			throw new Exception();
		} catch(Exception e) {
			System.out.println(e);
		}
		
		try {
			throw new RuntimeException();
		} catch(RuntimeException e) {
			System.out.println(e);
		}
	}
}