import java.util.*;

public class WithEnhancedForLoop {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);
		for(Integer i : list){
			System.out.println(i);
		}
	}
}