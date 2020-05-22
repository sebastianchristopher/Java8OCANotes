import java.io.IOException;
public class BadExceptionHandling {
	public static void main(String args[]){
		try {
			System.out.println("Hello world");
		} catch (IOException e) {
			System.out.println("Bad maths");
		}
	}
}