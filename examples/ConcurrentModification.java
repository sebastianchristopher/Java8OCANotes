import java.util.*;

public class ConcurrentModification {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3);
		Iterator<Integer> iterator = list.listIterator();
		list.add(4);
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
/* 
* Exception in thread "main" java.util.ConcurrentModificationException
*         at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
*         at java.util.ArrayList$Itr.next(ArrayList.java:851)
*         at ConcurrentModification.main(ConcurrentModification.java:10)
*/