# Additional Things I'm Not Good At

Things that have been flagged up by enthuware tests

## Standard Tests - Foundation Test

### Which of the following are correct about java.util.function.Predicate?
Answered Correctly
You had to select 1 option(s)
  - a) It is an interface that has only one abstract method (among other non-abstract methods) with the signature - `public void test(T t);` **FALSE**
  - b) It is an interface that has only one abstract method (among other non-abstract methods) with the signature - `public boolean test(T t);` **TRUE**
  - c) It is an abstract class that has only one abstract method (among other non-abstract methods) with the signature - `public abstract void test(T t);` **FALSE**
  - d) It is an abstract class that has only one abstract method (among other non-abstract methods) with the signature - `public abstract boolean test(T t);` **FALSE**


> java.util.function.Predicate is one of the several functional interfaces that have been added to Java 8. This interface has exactly one abstract method named test, which takes any object as input and returns a boolean. This comes in very handy when you have a collection of objects and you want to go through each object of that collection and see if that object satisfies some criteria. For example, you may have a collection of Employee objects and, in one place of your application, you want to remove all such employees whose age is below 50, while in other place, you want to remove all such employees whose salary is above 100,000. In both the cases, you want to go through your collection of employees, and check each Employee object to determine if it fits the criteria. This can be implemented by writing an interface named CheckEmployee and having a method check(Employee ) which would return true if the passed object satisfies the criteria. The following code fragments illustrate how it can be done -
```java
//define the interface for creating criteria
interface CheckEmployee {
  boolean check(Employee e );
}

...

//write a method that filters Employees based on given criteria.
public void filterEmployees(ArrayList<Employee> dataList, CheckEmployee p){
   Iterator<Employee> i = dataList.iterator();
   while(i.hasNext()){
        if(p.check(i.next())){
             i.remove();
    }
   }
}

...

//create a specific criteria by defining a class that implements CheckEmployee
class MyCheckEmployee implements CheckEmployee{
   public boolean check(Employee e){
       return e.getSalary()>100000;
   }
};
...

//use the filter method with the specific criteria to filter the collection.
filterEmployees(employeeList, new MyCheckEmployee());
```
> This is a very common requirement across applications. The purpose of Predicate interface (and other standard functional interfaces) is to eliminate the need for every application to write a customized interface.  For example, you can do the same thing with the Predicate interface as follows -
```java
public void filterEmployees(ArrayList<Employee> dataList, Predicate<Employee> p){
   Iterator<Employee> i = dataList.iterator();
   while(i.hasNext()){
        if(p.test(i.next())){
             i.remove();
    }
   }
}

...
```
> Instead of defining a MyPredicate class (like we did with MyCheckEmployee), we could also define and instantiate an anonymous inner class to reduce code clutter
```java
Predicate<Employee> p = new Predicate<Employee>(){
  public boolean test(Employee e){
     return e.getSalary()>100000;
  }
};
...

filterEmployees(employeeList, p);
```
> Note that both the interfaces (CheckEmployee and Predicate) can be used with lambda expressions in exactly the same way.  Instead of creating an anonymous inner class that implements the CheckEmployee or Predicate interface, you could just do -
```java
filterEmployees(employeeList, e->e.getSalary()>100000);
```
> The benefit with Predicate is that you don't have to write it. It is already there in the standard java library.
### Which of the following statements are correct regarding a functional interface?
Answered Incorrectly
You had to select 1 option(s)
  - a) It has exactly one method and it must be abstract.
  - b) It has exactly one method and it may or may not be abstract.
  - c) It must have exactly one abstract method and may have other default or static methods.
  - d) It must have exactly one static method and may have other default or abstract methods.
> A functional interface is an interface that contains exactly one abstract method. It may contain zero or more default methods and/or static methods. Because a functional interface contains exactly one abstract method, you can omit the name of that method when you implement it using a lambda expression. For example, consider the following interface -
```java
interface Predicate<T> {
    boolean test(T t);
}
```
* The purpose of this interface is to provide a method that operates on an object of class T and return a boolean.
* You could have a method that takes an instance of class that implements this interface defined like this -
```java
public void printImportantData(ArrayList<Data> dataList, Predicate<Data> p){
   for(Data d: dataList){
      if(p.test(d)) System.out.println(d);
   }
}
```
* where Data class could be as simple as public class Data{ public int value; }
* Now, you can call the above method as follows:
  - `printImportantData(al, (Data d)->{ return d.value>1; } );`
