import java.time.LocalDate;
import java.time.Period;

public class ChainingPeriodMethods {
	public static void main(String[] args) {
		LocalDate ld = LocalDate.of(2020, 2, 12);
		Period p = Period.ofYears(1000).ofWeeks(12).ofDays(1);
		p.plusDays(10);
		p = p.plusDays(1).plusDays(1);
		System.out.println(ld.plus(p));
	}
}
