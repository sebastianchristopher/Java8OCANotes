public abstract class Instrument {
	protected String name = "Instrument"; // protected member is accessible by subclasses 
	public String describe() { // public member is accessible by subclasses  and inherited as concrete method
		return "is a " + name;
	}
	
	public abstract void play(); // no implementation
}