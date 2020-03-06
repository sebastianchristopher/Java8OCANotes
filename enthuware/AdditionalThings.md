# Additional Things I'm Not Good At

Things that have been flagged up by enthuware tests

## Standard Tests - Foundation Test

### Protected Methods
```java
//In file AccessTest.java
package a;
public class AccessTest {
int a;
	private int b;
	protected void c(){ }
	public int d(){  return 0; }
}

//In file AccessTester.java
package b;
import a.AccessTest;

public class AccessTester extends AccessTest{
    public static void main(String[] args) {
        AccessTest ref = new AccessTest();
    }
}
```
* Which of the following statements are true?
  - a) Only c() and d() can be accessed by ref.
  - b) b, c(), as well as d(), can be accessed by ref.
  - c) Only d() can be accessed by ref.
  - d) Only a and d() can be accessed by ref.
* the answer is c), only d() can be accessed by ref
* A protected method can be called by any subclass within its class, so why can't we access it in AccessTester, a subclass of AccessTest?
* my version:
* from `Java8OCANotes\enthuware\protectedMethods` compile with `javac a\AccessTest.java b\AccessTester.java`
```java
//In file a\AccessTest.java
package a;
public class AccessTest {
int a;
private int b;
protected void c(){ }
public int d(){  return 0; }
}
```
```java
//In file b\AccessTester.java
package b;
import a.AccessTest;

public class AccessTester extends AccessTest{
    public static void main(String[] args) {
        AccessTest ref = new AccessTest();
		System.out.println(ref.d());
		// ref.c(); // DOES NOT COMPILE ->  c() has protected access in AccessTest
    }
}
```
* we get `error: c() has protected access in AccessTest`
* to be specific here, we are calling `c()` from an instance of AccessTest, not calling it from an instance of AccessTester
* if we call if from an instance of AccessTester, the subclass, it compiles:
* from `Java8OCANotes\enthuware\protectedMethods` compile with `javac a\AccessTest.java b\AccessTester.java`
```java
//In file b\AccessTester.java
package b;
import a.AccessTest;

public class AccessTester extends AccessTest{
    public static void main(String[] args) {
        AccessTest ref = new AccessTest();
		System.out.println(ref.d());
		
		AccessTester accTest = new AccessTester();
		accTest.c();
    }
}
```
* so why isn't an instance of AccessTest able to call its protected methods from a different class?
* it can call its protected methods from a different class if it is in the same package:
* from `Java8OCANotes\enthuware\protectedMethods` compile with `javac a\AccessTest.java a\SamePackageAccessTester.java`
* run with `java a.SamePackageAccessTester` (Windows), `javac -cp a.SamePackageAccessTester`(Mac OS)
```java
//In file a\SamePackageAccessTester.java
package a;

public class SamePackageAccessTester extends AccessTest{
    public static void main(String[] args) {
        AccessTest ref = new AccessTest();
		System.out.println(ref.d());
		ref.c();
    }
}
```
* `protected` allows access from subclasses and from other classes in the same package. That's why any `AccessTester` class instance can access the protected method in `AccessTest.`
* this snippet:
  ```java
  public class AccessTester extends AccessTest{
    public static void main(String[] args) {
        AccessTest ref = new AccessTest();
	```
  creates an `AccessTest` instance (not a `AccessTester` instance!!). And access to protected methods of that instance is only allowed from objects of the same package.
* [See more](#https://stackoverflow.com/questions/5562548/protected-member-access-from-different-packages-in-java-a-curiosity)
### Identify correct statements about a two dimensional array
  - a) It is like a rectangular matrix where number of rows and number of columns may be different but each row or each column have the same number of elements. **FALSE**
  - b) It is like a square matrix where number of rows and number of columns are same and each row or each column have the same number of elements. **FALSE**
  - c) The number of rows and columns must be specified at the time it is declared. **FALSE**
  - d) It is basically an array of arrays. **TRUE**
* I answered a) and d)
* a) is not true because unlike some other languages, multi dimensional arrays in Java are not like matrices. They are just arrays of arrays.
* For example, if you have a two dimensional array then each element of this array is a one dimensional array. Each such array element is independent and therefore can be of different lengths (but not of different type).
### Which of the following are valid declarations in a class?
  - a) abstract int absMethod(int param) throws Exception; **TRUE**
  - b) abstract native int absMethod(int param) throws Exception; **FALSE** -> native method cannot be abstract. **n.b. native is not on the exam**
  - c) float native getVariance() throws Exception; **FALSE** -> return type should always be on the immediate left of method name.
  - d) abstract private int absMethod(int param) throws Exception; **FALSE** -> private method cannot be abstract. A private method is not inherited so how can a subclass implement it?
### Which of the following are true about the "default" constructor?
  - a) It is provided by the compiler only if the class and any of its super classes does not define any constructor. **FALSE** -> It is provided by the compiler if the class does not define any constructor. It is immaterial if the super class provides a constructor or not.
  - b) It takes no arguments. **TRUE**
  - c) A default constructor is used to return a default value. **FALSE** -> A constructor does not return any value at all. It is meant to initialize the state of an object.
  - d) To define a default constructor, you must use the default keyword. **FALSE**
  - e) It is always public. **FALSE** -> The access type of a default constructor is same as the access type of the class. Thus, if a class is public, the default constructor will be public.
* The default constructor is provided by the compiler only when a class does not define ANY constructor explicitly. For example:
```java
public class A{
  public A()  //This constructor is automatically inserted by the compiler because there is no other constructor defined by the programmer explicitly.
  {
    super();  //Note that it calls the super class's default no-args constructor.
  }
}
public class A{
  //Compiler will not generate any constructor because the programmer has defined a constructor.
  public A(int i){
     //do something
  }
}
```
### Which of the following are valid operators in Java?
Answered Incorrectly
You had to select 4 option(s)
`!`
operates only on booleans
`~`
bitwise negation. Operates only on integral types. **bitwise negation is not on the exam**
`&`
bitwise AND
`%=`
similar to += or /= **I got this right but was unsure about it - don't think it's on the exam but have added it to README**
`$`
It is not an operator!
### Which of the following statements are true?
Answered Incorrectly
You had to select 2 option(s)
  - a)The modulus operator % can only be used with integer operands. **FALSE** -> It can be used on floating points operands also. For example, 5.5 % 3 = 2.5
  - b) & can have integral as well as boolean operands. **TRUE** -> unlike &&, & will not "short circuit" the expression if used on boolean parameters. **answered incorrectly**
  - c) The arithmetic operators *, / and % have the same level of precedence. **TRUE** -> && can have integer as well as boolean operands.
  - d) !, && and || operate only on booleans.
  - e) ~ can have integer as well as boolean operands. **FALSE** -> ~ Operates only on integral types **not on the exam**
* Note :
> integral types means byte, short, int, long, and char
> As per Section 4.1 of JLS 8 -
> The integral types are byte, short, int, and long, whose values are 8-bit, 16-bit, 32-bit and 64-bit signed two's-complement integers, respectively, and char, whose values are 16-bit unsigned integers representing UTF-16 code units.
* b) is true because it's a [bitwise operator](#https://javarevisited.blogspot.com/2015/01/difference-between-bitwsie-and-logical.html), but this shouldn't be on the exam
### 