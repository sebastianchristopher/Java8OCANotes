import java.util.*;

public class WithIterator {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);
		Iterator<Integer> iterator = list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}