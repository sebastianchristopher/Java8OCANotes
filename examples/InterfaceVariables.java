interface AnInterface {
	int MAX_LENGTH = 99; // compiler adds public final static
}

public class InterfaceVariables implements AnInterface {
	public static void main(String... args) {
		System.out.println(MAX_LENGTH); // -> 99
	}	
}