import java.time.LocalDateTime;
import java.time.Period;

public class PeriodAndLocalDateTime {
	public static void main(String args[]) {
		LocalDateTime birth = LocalDateTime.of(2020, 2, 12, 21, 31);
		Period aYearAndADay = Period.of(1, 0, 1);
		System.out.println(birth.plus(aYearAndADay)); // 2021-02-13T21:31
	}
}