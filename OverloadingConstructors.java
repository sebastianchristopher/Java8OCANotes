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
		OverloadingConstructors oc1 = new OverloadingConstructors();
		System.out.println(oc1.toString());
	}
}