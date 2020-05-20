class Mutable{
	private StringBuilder name;
	public Mutable(String name){
		this.name = new StringBuilder(name);
	}
	public StringBuilder getName(){
		return name;
	}
}
class DefensiveCopy{
	private StringBuilder name;
	public DefensiveCopy(String name){
		this.name = new StringBuilder(name);
	}
	public StringBuilder getName(){
		return new StringBuilder(name);
	}
}
class ImmutableReturnType{
	private StringBuilder name;
	public ImmutableReturnType(String name){
		this.name = new StringBuilder(name);
	}
	public String getName(){
		return name.toString();
	}
}
public class ImmutableClasses{
	public static void main(String... args){
		Mutable obj1 = new Mutable("Foo");
		obj1.getName().append("bar");
		System.out.println(obj1.getName()); // -> Foobar
		
		DefensiveCopy obj2 = new DefensiveCopy("Foo");
		obj2.getName().append("bar");
		System.out.println(obj2.getName()); // -> Foo
		
		ImmutableReturnType obj3 = new ImmutableReturnType("Foo");
		// obj3.getName().append("bar"); // can't append this as it's a String not a StringBuilder
		obj3.getName().concat("bar"); // but I can do this!
		System.out.println(obj3.getName()); // -> Foo
	}
}