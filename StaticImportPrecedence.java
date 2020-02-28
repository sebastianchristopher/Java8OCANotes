import static java.util.Arrays.asList;
public class StaticImportPrecedence {
	public static void main(String... args) {
		asList("foo", "bar");
	}
	
	public static void asList(String... args){
		for(String arg : args){
			System.out.print(arg);
		}
	}
}