public class OverloadingMain {
  public static void main(){
	System.out.println("Running overloaded main method");
  }
  
  public static final void main(String[] args) {
	System.out.println("Running exectuable main method");
    main(); 
  }
}
/* output:
 * Running exectuable main method
 * Running overloaded main method
*/