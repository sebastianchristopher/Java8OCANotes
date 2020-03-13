class LocalVariableScope {
	public static void main(String[] args) {
		int i = 1;
		{
			int j = 2;
			System.out.println(i + j); // -> 3
		}
		System.out.println(i + j); // -> error: cannot find symbol
	}
}