* Notice the lack of method name here. This is possible because the interface has only one abstract method so the compiler can figure out the name. This can be shortened to:
  - `printImportantData(al, (Data d)->d.value>1);  `
* Notice the lack of curly brackets, the return keyword, and the semicolon. This is possible because the method returns a boolean and the expression d.value>1 also returns a boolean. The compiler is therefore able to figure out that the value of this expression is to be returned from the method. This can be shortened even more to:
  - `printImportantData(al, d->d.value>1);`
* Notice that there is no declaration of d! The compiler can figure out all information it needs because the interface has only one abstract method and that method has only one parameter. So you don't need to write all those things in your code.
* Compare the above approach to the old style using an inner class that does the same thing -
```java
   printImportantData(al,  new Predicate<Data>(){
	public boolean test(Data d){
		 return d.value>1;
	 }   }   );
```
> The Predicate interface described above can be used anywhere there is a need to "do something with an object and return a boolean" and is actually provided by the standard java library in java.util.function package. This package provides a few other useful functional interfaces.
* Predicate<T>    Represents a predicate (boolean-valued function) of one argument of type T.
* Consumer<T> Represents an operation that accepts a single input argument of type T and returns no result.
* Function<T,R> Represents a function that accepts one argument of type T and produces a result of type R
* Supplier<T> Represents a supplier of results of type T.
> For the exam, you only need to be aware of Predicate.

Please see http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html for learning Lambda expressions in Java.

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
similar to += or /= **I got this right but was unsure about it - don't think it's on the exam but have added it to main README**
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
### Objects of which of the following classes can be thrown using a throw statement?
Answered Incorrectly
You had to select 3 option(s)
  - a) Event **FALSE**
  - b) Object **FALSE**
  - c) Throwable **TRUE** -> **answered incorrectly**
  - d) Exception **TRUE**
  - e) RuntimeException **TRUE**
* You can only throw a Throwable using a throws clause. Exception and Error are two main subclasses of Throwable.
* **you can throw Throwable and all of its subclasses:**
```java
public class ThrowableClasses {
	public static void main(String[] args) {
		try {
			throw new Throwable();
		} catch(Throwable e) {
			System.out.println(e);
		}
		
		try {
			throw new Error();
		} catch(Error e) {
			System.out.println(e);
		}
		
		try {
			throw new Exception();
		} catch(Exception e) {
			System.out.println(e);
		}
		
		try {
			throw new RuntimeException();
		} catch(RuntimeException e) {
			System.out.println(e);
		}
	}
}
```
* **added to main README**
### Identify the valid members of Boolean class.
Answered Correctly
You had to select 3 option(s)
  - a) parseBoolean(String ) **TRUE**
  - b) valueOf(boolean ) **TRUE**
  - c) parseBoolean(boolean ) **FALSE**
  - d) FALSE **TRUE** -> TRUE and FALSE are valid static members of Boolean class.
  - e) Boolean(Boolean ) **FALSE** -> There is no constructor that takes a Boolean.

* You need to remember the following points about Boolean:

1. Boolean class has two constructors - Boolean(String) and Boolean(boolean)
> The String constructor allocates a Boolean object representing the value true if the string argument is not null and is equal, ignoring case, to the string "true". Otherwise, allocate a Boolean object representing the value false. Examples: new Boolean("True") produces a Boolean object that represents true. new Boolean("yes") produces a Boolean object that represents false.

The boolean constructor is self explanatory.

2. Boolean class has two static helper methods for creating booleans - parseBoolean and valueOf.
> Boolean.parseBoolean(String ) method returns a primitive boolean and not a Boolean object (Note - Same is with the case with other parseXXX methods such as Integer.parseInt - they return primitives and not objects). The boolean returned represents the value true if the string argument is not null and is equal, ignoring case, to the string "true".

> Boolean.valueOf(String ) and its overloaded Boolean.valueOf(boolean ) version, on the other hand, work similarly but return a reference to either Boolean.TRUE or Boolean.FALSE wrapper objects. Observe that they dont create a new Boolean object but just return the static constants TRUE or FALSE defined in Boolean class.

3. When you use the equality operator ( == ) with booleans, if exactly one of the operands is a Boolean wrapper, it is first unboxed into a boolean primitive and then the two are compared (JLS 15.21.2). If both are Boolean wrappers, then their references are compared just like in the case of other objects. Thus, new Boolean("true") == new Boolean("true") is false, but new Boolean("true") == Boolean.parseBoolean("true") is true.

