import java.util.List;
import static java.util.Arrays.asList;
public class StaticImports {
	public static void main(String... args) {
		List<String> list = asList("foo", "bar");
		List<String> list2 = Arrays.asList("foo", "bar"); // cannot find symbol		List<String> list2 = Arrays.asList("foo", "bar");
	}
}
