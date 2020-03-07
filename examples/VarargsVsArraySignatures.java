public class VarargsVsArraySignatures {
	public static void varargsSignature(int... nums){}
	public static void arraySignature(int[] nums){}
	
	public static void main(String... args){
		varargsSignature(new int[]{1, 2, 3});
		varargsSignature(1, 2, 3);
		
		arraySignature(new int[]{1, 2, 3});
		arraySignature(1, 2, 3); // DOES NOT COMPILE -> method arraySignature cannot be applied to given types
		//	required: int[]
		//	found: int,int,int
		//	reason: actual and formal argument lists differ in length
	}
}