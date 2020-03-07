public class ConstantsAndStaticInitializers {
	public static final int one;
	public static final int two = 2;
    public static final int three; // DOES NOT COMPILE -> variable three not initialized in the default constructor
	static {
		one = 1;
	    two = 2; // DOES NOT COMPILE -> cannot assign a value to final variable two
	};	
}