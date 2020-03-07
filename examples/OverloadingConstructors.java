public class OverloadingConstructors {
	public int x;
	public String y;
	public OverloadingConstructors(){
		this(7, "bla");
	}
	public OverloadingConstructors(int x){
		this(x, "bla");
	}
	public OverloadingConstructors(String y){
		this(7, y);
	}
	public OverloadingConstructors(int x, String y){
		this.x = x;
		this.y = y;
	}
	public static void main(String... args) {
		OverloadingConstructors oc1 = new OverloadingConstructors(); // 			x: 7, y: bla
		OverloadingConstructors oc2 = new OverloadingConstructors(1); // 			x: 1, y: bla
		OverloadingConstructors oc3 = new OverloadingConstructors("word"); // 		x: 7, y: word
		OverloadingConstructors oc4 = new OverloadingConstructors(3, "stuff"); // 	x: 3, y: stuff
}