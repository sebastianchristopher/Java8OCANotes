public class Piano extends Instrument {
	public void play() {
		System.out.println("Piano is playing!");
	}
	public static void main(String... args) {
		Piano piano = new Piano();
		System.out.println(piano.describe());
		piano.play();
	}
}