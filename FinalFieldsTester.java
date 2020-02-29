class Rectangle {
	private final double AREA;
	public Rectangle(double w, double h){
		AREA = w * h;
	}
	public String toString(){
		return "Area is " + AREA;
	}
}
public class FinalFieldsTester {
	public static void main(String... args){
		Rectangle r = new Rectangle(2.2, 4.5);
		System.out.println(r); // -> Area is 9.9
	}
}