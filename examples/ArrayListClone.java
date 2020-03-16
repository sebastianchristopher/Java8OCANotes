import java.util.*;

public class ArrayListClone {
	public static void main(String[] args){
		int one, two, three;
		one = 666; two = 777; three = 888;
		
		ArrayList<Integer> list = new ArrayList<>();
 		list.add(one); list.add(two); list.add(three);

 		ArrayList<Integer> clonedList = (ArrayList<Integer>)list.clone();
		
 		Iterator<Integer> i1 = clonedList.iterator();
		loop(i1);
		
		i1 = list.iterator();
		loop(i1);
		
		i1 = clonedList.iterator();
		loop(i1);
		
		System.out.println(list == clonedList); // false - clone has created a new copy of the list
		
		System.out.println(list.get(0) == clonedList.get(0)); // true = shallow copy creates a new copy of the list but not the objects within it - so the object point to the same place in memory
		
		list.set(0, 999);
		
		i1 = list.iterator();
		loop(i1);
		
		i1 = clonedList.iterator();
		loop(i1);
		
		System.out.println(list.get(0) == clonedList.get(0)); // false - when set is used on one of the collections, a new object is created 
	}
	
	static void loop(Iterator i){
		while(i.hasNext()){
			System.out.println(i.next());
		}
	}
}


		