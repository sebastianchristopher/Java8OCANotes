//In file AccessTester.java
package b;
import a.AccessTest;

public class AccessTester extends AccessTest{
    public static void main(String[] args) {
        AccessTest ref = new AccessTest();
		System.out.println(ref.d());
		ref.c();
    }
}