### Identify the correct statements.
Answered Incorrectly
You had to select 1 option(s)
  - a) LocalDate, LocalTime, and LocalDateTime extend Date. **FALSE**
  - b) LocalDate, LocalTime, and LocalDateTime implement TemporalAccessor. ** TRUE**
  - c) Both - LocalDate and LocalTime extend LocalDateTime, which extends java.util.Date. **FALSE**
  - d) LocalDate, LocalTime, and LocalDateTime implement TemporalAccessor and extend java.util.Date. **FALSE** -> **answered incorrectly**

* Here are some points that you should keep in mind about the new Date/Time classes introduced in Java 8 -

1. They are in package java.time and they have no relation at all to the old java.util.Date and java.sql.Date.

2. java.time.temporal.TemporalAccessor is the base interface that is implemented by LocalDate, LocalTime, and LocalDateTime concrete classes. This interface defines read-only access to temporal objects, such as a date, time, offset or some combination of these, which are represented by the interface TemporalField.

3. LocalDate, LocalTime, and LocalDateTime classes do not have any parent/child relationship among themselves. As their names imply, LocalDate contains just the date information and no time information, LocalTime contains only time and no date, while LocalDateTime contains date as well as time. None of them contains zone information. For that, you can use ZonedDateTime.

> These classes are immutable and have no public constructors. You create objects of these classes using their static factory methods such as of(...) and from(TemporalAccessor ).  For example,
LocalDate ld = LocalDate.of(2015, Month.JANUARY, 1); or LocalDate ld = LocalDate.from(anotherDate); or LocalDateTime ldt = LocalDateTime.of(2015, Month.JANUARY, 1, 21, 10); //9.10 PM

Since you can't modify them once created, if you want to create new object with some changes to the original, you can use the instance method named with(...). For example,
LocalDate sunday = ld.with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.SUNDAY));

4. Formatting of date objects into String and parsing of Strings into date objects is done by java.time.format.DateTimeFormatter class. This class provides public static references to readymade DateTimeFormatter objects through the fields named ISO_DATE, ISO_LOCAL_DATE, ISO_LOCAL_DATE_TIME, etc.  For example -
        
LocalDate d1 = LocalDate.parse("2015-01-01", DateTimeFormatter.ISO_LOCAL_DATE);

> The parameter type and return type of the methods of DateTimeFormatter class is the base interface TemporalAccessor instead of concrete classes such as LocalDate or LocalDateTime. So you shouldn't directly cast the returned values to concrete classes like this -
   LocalDate d2 = (LocalDate) DateTimeFormatter.ISO_LOCAL_DATE.parse("2015-01-01"); //will compile but may or may not throw a ClassCastException at runtime.
You should do like this -
   LocalDate d2 = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse("2015-01-01"));

5. Besides dates, java.time package also provides Period and Duration classes. Period is used for quantity or amount of time in terms of years, months and days, while Duration is used for quantity or amount of time in terms of hour, minute, and seconds.

> Durations and periods differ in their treatment of daylight savings time when added to ZonedDateTime. A Duration will add an exact number of seconds, thus a duration of one day is always exactly 24 hours. By contrast, a Period will add a conceptual day, trying to maintain the local time.

> For example, consider adding a period of one day and a duration of one day to 18:00 on the evening before a daylight savings gap. The Period will add the conceptual day and result in a ZonedDateTime at 18:00 the following day. By contrast, the Duration will add exactly 24 hours, resulting in a ZonedDateTime at 19:00 the following day (assuming a one hour DST gap).

### Which statements concerning conversion are true?
Answered Incorrectly
You had to select 4 option(s)
  - a) Conversion from char to long does not need a cast. **TRUE**
  - b) Conversion from byte to short does not need a cast. **TRUE**
  - c) Conversion from short to char needs a cast. **TRUE** -> The reverse is also true. Because their ranges are not compatible.
  - d) Conversion from int to float needs a cast. **FALSE** -> It does not need a cast because a float can hold any int value. Note that the opposite is not true.
  - e) Conversion from byte, char or short to int, long or float does not need a cast. **TRUE** -> Because int, long or float are bigger that byte char or short.

