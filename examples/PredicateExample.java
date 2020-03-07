import java.util.function.*;

public class PredicateExample{
	static Predicate<Integer> isEven = i -> i % 2 == 0;
	public static void main(String... args){
		int[] ints = {1, 2, 3, 4, 5};
		for(int i : ints){
			System.out.println(isEven.test(i));
		}
	}
}
/* output:
* false
* true
* false
* true
* false
*/