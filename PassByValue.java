public class PassByValue {
	public static void main(String... args) {
		int i = 2;
		String str = "Foo";
		StringBuilder sb = new StringBuilder("Foo");
		
		change(i);
		change(str);
		change(sb);
		
		System.out.println(i); // -> 2
		System.out.println(str); // -> Foo
		System.out.println(sb); // -> Foobar
		
		i = change(i);
		str = change(str);
		sb = change(sb);
		
		System.out.println(i); // -> 3
		System.out.println(str); // -> Foobar
		System.out.println(sb); // -> Foobarbar
	}
	
	public static int change(int x){
		return x += 1;
	}
	public static String change(String x){
		return x += "bar";
	}
	public static StringBuilder change(StringBuilder x){
		return x.append("bar");
	}
}