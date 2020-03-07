public class ConstructorAfterFirstLine {
	public ConstructorAfterFirstLine(int i){}
	public ConstructorAfterFirstLine(){
		System.out.print("starting");
		this(7);
	}
}