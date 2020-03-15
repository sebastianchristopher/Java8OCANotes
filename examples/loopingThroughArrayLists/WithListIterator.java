import java.util.*;

public class WithListIterator {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);
		Iterator<Integer> */or ListIterator<Integer>*/ listIterator = list.listIterator();
		while(listIterator.hasNext()){
			System.out.println(listIterator.next());
		}
	}
}