> Think of it as transferring contents of one bucket into another. You can always transfer the contents of a smaller bucket to a bigger one. But the opposite is not always possible. You can transfer the contents of the bigger bucket into the smaller bucket only if the actual content in the bigger bucket can fit into the smaller one. Otherwise, it will spill.

> It is the same with integral types as well. byte is smaller than short or int. So you can assign a byte to an int (or an int to a float, or a float to a double) without any cast. But for the reverse you need to assure the compiler that the actual contents in my int will be smaller than a byte so let me assign this int to a byte. This is achieved by the cast.
int i = 10;
byte b = 20;
b = i;//will not compile because byte is smaller than int
b = (byte) i; //OK

> Further, if you have a final variable and its value fits into a smaller type, then you can assign it without a cast because compiler already knows its value and realizes that it can fit into the smaller type. This is called implicit narrowing and is allowed between byte, int, char, and, short but not for long, float, and double.


final int k = 10;
b = k; //Okay because k is final and 10 fits into a byte

final float f = 10.0;//will not compile because 10.0 is a double even though the value 10.0 fits into a float
i = f;//will not compile.

### Which of these combinations of switch expression types and case label value types are legal within a switch statement?
Answered Incorrectly
You had to select 1 option(s)
  - a) switch expression of type int and case label value of type char.
  ```
  Note that the following is invalid though because a char cannot be assigned to an Integer:
  Integer x = 1;  // int x = 1; is valid.
  switch(x){
    case 'a' : System.out.println("a");
  }
  ```
  - b) switch expression of type float and case label value of type int.
  - c) switch expression of type byte and case label value of type float.
  - d) switch expression of type char and case label value of type byte.
  ```
  This will not work in all cases because a byte may have negative values which cannot be assigned to a char. For example, char ch = -1; does not compile. Therefore, the following does not compile either:
    char ch = 'x';
    switch(ch){
        case -1 :        System.out.println("-1"); break; // This will not compile : "possible loss of precision"
        default:        System.out.println("default");    
    }
	```
  - e) switch expression of type boolean and case label value of type boolean.

* You should remember the following rules for a switch statement:

1. Only String, byte, char, short, int, and enum values can be used as types of a switch variable. (String is allowed since Java 7.) Wrapper classes Byte, Character, Short, and Integer are allowed as well.

2. The case constants must be assignable to the switch variable. For example, if your switch variable is of class String, your case labels must use Strings as well.

3. The switch variable must be big enough to hold all the case constants. For example, if the switch variable is of type char, then none of the case constants can be greater than 65535 because a char's range is from 0 to 65535. Similarly, the following will not compile because 300 cannot be assigned to 'by', which can only hold values from -128 to 127.
byte by = 10;
switch(by){
    case 200 :  //some code;
    case 300 :  //some code;
}

4.  All case labels should be COMPILE TIME CONSTANTS.

5. No two of the case constant expressions associated with a switch statement may have the same value.

6. At most one default label may be associated with the same switch statement.

### Which of these statements are true?
Answered Correctly
You had to select 2 option(s)
  - a) A super( <appropriate list of arguments> ) or this( <appropriate list of arguments> ) call must always be provided explicitly as the first statement in the body of the constructor. **FALSE**
  ```
  super(); is automatically added if the sub class constructor doesn't call any of the super class's constructors.
  ```
  - b) If a subclass does not have any declared constructors, the implicit default constructor of the subclass will have a call to super( ). **TRUE**
  - c) If neither super( ) or this( ) is declared as the first statement of the body of a constructor, then this( ) will implicitly be inserted as the first statement. **FALSE**
  ```
  super() is added and not this()
  ```
  - d) super(<appropriate list of arguments>) can only be called in the first line of the constructor but this(<appropriate list of arguments>) can be called from anywhere. **FALSE**
  - e) You can either call super(<appropriate list of arguments>) or this(<appropriate list of arguments>) but not both from a constructor.**TRUE**
### Final classes
```
class MyString extends String{
   MyString(){ super(); }
}
```
Will the above code compile? -> **NO**
```
This will not compile because String is a final class and final classes cannot be extended.
There are questions on this aspect in the exam and so you should remember that StringBuffer and StringBuilder are also final. All Primitive wrappers are also final (i.e. Boolean, Integer, Byte etc).
java.lang.System is also final.
```
### An overriding method must have a same parameter list and the same return type as that of the overridden method.
* **FALSE** - it can have covariant return types
