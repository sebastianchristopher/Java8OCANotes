import java.util.*;

public class ArrayListClone {
	public static void main(String[] args){
		// List<Integer> list = new ArrayList<>();
		// list.add(1); list.add(2); list.add(3);
		
		// List<Integer> clonedList = (ArrayList<Integer>)list.clone();
		
		// Iterator<Integer> i1 = list.iterator();
		ArrayList<StringBuilder> myArrList = new ArrayList<StringBuilder>();
		StringBuilder sb1 = new StringBuilder("Jan");
		StringBuilder sb2 = new StringBuilder("Feb");
		myArrList.add(sb1);
		myArrList.add(sb2);
		myArrList.add(sb2);
		List<StringBuilder> assignedArrList = myArrList;
		List<StringBuilder> clonedArrList = (ArrayList<StringBuilder>)myArrList.clone();
	}
}


		