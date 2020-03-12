# Java OCA

My notes from Boyarsky and Selikoff's *Oracle Certtified Associate Java SE 8 Programmer I Study Guide*

[Notes on Enthuware exams](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/enthuware/README.md)

**Table of contents**

---
[Chapter 1 - Java Building Blocks](#chapter-1---java-building-blocks)

[Chapter 2 - Operators and Statements](#chapter-2---operators-and-statements)

[Chapter 3 - Java Core APIs](#chapter-3---java-core-apis)

[Chapter 4 - Methods and Encapsulation](#chapter-4---methods-and-encapsulation)

[Chapter 5 - Class Design](#chapter-5---class-design)

[Chapter 6 - Exceptions](#chapter-6---exceptions)

---

## Chapter 1 - Java Building Blocks
**In this chapter:**

---
[Comments](#comments)

[Classes and Files](#classes-and-files)

[main() method](#main-method)

[Package declarations and imports](#package-declarations-and-imports)

[Code formatting on the exam](#code-formatting-on-the-exam)

[Benefits of Java](#benefits-of-java)

[Creating Objects](#creating-objects)

[Reference types and primitives](#reference-types-and-primitives)

[Reference Types](#reference-types)

[Declaring and initializing variables](#declaring-and-initializing-variables)

[Identifiers](#identifiers)

[Default initialization of variables](#default-initialization-of-variables)

[Order of elements in a class](#order-of-elements-in-a-class)

[Destroying objects](#destroying-objects)

---
### Comments
```java
/**
\* Javadoc multiline comment
*/
```

```java
/*
\* regular multiline comment
*/
```

```java
// single line comment
```
```java
String s = /* mid-line comment */ "foo";
System.out.println(s); // -> foo
```

### Classes and Files
* a file doesn't have to have a public class
* multiple classes are allowed in a file - at most **one** can be public
* if you do have a public class, it must match the filename

### main() method
* must have public access modifier
* must be static
* (i.e. must be `public static` or `static public`)
* return type must be `void`
* can have other modifiers (but these must not clash with `public` and `static`)
* args can be `(String[] args)`, `(String args[])`, `(String... args)`
* when running the program, spaces are used to separate the arguments
* if a string has spaces, surround it with quotes e.g. `> java Foo 'arg one' two three`
* trying to access an argument not supplied will throw a runtime error
* `args` can be any legal identifier e.g. `public static void main(String... blarguments){}`
* a Java class with a properly defined `main()` method is an *executable class*
* you can overload the `main()` method, in which case Java will still only call the main method with the correct signature:
```java
public class OverloadingMain {
  public static void main(){
	System.out.println("Running overloaded main method");
  }
  
  public static final void main(String[] args) {
	System.out.println("Running exectuable main method");
    main(); 
  }
}
/* output:
 * Running exectuable main method
 * Running overloaded main method
*/
```
#### Running with class path
* given the following source file `Java8OCANotes/myFolder/HelloWorld.java`
* from `Java8OCANotes:`
```bash
> javac myFolder/HelloWorld.java
> javac HelloWorld
Error: Could not find or load main class HelloWorld
Caused by: java.lang.ClassNotFoundException: HelloWorld
> java -cp myFolder HelloWorld
```
* if not in the directory, use classpath (`-cp` or `-classpath`) to specify
### Package declarations and imports
* classes in the same package do not need to be imported to be used
* (`java.lang.*` is already imported)
* put another way, only two packages are automatically imported - `java.lang` and the current package
```java
import java.util.*; // imports all classes in java.util
```
* doesn't import classes in child packages/subpackages e.g. the classes in `java.util.concurrent` won't be imported with `import java.util.*;`
* similarly, `import java.util.concurrent.*;` won't import the classes in `java.util`
---
here is an example using the classes `Files` and `Paths`, both in `java.nio.file`:
* to import, either use wildcard:
```java
import java.nio.file.*;
```
* or import both explicitly:
```java
import java.nio.file.Files;
import java.nio.file.Paths;
```
* **don't** use:
```java
import java.nio.*; // only matches classes, not packages
import java.nio.*.*; // can't recursively use wildcards - there can only be one and it must be at the end
```
---
#### Naming conflicts
```java
import java.util.*;
import java.sql.*;

public class Foo {
	Date date; // COMPILE ERROR
}
```
* the compiler doesn't know whether you mean `java.util.Date` or `java.sql.Date`
* so instead, we can import the class explicitly e.g.:
```java
import java.util.Date;
import java.sql.*;
```
* or, if both classes are needed, refer to one or both explicitly in code:
```java
public class Foo {
	java.util.Date date1;
	java.sql.Date date2;
}
```
* however, importing *both* explicitly will also throw a compiler error:
```java
import java.util.Date;
import java.sql.Date; // DOES NOT COMPILE
```
---
#### Create a package
* filepath `C:\temp\packagea\ClassA.java`
```java
package packagea;

public class ClassA{}
```
* filepath `C:\temp\packageb\ClassB.java`
```java
package packageb;
import packagea.ClassA

public class ClassB{
	public static void main(String[] args){}
}
```
* compile:
```
> cd temp
> javac packagea\ClassA.java packageb\ClassB.java
```
* run:
```
> java packageb.ClassB
```
* [More on compiling and running programs with packages and classpath](#https://javahungry.blogspot.com/2018/11/solved-error-could-not-find-or-load-main-class.html)
* if no package is specified, it will be part of the *default package*
* members of a named package can’t access classes and interfaces defined in the default package, as there is no way to reference them
### Code formatting on the exam
* if the code starts after line 1, assume all imports are correct
* if it starts on line 1, or there are no line numbers, make sure there are no imports missing
* if there is no `main()` method, assume that the main() method, the class definition, and all imports are present
### Benefits of Java
* Object Oriented Programming
  - Abstraction
  - Encapsulation
  - Inheritance
  - Polymorphism
* Platform-independent
* Type-safety
* Multithreading and concurrency
* Robust - prevents memory leaks with automatic garbage collection/automatic memory management
* Simple - no pointers or operator overloading
* Secure - runs in JVM sandbox environment
### Creating objects
#### Constructors
```java
public class Foo {
	public Foo(){}
}
```
* constructors don't have a return type - the following should not be mistaken for a constructor:
```java
public class Foo {
	public void Foo(){}
}
```
* a method name starting with a capital and with a return type is not a constructor - the exam will try to trip you up on this
#### Instance initializer blocks
* code blocks in a method are run when the method is called
* code blocks outside a method are instance initializers, run when the object is created:
```java
3. public static void main(String... args) {
4.     {System.out.println("Foobar");}
5. }
6. {System.out.println("Tactac");} // <- instance initializer block
```
#### Order of initialization
* fields and instance initializer blocks are run in order
* constructor is run after all the above have run
```java
1. public class Foobar(){
2.     public Foobar(){
3.         num = 3; // <- this runs third
4.     }
5.     int num = 1; // <- this runs first
6.     {num = 2;} // <- this runs second
7. }
```
* if we `new Foobar();`, num is declared and initialized as 1, then the instance initializer block runs and num == 2, then the constructor is called and num == 3
* num must be declared before it can be initialized, so lines 5 & 6 could not be the other way round
### Reference types and primitives
|  Keyword | Type                          | Example  |
| :------- | :----:                        | -------: |
|  boolean | true or false                 | true     |
|  byte    | 8-bit integral value          | 123      |
|  short   | 16-bit integral value         | 123      |
|  int     | 32-bit integral value         | 123      |
|  long    | 64-bit integral value         | 123      |
|  float   | 32-bit floating-point value   | 123.23f  |
|  double  | 64-bit floating-point value   | 123.23   |
|  char    | 16-bit unicode value          | 'a'      |
* each numeric type uses twice as many bits as its next smallest type
* byte can hold a value between -128 and 127
* char can be assigned a character literal e.g. `char c = z` or a positive integer e.g. `char c = 122` (both examples are equivalent)
* char can't be assigned a negative value (except through casting e.g. `char c = (char)-1` which will store an unexpected value)
##### Other bases
* **Octal** - starts with 0 e.g. `017` - digits 0-7
* **Hexadecimal** - starts with 0X or 0x e.g. `0xFF` - digits 0-9 letters A-F
* **Binary** - starts 0b or 0B e.g. `0b10` - digits 0-1
##### Underscores
* numeric literals can have underscores anywhere except:
  - at the start
  - at the end
  - right before a decimal point
  - right after a decimal point
  - right after the prefixes 0b, 0B, 0x, and 0X (but legal after octal prefix 0)
  - prior to an L or F suffix (or l, f, D, etc)
```java
double d1 = _0.1; // illegal
double d2 = 0._1; // illegal
double d3 = 100.01_; // illegal
double d4 = 101_.001; // illegal
double d5 = 1_0_1_1.0_123_5_0; // legal!

long l1 = 123_L; // illegal
_
long octVal = 0_413; // legal

long hexVal1 = 0x_10_BA_75; // illegal
long hexVal2 = 0x10_BA_75; // legal

long binVal1 = 0b_1_0000_10_11; // illegal
long binVal2 = 0b1_0000_10_11; // legal
```
* an edge example is that you can’t use an underscore in positions where a string of digits is expected:
```java
public class ParseIntUnderscores {
	public static void main(String... args) {
		int x = 12_34;
		int y = Integer.parseInt("12_34");
	}
}
```
* this will compile, as `parseInt()` expects a String, but throws java.lang.NumberFormatException at runtime
### Reference Types
* a reference points to the location in memory where an object is stored
* a value is assigned to a reference in one of two ways:
  - a reference can be assigned to another object of the same type
  - a reference can be assigned to a new object using the `new` keyword
```java
Foo foo1;
Foo foo2 = new Foo();
foo1 = foo2;
```
##### Key differences between primitives and reference types
* primitives cannot be assigned null
```java
int x = null; // DOES NOT COMPILE
```
* primitives do not have methods
```java
char c = 'c';
System.out.println(c.length()); // DOES NOT COMPILE
```
### Declaring and initializing variables
* declare:
```java
int x;
```
* initialize:
```java
x = 1;
```
* declare & initialize:
```java
int y = 2;
```
* multiple declarations:
```java
int i, i2 = 2, i3, i4 = 4; // four declared, two initialized
int j, j2, j3 = 3; // three declared, only last one initialized
```
* can't declare multiple types in same statement:
```java
int x = 3, double y = 4.4; //DOES NOT COMPILE
```
* can't use the type more than once:
```java
int x = 3, int x = 4; //DOES NOT COMPILE
```
* watch out for semi-colons rather than commas:
```java
int x = 3; int y = 4; // compiles - semi-colon means it's a new statement
```
### Identifiers
* must begin with a letter or `_` or `$` (also `£`, `¢`, `¥` and various other currency symbols)
* subsequent characters may be numbers
* must not be a Java reserved word (case-sensitive)
```java
String Class = "Class"; // legal
String class = "class"; // DOES NOT COMPILE
```
#### Keywords and reserved words that can't be used as identifiers
| (primitives)  | (branching)  | (files, objects)    | (access modifiers, inheritance, constructors)  | (err handling) | (misc)      | (modifiers)  |
| ------------- | ------------ | ------------------- | ---------------------------------------------- | -------------- | ----------- | ------------ |
| 	boolean	    | 	if	       | 	package	         | 	public	                                      | 	try	       | 	new	     | abstract	    |
| 	byte	    | 	else	   | 	import	         | 	protected	                                  | 	catch	   | 	null     | default	    |
| 	short	    | 	for	       | 		             | 	private	                                      | 	finally	   | 	void	 | static	    |
| 	int         | 	do	       | 	class	         | 		                                          | 	throw	   | 	return	 | final	    |
| 	long	    | 	while	   | 	interface	     | 	extends	                                      | 	throws	   | 	true	 | strictfp	    |
| 	float	    | 	switch	   | 	enum	         | 	implements	                                  | 		       | 	false	 | transient	|
| 	double	    | 	case	   | 		             | 		                                          | 		       | instanceof	 | 	native	    |
| 	char	    | 	break	   | 		             | 	this	                                      | 		       | 	assert	 | synchronized |
| 		        | 	continue   | 		             | 	super	                                      | 		       | 	const	 | volatile	    |
| 		        | 		       | 		             | 		                                          | 		       | 	goto	 | 		        |

* **all these are lowercase**, so using any spelled differently is legal
* class names are legal identifiers for variables:
```java
public class AStringCalledString {
	public static void main(String... args) {
		String String = "String";
		System.out.println(String); // -> String
	}
}
```
### Default initialization of variables
* *local variables* (i.e. variables in a method) must be initialized before use
* they do not have a default value
* the compiler won't let you read an uninitialized local variable value:
```java
public int foo() {
    int x = 1;
	int y;
	int z = x + y; // DOES NOT COMPILE
	return z;
}
```
* with branching code, the outer method or any branch that reads a local variable must initialize it or it won't compile:
```java
6.  int x;
7.  if(z == true){
8.      x = 3;
9.  } else {
10.     // do nothing
11. }
12. System.out.println(x); // DOES NOT COMPILE - if z == false, x won't be initialized
```
* *class variables* and *instance variables* do not require initialization
* they have a default value, assigned on declaration

| Variable type          | Default initialization value  |
| ---------------------- | ----------------------------  |
| boolean                | false                         |
| byte, short, int, long | 0 (in the type's bit length   |
| float, double          | 0.0 (in the type's bit length |
| char                   | '\u0000' (NUL) *not on exam   |
| All object references  | null                          |
### Variable scope
* local variables are in scope from declaration to the end of the block
* there are two local variables in the following snippet - the argument foo, and the local variable bar:
```java
public void fooPlus(int foo) {
	int bar = 1;
	foo += bar;
} // foo and bar go out of scope here
```
* instance variables are in scope from declaration until object is garbage collected
* class variables are in scope from declaration until the program ends
### Order of elements in a class
```java
package com.foo; //package declaration; optional
import com.bar; // imports; optional
public class Foobar { // class declaration; required
	int x; // declare any fields...
	void myMethod(){} //... or methods - order not important
}
```
* package, then imports, then class
* multiple classes can be in a file, but only one can be public
* the public class must match the filename
### Destroying objects
* an object remains on the heap until it is no longer reachable i.e.:
  - it no longer has any references pointing to it, or
  - all references to it have gone out of scope
* finalize()
  - may or may not get called, and definitely won't be called more than once
  - run only when the object is eligible for garbage collection

## Chapter 2 - Operators and Statements
**In this chapter:**

---
[Order of Precedence](#order-of-precedence)

[Pre unary and post unary increment and decrement](#pre-unary-and-post-unary-increment-and-decrement)

[Numeric promotion](#numeric-promotion)

[Logical complement (!) and negation (-) operators](#logical-complement--and-negation--operators)

[Primitive conversion](#primitive-conversion)

[Compound assignment operator](#compound-assignment-operator)

[Assignment operator](#assignment-operator)

[Logical operators](#logical-operators)

[if....](#if)

[if else](#if-else)

[Ternary](#ternary)

[switch](#switch)

[while](#while)

[do while](#do-while)

[for](#for)

[for each](#for-each)

[Labels](#labels)

[break](#break)

[continue](#continue)

[break and continue with labelled statements](#break-and-continue-with-labelled-statements)

[Advanced control flow usage](#advanced-control-flow-usage)

[Extra things](#extra-things)

---

### Order of Precedence

| Operator              | Symbol                             |
| --------------------- | ---------------------------------- |
| post unary            | `x++`, `x--`                       |
| pre unary             | `++x`, `--x`                       |
| other unary           | `!x`, `+x`, `-x`                   |
| multiplicative        | `*`, `/`, `%` (modulus)            |
| additive              | `+`, `-`                           |
| relational            | `<`, `>`, `<=`, `>=`, `instanceof` |
| equality              | `==`, `!=`                         |
| bitwise               | `&`, `\|`, `^`                      |
| short-circuit logical | `&&`, `\|\|`                         |
| ternary               | `x == true ? y : z`                |
| assignment            | `=`, `+=`, `-=`                    |

*if the above table isn't rendering properly, [see here](#https://github.com/jbt/markdown-editor/issues/20) *

### Pre unary and post unary increment and decrement
* pre unary increments/decrements and returns a new value
* post unary increments/decrements and returns the original value
```java
int x = 3;
int y = ++x * 5 / x-- + --x;
System.out.println("x is " + x);
System.out.println("y is " + y);
```

* step-by-step:
  - apply the operators, left to right:
    - `++x` -> increment, then assign -> `4 * 5 / x-- + --x` -> x is 4
    - `x--` -> assign, then decrement -> `4 * 5 / 4 + --x` -> x is 3
    - `--x` -> decrement, then assign -> `4 * 5 / 4 + 2` -> x is 2
  - evaluate the operations:
    - `20 / 4 + 2` -> `5 + 2` -> `7`
* another example:
```java
int a = 10;
a = a++ + a + a-- - a-- + ++a;
// 10 + 11 + 11 - 10 + 10 -> 32
System.out.println(a);
```
![Pre unary and post unary increment and decrement](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/media/unary-operators.png "Unary Operators")
[More on this](https://coderanch.com/t/653797/certification/Post-Pre-unary-operator-precedence)  
[Princeton on precedence](https://introcs.cs.princeton.edu/java/11precedence/)

### Logical complement (!) and negation (-) operators
* `!` flips the value of a boolean expression e.g.
```java
boolean x = true;
System.out.println(x); // true
System.out.println(!x); // false
```
* `-` reverses the sign of a numeric expressions e.g.
```java
int x = 1;
x = -x;
System.out.println(x); // -1
x = -x;
System.out.println(x); // 1
```
* in Java, 1 and true are not related (nor 0 and false):
```java
int x = !5; // DOES NOT COMPILE - can't logically invert a number
boolean y = -true; // DOES NOT COMPILE
boolean z = !0; // DOES NOT COMPILE
```
### Numeric promotion
1. if two values in an operation have different data types, the smaller will be promoted to the larger:
```java
int x = 1;
long y = 2;
System.out.println(x + y); // x promoted to long
```
2. if one value is integral and the other floating-point, the integral value is promoted to the floating-point value type:
```java
long x = 1;
double y = 1.1;
System.out.println(x + y); // x promoted to double
```
3. anything smaller than an int (char, byte, short) is promoted to int, even if neither is an int:
```java
short x = 1;
byte y = 1;
System.out.println(x + y); // both promoted to int
short z = x + y; // DOES NOT COMPILE -> int won't fit into short

final short xx = 1;
final byte yy = 1;
short zz = xx + yy; // compiles - the value is known at compile time and can fit into short
```
4. after all promotion, all operands will have the same type and the resulting value will be of that type:
```java
int x = 1;
long y = 2;
long z = x + y; // x promoted to long; z is also a long
```
### Primitive conversion
* Java automatically promotes from smaller to larger data types, but not the other way round
```java
float f = 1; // promotes integer to float
int x = 1.0; // DOES NOT COMPILE -> double can't assign to int
short y = 1921222; // DOES NOT COMPILE -> too large to fit into short type
int z = 9f; // DOES NOT COMPILE -> float can't assign to int
long l = 192_301_398_193_810_323; // DOES NOT COMPILE -> interprets literal as int & value is too large
```
* casting primitive values:
```java
int x = (int)1.0;
short y = (short)1921222; // stored as 20678 - value is too big too store in short, so numeric overflow occurs, wrapping around to the next lowest value then counting up from there
int z = (int)9f;
long l = 192_301_398_193_810_323L;
```
* any literal is assumed to be an int or double unless specified
```java
float f = 1.2; // DOES NOT COMPILE -> 1.2 interpreted as double -> double larger than float
float f = 1.2f; // compiles
float f = (float)1.2; // compiles
```
### Compound assignment operator
```java
int x = 2;
x *= 3;
System.out.println("x is " + x); // x is 6
```
* not just shorthand - also saves having to cast a value:
```java
long x = 10;
int y = 5;
y = x * y; // DOES NOT COMPILE - result promoted to long -> won't fit int
y = (int)x * y; // compiles
y *= x; // automatically casts
```
### Assignment operator
* the result of an assignment is an expression in and of iself, equal to the value of the assignment e.g.
```java
long x = 3;
long y = (x = 5);
System.out.println(y); // 5
System.out.println(x); // 5
```
### Logical operators
```java
true & true // true
/* inclusive or - either can be true */
true | false // true
true | true // true
/* exclusive or - only one can be true */
true ^ true // false
true ^ false // true
```
* make sure if asked about a value where || appears that the right hand side is reached:
```java
int x = 6;
boolean y = (x >= 6) || (++x <= 7);
System.out.println(x); // 6, as right hand operation never reached
```
* there are two situations in which short-circuiting happens:
  - the first condition of an `&&` is false (i.e. both can't be true)
  - the first condition of an `||` is true (i.e. it doesn't matter what the other is)
### if....
```java
if(booleanExpression)
	System.out.println("Foo"); // for a single statement, braces are optional
if(booleanExpression){
	System.out.println("Foo"); // more than one statement
	System.out.println("Bar"); // so braces are mandatory
}
```
* watch out for this on the exam, e.g.:
```java
boolean x = false;
if(x)
	System.out.print("one");
	System.out.print("two");
```
* output is "two" - despite the formatting, it is interpretted as a single statement if block so the second statement runs regardless
### if else
* again, braces optional for single statments
```java
if(true){
	System.out.print("true");
} else
	System.out.print("false");
	System.out.print("foobar"); // no longer in if-else
```
* output is "truefoobar"
* it is possible to create a single empty statement using the semi-colon, hence the following is legal:
```java
 if (false) ; else ;
 ```
---
* if statement must evaluate to boolean
```java
int x = 1;
if(x){ // DOES NOT COMPILE (unlike Groovy Truth)
	// do something
}
```
* watch out also for assignment instead of evaluation:
```java
int x = 1;
if(x = 1) { // DOES NOT COMPILE -> result of x = 1 is 1, which is not a boolean expression
	// do something
}
```
### Ternary
* if using for assignment, both outcomes must be assignable to type:
```java
int x = true ? 1 : 2;
int y = true ? 1 : "not true"; // DOES NOT COMPILE
```
* but otherwise no obligation that both need to be of same type:
```java
System.out.println(true ? 1 : "not true");
```
### switch
```java
int x = 4;
switch(x){ // braces required
	case 1:
		//bla bla
		break;
	case 2:
		// something else
		break;
	case 4:
		// this runs
		break;
	default:
		// if no matches
		break;
}
```
* supports:
  - `int` and `Integer`
  - `short` and `Short`
  - `byte` and `Byte`
  - `char` and `Character`
  - String
  - enum values
* does not support:
	- `long` or `Long`
	- `boolean` or `Boolean`
	- `double` or `Double`
	- `float` or `Float`
* additionally, it can't be passed a null value (NullPointerException)
---
* case values must be compile-time constants of same data type as switch value e.g.
```java
String x = "bla bla";
final String y = "tac tac";
String test = "foo";
switch(test) {
	case x: // DOES NOT COMPILE -> variable value not known at compile time
	case y: // compiles -> final constant
	case "bar": // compiles -> string literal
}
```
* switch will run **every** statement after entry point (case match or default) until it hits break:
```java
int x = 1;
switch(x) {
	case 0:
		// doesn't run
	case 1: //enters here
		// runs code
	case 2:
		// runs code
		break; // exits switch
	case 3:
		// doesn't run
}
```
* one final example:
```java
public void fooBar(String bla, final String tac) {
	final String foo = "Foo";
	switch(bla) {
	case foo: // compiles -> final constant at compile time
	case tac: // DOES NOT COMPILE -> final but not constant, as passed in as argument at runtime
	case 3: // DOES NOT COMPILE -> wrong data type
	case 'B': // DOES NOT COMPILE -> wrong data type
	case "B": // compiles -> String data type
	case java.time.DayOfWeek.SUNDAY: // DOES NOT COMPILE -> final constant, but of type enum
}
```
* You should remember the following rules for a switch statement:
  1. Only byte, char, short, int, their wrapper classes Byte, Character, Short, and Integer, String, and enum values can be used as types of a switch variable.
  2. The case constants must be assignable to the switch variable. For example, if your switch variable is of class String, your case labels must use strings as well.
  3. The switch variable must be big enough to hold all the case constants. For example, the following will not compile because 300 cannot be assigned to `byte b`, which can only hold values from -128 to 127
  ```java
  byte b = 10;
  switch(b){
	  case 200 : 
	  	//some code;
	  case 300 :
		//some code; 
  }
  ```
  4. All case labels should be COMPILE TIME CONSTANTS.
  5. No two of the case constant expressions associated with a switch statement may have the same value.
  6. At most one default label may be associated with the same switch statement.
### while
* braces optional for single-line statement
```java
while(booleanExpression)
	// do code
while(booleanExpression){
	// do code
}
```
```java
int x = 0;
while(x++ < 5){
	System.out.print(x);
}
// 01234
x = 0;
while(++x < 5){
	System.out.print(x);
}
// 1234
```
### do while
* braces optional for single-line statement
* `while` evaluates before running the block, `do-while` evaluates after
* `do-while` will always run at least once e.g.
```java
do {
	System.out.print(false); // out -> false
} while (false); // evaluates and doesn't run again
```

### for
```java
for(int i = 0; i < 5; i++){}
```
* format: for(`initialization`; `booleanExpression`; `updateStatement`){}
* curly braces `{}` optional for a one-line statement
* the initialization, boolean and update statements themselves can be blank, but the separating semicolons must be present:
```java
for( ; ; ){} // compiles - will run an infinite loop
for(){} // DOES NOT COMPILE
for(;){} // DOES NOT COMPILE
```
* we can add multiple terms:
```java
for(int x = 0, y = 5; y < 10 && x < 5; x++, y++){}
```
* can't redeclare a variable in the initialization block:
```java
int x = 5;
for(int y = 1, x = 2; x < 5; x++){} // DOES NOT COMPILE - x is already declared, and this is shorthand for int x = 2
```
* initialization block must **either** *declare* or *assign*
```java
int x;
long y = 10;
for(x = 7, y = 14; x < 10; x++){} // legal - already declared so x is assigned, y is reassigned
```
* variables **declared** in init block must be of the same type:
```java
for(int i = 0, long x = 0; i < 5; i++){} // DOES NOT COMPILE
```
* in fact, it's illegal to repeat the type, so not even possible:
```java
for(int i = 0, int j = 0; i < 5; i++){} // DOES NOT COMPILE -> instead you would use:
for(int i = 0, j = 0; i < 5; i++){} // legal
```
### for each
* format: for(`dataType` `variableName` : `collection`){}
* `collection` must be an object that implements Iterable, e.g. array, ArrayList
* curly braces `{}` optional for a one-line statement
```java
int[] arr = new int[] {1, 2, 3, 4};
for(int i : arr){
	System.out.print(i);
} // OUTPUT -> 1234
```
### Labels
```java
int[][] myComplexArray = { {1, 2, 3},{4, 5, 6},{7, 8, 9} };
OUTER_LOOP: for(int[] mySimpleArray : myComplexArray){
	INNER_LOOP: // code
}
```
### break
```java
while(booleanExpression){
	break; // break out of immediate loop
}
_optionalLabel: while(booleanExpression){
	break _optionalLabel; // break out of labelled loop
}
```
### continue
* break transfers to enclosing statement
* continue transfers control to the boolean expression - i.e. it ends the current iteration of the loop
```java
int i = 0;
int j = 0;
while(i < 5){
  System.out.print(i);
  i++;
  break;
} // OUTPUT -> 0
while(j < 5){
  System.out.print(j);
  j++;
  continue;
} // OUTPUT -> 01234
```
### break and continue with labelled statements
```java
String[] birds = {"duck", "duck", "duck", "goose", "duck", "duck", "duck"};
lbl: for(String bird : birds){
  if(bird.equals("duck")){
	System.out.println(bird);
	continue lbl;
  }
  System.out.println(bird + "!");
  break lbl;
} // OUTPUT -> duck duck duck goose!
```
### Advanced control flow usage
| Allows   | Optional labels | break statement | continue statement |
| ------   | :-------------: | :-------------: | :----------------: |
| if       | yes             | no              | no                 |
| while    | yes             | yes             | yes                |
| do-while | yes             | yes             | yes                |
| for      | yes             | yes             | yes                |
| switch   | yes             | yes             | no                |
### Extra things
* remember, numeric promotion also occurs when checking equality `==`
* can't compare boolean to other primitives
* can compare char to numeric types (although output will be false)
```java
int x = 1;
double y = 2;
boolean z = false;
x == y; // compiles -> int promotes to double -> true
x == z; // DOES NOT COMPILE -> boolean not numeric
byte b = 1;
char c = '1';
b == x; // compiles -> false
```
## Chapter 3 - Java Core APIs
**In this chapter:**

---
[Strings](#strings)

[String Methods](#string-methods)

[StringBuilder](#stringbuilder)

[StringBuilder Methods](#stringBuilder-methods)

[Understanding Equality](#understanding-equality)

[Arrays](#arrays)

[Array Methods](#array-methods)

[varargs](#varargs)

[Multidimensional Array](#multidimensional-array)

[ArrayList](#arraylist)

[ArrayList Methods](#arraylist-methods)

[Wrapper Classes and ArrayList](#wrapper-classes-and-arraylist)

[Autoboxing](#autoboxing)

[Converting between array and List](#converting-between-array-and-list)

[Dates and Times](#dates-and-times)

[Manipulating Dates and Times](#manipulating-dates-and-times)

[Periods](#periods)

[Formatting Dates and Times](#formatting-dates-and-times)

[Parsing dates and Times](#parsing-dates-and-times)

---
### Strings
* concatenation
```java
System.out.println("hello" + 1 + 2); // -> hello12
System.out.println(1 + 2 + "hello"); // -> 3hello
int three = 3;
String four = "4";
System.out.println(1 + 2 + three  + four); // -> 64
```
* Strings are *immutable*, and they are **final** -> the String class can't be extended
* **String pool**
  - string literals are stored in the string pool
  - they are not garbage collected
### String Methods
* `length()` -> `"Hello".length()` -> `5`
* `charAt(int index)` -> `"Hello".charAt(4);` -> `o`
* indexOf
  - `indexOf(char ch)`
  - `indexOf(String str)`
  - `indexOf(char ch, index fromIndex)`
  - `indexOf(String str, index fromIndex)`
```java
"Hello".indexOf('o'); // -> 4
"HelloHello".indexOf('o', 5); // -> 9
"Hello".indexOf("ell"); // -> 1
"Hello".indexOf('s'); // -> -1
```
* substring
  - `substring(int beginIndex)` -> `"Hello".substring(1);` -> `ello`
  - `substring(int beginIndex, int endIndex)` -> `"s".substring(0,1);` -> `s` (**n.b. endIndex not included**)
* `equals(String str)` -> `"Hello".equals("Hello");` -> `true` (*overriden equals, compares characters*)
* `equalsIgnoreCase(String str)` -> *case-sensitive equals*
* `startsWith(String str)` -> `"Hello".startsWith("H");` -> `true`
* `endsWith(String str)` -> `"Hello".endsWith("H");` -> `false`
* `contains(String str)` -> `"Hello".contains("Hell");` -> `true`
* replace
  - `replace(char oldChar, char newChar)` -> `"Hello".replace('H', 'J');` -> `Jello`
  - `replace(String oldStr, String newStr)` -> `"Hello".replace("lo", "icopter");` -> `Helicopter`
* `trim()` -> `"\t  a b c \n".trim();` -> `a b c` (*trims trailing whitespace, tab and newline*)
#### Chaining methods
* you can chain as many methods as you want - each method creates a new string object
* remember, strings are immutable so the original object isn't affected
```java
String s = "Hello"
s.replace("lo", "icopter").substring(3, 5) + "e"; // -> ice
s; // -> Hello
```
### StringBuilder
* StringBuilder objects are mutable
```java
StringBuilder sb = new StringBuilder("Hello");
sb.append("World"); // HelloWorld
```
* mutates the original object and returns it - String just returns a new object
* signatures:
  - `new StringBuilder()` -> *empty StringBuilder*
  - `new StringBuilder(String str)` -> *creates with that value*
  - `new StringBuilder(int size)` -> *reserves slots for characters, but not fixed size (as is mutable)*
### StringBuilder Methods
* **StringBuilder is final, and therefore can't be extended**
* charAt, indexOf, length methods all identical to String methods
* substring() - also identical to String method, and **returns new String object**, not mutated StringBuilder
```java
StringBuilder sb = new StringBuilder("HelloWorld");
sb.substring(2, 6); // lloW
sb; // HelloWorld
```
* `toString()` -> `new StringBuilder("Hello").toString();` -> `Hello` **return String object**
#### the return type of all the following methods is StringBuilder
* `append(Object o)` -> *takes different data types e.g. String, boolean*
```java
StringBuilder sb = new StringBuilder();
sb.append(1).append(1 < 0).append('c');
System.out.print(sb); // -> 1falsec
```
* `insert(int offset, String str)` -> `new StringBuilder("Hello").insert(5, "World");` -> `HelloWorld` (**offset can be greater than index**)
* `delete(int start, int end)` -> `new StringBuilder("Hello").delete(1, 5);` -> `H`
* `deleteCharAt(int index)` -> `new StringBuilder("Hello").deleteCharAt(0);` -> `ello`
* `reverse()` -> `new StringBuilder("Hello").reverse();` -> `olleH`
#### StringBuffer
* StringBuffer is an older, thread-safe (therefore, less efficient) version of StringBuilder which has the same methods
* **StringBuffer is final, and therefore can't be extended**
### Understanding Equality
```java
StringBuilder sb1 = new StringBuilder();
StringBuilder sb2 = sb1.append("a"); // both now point to same object
System.out.println(sb1 == sb2); // true
sb1.append("bcdef");
System.out.println(sb1 == sb2); // true - both still point at same object
```
#### String Equality
* `equals(String str)` compares logically - do both strings contain exactly the same characters
* `==` compares the objects themselves - do the references point to the same object
```java
String x = "Hello World";
String y = "Hello World";
String z = new String("Hello World");
System.out.println(x == y); // true -> string literals, both at the same location in string pool
System.out.println(x == z); // false -> one in stringpool, one a new string object
System.out.println(x.equals(y)); // true
System.out.println(x.equals(z));  // true

System.out.println(x == "Hello World ".trim()); // two different literals - as they aren't the same at compile time, a new string object is created
```
#### StringBuilder equality
* StringBuilder doesn't have an overridden `equals()`
```java
StringBuilder sb1 = new StringBuilder("abc");
StringBuilder sb2  = new StringBuilder("abc");
System.out.println(sb1 == sb2); // false
System.out.println(sb1.equals(sb2)); // false
System.out.println(sb1.toString().equals(sb2.toString())); // true
```
### Arrays
* Primitive arrays
```java
int[] myArray = new int[3];
int anotherArray[] = new int[3];
int[] anonymousArray = new int[] {1, 2, 3, 4};
int[] shorthandAnonymousArray = {1, 2, 3, 4};
// anonymous arrays have to be declared and initialized on the same line
```
* multiple declarations
```java
int[] ids, numbers; // declares two int[] objects
ids = new int[3];
numbers = new int[3];
```
```java
int ids[], numbers; // declares one int[] object, and one int
ids = new int[3];
numbers = new int[3]; // COMPILE ERROR: incompatible types: int[] cannot be converted to int
```
* an array of primitives is an object
* it can cast (and automatically promote)
* watch out for runtime errors when putting subtypes in a supertype array
```java
String[] strings = {"string"};
Object[] objects = strings;
String[] backToStrings = (String[])objects;
strings[0] = "new string";
strings[0] = new StringBuilder("new string"); // DOES NOT COMPILE - only allows string objects
objects[0] = new StringBuilder("new string"); // compiles but throws RuntimeException java.lang.ArrayStoreException: java.lang.StringBuilder
```
* (ArrayStoreException in Java occurs whenever an attempt is made to store the wrong type of object into an array of objects)
```java
Number[] numbers = new Double[1];
numbers[0] = new Integer(4); // throw java.lang.ArrayStoreException: java.lang.Integer
```
### Array Methods
* `equals(type[] array)` - not overridden so looks for reference equality
```java
int[] arr = {1, 2, 3};
int[] arr2 = {1, 2, 3};
System.out.println(arr.equals(arr2)); // false
```
* `Arrays.binarySearch(type[] array, Type key)`-> *static method of `java.util.Arrays`*
* must be used on a sorted array
```java
import java.util.*;
public class Main {
	public static void main(String... args) {    
		int[] nums = {4, 6, 8, 2};
		Arrays.sort(nums);
		Arrays.binarySearch(nums, 2); // -> 0 -> index of 2
		Arrays.binarySearch(nums, 5); // -> negative (index + 1) of where it would go
  }
}
```
* *remember for the exam, if line starts after 1 or is a snippet, assume imports are present*
* *if the array is unsorted, the result is unpredictable lok for answer like 'undefined' or 'unpredictable'*
### varargs
* you can use a variable created with varagrs as if it were a normal array
```java
public static void main(String[] args){}
public static void main(String args[]){}
public static void main(String... args){} // varargs
```
* varargs also takes a comma-separated parameter list
```java
static void foo(int... args){
	for(int i = 0; i < args.length; i++){
		System.out.print(args[i]);
	}
}
foo(1, 2, 3, 4, 5); // -> 12345
```
### Multidimensional Array
* creating:
```java
// three ways of creating a 2d array:
int[][] multiDim1;
int multiDim2[][];
int[] multiDim3[];

int[] vars, vars2[], vars3[][]; // 1d, 2d and 3d array
```
* the `[]`s after the type are appended to each variable in the declaration statement
* specify size inline optionally - *n.b. the first array must always specify size*
```java
int[][] nums = new int[3][2]; // an array containing three arrays containing two ints
int[][] nums2 = new int[3][]; // an array containing three arrays of indeterminate size
int[][] nums3 = new int[][]; // DOES NOT COMPILE
```
* Asymetrical array
```java
// an array containing three arrays containing 2 ints, 1 int and 3 ints
int[][] nums = { {1, 4}, {3},  {9, 8, 7} };
```
 * or the longer way:
 ```java
 int[][] nums = new int[3][];
 nums[0] = new int[2];
 nums[1] = new int[1];
 nums[2] = new int[2];
 ```
 * Looping
 ```java
 int[][] twoD = { {2, 4, 6}, {5, 3, 1} };
 for(int i = 0; i < twoD.length; i++){
	for(int j = 0; j < twoD[i].length; j++){
		System.out.print(twoD[i][j]); // 246531
	}
}
// or
for(int[] inner : twoD){
  for(int i : inner){
	System.out.print(i); // 246531
  }
}
```
### ArrayList
`java.util.ArrayList`
* mutable object, no fixed size
* creating:
```java
// before Java 5 and generics:
ArrayList arrList1 = new ArrayList();
ArrayList arrList2 = new ArrayList(10); // reserves slots for characters, but not fixed size (as is mutable) c.f. StringBuilder
ArrayList arrList3 = new ArrayList(arrList2);
// post-Java 5, using generics:
ArrayList<String> arrList4 = new ArrayList<String>();
// post-Java 7:
ArrayList<string> arrList5 = new ArrayList<>();
```
### ArrayList Methods
* add
  - `add(E element)`
  - `add(int index, E element)`
 (*E is an object of the ArrayList's type or a type that it extends/implements - if not specified, implicitly it is the ArrayList's type*)
 *argument must match type*
 ```java
 ArrayList aList1 = new ArrayList(); // implicitly new ArrayList<Object>()
 aList1.add("foo");
 aList1.add(Boolean.TRUE);
 
 ArrayList<String> aList2 = new ArrayList<>();
 aList2.add("foo");
 aList2.add(Boolean.TRUE); // DOES NOT COMPILE -> incompatible types: Boolean cannot beconverted to String
```
* `remove(Object obj)` -> `boolean`
```java
ArrayList<String> arrList = new ArrayList<>();
arrList.add("One");
arrList.add("Two");
arrList.add("Three");
System.out.println(arrList.remove("Three")); // true -> removes object so no longer in ArrayList
System.out.println(arrList.remove("Three")); // false
```* `remove(int index)` -> `object` or `IndexOutOfBoundsException`
```java
ArrayList<String> arrList = new ArrayList<>();
arrList.add("One");
arrList.add("Two");
arrList.add("Three");
System.out.println(arrList.remove(2)); // Three -> removes element, so indices change
System.out.println(arrList.remove(2)); // throws IndexOutOfBoundsException
```
* `set(int index, E newElement)` -> invalid index throws `IndexOutOfBoundsException`
```java
ArrayList<String> arrList = new ArrayList<>();
arrList.add("One");
arrList.add("Two");
System.out.println(arrList.toString()); // [One, Two]
arrList.set(0, "Foo");
arrList.set(1, "Bar");
System.out.println(arrList.toString()); // [Foo, Bar]
```
* `get(int index)` -> *opposite of remove, doesn't mutate ArrayList*
* `isEmpty()` -> `boolean`
* `size()` -> `int`
* `clear()` -> `void` *removes all elements from ArrayList*
* `contains(Object obj)` -> `boolean` *uses equals() method of type (so String overrides)*
* `equals(Object o)` -> `boolean` *compares the specified object with this list for equality*
* `java.util.Collections.sort(List l)`
```java
List<Integer> list = new ArrayList<>();
list.add(10);
list.add(5);
list.add(1);
System.out.println(list.toString()); // -> [10, 5, 1]
Collections.sort(list);
System.out.println(list.toString()); // -> [1, 5, 10]
```
### Wrapper Classes and ArrayList
* **Wrapper classes are final, and therefore can't be extended**
* `parseInt(String str)` -> `int` *String to primitive*
* `valueOf(String s)` -> `Integer` *String to wrapper class*
```java
int primitive = Integer.parseInt("123");
Integer wrapperClass = Integer.valueOf("123");
```
| WrapperClass   | String -> Primitive          | String -> Wrapper Class  |
| ------------   | ---------------------------- | ------------------------ |
| Boolean        | Boolean.parseBoolean("tRue") | Boolean.valueOf("TrUe")  |
| Byte           | Byte.parseByte("123")        | Byte.valueOf("123")      |
| Short          | Short.parseShort("123")      | Short.valueOf("123")     |
| Integer        | Integer.parseInt("123")      | Integer.valueOf("123")   |
| Long           | Long.parseLong("123")        | Long.valueOf("123")      |
| Float          | Float.parseFloat("123.23")   | Float.("123.23")         |
| Double         | Double.parseDouble("123.23") | Double.valueOf("123.23") |
| Character      | *n/a*                        | *n/a*                    |
* must be valid for type:
```java
int i = Integer.parseInt("a"); //throws java.lang.NumberFormatException
int j = Integer.parseInt("1.1"); //throws java.lang.NumberFormatException
```
* promotion applies for primitives, but not wrapper classes:
```java
double primitive = Integer.parseInt("1");
Double wrapperClass = Integer.parseInt("1"); // DOES NOT COMPILE -> incompatible types: int cannot be converted to Double
```
* however, if wrapper class and primitive match, will autobox/unbox:
```java
double primitive = Double.valueOf("1");
Double wrapperClass = Double.parseDouble("1");
```
#### Boolean members
* Constructors:
  - `Boolean(boolean value)`
  - `Boolean(String s)`
* Fields
  - `static Boolean`	`FALSE`
  - `static Boolean`	`TRUE`
* Methods
  - `static boolean`	`parseBoolean(String s)`
  - `static Boolean`	`valueOf(boolean b)`
  - `static Boolean`	`valueOf(String s)`
### Autoboxing
```java
List<Double> doubleList = new ArrayList<>();
doubleList.add(20.6); // double literal autoboxes to Double
```
* careful with autoboxing null:
```java

List<Double> doubleList = new ArrayList<>();
doubleList.add(null); // legal -> Double is an object, so can be null
double d = doubleList.remove(0); // NullPointerException
// equivalent of saying double d = null.unbox() -> NullPointerException
// n.b. unbox() isn't a real method
```
* careful unboxing into Integer and other numerics when using remove:
```java
List<Integer> integerList = new ArrayList<>();
integerList.add(1);
integerList.add(2);
integerList.remove(1);
System.out.print(integerList.toString()); // [1]
```
* remember, remove() takes either an object or an integer as an argument, and the above interprets it as an index to remove
* to explicitly remove the object:
```java
List<Integer> integerList = new ArrayList<>();
integerList.add(1);
integerList.add(2);
integerList.remove(new Integer(1)); // or integerList.remove(Integer.valueOf("1");
System.out.print(integerList.toString()); // [2]
```
### Converting between array and List
#### List to array
* `toArray()` -> `Object[]`
* `toArray(T[] a)` -> `<T> T[]` - > *returns an array of type T*
Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array.
```java
List<String> arrList = new ArrayList<>();
arrList.add("one");
arrList.add("two");
Object[] objectArray = arrList.toArray();
String[] stringArray = arrList.toArray(); // DOES NOT COMPILE -> incompatible types: Object[] cannot beconverted to String[]
String[] stringArray = arrList.toArray(new String[]); // DOES NOT COMPILE -> array dimension missing
String[] stringArray = arrList.toArray(new String[0]);
```
* remember, if no argument specified, default behaviour is an array of objects
* specifiying size of array
  - 0 means Java will use correct array size
  - using the correct size also will
  - using a larger number will return an array of that size with the elements from ArrayList at the start
  - using a smaller number will create a new array of the correct size
```java
List<String> arrList = new ArrayList<>();
arrList.add("one");
arrList.add("two");
String[] stringArray = arrList.toArray(new String[3]);
for(String el : stringArray){
  System.out.print(el + " "); // one two null
}
```
#### array to List
* this list will be of a fixed size - add or remove will throw UnsupportedOperationException
```java
String[] stringArray = {"One", "Two"};
List<String> stringList = Arrays.asList(stringArray);
System.out.println(stringList.toString());
stringList.set(1, "Three");
System.out.println(stringList.toString());
stringList.add("Four"); // throws UnsupportedOperationException
stringList.remove(1); // throws UnsupportedOperationException
```
* you can convert it to a new ArrayList to make it a mutable object
```java
String[] stringArray = {"One", "Two"};
List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));
stringList.add("Four");
stringList.remove(1);
System.out.print(stringList.toString()); // -> [One, Four]
```
### Dates and Times
** all immutable, all have no public constructor**
* `java.time.LocalDate`
* `java.time.LocalTime`
* `java.time.LocalDateTime`
* `LocalDate`, `LocalTime`, and `LocalDateTime` implement `TemporalAccessor`
* `LocalDate`, `LocalTime`, and `LocalDateTime` classes do not have any parent/child relationship among themselves

#### Signatures
* `public static LocalDate of(int year, int month, int dayOfMonth)` -> `LocalDate ld = LocalDate.of(2020, 2, 12);`
* `public static LocalDate of(int year, Month month, int dayOfMonth)` -> `LocalDate ld = LocalDate.of(2020, Month.FEBRUARY, 12);`
* `public static LocalTime of(int hour, int minute)` -> `LocalTime lt = LocalTime.of(14, 30);`
* `public static LocalTime of(int hour, int minute, int second)` -> `LocalTime lt = LocalTime.of(14, 30, 5);`
* `public static LocalTime of(int hour, int minute, int second, int nanos)` -> `LocalTime lt = LocalTime.of(14, 30, 5, 444);` *(valid values 0 - 999999999)*
* public static LocalDateTime of
  - `of(int year, int month, int dayOfMonth, int hour, int minute)`
  - `of(int year, int month, int dayOfMonth, int hour, int minute, int second)`
  - `of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond)`
  - `of(int year, Month month, int dayOfMonth, int hour, int minute)`
  - `of(int year, Month month, int dayOfMonth, int hour, int minute, int second)`
  - `of(int year, Month month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond)`
  - `of(LocalDate date, LocalTime time)`
```java
LocalDate ld = LocalDate.of(2020, 2, 12);
LocalTime lt = LocalTime.of(21, 31);
LocalDateTime ldt1 = LocalDateTime.of(2020, 2, 12, 21, 31);
LocalDateTime ldt2 = LocalDateTime.of(ld, lt);
LocalDateTime ldt3 = LocalDateTime.of(ld, 21, 31); // DOES NOT COMPILE
LocalDateTime ldt4 = LocalDateTime.of(2020, 2, 12, lt); // DOES NOT COMPILE
```
* these methods are static and must be called statically:
```java
LocalDate ld = new LocalDate(2020, 2, 12); // DOES NOT COMPILE
```
* using invalid arguments will throw a runtime error:
```java
LocalDate ld = LocalDate.of(1989, 1, 32); // throws java.time.DateTimeException: Invalid value for DayOfMonth (valid values 1 - 28/31): 32
```
### Manipulating Dates and Times
```java
LocalDate date = LocalDate.of(2020, 2, 12);
date = date.plusDays(14);
System.out.println(date); // 2020-06-22
date.plusDays(100);
System.out.println(date);  // still 2020-06-22 -> immutable
```
* methods available to LocalDate and LocalDateTime
  - plusYears/minusYears
  - plusMonths/minusMonths
  - plusWeeks/minusWeeks
  - plusDays/minusDays
  - `plus(TemporalAmount amountToAdd)`

* methods available to LocalTime and LocalDateTime
  - plusHours/minusHours
  - plusMinutes/minusMinutes
  - plusSeconds/minusSeconds
  - plusNanos/minusNanos
  - `plus(TemporalAmount amountToAdd)`
* these methods can be chained
* won't compile if methods belonging to wrong object used
```java
LocalDate date = LocalDate.of(2020, 2, 12);
date = date.plusDays(1).plusWeeks(1);
System.out.println(date); // 2020-02-20
date = date.plusDays(1).plusHours(2); // DOES NOT COMPILE
```
### Periods
`java.time.Period` *implements TemporalAmount*
* immutable
* `static Period	of(int years, int months, int days)`
* `static Period	ofDays(int days)`
* `static Period	ofMonths(int months)`
* `static Period	ofWeeks(int weeks)`
* `static Period	ofYears(int years)`
```java
import java.time.*;
public class PeriodTester {
	public static void main(String... args) {  
		LocalDate ld = LocalDate.of(2020, Month.FEBRUARY, 12);
		printFutureTime(ld, Period.of(1, 1, 1)); // Future date is 2021-03-13
		printFutureTime(ld, Period.ofDays(8)); // Future date is 2020-02-20
		printFutureTime(ld, Period.ofWeeks(5)); // Future date is 2020-03-18
		printFutureTime(ld, Period.ofMonths(6)); // Future date is 2020-08-12
		printFutureTime(ld, Period.ofYears(1)); // Future date is 2021-02-12
	}
	public static void printFutureTime(LocalDate date, Period period){
		System.out.println("Future date is " + date.plus(period));
	}
}
```
* its static methods (like all static methods) can't be chained (*runs but will only process last method*)
```java
LocalDate ld = LocalDate.of(2020, 2, 12);
Period p = Period.ofWeeks(1).ofDays(1); // only processes last method
System.out.println(ld.plus(p)); // 2020-02-13
```
* can't be used with DateTime -> will throw a runtime exception -> use `Duration` instead (also implements *TemporalAmount*)

### Formatting Dates and Times
`java.time.format.DateTimeFormatter`
* `static DateTimeFormatter	ofLocalizedDate(FormatStyle dateStyle)`
* `static DateTimeFormatter	ofLocalizedTime(FormatStyle timeStyle)`
* `static DateTimeFormatter	ofLocalizedDateTime(FormatStyle dateTimeStyle)`
* `static DateTimeFormatter	ofLocalizedDateTime(FormatStyle dateStyle, FormatStyle timeStyle)`
```java
DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT);
```
* `DateTimeFormatter.format(format(TemporalAccessor temporal))` -> String
* `LocalDate.format(DateTimeFormatter formatter)` -> String
* `LocalTime.format(DateTimeFormatter formatter)` -> String
* `LocalDateTime.format(DateTimeFormatter formatter)` -> String
* ofLocalizedDate can format LocalDate and LocalDateTime
* ofLocalizedTime can format LocalTime and LocalDateTime
* ofLocalizedDateTime can format LocalDateTime
* anything else throws java.time.temporal.UnsupportedTemporalTypeException
```java
LocalDate date = LocalDate.of(2020, 2, 12);
LocalTime time = LocalTime.of(21, 31);
LocalDateTime dateTime = LocalDateTime.of(date, time);
	
dateFormatter.format(date);
dateFormatter.format(dateTime);
    
timeFormatter.format(time);
timeFormatter.format(dateTime);

dateTimeFormatter.format(dateTime);
```
* `DateTimeFormatter.ofPattern(String pattern)`
* creating a pattern:
  - `M/MM/MMM/MMMM` -> e.g. 1/01/Jan/January
  - `d/dd` -> e.g. 2/02 or 20/02 -> *dd adds leading 0 if single digit*
  - `H/HH` -> 24 hour/with leading 0
  - `h/hh` -> 12 hour/with leading 0
  - `mm` -> minute
```java
LocalDate date = LocalDate.of(2020, 2, 12);
LocalTime time = LocalTime.of(21, 31);
LocalDateTime dateTime = LocalDateTime.of(date, time);

DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, yyyy, HH:mm")
dtf.format(dateTime); // -> February 12, 2020, 21:31
dtf.format(date); // -> throws UnsupportedTemporalTypeException
```
* ofPattern matches exactly, so if the String contains a time field it can't format a LocalDate
* if the String contains a date field it can't format a LocalTime
* however if the String only contains a date field, it can format a DateTime (it will just ignore the time), and the same for a time field
```java
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
dtf.format(date); // -> February 12, 2020
dtf.format(dateTime); // -> February 12, 2020
```
### Parsing Dates and Times
```java
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM dd yyyy");
LocalDate date = LocalDate.parse("00 02 2015", dtf);
LocalDateTime dateTime = LocalDateTime.parse("02 12 2020", dtf); // -> throws java.time.format.DateTimeParseException:

DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
LocalDateTime dateTime = LocalDateTime.parse("02 12 2020 21:31", dtf2);
LocalDate date = LocalDate.parse("02 12 2020 21:31", dtf2);
```
* the class that `parse()` uses must match the type being created
* if the type contains time fields, the string must be able to handle them e.g. "h:mm" or "M D yy HH:mm"
* if the type contains date fields, the string must have them e.g. "MMMM d yyyy"
## Chapter 4 - Methods and Encapsulation
**In this chapter:**

---
[Anatomy of a Method](#anatomy-of-a-method)

[More on varargs](#more-on-varargs)

[Applying Access Modifiers](#applying-access-modifiers)

[Designing Static Methods and Fields](#designing-static-methods-and-fields)

[Static vs Instance](#static-vs-instance)

[Static variables](#static-variables)

[Static initialization](#static-initialization)

[Static imports](#static-imports)

[Pass by Value](#pass-by-value)

[Overloading Methods](#overloading-methods)

[Overloading and Varargs](#overloading-and-varargs)

[Matching Overloaded Methods](#matching-overloaded-methods)

[Creating constructors](#creating-constructors)

[Default constructor](#default-constructor)

[Overloading constructors](#overloading-constructors)

[Final fields](#final-fields)

[Order of Initialization](#orderoof-initialization)

[Encapsulation](#encapsulation)

[Creating Immutable Classes](#creating-immutable-classes)

[Lambdas](#lambdas)

[Predicates](#predicates)

[More on lambdas and predicates](#more-on-lambdas-and-predicates)

---
### Anatomy of a Method
* `access modifier` `optional specifiers` `return type` `methodName` `(` *required parentheses* `optional parameter list` `)` `optional exception list` `{`*requierd braces*`}`
* `public` `final` `void` `foo` `(` `int age, String name` `)` `throws Interrupeted Exception` `{` `}`
### Access Modifiers
* `public` -> can be called from any class -> `public void foo()`
* `private` -> can only be called from within the same class -> `private void foo()`
* `protected` -> can only be called from classes in the package or child classes -> `protected void foo()`
* `Default (package private) Access` -> can only be called from classes within the same package -> `void foo()`
* **Default doesn't have a keyword - it is denoted by its absence**
#### Optional Specifiers
* `static`
* `final`
* `abstract`
* `synchronized` (**not on exam**)
* `native` (**not on exam**)
* `strictfp` (**not on exam**)
* can have multiple specifiers (or one, or none)
* must be before return type
* can be before access modifier
#### Return Type
* method signature must contain return type
* must contain a return statement matching return type that is reached by all branches
* if void, return statement is optional; either omit or return with no value
#### Method Name
* must be a [valid identifier](#identifiers)
#### Parameter List
* must have parentheses
* list can be comma-separated parameters or varargs - or both ([see below](#more-on-varargs))
#### Optional Exception List
* `public void foo() throws AnException, ASecondException, AnotherException {}`
* **NOT** `public void foo() throws AnException, throws ASecondException, throws AnotherException {}`
#### Method Body
* curly braces required (except interfaces and abstract objects)
### More on varargs
* must be the last element in a parameter list
* only one varargs per method signature
```java
public void foo(Date birthday, boolean important, String... people){};
public void foo(String... names, int... ages){}; // DOES NOT COMPILE
public void foo(Date birthday, String... people, boolean important){}; // DOES NOT COMPILE
```
* pass in either an array or the elements separated by comma
```java
public static void foo(int arg1, int... additionalArgs{}
public static void main(String... args){
	foo(5); // passes empty array as second (varargs) argument
	foo(5, 7, 8, 9);
	foo(5, new int[]{7, 8, 9};
	foo(5, null); // legal but could throw exception later
}
```
* uses same indexing as arrays
### Applying Access Modifiers
* access modifiers in order of restrictiveness (most to least):

| Access Modifier          | Accessibility                                   |
| ------------------------ | ----------------------------------------------- |
| `private`                | within same class                               |
| default (package-private | within same class or same package               |
| `protected`              | within same class or same package or subclasses |
| `public`                 | available to all classes                        |
#### Private
```java
// Cat.java
public class Cat{
	private int age = 5;
	public static void main(String... args){
		Cat cat = new Cat();
		System.out.println("Cat age is " + cat.age); // -> Cat age is 5
		System.out.println(cat.catYears()); // -> 35 in cat years
	}
	private String catYears(){
		int catYears = age * 7;
		return catYears + " in cat years";
	}
}
class CatTester extends Cat{
  public static void main(String... args){
      Cat cat = new Cat();
      System.out.println("Cat age is " + cat.age); // DOES NOT COMPILE
      System.out.println(cat.catYears()); // DOES NOT COMPILE
    }
}
```
#### Default
```java
// cat/Cat.java
package cat;

public class Cat{
	int age = 5;
	String catYears(){
		int catYears = age * 7;
		return catYears + " in cat years";
	}
}
class CatTester extends Cat{
  public static void main(String... args){
      Cat cat = new Cat();
      System.out.println("Cat age is " + cat.age); // -> Cat age is 5
      System.out.println(cat.catYears()); // -> 35 in cat years
    }
}

// catTester/CatTester.java
package catTester;
import cat.Cat;

public class CatTester extends Cat{
  public static void main(String... args){
      Cat cat = new Cat();
      System.out.println("Cat age is " + cat.age); // DOES NOT COMPILE -> age is not public in Cat; cannot be accessed from outside package
      System.out.println(cat.catYears()); // DOES NOT COMPILE -> catYears() is not public in Cat; cannot be accessed from outside package
    }
}
```
#### Protected
* subclasses can call protected methods *provided the reference type is the subclass, not the superclass*
```java
// cat/Cat.java
package cat;

public class Cat{
	protected int age = 5;
	protected String catYears(){
		int catYears = age * 7;
		return catYears + " in cat years";
	}
}

// catTester/CatTester.java
package catTester;
import cat.Cat;

public class CatTester extends Cat{
  public static void main(String... args){
      CatTester catTester = new CatTester();
      System.out.println("Cat age is " + catTester.age); // -> Cat age is 5
      System.out.println(catTester.catYears()); // -> 35 in cat years
	  
	  Cat cat = new CatTester();
      System.out.println("Cat age is " + cat.age); //DOES NOT COMPILE -> called by superclass
      System.out.println(cat.catYears()); // DOES NOT COMPILE
    }
}
```
#### Public
* anything goes
```java
// cat/Cat.java
package cat;

public class Cat{
	public int age = 5;
	public String catYears(){
		int catYears = age * 7;
		return catYears + " in cat years";
	}
}

// catTester/CatTester.java
package catTester;
import cat.Cat;

public class CatTester extends Cat{
  public static void main(String... args){
      Cat cat = new CatTester();
      System.out.println("Cat age is " + cat.age); // -> Cat age is 5
      System.out.println(cat.catYears()); // -> 35 in cat years
    }
}
```
### Designing Static Methods and Fields
* in addition to main(), static methods have two main purposes:
  - utility or helper methods that don't require object state
  - for state that is shared by all instances of a class, e.g. a counter
* we can call `main()` like any other static method:
```java
public class Test {
	public static void main(String... args){}
}
class Tester {
	public static void main(String... args){
		Test.main();
		Test.main("bla", "bla");
		Test.main(new String[0]);
	}
}
```
* we can use an instance of an object to call a static method or variable:
```java
public class Test {
	String testString = "test string";
	public static void main(String... args){}
}
class Tester {
	public static void main(String... args){
		Test test = new Test();

		test.main();
		Test.main();

		System.out.println(Test.testString);
		System.out.println(test.testString);
		
		test = null;
		System.out.println(test.testString);
		
		Test test2 = null;
		System.out.println(test2.testString);
		
	}
}
```
* even though test and test2 are null, it still has a reference type of Test
* as it is using the reference rather than the instance to call the static method, we don't get a NullPointerException
### Static vs Instance
* a static member cannot call an instance member
```java
public class StaticVsInstance {
	public static String stringOne = "One";
	public String stringTwo = "Two";
	public static void one(){
		System.out.println("Called one()");
	}
	public void two(){
		System.out.println("Called two()");
	}
	public static void main(String... args){
		one();
		two();  // DOES NOT COMPILE -> error: non-static method two() cannot be referenced from a static context    two();
		
		System.out.println(stringOne);
		System.out.println(stringTwo);  // -> error: non-static variable stringTwo cannot be referenced from a static context    System.out.println(stringTwo);
	}
}
```
### Static variables
* constants
   - use the `final` modifier -> e.g. `public static final int MAX = 100;`
   - final variables can't be reassigned (=)
   - final variables whose type is mutable can have their values changed
```java
public class Constants {
	static final int MAX = 100;
	static final int[] MIN_MAX = {0, 100};
	
	static final String HELLO_STRING = "Hello";
	static final StringBuilder HELLO_BUILDER = new StringBuilder("Hello");
	public static void main(String... args){
		MAX = 5; // DOES NOT COMPILE -> cannot assign a value to final variable MAX
		MIN_MAX[0] = 5; // compiles - value changed but didn't reassign object
		MIN_MAX = new int[]{5, 50}; // DOES NOT COMPILE -> cannot assign a value to final variable MIN_MAX
		
		HELLO_STRING += "World"; // DOES NOT COMPILE -> cannot assign a value to final variable HELLO_STRING
		HELLO_BUILDER.append("World"); // -> value of HELLO_BUILDER is now "HelloWorld"
		HELLO_BUILDER = new StringBuilder("HelloWorld");  // DOES NOT COMPILE -> cannot assign a value to final variable HELLO_BUILDER
	}
}
```
### Static initialization
* `static{}`
* static initializers run when the class is first used
* remember, FINAL variables can't be reassigned
* but they can be declared in the class and then initialized in a static initializer block, as this will only be run once
```java
public class ConstantsAndStaticInitializers {
	public static final int one;
	public static final int two = 2;
    public static final int three; // DOES NOT COMPILE -> variable three not initialized in the default constructor
	static {
		one = 1;
	    two = 2; // DOES NOT COMPILE -> cannot assign a value to final variable two
	};	
}
```
### Static imports
* regular imports are for importing classes
* static imports are for importing static members of classes
```java
1. import java.util.List;
2. import static java.util.Arrays.asList;
3. public class StaticImports {
4. 	public static void main(String... args) {
5. 		List<String> list = asList("foo", "bar");
6. 		List<String> list2 = Arrays.asList("foo", "bar"); // DOES NOT COMPILE -> cannot find symbol		List<String> list2 = Arrays.asList("foo", "bar");
7. 	}
8. }
```
* line 6 doesn't compile because the Arrays class hasn't been imported
* local methods have precedence over static imports - if we created `asList()` in the class, that would be used:
```java
import static java.util.Arrays.asList;
public class StaticImportPrecedence {
	public static void main(String... args) {
		asList("foo", "bar");
	}
	
	public static void asList(String... args){
		for(String arg : args){
			System.out.print(arg); // -> foobar
		}
	}
}
```
* although they are called static imports, the syntax is `import static`
* `import static java,util.Arrays;` -> DOES NOT COMPILE -> class not static member
* you can use wildcards -> `import static java.util.Arrays.*;` -> imports all static members of Arrays statically
* can't import two static variables with the same name, or two static methods with the same signature:
```java
package statics;

public class A {
	public static final String TYPE = "A";
	public static final String DIFFERENT_SIGNATURE(String foo, String bar){
		return foo + bar;
	}
	public static final String SAME_SIGNATURE(String foo){
		return foo;
	}
}

package statics;

public class B {
	public static final String TYPE = "B";
	public static final String DIFFERENT_SIGNATURE(String bar){
		return bar;
	}
	public static final String SAME_SIGNATURE(String foo){
		return foo;
	}
}

import static statics.A.TYPE;
import static statics.B.TYPE;
import static statics.A.SAME_SIGNATURE;
import static statics.B.SAME_SIGNATURE;
import static statics.A.DIFFERENT_SIGNATURE;
import static statics.B.DIFFERENT_SIGNATURE;

public class StaticImportClashTest {
	public static void main(String... args) {
		System.out.println(TYPE); // DOES NOT COMPILE -> reference to TYPE is ambiguous
		//		System.out.println(TYPE);   
		// ^ both variable TYPE in A and variable TYPE in B match

		System.out.println(SAME_SIGNATURE("foo")); // DOES NOT COMPILE -> error: reference to SAME_SIGNATURE is ambiguous
		//		System.out.println(SAME_SIGNATURE("foo"));
		// ^ both method SAME_SIGNATURE(String) in B and method SAME_SIGNATURE(String) in A match
					
		System.out.println(DIFFERENT_SIGNATURE("foo")); // -> foo
		System.out.println(DIFFERENT_SIGNATURE("foo", "bar")); // -> foo\nbar
	}
}
```
### Pass by Value
> changes made to variables passed into methods don't persist outside those methods if they are assignments, but do if they are methods called on mutable variable types (*c.f. final members*)
```java
public class PassByValue {
	public static void main(String... args) {
		int i = 2;
		String str = "Foo";
		StringBuilder sb = new StringBuilder("Foo");
		
		change(i); // -> 2
		change(str); // -> Foo
		change(sb); // -> Foobar
		
		i = change(i); // -> 3
		str = change(str); // -> Foobar
		sb = change(sb); // -> Foobarbar
	}
	
	public static int change(int x){
		return x += 1;
	}
	public static String change(String x){
		return x += "bar";
	}
	public static StringBuilder change(StringBuilder x){
		return x.append("bar");
	}
}
```
* Java uses pass-by-value to get data into a method
* assigning a new primitive or reference to a parameter doesn't change the caller
* calling methods on a reference to an object does affect the caller
* careful - if the assigning happens *outside* of the method, the value will change as normal
### Overloading Methods
* change the parameters of a named method to overload it
* this includes changeing the order but not the variable names e.g.
```java
void foo(int i, long l){}
void foo(long l, int i){} // overloads foo
void foo(int blabla, long tactac){} // DOES NOT COMPILE -> same parameter list
```
* the return type isn't important
```java
String foo(int i){}
Boolean foo(int j){} // DOES NOT COMPILE -> same parameter list
```
* watch for case: the following are two different methods, not overloaded
```java
void foo(int i);
void Foo(int i);
```
* the signature is: the name, types and order of the parameters
### Overloading and Varargs
* Java treats varargs as an array so the following two signatures are identical:
```java
public void run(int[] distances){}
public void run(int... dists){} // DOES NOT COMPILE
```
* when compiled, they behave slightly differently:
```java
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
```
* a varargs parameter can accept an array or comma-separated values
* an array parameter can only accept an array
### Matching Overloaded Methods
* Java finds the most specific match it can, then autoboxes/converts
* exact match -> numeric promotion -> autoboxed wrapper -> superclasses -> varargs
* can only perform one conversion
  - Wrapper types cannot be widened to another wrapper type (e.g. a Byte cannot widen to an Integer).
  - Primitive types cannot be widened and then boxed (e.g. a byte cannot widen to an int and then be boxed as an Integer).
  - (*this is similar to how this won't compile without casting:* `Long x = 3; // DOES NOT COMPILE`)
* the following is the order in which an int would be matched:
```java
public class MatchingOverloadedMethods {
	public static void main(String... args){
		foo(7);
	}
	
	public static void foo(short x){ // WON'T MATCH THIS - can't demote to short
		System.out.println("short");
	}
	public static void foo(int x){
		System.out.println("int");
	}
	public static void foo(long x){
		System.out.println("long");
	}
	public static void foo(double x){
		System.out.println("double");
	}
	public static void foo(Integer x){
		System.out.println("Integer");
	}
	public static void foo(Long x){ // WON'T MATCH THIS - can't promote to long then autobox to Long
		System.out.println("Long"); // foo(7L); or foo((long)); would match
	}
	public static void foo(Number x){
		// System.out.println("Number");
	}
	public static void foo(Object x){
		System.out.println("Object");
	}
	public static void foo(int... x){
		System.out.println("int...");
	}
}
```
[http://how2examples.com/java/method-overloading](http://how2examples.com/java/method-overloading)
### Creating constructors
* a constructor is a method that matches the name of the class
* they are called when creating a new object - when Java sees the keyword `new`, it looking for a constructor and calls it
```java
public class Foo {
	public Foo(){} // this is a constructor
	public foo(){} // COMPILE ERROR - method name doesn't match class name so just a method without a return type, which is illegal
	public void Foo(){} // not a constructor - regular method with return type void
}
```
#### this
* if two variables have the same name (a parameter and an instance variable) in a constructor, Java gives precedence to the parameter
* use the `this` keyword to reference an instance variable in that context
```java
public class Foo {
	public int myNum;
	
	public static void main(String... args){
		Foo myFoo = new Foo(5);
	}
	
	public Foo(int myNum){
		// myNum = myNum; // -> sets parameter myNum to equal itself - won't persist outside method
		// myNum = this.myNum; // -> sets parameter myNum to equal instance variable myNum - won't persist outside method
		this.myNum = myNum; // -> sets instance variable myNum = parameter myNum
	}
}
```
### Default constructor
* if you provide **no** constructor, Java creates a default no args constructor
* **the access type of a default constructor is same as the access type of the class.** Thus, if a class is public, the default constructor will be public.
* having a private constructor prevents Java creating a default constructor, and other classes from instantiating the class
```java
class PrivateConstructor {
	private PrivateConstructor(){};
}
public class PrivateConstructorTest {
	public static void main(String... args) {
		PrivateConstructor foo = new PrivateConstructor(); // DOES NOT COMPILE -> PrivateConstructor() has private access in PrivateConstructor
	}
}
```
### Overloading constructors
* DRYs up code and adds degulat values to variables
* the first line of a constructor is always either:
  - a call to another constructor in the same class using this
  - a call to a constructor in the superclass
    - a implicit call to a constructor in the superclass (added by the compiler)
```java
public class OverloadingConstructors {
	public int x;
	public String y;
	public OverloadingConstructors(){
		this(7, "bla");
	}
	public OverloadingConstructors(int x){
		this(x, "bla");
	}
	public OverloadingConstructors(String y){
		this(7, y);
	}
	public OverloadingConstructors(int x, String y){
		this.x = x;
		this.y = y;
	}
	public static void main(String... args) {
		OverloadingConstructors oc1 = new OverloadingConstructors(); // 			x: 7, y: bla
		OverloadingConstructors oc2 = new OverloadingConstructors(1); // 			x: 1, y: bla
		OverloadingConstructors oc3 = new OverloadingConstructors("word"); // 		x: 7, y: word
		OverloadingConstructors oc4 = new OverloadingConstructors(3, "stuff"); // 	x: 3, y: stuff
}
```
* `this()` or a call to a constructor in the superclass **cannot** come after the first line:
```java
public class ConstructorAfterFirstLine {
	public ConstructorAfterFirstLine(int i){}
	public ConstructorAfterFirstLine(){
		System.out.print("starting");
		this(7); // DOES NOT COMPILE ->  error: call to this must be first statement in constructor
	}
}
```
### Final fields
* like final static variables, final instance variables must be assigned a value exaclty once
* this can be in the line of declaration, an initialization block, or in the constructor
```java
class Rectangle {
	private final double AREA;
	public Rectangle(double w, double h){
		AREA = w * h;
	}
	public String toString(){
		return "Area is " + AREA;
	}
}
public class FinalFieldsTester {
	public static void main(String... args){
		Rectangle r = new Rectangle(2.2, 4.5);
		System.out.println(r); // -> Area is 9.9
	}
}
```
* by the stime the constructor completes, all final instance variables must have been assigned
### Order of Initialization
1. initialize superclass if present
2. static members in order they appear (variables and initializers)
3. instnace members in order they appear (variables and initializers)
4. the constructor
```java
public class OrderOfInitialization {
	static void print(int num){
		System.out.print(num);
	}
	
	static{print(1);}
	
	public OrderOfInitialization(){
		print(5);
	}
	
	{print(3);}
	
	static{print(2);}
	
	{print(4);}
	
	public static void main(String... args){
		OrderOfInitialization foo = new OrderOfInitialization();
	}
}
// output: 12345
```
### Encapsulation
```java
class Encapsulation{
	private int num;
	public void setNum(int num){
		this.num = num;
	}
	public int getNum(){
		return num;
	}		
}
public class EncapsulationTester{
	public static void main(String... args){
		EncapsulationTest foo = new EncapsulationTest();
		
		foo.num = 5; // DOES NOT COMPILE ->  error: num has private access in EncapsulationTest
		foo.setNum(5);
				
		System.out.println(foo.num); // DOES NOT COMPILE ->  error: num has private access in EncapsulationTest
		System.out.println(foo.getNum()); // -> 5
	}
}
```
* JavaBeans rules
  - properties are private
  - getter and setter methods are public
  - getter prefix is `get` or `if` for boolean, `get` for everything else
  - setter prefix is `set`
  - followed by PascalCase identifier (the property name by convention)
```java
private int numberOfHats;
private boolean hat;
public int getNumberOfHats(){
	return numberOfHats;
}
public booealn isHat(){
	return hat;
}
public void setNumberOfHats(int num){
	numberOfHats = num;
}
public void setHat(boolean hat){
	this.hat = hat;
}
```
### Creating Immutable Classes
* omit the setters and initialize variables in the constructor
```java
public class Person {
	private String name;
	public String getName(){
		return name;
	}
	public Person(String name){
		this.name = name;
	}
}
```
* careful about the return type of variables - a mutable return type can have its value changed
* to overcome this, either return a copy of the object (a defensive copy) or an immutable object type
```java
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
		System.out.println(obj3.getName()); // -> Foo
	}
}
```
### Lambdas
* lambdas expression without optional parts:
  - `parameter` `arrow` `body`
  - `a ` `->` `a.isHat()`
* with optional parts:
  - `(` `optional parameter type` `parameter name` `)` `arrow` `{` `return keyword` `body` `;` `}`
  - `(Clothing a) -> {return a.isHat();}`
* rules for omitting lambda parts:
  1. parentheses can be omitted if there is only one parameter and the type is not explicitly stated
  2. curly braces `{}` can be omitted if there is only one statement
  3. the return keyword can be omitted if not using a code block `{}`
  4. semidcolon can be omitted if not using a code block `{}`
#### valid lambdas
* `() -> true; ` -> 0 parameters
* `a -> a.startsWith("foo")` -> 1 parameter
* `(String a) -> a.startsWith("foo")` -> 1 parameter, named type
* `(a, b) -> a.startsWith("foo")` -> 2 parameters
* `(String a, String b) -> a.startsWith("foo")` -> 2 parameters, named type
#### illegal examples
* `a, b -> a.startsWith("foo")` -> illegal - doesn't have only one parameter
* `a -> {a.startsWith("foo")}` -> illegal - curly braces with no return and no semicolon
* `a -> {a.startsWith("foo");}` -> illegal - curly braces with no return
* `a -> {return a.startsWith("foo")}` -> illegal - curly braces with no semicolon
#### redeclaring variables
* you can't redeclare variables in the code block, but you can change variables values and declare new variables:
`(a, b) -> {int a = 3; return 5;}` -> illegal - redeclares variable in code block
`(a, b) -> {a = 3; return 5;}` -> legal - reassigns variable in code block
`(a, b) -> {int c = 3; return 5;}` -> legal - declares new variable in code block
### Predicates
* lambdas work with interfaces that only have one method
* these are claled functional interfaces
* Java provides a general interface for this purpose: `java.util.function.Predicate`
```java
public interface Predicate<T>{
	boolean test(T t);
}
```
* example:
```java
import java.util.function.*;

public class PredicateExample{
	static Predicate<Integer> isEven = i -> i % 2 == 0;
	public static void main(String... args){
		int[] ints = {1, 2, 3, 4, 5};
		for(int i : ints){
			System.out.println(isEven.test(i));
		}
	}
}
/* output:
* false
* true
* false
* true
* false
*/
```
* ArrayList has a method, `removeIf()`, that takes a predicate (and can just pass in a lambda expression):
```java
import java.util.*;
import java.util.function.Predicate;

public class RemoveIfTest{
	static Predicate<Integer> isOdd = i -> i % 2 == 0;
	static Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	
	public static void main(String... args){
		List<Integer> nums = new ArrayList<>(Arrays.asList(ints));
		nums.removeIf(isOdd);
		System.out.println(nums); // ->[1, 3, 5, 7, 9]
		
		List<Integer> nums2 = new ArrayList<>(Arrays.asList(ints));
		nums2.removeIf(i -> i % 2 != 0);
		System.out.println(nums2); // -> [2, 4, 6, 8, 10]
	}	
}
```
### More on lambdas and predicates
* `java.util.function.Predicate`
```java
interface Predicate<T> {
	boolean test(T t);
}
```
* Predicate is an interface that has only one abstract method (among other non-abstract methods)
* its signature is `boolean test(T t)`
* where T is the type it receives
* as Predicate only has one <T> argument, it can only accept lambdas with one parameter
  - `Predicate<Integer> p = i -> i > 5;`
  - not `Predicate<Integer> p = (i ,j) -> i < 5 || j < 10;` // DOES NOT COMPILE -> `PredicateTester.java:5: error: incompatible types: incompatible parameter types in lambda expression`
* Unlike regular interfaces, Functional interfaces can be instantiated by a lambda expression, as shown above, as long as the lambda arguments match the interface arguments
## Chapter 5 - Class Design
**In this chapter:**

---
[Class Inheritance](#class-inheritance)

[Defining Constructors](#defining-constructors)

[Reviewing constructor rules](#reviewing-constructor-rules)

[Calling constructors](#calling-constructors)

[Calling inherited class members](#calling-inherited-class-members)

[Inheriting Methods](#inheriting-methods)

[Overriding a method](#overriding-a-method)

[Rules for overriding a method](#rules-for-overriding-a-method)

[Redeclaring Private Methods](#redeclaring-private-methods)

[Hiding Static Methods](#hiding-static-methods)

[Overriding vs Hiding Methods](#overriding-vs-hiding-methods)

[Creating final methods](#creating-final-methods)

[Inheriting variables](#inheriting-variables)

[Abstract Classes](#abstract-classes)

[Creating a concrete class](#creating-a-concrete-class)

[Abstract Class Definition Rules](#abstract-class-definition-rules)

[Abstract Method Definition Rules](#abstract-method-definition-rules)

[Implementing Interfaces](#implementing-interfaces)

[Defining an Interface](#defining-an-interface)

[Inheriting an Interface](#inheriting-an-interface)

[Classes, Interfaces and Keywords](#classes-interfaces-and-keywords)

[Abstract Methods and Multiple Inheritance](#abstract-methods-and-multiple-inheritance)

[Interface Variables](#interface-variables)

[Default Interface Methods](#default-interface-methods)

[Default Methods and Multiple Inheritance](#default-methods-and-multiple-inheritance)

[Static Interface Methods](#static-interface-methods)

[Understanding Polymorphism](#understanding-polymorphism)

[Object vs Reference](#object-vs-reference)

[Casting Objects](#casting-objects)

[Virtual Methods](#virtual-methods)

[Polymorphic Parameters](#polymorphic-parameters)

[More on hidden methods and variables](#more-on-hidden-methods-and-variables)

---

### Class Inheritance
* child class declaration structure:
  - `public/[default] access modifier` `abstract/final (optional)` `class keyword` `class name` `extends parent class` `{}`
  - `public` `abstract` `class` `Rectangle` `extends Shape` `{}`
* a top-level class (or interface) can be defined only by using the public or default access modifiers
* a class with default access can only be referenced by classes in the same package:
```java
// in source file privateAccess/PrivateClass.java
package privateAccess;

class PrivateClass {}
```
```java
// in source file publicAccess/PublicClass.java
package publicAccess;
import privateAccess.PrivateClass;

public class PublicClass {
	public static void main(){}
}
```
```bash
> javac publicAccess/PublicClass.java privateAccess/PrivateClass.java
publicAccess/PublicClass.java:2: error: PrivateClass is not public in privateAccess; cannot be accessed from outside package
import privateAccess.PrivateClass;

```

* final classes can't have any child classes
* a child class can access all public members of the parent class
* a child class can, through inheritance, access all protected  members of the parent class
* if it is in the same package can access all its protected members directly, otherwise it can only through the child class:
```java
// in source file packageOne/Parent.java
package packageOne;

public class Parent {
	protected String name = "Chris";
}
```
```java
// in source file packageTwo/Child.java
package packageTwo;
import packageOne.Parent;

public class Child extends Parent {
	public static void main(String[] args){
		Child c = new Child();
		System.out.println(c.name);
		
		Parent p = new Parent();
		System.out.println(p.name); // DOES NOT COMPILE
	}
}
```
```bash
> javac packageOne/Parent.java packageTwo/Child.java
packageTwo/Child.java:11: error: name has protected access in Parent
		System.out.println(p.name);
```

* a child class in a different package to the parent class won't compile unless it imports it:
```java
// parents/Parent.java
package parents;
public class Parent{}

// children/Child.java
package children;
public class Child extends Parent{} // DOES NOT COMPILE -> cannot find symbol Parent{}
```

* a child class inherits the parent class's private methods **but is unable to access them**
* all classes ultimately extend java.lang.Object **n.b. but interfaces don't - interfaces can only extend other interfaces**
### Defining Constructors
* the first statement of every constructor is either:
  - a call to another constructor in the class, using `this()`
  - or a call to a constructor in the parent class, using `super()`
* in the case of a parent class, this will probably be an implicity super() call to `java.lang.Object`, provided by the compiler:
```java
// the following are all equivalent:
public class Foo{}

public class Foo{
	public Foo(){}
}

public class Foo{
	public Foo(){
		super();
	}
}
```
* an explicit constructor means the compiler won't insert a default no-args constructor
* if the parent class has an explicit constructor, with an argument, the child must match this
* a default constructor will not be inserted by the compiler
```java
public class Parent{
	public Parent(int age); //explicit constructor so no default no-args constructor
}

public class Child extends Parent{ // DOES NOT COMPILE -> see below
	
	// the compiler will try to create a default no-args constructor e.g.
	// public Child(){
	//		super(); // NO MATCHING METHOD IN THE PARENT CLASS - this causes the compile error
	//}
	
}
```
* however, you can use a no-args constructor in the child class if it calls a valid `super()` method:
```java
public class Child{
	public Child(){
		super(10);
	}
}
```
* the child constructor doesn't have to match, just the call to `super()`
### Reviewing constructor rules
1. the first statement in a constructor must be either a call to another constructor in the class, using `this()`, or a call to a constructor in the parent class, using `super()`
2. the `super()` method must not be used after the first statement of the construct (i.e. at most once, and on the first line)
3. if no `super()` call is declared in a constructor, Java inserts a no-args `super()` as the first statement
4. if the parent hasn't got a default no-args constructor (i.e. it defines its own constructor with args) and the child doesn't define any constructors, it will not compile
5. if the parent doesn't have a default no-args constructor, the compiler requires an explicit call to a parent constructor on the first line of each child constructor
### Calling constructors
* the parent constructor is always executed before the child constructor
* as `super()` is always the first line, this makes sense!
```java
public class Parent{
	public Parent(){
		System.out.println("Parent");
	}
}

public class Child{
	public Child(){
		System.out.println("Child");
	}
}

public class Grandchild{
	public static void main(String... args){
		new Grandchild();
	}
}
/* output of  running Grandchild is:
* Parent
* Child
*/
```
### Calling inherited class members
* child classes can use any public or protected members from the parent class
* if they are in the same package, they can also use any default (package-private) members
* they don't have direct access to any of the parent's private members, although encapsulation may provide getter/setter access
* parent members can be called directly:
```java
public class Parent{
	public String name = "Parent";
}

public class Child{
	public static void main(String... args){
		System.out.println(name); // -> Parent
	}
}
```
* if a member exists in the parent class but not the child class, `this.` will call it e.g. `System.out.println(this.name);`
* you can use `super.` to call any accessible parent method e.g. `System.out.println(super.name);`
* if a child method overrides a parent method, `this.` calls the child method and `super.` calls the parent method
* if a method doesn't exist in the parent class, trying to call it with `super.` will cause a compile error
> super() and super are different, like this() and this - watch out for this on the exam
### Inheriting Methods
* a subclass has access to all public and protected methods of the parent class
* this can lead to collisions between methods
### Overriding a method
* you can override a parent class method by using the same signature (name and parameter list) in the child class
* you can also reference the parent method in the override method using `super.`
```java
public class Parent{
	public int getAge(){
		return 50;
	}
}
public class Child extends Parent{
	public int getAge(){
		return super.getAge() / 2;
	}
}
```
* what happens if we don't use `super.`?
```java
public int getAge(){
	return getAge() / 2; // INFINITE RECURSION
}
```
### Rules for overriding a method
1. child method must have same signature as parent method
2. the child method must be at least as accessible as the parent method
3. the child method must not introduce any new checked exceptions unless they are broader than the parent method's checked exceptions
4. the child method's return type must be the same or a subclass of the parent's return type
* the last rule is known as *covariant returns* - the return type of an overriding method must be the same or a subtype
```java
public class Parent{
	public void foo(){
		System.out.println("foo");
	}
}
public class Child extends Parent{
	public String foo(){ // DOES NOT COMPILE -> non-covariant return types
		return "foo";
	}
}
```
* overriding is a lot more restrictive than overloading (where only the method signature has to match)
```java
public class Parent{
	public void foo(){
		System.out.println("foo");
	}
}
public class Child extends Parent{
	protected void foo(){ // DOES NOT COMPILE -> child method less accessible than parent method
		System.out.println("foo");
	}
}
```
* checked exceptions must be **as** or **less** broad
```java
class SpecificException extends Exception{}

public class Parent {
	public void foo() throws SpecificException {
		System.out.println("foo");
	}
}
public class Child extends Parent {
	public String foo() throws Exception { // DOES NOT COMPILE -> child's checked exception is broader than parent's
		return "foo";
	}
}
```
* exceptions in the child method must be the same or narrower
* return types in the child method must be the same or narrower
* access to the child method must be the same or broader
### Redeclaring Private Methods
* private methods can't be overriden as the child class can't access them
* they can be overwritten/redeclared
* as it is a new method with no connection to the parent, none of the rules of overriding apply
* their implementation at runtime depends on the reference - if the reference type is to the parent, as the method is hidden and not overridden, the parent method will be used:
```java
public class RedeclaringPrivateMethods {
	private void foo() {
		System.out.println("RedeclaringPrivateMethods");
	}
	
	public static void main(String... args) {
		RedeclaringPrivateMethods parentReference = new AChildClass();
		parentReference.foo(); // -> RedeclaringPrivateMethods
		
		AChildClass childReference = new AChildClass();
		childReference.foo(); // -> AChildClass
	}
}

class AChildClass extends RedeclaringPrivateMethods {
	public void foo() {
		System.out.println("AChildClass");
	}
}
```
### Hiding Static Methods
* method hiding - a child static method with the same signature as a parent static method
* follows the same rules as method overriding, plus:
5. if the parent method is static, the child method must be static (method hiding). If the parent method is not static, the child method must not be static (method overriding)
* in short, the child method should match the static-ness of the parent method - if both are static, the method is hidden not overriden
### Overriding vs Hiding Methods
```java
public class Parent {
	public static String name(){
		return "Parent";
	}
	
	public void sayParentName(){
		System.out.println("Parent is " + name());
	}
}

public class Child extends Parent {
	public static String name(){
		return "Child";
	}
	
	public void sayChildName(){
		System.out.println("Child is " + name());
	}
}

public class HidingMethods {
	public static void main(String... args) {
		Child child = new Child();
		child.sayChildName(); // -> Child is Child
		child.sayParentName(); -> Parent is Child
	}
}
```
* unlike overridden methods, hidden methods only replace parent methods in calls defined in the child class
* **hidden is basically the opposite of overridden - any child method with a parent method that isn't overridden is therefore hidden**
* compare the above example to the below, where the static methods have been replaced with non-static methods (i.e. overridden not hidden methods):
```java
public class Parent {
	public String name(){
		return "Parent";
	}
	
	public void sayParentName(){
		System.out.println("Parent is " + name());
	}
}

public class Child extends Parent {
	public String name(){
		return "Child";
	}
	
	public void sayChildName(){
		System.out.println("Child is " + name());
	}
}

public class OverridingMethods {
	public static void main(String... args) {
		Child child = new Child();
		child.sayChildName(); // -> Child is Parent
		child.sayParentName(); // -> Child is Parent
	}
}
```
* if a child instance calls a hidden method from the child class, it will use the child method
* if the call to the hidden method is in the parent class, it will use the parent method
* if a child instance calls an overridden method, it will use the child method whether the call is defined in the child class or the parent class
* parent instance obviously can't call child method:
```java
Parent parent = new Parent();
parent.sayParentName(); // -> Parent is Parent
parent.sayChildName(); // DOES NOT COMPILE -> can't find symbol
```
### Creating final methods
* final methods cannot be overriden
* the `final` keywords forbids methods from being hidden or overridden
### Inheriting variables
* variables can't be overriden, only hidden
* to hide a variable, define a variable in the child class with the same name as a variable in the parent class
* as with hidden methods, if you reference the variable from within the parent class, the variable defined in the parent class is used
* if referncing from within the child class, the variable defined in the child class is used
```java
public class Parent {
	public String name = "Parent";
		
	public void sayParentName(){
		System.out.println("Parent is " + name);
	}
}

public class Child extends Parent {
	public String name = "Child";
	
	public void sayChildName(){
		System.out.println("Child is " + name);
	}
}

public class HidingVariables {
	public static void main(String... args) {
		Child child = new Child();
		child.sayChildName(); // -> Child is Child
		child.sayParentName(); // -> Parent is Parent
	}
}
```
* this would be the same for static and non-static variables
* you can explictly call the parent variable from a method defined in the child class by using `super.`:
```java
public class Parent {
	public int age = 50;
		
	public void printAge() {
		System.out.println(age);
	}
}

public class Child extends Parent {
	public int age = 20;
	
	public void printAgeAndParentAge() {
		System.out.println("Age is " + age + "; Parent's age is " + super.age);
	}
}

public class HidingVariables {
	public static void main(String... args) {
		Child child = new Child();
		child.printAge(); // -> 50
		child.printAgeAndParentAge(); // -> Age is 50; Parent's age is 20
	}
}
```
* the instance of Child contains two copies of the age variables - one defined in the parent and one in the child
* the instances are kept separate, allowing the instance of child to access both independently
* *the rules are the same for static and non-static variables*
> The nonprivate static variables and methods are inherited by derived classes. The static members aren’t involved in runtime polymorphism. You can’t override the static members in a derived class, but you can redefine them.
### Abstract Classes
* abstract classes allow you to create a blueprint parent class, for other class to extend, without hacing to implement any of the methods in the parent class
* abstract classes cannot be instantiated
* abstract methods have no implementation in the class they are declared in
```java
public abstract class Instrument {
	protected String name = "Instrument"; // protected member is accessible by subclasses 
	public String describe() { // public member is accessible by subclasses  and inherited as concrete method
		return "is a " + name;
	}
	
	public abstract void play(); // no implementation
}
```
```java
public class Piano extends Instrument { // DOES NOT COMPILE -> error: Piano is not abstract and does not override abstract method play() in Instrument
	public static void main(String... args) {
		Piano piano = new Piano();
	}
}
```
* any concrete class that extends an abstract class must implement/override all its abstract methods:
```java
public class Piano extends Instrument {
	public void play() {
		System.out.println("Piano is playing!");
	}
	public static void main(String... args) {
		Piano piano = new Piano();
		System.out.println(piano.describe()); // -> is a instrument
		piano.play(); // -> Piano is playing!
	}
}
```
* abstract classes may contain non-abstract members - these are inherited as concrete classes by any subclasses (as they would be from any class)
* abstract classes are allowed to contain no abstract methods if they wish:
```java
public abstract class NoMethods {}; // compiles fine
```
* abstract methods can only be defined in abstract classes:
```java
public class Concrete {
	public abstract void foo(); // DOES NOT COMPILE
}
```
* the examiners will try to trick you with something like this
```java
public abstract class Cat {
	public abstract void miaow(){} // DOES NOT COMPILE
}
```
* remember, if it's an abstract method, there are no curly braces, just name, parentheses and optional parameter list, then semicolon:
```java
public abstract class Cat {
	public abstract void miaow();
}
```
* abstract classes can't be final - `final` prohibits extending a class, and abstract classes are there to be extended
* abstract methods can't be final, for the same reason - they can't be overridden, and as it has no implementation it is useless
* abstract methods can't be private - similar to above, if a subclass can't access a method, then it can't implement it
```java
public final abstract class Foo { // DOES NOT COMPILE -> abstract class can't be final
	public abstract final int age(); // DOES NOT COMPILE -> abstract methods can't be final
	private abstract void foobar(); // DOES NOT COMPILE -> abstract methods can't be private
}
```
* even with abstract methods, the rules for overriding methods must be followed:
```java
public abstract class Animal {
	public abstract void speak();
}
public class Cat extends Animal {
	private String speak() throws Exception { // DOES NOT COMPILE
		// some implementation
	}
}
```
* the above snippet breaks three of the rules of overriding:
  - non-covariant return types
  - subclass method is less accessible than its parent method
  - child method introduces a new checked excpetion
### Creating a concrete class
* abstract classes can't be instantiated so any non-abstract members they contain probably won't do more than define static variables and methods
* remember that abstract classes can't be directly instantiated:
```java
public abstract class Animal{}
public class AnimalTester{
	public static void main(String... args){
		Animal shark = new Animal(); // DOES NOT COMPILE
	}
}
```
* an abstract becomes useful when it is extended by a concrete subclass
* a concrete class is the first non-abstract class to extend an abstract class
* a concrete class must implement all the abstract methods it inherits
> Abstract classes can be extended by other abstract classes. They don't have to implement its methods, but if they do they are passed to any class that extends it as concrete methods. Any inherited abstract methods not implemented, and any new abstract methods defined in the class, are passed to subclasses that extend it.
```java
public abstract class Animal {
	public abstract String eats();
	public abstract boolean givesBirth();
}

public abstract class Mammal extends Animal{
	public boolean givesBirth() {
		return true;
	}
	public abstract int numLegs();
}

public class Cat extends Mammal {
	public String eats() {
		return "Mice";
	}
	public int numLegs() {
		return 4;
	}
}
```
* Cat is the concrete class (first non-abstract class to extend)
* so it must implement all inherited abstract methods
* as givesBirth() was implemented by Mammal, it inherits it as a concrete class and doesn't need to implement it
### Abstract Class Definition Rules
1. can't be instantiated directly
2. may be defined with any number (including zero) of abstract methods
3. can't be marked `final` or `private`
4. an abstract class that extends another abstract class inherits all its abstract methods as its own abstract methods
5. the first concrete class that extends an abstract class must provide an implementation for all the inherited abstract methods
### Abstract Method Definition Rules
1. can only be defined in abstract classes
2. can't be declared `final` or `private`
3. must not provide a method body/implementation in the class in which they are declared
4. implementing an abstract method in a subclass follows the same rules for overriding a method
### Implementing Interfaces
* an interface is an abstract data type which defines a list of abstract public methods than any class implementing the interface must provide
* it can also include a list of constant variables and default methods
* a class invoking the interface `implements` it (c.f. `extends` for abstract classes)
#### Public or default?
* according to Boyarsky and Selikoff,
> All top-level interfaces are assumed to have public or default access
* if the class implementing is in a different package, it is assume to have public access or default access?
* testing the following:
```java
// packageA/MyInterface.java
package packageA;

interface MyInterface {}

// packageB/MyClass.java
package packageB;
import packageA.*;

public class MyClass implements MyInterface{}
```
```shell
> javac packageA/MyInterface.java packageB/MyClass.java
	packageB/MyClass.java:4: error: cannot find symbol
	public class MyClass implements MyInterface{}
```
* it seems that if they have the `public` keyword, they have public access, if not, they have default access
[https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html](https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html)
* **a file can have at most one public interface (or class) and it should match the file name**
#### Defining and implementing
* Defining an interface:
![Defining an interface](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/media/defining-an-interface.jpg "Defining an interface")
* Implementing an interface:
![Implementing an interface](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/media/implementing-an-interface.jpg "Implementing an interface")
* The public access specifier indicates that the interface can be used by any class in any package. If you do not specify that the interface is public, then your interface is accessible only to classes defined in the same package as the interface.
* abstract is assumed and optional for the interface
* public static for variables is assumed and therefore optional
* public abstract for methods is assumed and therefore optional
* classes may implement multiple interfaces, separated by comma:
```java
public class Rectangle implements isDrawable, isScalable{}
```
* in the above example, Recangle is requireed to implement all abstract methods defined in isDrawable and isScalable (if any)
### Defining an Interface
1. interfaces cannot be instantiated diredctly
2. an interface doesn't have to have methods if it doesn't want to
3. can't be marked as `final`
4. all top-level interfaces can only have `public` or default access (they are "assumed" to have "`public` or default" access) - therefore marking an interfaces as private or protected will trigger a compilation error, since this is incompatible with these assumptions
5. the `abstract` keyword in an interface definition is assumed (and allowed although its use is discouraged) - therefore marking an interface as `final` will cause a compile error
6. all non-default methods in an interface are assumed to have the modifiers `abstract` and `public` in their definition - therefore, marking a method as private, protected or final will trigger compiler errors as these are incompatible with the `abstract` and `public` keywords
* assumed keywords - essentially the compiler inserts them if they aren't present
* **n.b. the compiler won't add `public` to an interface definition - no access modifier keyword means it has default access**
* hence the following two examples are the same:
```java
public interface Animal {
	void sayName();
	abstract void eat();
	public void sleep();
}
```
```java
public abstract interface Animal {
	public abstract void sayName();
	public abstract void eat();
	public abstract void sleep();
}
```
* the following example violates assumed keyword rules:
```java
1. private final interface Animal { // DOES NOT COMPILE -> (public or default) conflicts with private, abstract conflicts with final
2. 		private void sayName(); // DOES NOT COMPILE -> (public or default) conflicts with private
3. 		protected void eat(); // DOES NOT COMPILE -> (public or default) conflicts with protected
4. 		public final void sleep(); // DOES NOT COMPILE -> final conflicts with abstract
5. }
```
### Inheriting an Interface
1. an interface that extends another interface, as well as an abstract class that implements an interface, inherits all the abstract methods as its own abstract methods
2. the first concrete class that implements an interface, or extends an abstract class that implements an interface, must provide an implementation for all of the inherited abstract methods
* like an abstract class, an interface may be extended using the exends keyword - the new child interface inherits all the abstract methods
* unlike an abstract class, an interface may extend multiple interfaces
```java
public interface HasTail {
	public int getTailLength();
}

public interface HasWhiskers {
	public int getNumberOfWhiskers();
}

public interface Seal extends HasTail, HasWhiskers {}
```
* any concrete class that implements the Seal interface must provide implementation for all of its abstract methods - in this case, getTailLength() inherited from HasTail interface, and getNumberOfWhiskers() inherited from HasWhiskers
* an abstract class that implements an interface is treated in the same way - it inherits all the abstract methods but is not required to implement them
* the first concrete class to extend that abstract class must provide implementation for all of its (inherited and defined) abstract methods
```java
public interface HasTail {
	public int getTailLength();
}

public interface HasWhiskers {
	public int getNumberOfWhiskers();
}

public abstract class HarbourSeal implements HasTail, HasWhiskers {}

public class LeopardSeal implements HasTail, HasWhiskers {} // DOES NOT COMPILE
```
* HarbourSeal is an abstract class so inherits the parent abstract methods without being required to implement them
* LeopardSeal is a concrete class so it is required to implement all the abstract methods it inherits
### Classes, Interfaces and Keywords
```java
public interface CanRun {}
public class Cheetah extends CanRun {} // DOES NOT COMPILE
public class Hyena {}
public interface HasFur extends Hyena {} // DOES NOT COMPILE
```
* the will have questions mixing class and interface terminology - so:
  - a class can implement an interface
  - a class cannot extend an interface
  - a class cannot extend an interface
  - a class can extend a class
  - a class cannot implement a class
  - an interface can extend an interface
  - an interface cannot extend a class
  - an interface cannot implement a class or interface
* `interface` `extends` `interface`
* `class` `implements` `interface`
* `class` `extends` `class`
### Abstract Methods and Multiple Inheritance
* what happens if two interfaces contain the same abstract method and a concrete class implements them both?
* if they have the same name but a different signature, they are overloaded methods and follow those rules
* if they have the same name and signature, **and** the same return type, they are considered duplicates and the class only needs one implementation
```java
package abstractMethodsAndMultipleInheritance.sameNameAndReturn;
interface Foo {
	void foo();
}

interface Bar {
	public abstract void foo();
}

public class SameNameAndReturn implements Foo, Bar {
	public void foo(){
		System.out.println("Foo!");
	}
	public static void main(String... args){
		SameNameAndReturn test =  new SameNameAndReturn();
		test.foo(); // -> Foo!
	}
}
```
* if they have the same name and signature but a different return type, that violates the laws of methods in classes - any class that tries to implement both will not compile
* the same is true of an interface extending both or an abstract class implementing both:
```java
package abstractMethodsAndMultipleInheritance.sameNameDifferentReturn;
interface Foo {
	void foo();
}

interface Bar {
	public abstract int foo();
}

public class SameNameDifferentReturn implements Foo, Bar { // DOES NOT COMPILE -> error: SameNameDifferentReturn is not abstract and does not override abstract method foo() in Bar
	public void foo(){ // DOES NOT COMPILE -> error: foo() in SameNameDifferentReturn cannot implement foo() in Bar
		System.out.println("Foo!");
	}
	public int foo(){ // DOES NOT COMPILE -> error: method foo() is already defined in class SameNameDifferent
		return 0;
	}
	public static void main(String... args){
		SameNameDifferentReturn test =  new SameNameDifferentReturn();
		test.foo();
	}
}
```
* even without implementation, Java will recognize the conflict and throw a compiler error:
```java
package abstractMethodsAndMultipleInheritance.sameNameDifferentReturn;
interface Foo {
	void foo();
}

interface Bar {
	public abstract int foo();
}

abstract class AbstractClassSameNameDifferentReturn implements Foo, Bar {} // DOES NOT COMPILE
// -> error: types Bar and Foo are incompatible; both define foo(), but with unrelated return types

interface InterfaceSameNameDifferentReturn extends Foo, Bar {} // DOES NOT COMPILE
// -> error: types Bar and Foo are incompatible; both define foo(), but with unrelated return types

```
### Interface Variables
### Interface Variables
* are, like interface methods, assumed to be public
* are, unlike interface methods, assumed to be static and final
#### Rules
1. interface variables are assumed to be public, static and final - therefore, marking an interface variable as `private`, `prtected` or `abstract` will cause a compile error
2. the value of an interface variable must be set when it is declared, as it is marked final
* so, interface variables are essentially constant variables defined at the interface level
* as they are static, they are available even without an instance of the interface (or a concrete class implementing it):
```java
interface AnInterface {
	int MAX_LENGTH = 99; // compiler adds public final static
}

public class InterfaceVariables implements AnInterface {
	public static void main(String... args) {
		System.out.println(MAX_LENGTH); // -> 99
	}	
}
```
* anything conflicting with the assumed keywords will trigger a compiler error:
```java
public interface IllegalExamples {
	private int one = 1; // DOES NOT COMPILE -> public private conflict
	protected int two = 2; // DOES NOT COMPILE -> public protected conflict
	abstract int three = 3; // DOES NOT COMPILE -> final abstract conflict
	int 4; // DOES NOT COMPILE -> not assigned a value
}
```
### Default Interface Methods
* Java 8 introduced the `default` method to interfaces
* marked `default`, it has a method body unlike regular interface methods which are assumed abstract and have no implementation
* classes have the option to override the default implementation, but are not required to do so
* if they don't, the default implementation is used
```java
interface DefaultMethods {
	default void HelloWorld() { // compiler adds public
		System.out.println("Hello World!");
	}
}
```
* the method is assumed to be public, as all methods in an interface are assumed to be public
> **the keyword `default` is not the same as default access**
> default access is denoted by the lack of an access modifier in normal classes
> because all interface methods are public, the access modifier (assumed or explicit) for a default method is always public
####Rules
1. a default method can only be declared in an interface, not in a class or abstract class
2. a default method must be marked with the default keyword
3. a default method must provide a method body
4. unlike regular abstract methods, a default method is **not** assumed to be static, final or abstract, as it may be overridden by a class that implements the interface
5. like all methods in an interface, a default method is assumed to be public and will not compile if marked as private or protected
* so, default methods will only ever be defined in interfaces - if one appears in a class, assume it won't compile
* default methods must be marked default and must have a method body - if not, they won't compile
```java
public interface IllegalDefaultDeclarations {
	default void foo(); // DOES NOT COMPILE -> default keyword and no method body
	public void foobar(){}; // DOES NOT COMPILE -> method body provided for non-default method
}
```
* unlike interface variables, which are assumed static, default methods cannot be marked as static and require an instance of the implementing class in order to be invoked
* they also can't be marked as final or abstract, as they are allowed to be overridden in subclasses
* when an interface extends/an abstract class implements an interface with a default method, it can:
  - ignore it, in which case the default implementation will be used
  - override it, using the standard rules
  - redeclare the method as abstract, reequiring that classes implementing the new interface explicitly provide a method body
```java
interface DefaultMethod {
	default void foo() {
		System.out.println("Foo in DefaultMethod");
	}
}

interface RedeclaresAbstract extends DefaultMethod {
	void foo(); // redeclares foo() as abstract
}

interface OverridesBackToDefault extends RedeclaresAbstract{
	default void foo() { // overrides abstract foo() as default method
		System.out.println("Foo in OverridesBackToDefault");
	}
}

class ImplementsDefaultMethod implements DefaultMethod {} // inherits default method so doesn't need to implement foo()

class ImplementsRedeclaresAbstract implements RedeclaresAbstract {  // inherits abstract method so needs to implement foo()
	public void foo() { // follows overriding rules so must be as accessible as parent method - which is assumed public
		System.out.println("Foo in ImplementsDefaultMethod");
	}
}

class ImplementsOverridesBackToDefault implements OverridesBackToDefault { // inherits default method so doesn't need to implement foo() but it is allowed to so we will
	public void foo() { // follows overriding rules so must be as accessible as parent method - which is assumed public
		System.out.println("Foo in ImplementsOverridesBackToDefault");
	}
}

public class DefaultInheritance {
	public static void main(String... args) {
		ImplementsDefaultMethod obj1 =  new ImplementsDefaultMethod();
		ImplementsRedeclaresAbstract obj2 = new ImplementsRedeclaresAbstract();
		ImplementsOverridesBackToDefault obj3 = new ImplementsOverridesBackToDefault();
		
		obj1.foo(); // -> Foo in DefaultMethod
		obj2.foo(); // -> Foo in ImplementsDefaultMethod
		obj3.foo(); // -> Foo in ImplementsOverridesBackToDefault
	}
}
```
### Default Methods and Multiple Inheritance
* if a class implements two interfaces that have default methods with the same name and signature, the compiler will error
* however, overriding the method removes the ambiguity over which method to use (i.e., neither)
```java
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

class DoesNotOverride implements Walk, Run {} // DOES NOT COMPILE -> error: class DoesNotOverride inherits unrelated defaults for getSpeed() from types Walk and Run
	
public class DefaultMethodsAndMultipleInheritance implements Walk, Run {
	public int getSpeed() {
		return 20;
	}
}
```
### Static Interface Methods
* Java 8 also introduced static methods within interfaces
* static methods are marked with the `static` keyword
* static methods function mearly identically to static methods defined in classes, except that any classes that implement the interface do not inherit the static method
#### Rules;
1. is assumed to be `public` - cannot be marked `private` or `protected`
2. to reference the static method, a reference to the name of the interface must be used - as it isn't inherited, it won't be present in the concrete class or its instances, so this is the only way to invoke it
```java
interface Hop {
	public static int getHeight() {
		return 10;
	}
}

public class Rabbit implements Hop {
	public void printHeight() {
		System.out.println(getHeight()); // DOES NOT COMPILE
		System.out.println(Hop.getHeight()); // -> 10
	}
}
```
> unlike default methods, two interfaces with identical static methods can both be implemented by a class without a compile error - they aren't inherited so there is no conflict
### Understanding Polymorphism
* Polymorphism is the property of an object to take on many different forms
* more precisely, a Java object may be accessed using:
  - a reference with the same type as the object
  - a reference that is a superclass of the object
  - a reference that defines an interface that the object implements, either directly or through a superclass
* a cast is not required if the the object is being reassigned to a supertype or interface of the object
```java
class Mammal {
	public boolean isWarmBlooded() {
		return true;
	}
}

interface Swims {
	void swim();
}

class Dog extends Mammal implements Swims {
	public void swim() {
		System.out.println("Swimming!");
	}
	public int age = 10;
}
public class UnderstandingPolymorphism {
	public static void main(String... args) {
		Dog dog = new Dog();
		System.out.println(dog.age); // -> 10
		
		Swims swims = dog;
		swims.swim(); // -> Swimming!
		
		Mammal mammal = dog;
		System.out.println(mammal.isWarmBlooded()); // -> true
	}
}
```
* only one object, Dog, is created and referenced
* the ability of a Dog instance to be passed as an instance of an interface it implements, and as an instance of one of its superclasses, is the nature of polymorphism
* once the object has been assigned a new reference type, only the methods and variables available to that reference tyoe are callable on the object without an explicit class
```java
class Mammal {
	public boolean isWarmBlooded() {
		return true;
	}
}

interface Swims {
	void swim();
}

class Dog extends Mammal implements Swims {
	public void swim() {
		System.out.println("Swimming!");
	}
	public int age = 10;
}
public class MisunderstandingPolymorphism {
	public static void main(String... args) {
		Dog dog = new Dog();
		
		Swims swims = dog;
		System.out.println(swims.age); // DOES NOT COMPILE -> error: cannot find symbol
		System.out.println(swims.isWarmBlooded()); // DOES NOT COMPILE -> error: cannot find symbol
		
		Mammal mammal = dog;
		System.out.println(mammal.age); // DOES NOT COMPILE -> error: cannot find symbol
		mammal.swim(); // DOES NOT COMPILE -> error: cannot find symbol
	}
}
```
* dog has access to all its superclasses' methods, and any inherited concrete and default methods - swims and mammal only have access to their members
### Object vs Reference
* in Java, all objects are accessed by reference
* conceptually, the object is the entity that exists in memory
* regardless of the type of the reference you have for the object in memory, the object itself doesn't change
* for example, all object inherit `java.lang.Object`, so are reassignable to it:
  - `Dog dog = new Dog();`
  - `Object dogAsObject = dog;`
* even though the Dog/dog object has been assigned a reference with a different type, the object itself has not changed and still remains a Dog object in memory
* what has changed is out ability to access methods within the Dog class with the dogAsObj reference
* without an explicit cast back to a Dog reference, we don't have access to them
#### Rules for this principle
1. the type of the object determines which properties exist within the object in memory
2. the type of reference to the object determines which methods and variables are available to the Java program
> changing a reference of an object to a new reference may give **access** to **new properties** - but these properties **existed** before the change
![Object vs Reference](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/media/object-vs-reference.png "Object vs Reference")
### Casting Objects
* in order to get access to those properties again, we can. use casting:
```java
class Mammal {
	public boolean isWarmBlooded() {
		return true;
	}
}

interface Swims {
	void swim();
}

class Dog extends Mammal implements Swims {
	public void swim() {
		System.out.println("Swimming!");
	}
	public int age = 10;
}
public class CastingObjects {
	public static void main(String... args) {
		Dog dog = new Dog();
		Mammal m = dog; // doesn't require cast
		// Dog dog2 = m; // DOES NOT COMPILE -> error: incompatible types: Mammal cannot be converted to Dog
		Dog dog3 = (Dog)m; // cast back to dog
		System.out.println(dog3.age); // now has access to Dog methods
		System.out.println(( (Dog)m).age) ; // or use a temporary cast to gain access to Dog methods
		// must be in parentheses, or the compiler will look for m.age rather than ((Dog)m).age
	}
}
```

#### Basic rules:
1. casting an object from a subclass to a superclass does not require an explicit cast
2. casting an object from a superclass to a subclass requires an explicit cast
3. the compiler will not allow casts to unrelated types
4. even when the code compiles without an issue, an exception will be thrown at runtime if the object being cast is not actually an instance of that class
* trying to cast unrelated types causes a compile error:
```java
class Cat {}
class Canary {}
public class CastingUnrelatedTypes {
	public static void main(String... args) {
		Cat cat = new Cat();
		Canary canary = (Canary)cat; // DOES NOT COMPILE -> error: incompatible types: Cat cannot be converted to Canary
	}
}
```
* Cat and Canary are not related through any class hierarchy so this won't compile
* if the types share a related hierarchy, it will compile, but if the object being referenced is not an instance of the type being cast to, it will throw a ClassCastException at runtime:
```java
class Cat {}
class Tabby extends Cat {}

public class CastingRuntimeErrors {
	public static void main(String... args) {		
		Cat cat = new Cat();
		Tabby tabby = (Tabby)cat; // compiles, but throws RuntimeError
		// java.lang.ClassCastException: class Cat cannot be cast to class Tabby		
	}
}
```
* if they share a hierarchy, it will compile
* at runtime, it will check whether the cat object is an instance of Tabby - it is not, so throws a runtime error
* we can rewrite the previous code to demonstrate that
```java
class Cat {}
class Tabby extends Cat {}

public class CastingRuntimeErrors {
	public static void main(String... args) {	
		Cat cat = new Cat();
		if(cat instanceof Tabby) {
			Tabby tabby = (Tabby)cat;
		} else {
			System.out.println("Cannot cast");
		}
	}
}
// output -> Cannot cast
```
### Virtual Methods
* the most important feature of polymorphism is virtual methods
* a virtual method is a method in which the specific implementation is not determined until runtime
* all non-final, non-static and non-private methods are considered virtual method
* if you call a method that ovverrides a method on an object, you get the overridden method, even if the call to the method is on a parent method or within the parent class
```java
class Mammal {
	public void info() {
		System.out.println("This mammal is " + getName());
	}
	
	public String getName() {
		return "undefined";
	}
}

class Cat extends Mammal {
	public String getName() {
		return "a cat";
	}
}

public class VirtualMethods {
	public static void main(String... args) {
		Mammal mammal = new Cat();
		mammal.info();
	}
}
// output -> This mammal is a cat
```
* the method `getName()` is overridden in the Cat class
* more importantly, the value of the `getName()` method in the `info()` method is replaced at runtime with the value of the implementation in the subclass Cat
* even though the parent class Mammal defines its own `getName()` method and knows nothing about the Cat class at compilation, at runtime the instance uses the overridden version of the method, as defined on the instance of the object
### Polymorphic Parameters
* allows you to pass various instances of a subclass or interface to a method
* for example, you could define a method that takes an instance of an interface as a parameter
* any class that implements the interface can then be passed to the method
* you don't need to cast explicitly as it's casting a subclass to a superclass
```java
interface Mammal {
	default void info() {
		System.out.println("This is " + getName());
	}
	
	public String getName();
}

class Cat implements Mammal {
	public String getName() {
		return "a cat";
	}
}

class Dog implements Mammal {
	public String getName() {
		return "a dog";
	}
}

class Mouse implements Mammal {
	public String getName() {
		return "a mouse";
	}
}

public class PolymorphicParameters {
	public static void mammalInfo(Mammal m) {
		m.info();
	}
	
	public static void main(String... args) {
		mammalInfo(new Cat()); // -> This is a cat
		mammalInfo(new Dog()); // -> This is a dog
		mammalInfo(new Mouse()); // ->This is a mouse	
	}
}
```
* we can pass any class that implements Mammal to the `mammalInfo()` method
* it will call the overridden method, depending on the object passed in
* otherwise to achieve this, we would have to define a method for each type:
```java
public void catInfo(Cat c) {
	c.info();
}

public void dogInfo(Dog d) {
	d.info();
}

public void mouseInfo(Mouse m) {
	m.info();
}
```
* you obviously can't pass an unrelated type:
```java
class Rectangle {}
mammalInfo(new Rectangle()); // DOES NOT COMPILE - > Rectangle cannot be converted to a Mammal (incompatible types)
```
### More on hidden methods and variables
* Chapter 5 Review Question 20:
  - What is the result of the following code?
```java
1: 	public abstract class Bird {
2: 		private void fly() { System.out.println("Bird is flying"); }
3: 		public static void main(String[] args) {
4: 			Bird bird = new Pelican();
5: 			bird.fly();
6: 		}
7: 	}
8: 	class Pelican extends Bird {
9: 		protected void fly() { System.out.println("Pelican is flying"); }
10: 	}
```
A. Bird is flying
B. Pelican is flying
C. The code will not compile because of line 4.
D. The code will not compile because of line 5.
E. The code will not compile because of line 9.

* the answer is A
> The trick here is that the method fly() is marked as private in the parent class Bird,
which means it may only be hidden, not overridden. With hidden methods, the specific
method used depends on where it is referenced. Since it is referenced within the Bird
class, the method declared on line 2 was used, and option A is correct. Alternatively,
if the method was referenced within the Pelican class, or if the method in the parent
class was marked as protected and overridden in the subclass, then the method on line
9 would have been used.
* **n.b. it makes no difference that Bird is an abstract class, the result would be the same even if it weren't**
* remember, [private methods can't be overridden](#https://github.com/sebastianchristopher/Java8OCANotes#redeclaring-private-methods)
* private methods that have a child method with the same name and signature are hidden methods

## Chapter 6 - Exceptions
**In this chapter:**

---
[What are exceptions](#what-are-exceptions)

[Throwing an Exception](#throwing-an-exception)

[Types of Exception](#types-of-exception)

[Try statement](#try-statement)

[Finally block](#finally-block)

[Catching various types of exception](#catching-various-types-of-exception)

[Order of catch blocks](#order-of-catch-blocks)

[Throwing a second exception](#throwing-a-second-exception)

[Common Exception Types](#common-exception-types)

[Runtime Exceptions](#runtime-exceptions)

[Checked Exceptions](#checked-exceptions)

[Errors](#errors)

[Calling methods that throw exceptions](#calling-methods-that-throw-exceptions)

[Overriding methods with an exception in the method declaration](#overriding-methods-with-an-exception-in-the-method-declaration)

[Printing an exception](#printing-an-exception)

---

### What are exceptions
* an exception is an event that alters program flow
*  all objects that represent these events come from the `Throwable` superclass
* **n.b. not all have the word exception in them**
#### Key Subclasses of Throwable
![Key Subclasses of Throwable](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/media/key-subclasses-of-throwable.png "Key Subclasses of Throwable")
* `Error` means something irrecoverable went wrong
* `RuntimeException` and its subclasses  are *runtime exceptions*  - unexpected but not fatal
		- e.g. accessing an invalid array index
* they are also know as **Unchecked Exceptions**
* **Checked Exceptions** include `Excpetion` and all subclasses that do not extend `RuntimeException`
* Java has a rule called *handle or declare*
* for checked exceptions, Java requires that the code either handle or the, or declare them in the method signature
```java
public class Fail {
	void fail() {
		throw new Exception(); // DOES NOT COMPILE
	}
}
```
```java
public class CatchFail  {
	void fail() throws Exception {
		throw new Exception(); // DOES NOT COMPILE
	}
}
```
* throws **delcares** it *might* throw a an exception
* the `throws` keyword tells Java you want to have the option of throwing an exception
* `throw` tells Java to throw an exception
> An example of an unchecked (or runtime) exception is NullPointerException. This can happen in any method - but you don't see `throws NullPointerException` everywhere because it is unchecked
### Throwing an Exception
* there may be made up exceptions on the exam but threat them as real exceptions - `class MyMadeUpException extends Exception {}`
* for the exam, there are two types of code that throw exceptions:
  - code that is wrong e.g.
  ```java
  String[] foo = new String[0];
  System.out.println(foo[0]); // throws ArrayIndexOutOfBoundsException
  ```
  - code that explicitly throws an exception e.g.
  ```java
  throw new Exception("No money in the bank");
  throw new Exception();
  throw new RuntimeException("You have exceded your overdraft");
  ```
* exception classes usually take an optional string constructor argument - both types above
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
### Types of Exception
| Type                        | How to recognize                                                  | OK for program to catch? | Required to declare or handle? |
| -------------------- | --------------------------------------------------- | -------------------------- | --------------------------------- |
| Runtime exception  | subclass of RuntimeException                               | Yes                                   | No                                              |
| Checked exception | subclass of Exception but not RuntimeException  | Yes                                   | Yes                                             |
| Error                       | subclass of Error                                                   | No                                    | No                                               |

### Try statement
```java
try {
	// this block is known as the try block, or protected code
} catch(RuntimeException e) {
	// exception handler block
}
```
* if the code in the try clause throws an exception, it stop running and excecution passes to the catch statement
* curly braces are **required**
* try statements and methods always require curly braces
* if statements and loops allow you to omit curly braces if there is only one statement inside the code block
* a `try` block needs to have `catch` or `finally`
```java
try { // DOES NOT COMPILE
	// do something
}
```
### Finally block
```java
try {
	// something
} catch(Exception e) {
	// handle exception
} finally {
	// this always occurs, whether or not an exception is thrown
}
```
* the order is: `try` -> `catch` -> `finally` - any other order won't compile
* `catch` is optional if `finally` is present:
```java
try {
	// try something
} finally {
	// this always runs
}
```
### Catching various types of exception
```java
class ACheckedException extends Exception {}
class AnUncheckedException extends RuntimeException {}

public class CatchingVariousTypesOfException {
	static void foo(int i) throws ACheckedException {
		if(i == 1) {
			throw new ACheckedException();
		} else if(i == 2) {
			throw new AnUncheckedException();
		}
	}
	
	static void test(int i) {
		try {
			foo(i);
		} catch(ACheckedException e) {
			System.out.println("Checked");
		} catch(AnUncheckedException e) {
			System.out.println("Unchecked");
		} finally {
			System.out.println("I run regardless");
		}
	}
	
	public static void main(String... args) {
		test(1); // -> Checked \n I run regardless
		test(2); // -> Unchecked \n I run regardless
		test(3); // -> I run regardless
	}
}
```
* in the method `test()`, if `foo()` doesn't throw an exception, runs as normal then goes to finally
* if it throws `AnUncheckedException`, it goes to the `catch(AnUncheckedException e) ` block
* if it throws a different unchecked exception, e.g. `RuntimeException`, it won't be caught as it's not in a catch block
* if it throws `ACheckedException`, it will be caught
* if it throws a different checked exception e.g. `Exception`, there will be a compiler error - checked exceptions must be declared or handled, and `main()` and `test()` don't handle Exception
* all callers, including main, must either declare or handle all checked exceptions
* the callers here are `main()` and `test()`, so if `Exception` was thrown in the try block, both those callers would have to handle or declare that
### Order of catch blocks
* the order must be such that all catch blocks can be reached
* Java executes each in order then moves to the next until it matches the type(c.f. switch statements)
```java
try {
	foo();
} catch(Exception e) {
	// do something
} catch(RuntimeException e) { // DOES NOT COMPILE -> RuntimeException has already been caught
	// do something
}
```
* superclasses can't be caught before subclasses
* order should go upwards, from the narrowest subclass up to the superclass
### Throwing a second exception
```java
try {
	throw new RuntimeException();
} catch(RuntimeException e){
	throw new RuntimeException();
}
```
* output is: Exception in thread "main" java.lang.RuntimeException
* however, if we include a finally clause:
```java
try {
	throw new RuntimeException();
} catch(RuntimeException e){
	throw new RuntimeException();
} finally {
	throw new Exception();
}
```
* output is: Exception in thread "main" java.lang.Exception
> because the finally lbock **always** has to run, the catch clause will execute up until the exception, then pass to finally - it can't throw two exceptions so has to go to finally
* another example:
```java
public class ThrowingASecondException {
	public static void main(String... args){
		String result = "";
		String x = null;
		try {
			try {
				result += "before ";
				System.out.println(x.length());
				result += "after ";
			} catch(NullPointerException e){
				result += "catch ";
				throw new RuntimeException();
			} finally {
				result += "finally ";
				throw new Exception();
			}
		} catch(Exception e) {
			result += "done ";
		}
		System.out.println(result); // -> before catch finally done
	}
}
```
### Common Exception Types
* 3 types for the exam:
  1. runtime exceptions (also known as unchecked exceptions)
  2. checked exceptions
  3. errors
### Runtime Exceptions
* `ArithmeticException` - thrown by the JVM when code attempts to divide by zero e.g. `int x = ``/0;`
* `ArrayOutOfBoundsException` - thrown by the JVM e.g.
  ```java
  String[] arr = new String[0];
  System.out.println(arr[-1]); // -> throws ArrayOutOfBoundsException -> array indices can't be negative
  System.out.println(arr[3]); // -> throws ArrayOutOfBoundsException -> arraysize is 0 - so 3 is out of bounds
  ```
* `ClassCastException` - thrown by the JVM when an attempt is made to cast an object to a subclass of which it is not an instance e.g.
  ```java
  String foo = "foo";
  Integer num = (Integer)foo; // DOES NOT COMPILE
  ```
  - Java knows Integer isn't a subclass of String so compile fails
  ```java
  Object foo = new String("foo");
  Integer num = (Integer)foo; // throws ClassCastException
  ```
  - Integer is a subclass of Object, so it compiles
  - but at runtime, it checks if the object in memory (foo, a String) is an instance of Integer
  - `foo instanceof Integer` -> `false`
  - it isn't, so throws ClassCastException
* `IllegalArgumentException` - thrown by the programmer to indicate that a method has been passed an illegal or inappropriate argument
  ```java
  class Dog {
	int numLegs;
	public void setNumLegs(int legs) throws IllegalArgumentException{ // n.b. doesn't need to be declared as it's an unchecked exception
		if(legs < 0 || legs > 4){
			throw new IllegalArgumentException();
		} else {
			numLegs = legs;
		}
	}
}
  ```
* `NullPointerException` - thrown by the JVM when there is a null reference where an object is required - instance variables and methods must be called on a non-null reference
  ```java
  class Foo {
	String name;
	void bla() {
		System.out.println(name.length()); // -> throws NullPointerException -> null.length();
	}
} // watch out for this with objects whose initializing default value is null
  ```
* `NumberFormatException` - thrown by the programmer when an attempt is made to convert a string to a numeric type but the string doesn't have an appropriate format
  - subclass of `IllegalArgumentException`, used for example by Integer:
  ```java
  Integer.parseInt("abc"); // -> NumberFormatException
  ```
### Checked Exceptions
* checked exceptions:
  - are subclasses of `Exception` but not of `RuntimeException`
  - must be handled or declared
  - can be thrown by the JVM or the programmer
* `FileNotFoundException` - subclass of `IOException`
* `IOException` - thrown programmatically when there's a problem reading/writing a file
### Errors
* errors:
  - are thrown by the JVM
  - are not handled or declared
  - extend the `Error` class
* `ExceptionInitializerError` - thrown when a static initializer throws an exception and doesn't handle it
  ```java
  public class ExceptionInitializerErrorExample {
	static int age;
	static {
		int[] ages = {1, 17, 28, 32};
		age = ages[ages.length];
	}
	public static void main(String[] args){
		System.out.println(age);
	}
  }
  /*
  * Exception in thread "main" java.lang.ExceptionInInitializerError
  * Caused by: java.lang.ArrayIndexOutOfBoundsException: 4
  *         at ExceptionInitializerErrorExample.<clinit>(ExceptionInitializerErrorExample.java:5)
  */
  ```
* `NoClassDefFoundError` - occurs when Java can't find a class at runtime
### Calling methods that throw exceptions
```java
static void foo() throws Exception {}
public static void main(String... args){
	foo(); // DOES NOT COMPILE
}
```
* if a method throws (or even just declares it throws) a checked exception, the caller must either handle or declare it:
```java
static void foo() throws Exception {}
public static void main(String... args) throws Exception{
	foo();
}
```
* or:
```java
static void foo() throws Exception {}
public static void main(String... args){
	try {
		foo();
	} catch(Exception e) {
		System.out.println("no can foo");
	}
}
```
### Overriding methods with an exception in the method declaration
* an overriding method in the subclass must not introduce new checked exceptions
```java
class Parent {
	public void foo() {}
}

class Child extends Parent {
	public void foo() throws Exception {} // DOES NOT COMPILE
}
```
* an overriding method in a subclass is allowed to throw fewer exceptions than the parent method as the parent method takes care of them:
```java
class Parent {
	public void foo() throws Exception {}
}

class Child extends Parent {
	public void foo() {}
}
```
* similarly, they are also allowed to throw subtypes as the superclass/interface has also taken care of the broader type:
```java
class SubException extends Exception {}
class Parent {
	public void foo() throws Exception {}
}

class Child extends Parent throws SubException{
	public void foo() {}
}
```
* they can also throw whatever unchecked/runtime exceptions they want, as these aren't required to be handled or declared
### Printing an exception
```java
public class PrintingAnException {
	static void foo() {
		throw new RuntimeException("Whoops");
	}
	
	public static void main(String... args) {
		try {
			foo();
		} catch(RuntimeException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
/*
* output:
* java.lang.RuntimeException: Whoops
* Whoops
* java.lang.RuntimeException: Whoops
*         at PrintingAnException.foo(PrintingAnException.java:3)
*         at PrintingAnException.main(PrintingAnException.java:8)
*/
```