import java.io.IOException;

public class ThisDoesntCompile {
	public static void main(String args[]) {
		try {
			System.out.println("Hello world!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}