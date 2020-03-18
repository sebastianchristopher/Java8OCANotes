import java.time.*;

public class IsAfterAndIsBefore {
	public static void main(String[] args){
		LocalDate d1 = LocalDate.of(1999, 12, 31);
		LocalDate d2 = LocalDate.of(2000, 1, 1);
		System.out.println(d1.isBefore(d2)); // true
		System.out.println(d1.isAfter(d2)); // false
		
		LocalTime t1 = LocalTime.of(11, 59);
		LocalTime t2 = LocalTime.of(00, 00);
		System.out.println(t1.isBefore(t2)); // false
		System.out.println(t1.isAfter(t2)); // true
		
		LocalDateTime ldt1 = d1.atTime(t1);
		LocalDateTime ldt2 = t2.atDate(d2);
		System.out.println(ldt1.isBefore(ldt2)); // true
		System.out.println(ldt1.isAfter(ldt2)); // false
	}
}