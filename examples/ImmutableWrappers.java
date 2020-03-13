public class ImmutableWrappers {
	public static void main(String[] args) {
		
		Integer i = Integer.valueOf(1);
		Integer j = i;
		
		System.out.println(i == j); // true
		System.out.println(++i == ++j); // true
		
		Integer k = Integer.valueOf(128);
		Integer l = k;
		
		System.out.println(k == l); // true
		System.out.println(++k == ++l); // false
	}
	
}