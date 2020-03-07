interface Walk {
	default int getSpeed(){
		return 5;
	}
}

interface Run {
	default int getSpeed(){
		return 10;
	}
}

// class DoesNotOverride implements Walk, Run {} // DOES NOT COMPILE -> error: class DefaultMethodsAndMultipleInheritance inherits unrelated defaults for getSpeed() from types Walk and Run

public class DefaultMethodsAndMultipleInheritance implements Walk, Run {
	public int getSpeed() {
		return 20;
	}
}