import java.time.*;

public class LocalDateTimeWith {
	public static void main(String[] args) {
		LocalDateTime dtm = LocalDateTime.of(2020, 2, 11, 21, 31);
		System.out.println(dtm.withYear(2021)); // -> 2021-02-11T21:31
		System.out.println(dtm.withHour(0).withMinute(0)); // -> 2020-02-11T00:00
	}
}