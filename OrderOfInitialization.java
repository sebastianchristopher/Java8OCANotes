public class OrderOfInitialization {
	static void print(int num){
		System.out.print(num);
	}
	
	static{print(1);}
	
	public OrderOfInitialization(){
		print(5);
	}
	
	{print(3);}
	
	static{print(2);}
	
	{print(4);}
	
	public static void main(String... args){
		OrderOfInitialization foo = new OrderOfInitialization();
	}
}