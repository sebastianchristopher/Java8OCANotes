import java.util.*;
import java.util.function.Predicate;

public class RemoveIfTest{
	static Predicate<Integer> isOdd = i -> i % 2 == 0;
	static Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	
	public static void main(String... args){
		List<Integer> nums = new ArrayList<>(Arrays.asList(ints));
		nums.removeIf(isOdd);
		System.out.println(nums);
		
		List<Integer> nums2 = new ArrayList<>(Arrays.asList(ints));
		nums.removeIf(i -> i % 2 =! 0);
		System.out.println(nums2);
	}	
}