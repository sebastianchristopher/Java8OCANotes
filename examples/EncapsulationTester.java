class Encapsulation{
	private int num;
	public void setNum(int num){
		this.num = num;
	}
	public int getNum(){
		return num;
	}		
}
public class EncapsulationTester{
	public static void main(String... args){
		EncapsulationTest foo = new EncapsulationTest();
		
		foo.num = 5; // DOES NOT COMPILE ->  error: num has private access in EncapsulationTest
		foo.setNum(5);
				
		System.out.println(foo.num); // DOES NOT COMPILE ->  error: num has private access in EncapsulationTest
		System.out.println(foo.getNum()); // -> 5
	}
}