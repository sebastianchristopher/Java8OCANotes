import java.time.*;

public class LocalDateAndTimeAt {
	public static void main(String[] args) {
		LocalDate dt = LocalDate.of(2020, 2, 11);
		LocalTime tm = LocalTime.of(21, 31);
		
		LocalDateTime dtm1 = dt.atTime(tm);
		System.out.println(dtm1); // -> 2020-02-11T21:31
				
		LocalDateTime dtm2 = tm.atDate(dt);
		System.out.println(dtm2); // -> 2020-02-11T21:31
		
		LocalDateTime dtm3 = dt.atTime(12, 31);
		System.out.println(dtm3); // -> 2020-02-11T12:31
		
		LocalDateTime dtm4 = dt.atTime(12, 31, 33);
		System.out.println(dtm4); // -> 2020-02-11T12:31:33
		
		LocalDateTime dtm5 = dt.atTime(12, 31, 33, 400);
		System.out.println(dtm5); // -> 2020-02-11T12:31:33.000000400
	}
}