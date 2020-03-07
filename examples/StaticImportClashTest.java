import static statics.A.TYPE;
import static statics.B.TYPE;
import static statics.A.SAME_SIGNATURE;
import static statics.B.SAME_SIGNATURE;
import static statics.A.DIFFERENT_SIGNATURE;
import static statics.B.DIFFERENT_SIGNATURE;

public class StaticImportClashTest {
	public static void main(String... args) {
		System.out.println(TYPE); // DOES NOT COMPILE -> reference to TYPE is ambiguous
		//		System.out.println(TYPE);   
		// ^ both variable TYPE in A and variable TYPE in B match

		System.out.println(SAME_SIGNATURE("foo")); // DOES NOT COMPILE -> error: reference to SAME_SIGNATURE is ambiguous
		//		System.out.println(SAME_SIGNATURE("foo"));
		// ^ both method SAME_SIGNATURE(String) in B and method SAME_SIGNATURE(String) in A match
					
		System.out.println(DIFFERENT_SIGNATURE("foo")); // -> foo
		System.out.println(DIFFERENT_SIGNATURE("foo", "bar")); // -> foo\nbar
	}
}