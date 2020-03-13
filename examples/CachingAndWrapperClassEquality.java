public class CachingAndWrapperClassEquality {
	public static void main(String[] args) {
		Integer i = Integer.valueOf("1");
		Integer j = 1;
		System.out.println(i == j);
		
		Integer k = new Integer(1);
		Integer l = new Integer(1);
		System.out.println(k == l);
		
		Integer m = Integer.valueOf("128");
		Integer n = Integer.valueOf("128");
		System.out.println(m == n);
	}
}