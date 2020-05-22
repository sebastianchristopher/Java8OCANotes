public class LocalVariableDeclarationStatement {
	public static void main(String[] blabla) {
		// this compiles
		int x = 1;
		if(true)
			switch(x) {
				case 1:
				case 2:
				case 3:
				default:
			}
		
		// this won't compile
		if(true)
			int y = 2; // error: variable declaration not allowed here
		
	}	
}