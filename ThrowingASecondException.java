public class ThrowingASecondException {
	public static void main(String... args){
		String result = "";
		String x = null;
		try {
			try {
				result += "before ";
				System.out.println(x.length());
				result += "after ";
			} catch(NullPointerException e){
				result += "catch ";
				throw new RuntimeException();
			} finally {
				result += "finally ";
				throw new Exception();
			}
		} catch(Exception e) {
			result += "done ";
		}
		System.out.println(result); // -> before catch finally done
	}
}