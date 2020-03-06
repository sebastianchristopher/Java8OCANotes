//In file a\SamePackageAccessTester.java
package a;

public class SamePackageAccessTester extends AccessTest{
    public static void main(String[] args) {
        AccessTest ref = new AccessTest();
		System.out.println(ref.d());
		ref.c();
    }
}