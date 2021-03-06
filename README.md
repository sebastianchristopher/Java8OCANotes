# Java OCA

My notes from Boyarsky and Selikoff's *Oracle Certtified Associate Java SE 8 Programmer I Study Guide*, with top-ups from Mala Gupta's *OCA Java SE 8 Programmer I Certification Guide*

[Notes on Enthuware exams](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/enthuware/README.md)

**Table of contents**

---
[Chapter 1 - Java Building Blocks](#chapter-1---java-building-blocks)

[Chapter 2 - Operators and Statements](#chapter-2---operators-and-statements)

[Chapter 3 - Java Core APIs](#chapter-3---java-core-apis)

[Chapter 4 - Methods and Encapsulation](#chapter-4---methods-and-encapsulation)

[Chapter 5 - Class Design](#chapter-5---class-design)

[Chapter 6 - Exceptions](#chapter-6---exceptions)

[Misc notes](#misc-notes)

[Exam Essentials](#exam-essentials)

---

[Index](#index)

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
* **if run without arguments, `args` is an empty String array - not null**
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
* a non-public class can have a main() method, and it can still run from the command line
* a class can have a main() method without `String[]` argument, but it won't be executable
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
* here is an example using the classes `Files` and `Paths`, both in `java.nio.file`:
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
* a method name with a return type is not a constructor - the exam will try to trip you up on this
* a constructor can take the same type as a parameter -> `String(String s)`, `Foo(Foo f)`
* a constructor cannot be final, static or abstract
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
* char can be assigned a character literal e.g. `char c = 'z'` or a positive integer e.g. `char c = 122` (both examples are equivalent)
* char can't be assigned a negative value (except through casting e.g. `char c = (char)-1` which will store an unexpected value)
* because char is equivalent to an unsigned short in Java, arithmetic operators can run on it
* upercase letters come before lowercase, therefore have a lower ASCII value -> `System.out.println('B' > 'b');` -> `false`
* when char is used as an operand to arithmetic operators, its corresponding ASCII value is used in the arithmetic operation, and the result is numeric
* this is not the case when using unary or compound assignment operators, as they will [automatically cast](#automatic-casting)
```java
class CharAddition {
	public static void main(String[] args) {
		char a = 'a';
		char b = 'b';
		System.out.println(a + b); // -> 195
		System.out.println(++a); // -> b
		System.out.println(a += b); // -> Ä
	}
}
```
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
  - right after (or between) the prefixes 0b, 0B, 0x, and 0X (but legal after octal prefix 0)
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
long hexVal2 = 0_x10_BA_75; // illegal
long hexVal3 = 0x10_BA_75; // legal

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
* another edge case is that, stupidly, you can have as many underscores next to each other as you want, as long as their position is legal:
```java
int i = 1_______________________________0____________________________________________0;
System.out.println(++i); // 101
```
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
* In case there is a conflict between variable name and class name, if the variable is in scope then it is the variable that is accessed instead of the class:
```java
String String = "String";
System.out.println(String.length()); // prints 6
```
* Here, the variable String is in scope and therefore the length of the string pointed to by the variable String is printed. The compiler does not confuse it with the class name String.
* What if we want to use a static method of the String class?
```java
String String = "String";
System.out.println(String.valueOf(String); // prints String
// it's the same as:
String str = "String";
System.out.println(str.valueOf(str); // prints String
```
* since we can call a static method with an instance of the object, this compiles and runs without any issues
### Default initialization of variables
* *local variables* (i.e. variables in a method, initializer block or static initializer block) must be initialized before use
* their scope is confined to the method or block they are declared in
* they can't be marked with access modifiers
* they do not have a default value and must be initialized before use
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
* careful when a local variable uses the compound assignment operator that it has been initialized beforehand:

```java
public static void main(String... args){
	int i = 1;
	int y += i; // DOES NOT COMPILE - y has not been initialized
	System.out.println(y);
}
```
* interestingly also can't do this on a single line with an instance or class variable:

```java
public class CompoundAssignmentLocalInstanceVariables {
	static y += 1; // DOES NOT COMPILE
	public static void main(String... args){
		System.out.println(y);
	}
}
```

```bash
javac CompoundAssignmentLocalInstanceVariables.java
CompoundAssignmentLocalInstanceVariables.java:3: error: <identifier> expected
        static y += 1;
                     ^
```
* the declaration happens on the left-hand side - the assignment is provided by the `=`:
```java
int x = x + x; // does not compile - can't use before initialization
int x = (x = 1) + x // the brackets give precedence to the assignment (x = 1), which allows it to be used
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
* the default value of char is an empty char; its ASCII value is 0 - if cast to int or used in an operation its value is 0
### Variable scope
* local variables are in scope from declaration to the end of the block
* in the following example, `int i` is in scope from its declaration to the end of the method
* `int j` is in scope from its declaration to the end of the `{}` block
```java
class LocalVariableScope {
	public static void main(String[] args) {
		int i = 1;
		{
			int j = 2;
			System.out.println(i + j); // -> 3
		}
		System.out.println(i + j); // -> error: cannot find symbol j
	}
}
```
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

* In Java, the dot operator has higher precedence than the cast operator:
```java
class Parent{
	int x;
}
class Child extends Parent{
	Long x;
}
class MyTest{
	public static void main(String[] args){
		Child child = new Child();
		int x = (Parent)child.x; // DOES NOT COMPILE ->  error: incompatible types: Long cannot be converted to Parent
	}
}
```
* the `.` is executed first, so it tries to cast Long to Parent. The parentheses denoting a cast are not the same as parentheses overriding precedence. The following is the correct way to do it:
```java
class Parent{
	int x;
}
class Child extends Parent{
	Long x;
}
class MyTest{
	public static void main(String[] args){
		Child child = new Child();
		int x = ( (Parent)child).x; // these extra parentheses override precendence - they say, perform the cast before the dot
	}
}
```
* what if one of my operands is a method?
* this is confusing - basically, it seems that the method will be called based on evaluating left to right, ignoring parentheses:
```java
static int num(){return 1;}
public static void main(String[]args){
	int a = 0;
	int b = num() + (a = 1); // here num() is called before (a = 1)
	int c = (a = 1) + num(); // here num() is called after (a = 1)
}
```
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

* [More on this](https://coderanch.com/t/653797/certification/Post-Pre-unary-operator-precedence)  
* [Princeton on precedence](https://introcs.cs.princeton.edu/java/11precedence/)
* careful when assigning and using the post-increment/decrement operator:
```java
int a = 0;
a = a++;
System.out.println(a); // -> 0

int b = 0 ;
b = b++ + b++; // -> 1
```
* example one - reading left to right - a is assigned the value of a, 0. The incremented value is stored but never applied?
* example two - b is 0, post-incremented - it is added to b, which is now 1 - 0 + 1 = 1. Again the last post incremented value isn't included when the assignment is made
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
5. narrowing primitive conversion may be used if the type of the variable is byte, short, or char, and the value of the constant expression is representable in the type of the variable:
```java
short x = 5;
```
* No such narrowing primitive conversion is allowed in the invocation context:
```java
static void printNum(short num) { System.out.println(num)); }
public static void main(String[] args){
	short x = 5;
	printNum(x); // Java can narrow this
	printNum(5); // DOES NOT COMPILE -> incompatible types; possible lossy conversion from into to short
}
```
* remember, these rules apply to all operations **including** assignment - e.g. `float f = 1;` -> int literal is promoted to the floating-point value type
* also remember, all numbers without a decimal point are interpreted as int literals, all with a decimal point are interpreted as double literals
### Primitive conversion
* Java automatically promotes from smaller to larger data types, but not the other way round (except int literals which can narrow to byte, short and char types if the value fits, and char literals which can narrow to byte, if the value fits)
```java
int x = 1.0; // DOES NOT COMPILE -> double can't assign to int
short y = 1921222; // DOES NOT COMPILE -> too large to fit into short type
byte b = 128;
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
* numeric underflow again - here is a good example:
```java
byte b = (byte)128;
System.out.println(b); // -128     -> 127 is the largest possible byte, so it wraps one place to the next possible value

byte b2 = (byte) -129;
System.out.println(b2); // 127
```
### Compound assignment operator
```java
int x = 2;
x *= 3;
System.out.println("x is " + x); // x is 6
```
###### Automatic casting
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
* `=` can be chained:
```java
int x, y, z;
int a = x = y = z = 5;
// a, x, y, z are all equal to 5
```
* however, all variables to the right of the first `=` must have already been declared, or it won't compile

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
* it doesn't matter how many other conditions are after - if the first condition of `&&` is false or `||` is true, *everything* on the other side won't be evaluated:
```java
boolean a = (a = false), b = (b = false), c = (c = false), d = (d = false);
boolean foo = (a = true) || (b = true) && (c = true) || (d = true);
System.out.println(a + " " + b + " " + c + " " + d);
```
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
* what constitutes a single statement is confusing - it can be a block of several lines, an entire if-elseif-else statement - see [single statements](#single-statements)
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
* both sides need to return a value - hence the following is invalid:
```java
int i = 0;
while(true){
	i++;
	i % 2 == 0 ? break : continue; // does not compile -> Both sides of : should return some value
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
* **a switch statement must have at least one of a valid case label or a default label** - if neither are present, it won't compile
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
* the last two can be summed up as: **no duplicate cases**
* point 3 essentially says that switch uses numeric promotion/conversion - if you can't do `byte b = 300;` then it won't compile
#### A bit more on switch statements
* when used with a String uses the equals() method to compare the given expression to each value in the case statement and will therefore throw a NullPointerException if the expression is null
* it can match for multiple cases:
```java
int x = 2;
switch(x) {
	case 1: case 2:
		System.out.println("It is less than three");
		break;
	default:
		System.out.println("It is not less than three");
}
```
* variable declarations made inside the switch statement happen whether or not the case is reached:
```java
int x = 2;
switch(x) {
	case 1:
		String s = "One";
		System.out.println(s);
		break;
	case 2:
		s = "Two";
		System.out.println(s);
}
```
* as long as a variable is declared inside a case label, before it is initialized (or on the same line), it declares it
* the reason is, I think, that the case blocks don't have any scope - so the declaration can only happen once within the switch scope
> case 2 is in the same block as case 1 and appears after it, even though case 1 will never execute... so the local variable is in scope and available for writing despite you logically never "executing" the declaration. (A declaration isn't really "executable" although initialization is.)

> Declarations aren't "run" - they're not something that needs to execute, they just tell the compiler the type of a variable. (An initializer would run, but that's fine - you're not trying to read from the variable before assigning a value to it.)
### while
* braces optional for single statement
* condition expression in a while header is required
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
* braces optional for single statement
* boolean condition expression is required
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
* the initialization, boolean and update statements themselves can be blank (unlike while condition), but the separating semicolons must be present:
```java
for( ; ; ){} // compiles - will run an infinite loop
for(){} // DOES NOT COMPILE
for(;){} // DOES NOT COMPILE
```
* the update statement happens at the end of an interation of the loop
* if the boolean condition is blank, it evaluates to true:
```java
for( ; ; ){
	// neverending loop
}
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
* this might be a bit confusing - basically, the initialization block (the bit before the first `;`) should be treated like another line of code in the same scope, in terms of whether it compiles
* so don't redeclare anything in the same scope
* don't try to declare two types in the same statement
* it means you can do things like:
```java
for(int x, y, z = 0; z < 5; x = y = ++z){}`
```
* but you wouldn't be able to use x or y inside the for block, as they might not have been initialized (only after the update statement has run)
### for each
* format: for(`dataType` `variableName` : `collection`){}
* `collection` must be an object that implements Iterable, e.g. array, Collection, List, ArrayList, Stack - it cannot iterate over Map
* curly braces `{}` optional for a one-line statement
```java
int[] arr = new int[] {1, 2, 3, 4};
for(int i : arr){
	System.out.print(i);
} // OUTPUT -> 1234
```
#### Unreachable lines
* code in a for or while loop has to be reachable to compile
* if the while condition (or the middle condition in a for loop) evaluate to false at compile time, an compiler error will occur:
```java
while(false) { // does not compile
	System.out.println("Foo"); // unreachable code
}
for(int i = 0; false; i++) { // does not compile
	System.out.println("Fooble"); // unreachable code
}
```
```java
for(int i = 0; i < 100; i++){
	if(i % 2 == 0){
		break;
	} else {
		continue;
	}
	++i; // DOES NOT COMPILE - unreachable as it will always break or continue before these lines
	System.out.println(i); // as above
}
```
```java
public class UnreachableCode {
	static boolean bool1 = false;
	static final boolean bool2 = false;
	public static void main(String[] args) {
		while(bool1) {
			System.out.println("Foo"); // bool1 is not a compile-time constant so this is reachable
		}
		
		while(bool2) {// does not compile
			System.out.println("Foo"); // bool2 is compile-time constant - code not reachable
		}
	}
}
```
* this is not the case with enhanced for loops (there is no condition), do-while loops (the condition is checked after the block has run), or if statements (which allow branching unreachable code)
#### final variables in loops
* final variables are allowed to be assigned in the initialization of a for loop:
```java
int i = 0;
for(final int j = 99; i < 3; i++) {
	System.out.println(j);
}
```
* compiles because the final value is not changed. You would not be allowed to use it in the increment block. If used in the condition block it would endlessly loop unless it was looking for that value
* also allowed in a for-each, which is more useful:
```java
int[] ints = {1, 2, 3};
for(final int item : ints) {
	// item can't be modified
}
```
* not allowed in `while` or do-while
* they can also be declared in the body of a loop, as they will go out of scope at the end of each iteration:
```java
int i = 0;
while(i < 6){
	i++;
	final String s = "Iteration number " + i;
}
```
### Labels
```java
int[][] myComplexArray = { {1, 2, 3},{4, 5, 6},{7, 8, 9} };
OUTER_LOOP: for(int[] mySimpleArray : myComplexArray){
	INNER_LOOP: // code
}
```
* You can apply a label to any code block or a block level statement (such as a for statement) but not to declarations. For example: loopX : int i = 10;
* Labeled blocks only work with break and continue statements, but they can be before other statements (**not variable declarations**) - they just won't do anything
* `break` and `continue` on a label must be called within its scope
```java
int x;
label1 : x = 1; // legal but uncallable
label2: while(x <5) {
	break label1; // DOES NOT COMPILE - label1 is not in scope (its scope is just that initialization line so this can never be called)
}
```
```java
myForLoop: for(int i = 0; i < 5; i++) {
	System.out.println(i);
}
continue myForLoop; // DOES NOT COMPILE - not in scope
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
* can compare char to numeric types
  - a char representing a numeric type wont' match (the ASCII number of '1' is 49)
```java
int x = 1;
double y = 2;
boolean z = false;
x == y; // compiles -> int promotes to double -> true
x == z; // DOES NOT COMPILE -> boolean not numeric

byte b = 1;
char c = '1';
b == x; // compiles -> false

// however
int bla = 49;
bla == c; // compiles -> true // but you aren't expected to know all ASCII codes so this won't come up
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

[Looping through an ArrayList](#looping-through-an-arrayList)

[Caching and Wrapper Class Equality](#caching-and-wrapper-class-equality)

[Autoboxing](#autoboxing)

[Converting between array and List](#converting-between-array-and-list)

[Dates and Times](#dates-and-times)

[Querying Dates and Times](#querying-dates-and-times)

[Manipulating Dates and Times](#manipulating-dates-and-times)

[Periods](#periods)

[Formatting Dates and Times](#formatting-dates-and-times)

[Parsing dates and Times](#parsing-dates-and-times)

---
### Strings
* Strings are *immutable*, and they are **final** -> the String class can't be extended
* Strings can be created in three ways:
  - with a constructor:
  ```java
  String s1 = new String(); // an empty string
  
  String s2 = new String("Foo"); // takes String as an argument
  
  StringBuilder sb = new StringBuilder("Foo");
  String s3 - new String(sb); // takes StringBuilder as an argument
  
  char[] chars = {'f', 'o', 'o'};
  String s4 = new String(chars); // takes an char array as an agrument
  ```
  - with a String literal assignment:
  ```java
  String s5 = "Foo";
  ```
  - assignment to an existing variable:
  ```java
  String s6 = "Foo";
  String s7 = s6;
  ```
* I suspsect the important constructors for the exam are: `String()`, `String(char[] value)`, `String(StringBuilder builder)` (also StringBuffer), `String(String original`, and String("StringLiteral")
* using a constructor always creates a new String object in memory, whereas using a string literal only creates a new object if it isn't already in the string pool
* using `intern()` will add a String object to the String pool (if not already present)
* **String pool**
  - string literals are stored in the string pool
  - they are not garbage collected
  - they are added to the string pool any time a new String literal is used e.g. `System.out.println("Foo");` adds a new String object to the string pool
* concatenation
```java
System.out.println("hello" + 1 + 2); // -> hello12
System.out.println(1 + 2 + "hello"); // -> 3hello
int three = 3;
String four = "4";
System.out.println(1 + 2 + three  + four); // -> 64
```
* using `println` on a null String reference prints `null`, rather than nothing:
```java
public class PrintingNullString {
	static String s1 = "Hello";
	static String s2;
	public static void main(String[] args) {
		System.out.println(s1 + " " + s2); // Hello null
	}
}
```
* *an aside* - `println` doesn't call `toString()`, as this would result in a null pointer reference - it calls `String.valueOf(s2).toString()`
* `println()` can print any object - or print the result of a method or operation it is called on (e.g. `println(1 + 2);`_)
* careful though - it must be passed either a valid object or a valid operation:
  - `println(1 + 2 + "" + null + null);` - this compiles - `""` is a String so converts and concatenates
  - `println(1 + 2 + null + null);` - `2 + null` is not valid so compilation fails
### String Methods
* `length()` -> `"Hello".length()` -> `5`
* `charAt(int index)` -> `"Hello".charAt(4);` -> `o` -> As per the API documentation for charAt, it throws `IndexOutOfBoundsException` if you pass an invalid value. In practice, the message you will get back is `StringIndexOutOfBoundsException`
  - if there is a question on this - answer is that "it throws `IndexOutOfBoundsException`"
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
  - `substring(int beginIndex, int endIndex)` -> `"s".substring(0,1);` -> `s` (**n.b. endIndex not included** - the length is endIndex - beginIndex)
* `equals(String str)` -> `"Hello".equals("Hello");` -> `true` (*overriden equals, compares characters*)
* `equalsIgnoreCase(String str)` -> *case-insensitive equals*
* `startsWith(String str)` -> `"Hello".startsWith("H");` -> `true`
* `endsWith(String str)` -> `"Hello".endsWith("H");` -> `false` **remember these take a String as their argument** - the following won't compile:" `"Hello".startsWith('H');`
* `contains(String str)` -> `"Hello".contains("Hell");` -> `true`
* replace
  - `replace(char oldChar, char newChar)` -> `"Hello".replace('H', 'J');` -> `Jello` -> **returns a new object if the string changes, otherwise the same object**
  - `replace(String oldStr, String newStr)` -> `"Hello".replace("lo", "icopter");` -> `Helicopter` -> **returns a new object even if there is no change**
* `trim()` -> `"\t  a b c \n".trim();` -> `a b c` (*trims trailing whitespace, tab and newline*)
* `concat(String s)` -> `"Hello".concat("World");` -> `HelloWorld`
* `intern()` -> the intern() method creates an exact copy of a String object in the heap memory and stores it in the String constant pool, unless it already exists in which case it points to that object:
```java
String s1 = "String";
String s2 = new String("String");
System.out.println(s1 == s2); // false
String s3 = s2.intern();
System.out.println(s1 == s3); // true
```
#### Chaining methods
* you can chain as many methods as you want - each method creates a new string object
* remember, strings are immutable so the original object isn't affected
```java
String s = "Hello"
s.replace("lo", "icopter").substring(3, 5) + "e"; // -> ice
s; // -> Hello
```
#### String methods that accept char
* the only String methods that have `char` as an argument (from those listed above) are:
  - `replace(char oldChar, char newChar)`
  - `indexOf(char ch)`
### StringBuilder
* StringBuilder objects are mutable
```java
StringBuilder sb = new StringBuilder("Hello");
sb.append("World"); // HelloWorld
```
* mutates the original object and returns it - String just returns a new object
* signatures:
  - `new StringBuilder()` -> *empty StringBuilder, with intial size of 16 reserved character slots*
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
* `subSequence()` -> similar to substring but returns `CharSequence`
* `toString()` -> `new StringBuilder("Hello").toString();` -> `Hello` **return String object**
#### the return type of all the following methods is StringBuilder (i.e., the object the method was called on)
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
* `replace(int start, int end, String str)` -> `new StringBuilder("Hello").replace(2, 4, "zz")` -> `Hezzo`
  - Replaces the characters in a substring of this sequence with characters in the specified String.
  - **n.b.** different to `String`'s replace method which takes a String to find as its first arg and the replacement String as its second
* `setLength(int len)`
  - Sets the length of this String buffer.
  - `StringBuilder sb = new StringBuilder(); sb.setLength(100); System.out.println(sb.length());` -> 100
* `ensureCapacity(int minCapacity)`
  - Ensures that the capacity of the buffer is at least equal to the specified minimum.
  - if not, modifies the capacity to be the larger of the minimumCapacity argument or twice the old capacity, plus 2.
  - `StringBuilder sb = new StringBuilder(); sb.setLength(100); sb.getCapacity(101); System.out.println(sb.capacity());` -> 202
* `capacity()` -> returns the capacity
* the default capacity is 16 -> `StringBuilder sb = new StringBuilder(); System.out.println(sb.capacity());` -> 16
#### StringBuffer
* StringBuffer is an older, thread-safe (therefore, less efficient) version of StringBuilder which has the same methods
* **StringBuffer is final, and therefore can't be extended**
##### Difference between mutability in String and StringBuilder
```java
StringBuilder sb = new StringBuilder("Hello");
System.out.println(sb.append("World"); // -> HelloWorld
System.out.println(sb); // -> HelloWorld

String s = "Hello";
System.out.println(s.concat("World"); // -> HelloWorld
System.out.println(s); // -> Hello
```
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
* String things:
1. Literal strings within the same class in the same package represent references to the same String object. 
2. Literal strings within different classes in the same package represent references to the same String object. 
3. Literal strings within different classes in different packages likewise represent references to the same String object. 
4. Strings computed by constant expressions are computed at compile time and then treated as if they were literals.
5. Strings computed at run time are newly created and therefore are distinct. e.g. `"String ".trim()` will not `==` `"String"`
6. The result of explicitly interning a computed string is the same string as any pre-existing literal string with the same contents. 
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
* an arrays is an object, so it can be assigned null:
```java
int[] ints = null;
```
* if you try to access a null array's indices, it compiles but you get a NullPointerException at runtime:
```java
int[] ints = null;
int i = ints[0]; // Exception in thread -> NullPointerException
```
**arrays are mutable, but their length is fixed**
* Primitive arrays
```java
int[] myArray = new int[3];
int anotherArray[] = new int[3];
int[] anonymousArray = new int[] {1, 2, 3, 4};
int[] shorthandAnonymousArray = {1, 2, 3, 4};
```
* shorthand anonymous arrays (i.e. not using `new`) have to be declared and initialized on the same line:
```java
int[] anonArray;
anonArray = {1, 2, 3, 4}; // DOES NOT COMPILE
```
* but longhand anonymous arrays (i.e. using `new`) don't:
```java
int[] anonArray;
anonArray = new int[]{1, 2, 3, 4}; // compiles
```
* as long as the initialization is on the same line - you can't do the following:
```java
int[] arr = new int[];
arr = {1, 2, 3}; // DOES NOT COMPILE
```
* anonymous arrays can't specify the size of the array:
```java
int[] anonArray = new int[4]{1, 2, 3, 4}; // DOES NOT COMPILE
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
* the first array must always specify size (which must evaulate to an int value):
```java
int[] ints = new int[]; // DOES NOT COMPILE
int[] ints2 = new int[3]; // compiles
int[][] multi = new int[3][]; // compiles - first array has specified size
```
* this can be as an expression as long as it evaluates to an int value:
```java
byte a = 1;
short b = 2;
int[] ints = new int[a + b];
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
* *not an array method, but need to know*:
  - the `isArray()` method of a Class returns true. For example, `int[] ints = {1}; ints.getClass().isArray()` will return true.
  - `getClass()` returns the class of the object, not the reference
* `equals(type[] array)` - not overridden so looks for reference equality
```java
int[] arr = {1, 2, 3};
int[] arr2 = {1, 2, 3};
System.out.println(arr.equals(arr2)); // false
```
* `clone()` -> returns a new copy of the array


* **arrays have a property, `length` - this is not a method so has no brackets `()`**
* `Arrays.binarySearch(type[] array, Type key)`-> *static method of `java.util.Arrays`*
* must be used on a sorted array
```java
import java.util.*;
public class Main {
	public static void main(String... args) {    
		int[] nums = {4, 6, 8, 2};
		Arrays.sort(nums);
		Arrays.binarySearch(nums, 2); // -> 0 -> index of 2
		Arrays.binarySearch(nums, 5); // -> -3 -> negative (index + 1) of where it would go
  }
}
```
* *remember for the exam, if line starts after 1 or is a snippet, assume imports are present*
* **if the line starts on 1, CHECK ALL IMPORTS ARE PRESENT - if it uses for example LIst<>, MAKE SURE java.util.List is imported**
* *if the array is unsorted, the result is unpredictable - look for answer like 'undefined' or 'unpredictable'*
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
 * the number of dimensions in the declaration must match the allocation:
 ```java
 int[] ints = new int[3][]; // DOES NOT COMPILE
 int[][] ints2 = new int[3]; // DOES NOT COMPILE
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
`java.util.ArrayList` -> (extends `java.util.AbstractList`)
* mutable object, no fixed size
* An ArrayList is backed by an array. 
  - the elements are stored in an array (the expression "backed by an array" means that the implementation of ArrayList actually uses an array to store elements)
* creating:
```java
// before Java 5 and generics:
ArrayList arrList1 = new ArrayList();
ArrayList arrList2 = new ArrayList(10); // reserves slots for characters, but not fixed size (as is mutable) c.f. StringBuilder
ArrayList arrList3 = new ArrayList(arrList2);
// post-Java 5, using generics:
ArrayList<String> arrList4 = new ArrayList<String>();
// post-Java 7:
ArrayList<String> arrList5 = new ArrayList<>();
```
* initializing an ArrayList with a size *reserves slots* but does not create an ArrayList of that size:
```java
List<Integer> arrList = new ArrayList<>(5);
arrList.size(); // 0
arrList.get(0); // throws IndexOutOfBoundsException at runtime
```
### ArrayList Methods
* add
  - `add(E element)` -> returns `boolean` (i.e. `true`)
  - `add(int index, E element)` -> void
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
```
  - uses `equals()` to compare equality so careful when using types such as `StringBuilder` which don't override `equals()`
* `remove(int index)` -> `object` or `IndexOutOfBoundsException`
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
* `size()` -> `int` -> **String has `length()`, arrays have `length`, ArrayList has `size()`**
* `clear()` -> `void` *removes all elements from ArrayList*
* `contains(Object obj)` -> `boolean` *uses equals() method of class (so String overrides)*
* `indexOf(Object o)` -> `index` -> returns index of first occurence or -1 if not found
* `lastIndexOf(Object o)` -> `index` -> returns index of last occurence or -1 if not found
* `equals(Object o)` -> `boolean` *compares the specified object with this list for equality*
* `addAll(Collection c)` -> adds the `Collection` (or subclass of `Collection` e.g. `ArrayList`)
* `clone()` -> shallow copy - creates a new copy of the ArrayList, but not copies of its elements which still refer to the initial objects (until modified - see below)
```java
ArrayList<Integer> list = new ArrayList<>();
list.add(666); list.add(777); list.add(888);

ArrayList<Integer> clonedList = (ArrayList<Integer>)list.clone();
		
System.out.println(list == clonedList); // false - clone has created a new copy of the list
System.out.println(list.get(0) == clonedList.get(0)); // true = shallow copy creates a new copy of the list but not the objects within it - so the object point to the same place in memory
		
list.set(0, 999);
System.out.println(list.get(0) == clonedList.get(0)); // false - when set is used on one of the collections, a new object is created 
```
  - must be of the same type e.g. `List<String>` and `ArrayList<String>`
* `addAll(int index, Collection c)` -> as above but at the specified index
* `subList(int fromIndex, int toIndex)` -> returns a new List object of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive.
```java
int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9};
List<Integer> list = new ArrayList<>();
for(int i : ints) list.add(i);
List<Integer> subList = list.subList(5, 9);
System.out.println(subList); // [6, 7, 8, 9]
```		
* the class `java.util.Collections` contains the method `sort()` which takes a List as an argument:
```java
List<Integer> list = new ArrayList<>();
list.add(10);
list.add(5);
list.add(1);
System.out.println(list.toString()); // -> [10, 5, 1]
Collections.sort(list);
System.out.println(list.toString()); // -> [1, 5, 10]
```

#### Printing an ArrayList

* ArrayList can be printed directly, which will output the elements surrounded by square brackets:

```java
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);
list.add(3);
System.out.println(list); // [1, 2, 3]
```

### Looping through an ArrayList
* you can loop through an ArrayList using a for loop:
```java
import java.util.*;

public class WithForLoop {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
	}
}
	
```
* using an enhanced for loop:
```java
import java.util.*;

public class WithEnhancedForLoop {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);
		for(Integer i : list){
			System.out.println(i);
		}
	}
}
```
* using an `Iterator` (`java.util.Iterator`):
```java
import java.util.*;

public class WithIterator {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);
		Iterator<Integer> iterator = list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
```
* using a `ListIterator`:
```java
import java.util.*;

public class WithListIterator {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3); list.add(4); list.add(5);
		Iterator<Integer> */or ListIterator<Integer>*/ listIterator = list.listIterator();
		while(listIterator.hasNext()){
			System.out.println(listIterator.next());
		}
	}
}
```
* `Iterator` and `ListIterator` are interfaces in `java.util`
* `ListIterator` implements `Iterator`
* using an iterator allows you to remove elements as you loop through, which is not possible with a for loop or enhanced for loop
* the iterator is linked to the ArrayList when it is initialized - if modifications are made to the ArrayList after the iterator is initialized, it will throw a runtime error:
```java
import java.util.*;

public class ConcurrentModification {
	public static void main(String... args) {
		List<Integer> list = new ArrayList<>();
		list.add(1); list.add(2); list.add(3);
		Iterator<Integer> iterator = list.listIterator();
		list.add(4);
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
/* 
* Exception in thread "main" java.util.ConcurrentModificationException
*         at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
*         at java.util.ArrayList$Itr.next(ArrayList.java:851)
*         at ConcurrentModification.main(ConcurrentModification.java:10)
*/
```
### Wrapper Classes and ArrayList
* **Wrapper classes are final, and therefore can't be extended**
* `parseInt(String str)` -> `int` *String to primitive*
* `valueOf(String s)` -> `Integer` *String to wrapper class*
* **Character has a `valueOf` method, which takes a character, but not a String, and has no `parseChar`**
* all throw NumberFormatException for incorrect values **except** `parseBoolean` which returns `false`
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
| Character      | *n/a*                        | Character.valueOf('c')   |
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
### Caching and Wrapper Class Equality
> Wrapper classes Byte, Short, Integer, and Long cache objects with values in the range of -128 to 127. The Character class caches objects with values 0 to 127. These classes define inner static classes that store objects for the primitive values -128 to 127 or 0 to 127 in an array. If you request an object of any of these classes, from this range, the valueOf() method returns a reference to a predefined object; otherwise, it cre- ates a new object and returns its reference
* compare with Boolean, whose cached instances are accessible directly because only two exist: static constants Boolean.TRUE and Boolean.FALSE.
* Wrapper classes Float and Double don’t cache objects for any range of values.
* this has an effect on comparing classes:
```java
public class CachingAndWrapperClassEquality {
	public static void main(String[] args) {
		
		Integer i = Integer.valueOf("1");
		Integer j = 1;
		System.out.println(i == j); // true
		
		Integer k = new Integer(1);
		Integer l = new Integer(1);
		System.out.println(k == l); // false
		
		Integer m = Integer.valueOf("128");
		Integer n = Integer.valueOf("128");
		System.out.println(m == n); // false
	}
}
```
* because wrapper classes are immutable, performing operations on them will return a new object:
```java
public class ImmutableWrappers {
	public static void main(String[] args) {
		
		Integer i = Integer.valueOf(1);
		Integer j = i;
		
		System.out.println(i == j); // true
		System.out.println(++i == ++j); // true
		
		Integer k = Integer.valueOf(128);
		Integer l = k;
		
		System.out.println(k == l); // true
		System.out.println(++k == ++l); // false
	}
	
}
```
* essentially: `Byte`, `Short`, `Integer` or `Long` between -128 and 127 created using `valueOf` or through autoboxing will point to the same object and have `==` equality
* the same for `Character` between 0 and 127
* constructors always create new objects
* you can't compare (`==`) wrapper classes of different types as `==` only works with the same object type e.g. `Double.valueOf(1) == Integer.valueOf(1);` doesn't compile
#### Using equals() on wrapper classes
* the signature of the `equals()` method is `boolean equals(Object o)`, so it can take any object
* the equals methods of all wrapper classes first checks if the two object are of same class or not. If not, they immediately return false.
```java
public static void main(String[] args) {
	Integer num1 = Integer.valueOf(1);
	Integer num2 = Integer.valueOf(1);
	Short num3 = Short.valueOf((short)1); // need to case as valueOf() takes the primitive and short is smaller than int
	
	System.out.println(num1.equals(num2)); // true
	System.out.println(num1.equals(num3)); // false
}
```

#### How to create a wrapper instance
* wrapper classes are created by:
  - assignment e.g. `Boolean a = true;`, `Boolean b = Boolean.TRUE;`
  - constructors e.g. `Boolean e = new Boolean(true);`, `Boolean d = new Boolean("truE");`
  - static methods e.g. `Boolean.valueOf(true);`, `Boolean.valueOf("TrUe");`
* this is true for all wrapper classes, below are the full Boolean signatures as an example
* **n.b.** String argument is non-case-sensitive
#### You need to remember the following points about Boolean:
1. Boolean class has two constructors - Boolean(String) and Boolean(boolean)
  - The String constructor allocates a Boolean object representing the value true if the string argument is not null and is equal, ignoring case, to the string "true".
  - Otherwise, allocate a Boolean object representing the value false.
  - Examples: new Boolean("True") produces a Boolean object that represents true. new Boolean("yes") produces a Boolean object that represents false.
  - The boolean constructor is self explanatory.
2. Boolean class has two static helper methods for creating booleans - parseBoolean and valueOf.
  - Boolean.parseBoolean(String ) method returns a primitive boolean and not a Boolean object
  - (Note - Same is with the case with other parseXXX methods such as Integer.parseInt - they return primitives and not objects).
  - The boolean returned represents the value true if the string argument is not null and is equal, ignoring case, to the string "true".
  - Boolean.valueOf(String ) and its overloaded Boolean.valueOf(boolean ) version, on the other hand, work similarly but return a reference to either Boolean.TRUE or Boolean.FALSE wrapper objects.
  - Observe that they dont create a new Boolean object but just return the static constants TRUE or FALSE defined in Boolean class.
3. When you use the equality operator ( == ) with booleans, if exactly one of the operands is a Boolean wrapper, it is first unboxed into a boolean primitive and then the two are compared (JLS 15.21.2).
  - If both are Boolean wrappers, then their references are compared just like in the case of other objects.
  - Thus, new Boolean("true") == new Boolean("true") is false, but new Boolean("true") == Boolean.parseBoolean("true") is true.
#### Boolean members
* Constructors:
  - `Boolean(boolean value)`
  - `Boolean(String s)`
* Fields
  - `static Boolean`	`FALSE`
  - `static Boolean`	`TRUE`
* Methods
  - `static boolean`	`parseBoolean(String s)` -> returns primitive
  - `static Boolean`	`valueOf(boolean b)` -> returns wrapper class
  - `static Boolean`	`valueOf(String s)` -> returns wrapper class
  - `boolean`			`booleanValue()` -> returns primitive
* **so** - a wrapper class's static methods are:
 - `valueOf()`, which takes a String or a primitive and returns the wrapper class
 - if passing a primitive to `valueOf()`, it must be the same type, or promotable to the type, or otherwise cast:
```java
Integer i = Integer.valueOf(6); // same primitive type
Double d = Double.valueOf(6); // int promotes to double
Short s = Short.valueOf(  (short)6 ); // int cast to short
```
 - parsePrimitiveName e.g. `parseInt()`, which takes a String and returns a primitive
* it also has the non-static primitiveNameValue() e.g. `intValue()`, which turns a wrapper class into a primitive:

```java
Double d1 = Double.valueOf(5);
double d2 = d1.doubleValue();
```

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
// equivalent of saying double d = null.doubleValue() -> NullPointerException
* don't worry about the doubleValue method - it takes a Double and returns a double, but hopefully won't be on the exam
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
* passing `remove()` for example, 1, is an `int` not an object - this will be an index
* passing `remove()` `new Integer(1)` or `Integer.valueOf("1")` is an object
* to explicitly remove the object:
```java
List<Integer> integerList = new ArrayList<>();
integerList.add(1);
integerList.add(2);
integerList.remove(new Integer(1)); // or integerList.remove(Integer.valueOf("1");
System.out.print(integerList.toString()); // [2]
```
#### Autoboxing and promotion
* Java can't promote a primitive and then autobox it:
```java
List<Double> list = new ArrayList<>();
list.add(1); // DOES NOT COMPILE -> int cannot be converted to Double
```
```java
List<Integer> list = new ArrayList<>();
short x = 1;
list.add(x); // DOES NOT COMPILE -> short cannot be converted to Integer
```
* you can overcome this by casting ( `list.add( (int) x));` or using one of the wrapper class's static methods to convert it
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
* you can however change the elements within it, and call e.g. Collections.sort() on it
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
* **all immutable, all have no public constructor**
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
#### LocalTime constants
* `LocalTime.MIN` —> Minimum supported time (00:00)
* `LocalTime.MAX` —> Maximum supported time (23:59:59.999999999)
* `LocalTime.MIDNIGHT` —> midnight (00:00)
* `LocalTime.NOON` —> midday (12:00)
#### System time
* `static`	`now()` -> e.g. `LocalTime.now();`, `LocalDate.now();`, `LocalDateTime.now();`
### Querying Dates and Times
* `static`	`getDayOfWeek()` -> returns `DayOfWeek enum` -> available to `LocalDate` and `LocalDateTime`
* `static`	`getDayOfMonth()` -> returns `int` -> available to `LocalDate` and `LocalDateTime`
* `static`	`getDayOfYear()` -> returns `int` -> available to `LocalDate` and `LocalDateTime`
* `static`	`getDayOfYear()` -> returns `int` -> available to `LocalDate` and `LocalDateTime`
* `static`	`getMonth()` -> returns `Month enum` -> available to `LocalDate` and `LocalDateTime`
* `static`	`getMonthValue()` -> returns `int` -> available to `LocalDate` and `LocalDateTime`
* `static`	`getYear()` -> returns `int` -> available to `LocalDate` and `LocalDateTime`

* `static`	`getNano()` -> returns `int` -> available to `LocalTime` and `LocalDateTime`
* `static`	`getSecond()` -> returns `int` -> available to `LocalTime` and `LocalDateTime`
* `static`	`getMinute()` -> returns `int` -> available to `LocalTime` and `LocalDateTime`
* `static`	`getHour()` -> returns `int` -> available to `LocalTime` and `LocalDateTime`

#### isBefore, isAfter
```java
import java.time.*;

public class IsAfterAndIsBefore {
	public static void main(String[] args){
		LocalDate d1 = LocalDate.of(1999, 12, 31);
		LocalDate d2 = LocalDate.of(2000, 1, 1);
		System.out.println(d1.isBefore(d2)); // true
		System.out.println(d1.isAfter(d2)); // false
		
		LocalTime t1 = LocalTime.of(11, 59);
		LocalTime t2 = LocalTime.of(00, 00);
		System.out.println(t1.isBefore(t2)); // false
		System.out.println(t1.isAfter(t2)); // true
		
		LocalDateTime ldt1 = d1.atTime(t1);
		LocalDateTime ldt2 = t2.atDate(d2);
		System.out.println(ldt1.isBefore(ldt2)); // true
		System.out.println(ldt1.isAfter(ldt2)); // false
	}
}
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
#### with
* `withDayOfMonth(int dayOfMonth)` -> Returns a copy of this LocalDate with the day-of-month altered
* `withDayOfYear(int dayOfYear)` -> Returns a copy of this LocalDate with the day-of-year altered
* `withMonth(int month)` -> Returns a copy of this LocalDate with the month-of-year altered
* `withYear(int year)` -> Returns a copy of this LocalDate with the year altered

* `withHour(int hour)` -> Returns a copy of this LocalTime with the hour-of-day altered.
* `withMinute(int minute)` -> Returns a copy of this LocalTime with the minute-of-hour altered.
* `withNano(int nanoOfSecond)` -> Returns a copy of this LocalTime with the nano-of-second altered.
* `withSecond(int second)` -> Returns a copy of this LocalTime with the second-of-minute altered.

* `**LocalDateTime` has access to all the above methods, returning a `LocalDateTime` instance**
* the methods are chainable:
```java
import java.time.*;

public class LocalDateTimeWith {
	public static void main(String[] args) {
		LocalDateTime dtm = LocalDateTime.of(2020, 2, 11, 21, 31);
		System.out.println(dtm.withYear(2021)); // -> 2021-02-11T21:31
		System.out.println(dtm.withHour(0).withMinute(0)); // -> 2020-02-11T00:00
	}
}
```
#### at
* combine a LocalDate and LocalTime to create a LocalDateTime object
```java
import java.time.*;

public class LocalDateAndTimeAt {
	public static void main(String[] args) {
		LocalDate dt = LocalDate.of(2020, 2, 11);
		LocalTime tm = LocalTime.of(21, 31);
		
		LocalDateTime dtm1 = dt.atTime(tm);
		System.out.println(dtm1); // -> 2020-02-11T21:31
				
		LocalDateTime dtm2 = tm.atDate(dt);
		System.out.println(dtm2); // -> 2020-02-11T21:31
		
		LocalDateTime dtm3 = dt.atTime(12, 31);
		System.out.println(dtm3); // -> 2020-02-11T12:31
		
		LocalDateTime dtm4 = dt.atTime(12, 31, 33);
		System.out.println(dtm4); // -> 2020-02-11T12:31:33
		
		LocalDateTime dtm5 = dt.atTime(12, 31, 33, 400);
		System.out.println(dtm5); // -> 2020-02-11T12:31:33.000000400
	}
}
```
* **n.b. there is no overloaded `atDate()` - only  `atDate(LocalDate dt)`**
#### epoch
* `LocalDate`	`toEpochDay()` -> `long` -> Converts this date to the Epoch Day (the count of days since January 1 1970 00:00:00 GMT)
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
* can't be used with LocalTime -> will throw a runtime exception -> use `Duration` instead (also implements *TemporalAmount*)
* but can be used with LocalDateTime - it will make the date adjustments and leave the time as it was:

```java
import java.time.LocalDateTime;
import java.time.Period;

public class PeriodAndLocalDateTime {
	public static void main(String args[]) {
		LocalDateTime birth = LocalDateTime.of(2020, 2, 12, 21, 31);
		Period aYearAndADay = Period.of(1, 0, 1);
		System.out.println(birth.plus(aYearAndADay)); // 2021-02-13T21:31
	}
}
```

#### between
```java
Period periodBetween = Period.between(carnivalStart, carnivalEnd);
System.out.println(periodBetween);
```

* **LocalDateTime etc instance methods e.g. `plusYears()` can be chained** -> `dt.plusDays(1).plusWeeks(2);` -> the date plus two weeks and a day
* **Period static methods e.g. `ofYears()` can't be chained - they will compile but only the last method will take effect** -> `Period.ofWeeks(2).ofDays(1);` -> a period of 1 day
* **Period instance methods e.g. `minus` can be chained**
* What is the output of the following?:

```java
import java.time.LocalDate;
import java.time.Period;

public class ChainingPeriodMethods {
	public static void main(String[] args) {
		LocalDate ld = LocalDate.of(2020, 2, 12);
		Period p = Period.ofYears(1000).ofWeeks(12).ofDays(1);
		p.plusDays(10);
		p = p.plusDays(1).plusDays(1);
		System.out.println(ld.plus(p));
	}
}

```

* The output is `2020-02-15`
  - the static methods `ofYears()`, `ofWeeks()` & `ofDays()` don't chain, so it creates a period of a day.
  - Period is immutable so `p.plusDays(10);` makes no change to the object
  - `p = p.plusDays(1).plusDays(1);` makes a new assignment, so the new value uses the chained methods on the instance, which adds two days

#### parse period
* can also be instantiated with static `parse()` which takes ISO-8601 period formats `PnYnMnD` and `PnW` as arguments:
> The string starts with an optional sign, denoted by the ASCII negative or positive symbol. If negative, the whole period is negated. The ASCII letter "P" is next in upper or lower case. > There are then four sections, each consisting of a number and a suffix. At least one of the four sections must be present. The sections have suffixes in ASCII of "Y", "M", "W" and "D" for years, months, weeks and days, accepted in upper or lower case. The suffixes must occur in order. The number part of each section must consist of ASCII digits. The number may be prefixed by the ASCII negative or positive symbol. The number must parse to an int.
>
>The leading plus/minus sign, and negative values for other units are not part of the ISO-8601 standard. In addition, ISO-8601 does not permit mixing between the PnYnMnD and PnW formats. >Any week-based input is multiplied by 7 and treated as a number of days.
>
>For example, the following are valid inputs:
>
>   "P2Y"             -- Period.ofYears(2)
>   "P3M"             -- Period.ofMonths(3)
>   "P4W"             -- Period.ofWeeks(4)
>   "P5D"             -- Period.ofDays(5)
>   "P1Y2M3D"         -- Period.of(1, 2, 3)
>   "P1Y2M3W4D"       -- Period.of(1, 2, 25)
>   "P-1Y2M"          -- Period.of(-1, 2, 0)
>   "-P1Y2M"          -- Period.of(-1, -2, 0)
#### querying period
```java
Period period = Period.of(2,4,40);
System.out.println(period.getYears()); // 2
System.out.println(period.getMonths()); // 4
System.out.println(period.getDays()); // 40
```
#### isZero() and isNegative()
* instance methods - `isZero()` returns boolean true if all three units are zero - 
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
* `static`	`parse()`
* `static`	`parse(Formatter f)`
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
* if used without a formatter, must match the pattern `yyyy-MM-dd` for LocalDate and `HH-mm-ss` for LocalTime exactly
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
* must contain a return statement matching return type (**OR** throw an error) that is reached by all branches
* if void, return statement is optional; either omit or return with no value
* **return value can be a subclass of the return type**
* **return value can be a primitive that automatically promotes to the return type**
```java
public class ReturnValues {
	static int foo() {
		byte b = 1;
		return b;
	}
	static Parent parent() {
		Child c = new Child();
		return c;
	}
}
class Parent {}
class Child extends Parent {}
```
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
* **`this` isn't available in a static context** - so you wouldn't be able to call it from `main()`
* constructors can't call themselves:
```java
public class RecursiveConstructorInvocation {
	RecursiveConstructorInvocation() { // DOES NOT COMPILE -> error: recursive constructor invocation
		this();
	}
}
```
* nor can they refer back to themselves (call a constructor that has already been called):
```java
public class RecursiveConstructorInvocation {
	RecursiveConstructorInvocation(int i){
		this("foo");
	}
	
	RecursiveConstructorInvocation(String s){ // DOES NOT COMPILE -> error: recursive constructor invocation
		this(7); 
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
* DRYs up code and adds default values to variables
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
2. static members in order they appear in the topmost class (variables and initializers)
3. instance members in order they appear in the topmost class (variables and initializers)
4. repeat 2 and 3 down through each subclass
5. the constructor (the rest of the constructor after the super() call)
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
* Benefits of (asked the mock):
  - It helps make sure that clients have no accidental dependence on the choice of representation
  - It helps avoiding name clashes as internal variables are not visible outside
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
public boolean isHat(){
	return hat;
}
public void setNumberOfHats(int num){
	numberOfHats = num;
}
public void setHat(boolean hat){
	this.hat = hat;
}
```

* **encapsulation allows setters, but doesn't require them (class is still encapsulated if it doesn't have setters)**
* **it must have setters if it is to follow JavaBeans rules**
* **Immutability requires private instance variables and no setters.**

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
		obj3.getName().concat("bar"); // but I can do this!
		System.out.println(obj3.getName()); // -> Foo
	}
}
```
### Lambdas
* lambda expression without optional parts:
  - `parameter` `arrow` `body`
  - `a ` `->` `a.isHat()`
* with optional parts:
  - `(` `optional parameter type` `parameter name` `)` `arrow` `{` `return keyword` `body` `;` `}`
  - `(Clothing a) -> {return a.isHat();}`
* rules for omitting lambda parts:
  1. parentheses can be omitted if there is only one parameter and the type is not explicitly stated
  2. curly braces `{}` can be omitted if there is only one statement
  3. the return keyword can be omitted if not using a code block `{}`
  4. semicolon can be omitted if not using a code block `{}`
  4. semicolon can be omitted if not using a code block `{}`
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
* you also can't redeclare variables in the arguments if they have been used in the method calling the lambda:

```java
public static void main(String[] args) {
	int i = 5;
	Predicate<Integer> isFive = i -> i == 5; // error: variable i is already defined in method main(String[])
	System.out.println(isFive.test(i));
}
```
* if a variable has already been declared in the method that defines a lambda, you can't reuse that variable in the lambda expression:
```java
import java.util.function.Predicate;

class MyTest{
	public static void main(String[] args){
		String s = "Foo";
		Predicate<String> isFoo = s -> s.equals("Foo"); // DOES NOT COMPILE -> error: variable s is already defined in method main(String[])
		isFoo("Foo", isFoo);
	}
	static void isFoo(String s, Predicate<String> isFoo){
		if(isFoo.test(s)){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
}
```
* you are free to use it afterwards, as it only exists in the lambda scope
### Predicates
* lambdas work with interfaces that only have one method
* these are called functional interfaces
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

#### Functional interfaces

* Any interface with a Single Abstract Method (an SAM) is a functional interface. The examples on the exam will all have a boolean method.
* the important thing to note is that where a method calls for a functional interface (or Predicate) argument, a lambda can be passed as the reference
* We can show this by creating our own functional interface:

```java
interface Age {
	boolean tooOld(Integer age);
}
public class TestingFunctionalInterface {
	public static void main(String[] args){
		checkAge(i -> i > 50, 25); // Just right
		checkAge( (Integer i) -> i > 45, 55); // Too old
		checkAge( (int i) -> i > 1, 100); // Does not compile - type is Integer, not int - predicates can't autobox in the same way that Collections can
	}
	public static void checkAge(Age age, int years){
		if(age.tooOld(years))
			System.out.println("Too old");
		else
			System.out.println("Just right");
	}
}
```
* Any reference to a functional interface can be assigned a lambda - e.g. `Age a = i -> i == 0;`, `Predicate<String> foo = s -> s.equals("Foo");`
* if specifying the type in the lambda, it must match the type of the functional interface - e.g. `(double d -> d > 0)` won't match `Predicate<Double>` or `singleAbstractMethod(Double d)` - it doesn't autobox
* however, if the type argument isn't specified, or is correctly specified (`(Double d) -> d > 0)`) then it can be passed a primitive, which will autobox to its wrapper class
* `Predicate` only takes one argument, but we could write a functional interface with more than one argument:

```java
interface Shape {
	boolean test(int h, double l);
}

public class TestingFuncInterfaceWithMultipleArgs {
	public static void main(String[] args) {
		Shape square = (h, l) -> h == l;
		System.out.println(square.test(5, 5));
		System.out.println(square.test(3, 7));
		
		Shape rectangleHeightTwiceLength = (int h, double l) -> h == (l * 2);
		System.out.println(rectangleHeightTwiceLength.test(4, 2));
		System.out.println(rectangleHeightTwiceLength.test(1, 5));
		
		// Shape lengthIsOne = (int h, l) -> l == 1; // DOES NOT COMPILE - if one argument has a type, all others have to as well
	}
}
```
* all arguments have to match if the type is specified (subclasses aren't allowed - `Predicate<List>` won't take `(ArrayList arrList) -> arrList.isEmpty();`)
* if one type is specified, all types have to be specified
#### Another functional interface and lambda example, for fun
```java
import java.util.*;

interface Functional {
	boolean testList(List list, String string);
}

public class QuickTest {
	public static void main(String[] args) {
		List<Character> vowels = Arrays.asList(new Character[]{'A', 'E', 'I', 'O', 'U'});
		Functional startsWithVowel = (list, s) -> list.contains(s.charAt(0));
		System.out.println(startsWithVowel.testList(vowels, "Java")); // false
		System.out.println(startsWithVowel.testList(vowels, "Objective-C")); // true
	}
	
}
```
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

[Overriding Equals](#overriding-equals)

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

* final classes can't have any child classes (Wrapper classes and `System` are, amongst many others, final classes (I didn't know where else to put this))
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
* if the parent class has an explicit constructor, with an argument, the child must also have an explicit constructor
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
#### constructors and exceptions
* the rule for overriding a method is opposite to the rule for constructors
* An overriding method cannot throw a superclass exception, while a constructor of a subclass cannot throw subclass exception (Assuming that the same exception or its super class is not present in the subclass constructor's throws clause):
```java
class A{
     public A() throws IOException{ }
     void m() throws IOException{ }
}    
class B extends A{
     //IOException is valid here, but FileNotFoundException is invalid
     public B() throws IOException{ }

     //FileNotFoundException is valid here, but Exception is invalid
     void m() throws FileNotFoundException{ } 
}
```
### Reviewing constructor rules
1. the first statement in a constructor must be either a call to another constructor in the class, using `this()`, or a call to a constructor in the parent class, using `super()`
2. the `super()` method must not be used after the first statement of the construct (i.e. at most once, and on the first line)
3. if no `super()` call is declared in a constructor, Java inserts a no-args `super()` as the first statement
4. if the parent hasn't got a default no-args constructor (i.e. it defines its own constructor with args) and the child doesn't define any constructors, it will not compile
5. if the parent doesn't have a default no-args constructor, the compiler requires an explicit call to a parent constructor on the first line of each child constructor

* if you see a constructor in a parent class, **check whether the child class will compile**. The exam will probably try to trip you up - if it doesn't make a correct `super()` call to the parent constructor, it won't compile

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
#### Polymorphism and methods in constructors
* What will the following print?
```java
class Parent{
	void foo() { System.out.println("Parent"); }
	Parent(){
		foo();
	}
}
public class Child extends Parent {
	void foo() { System.out.println("Child");
	public static void main(String[] rags){
		Child c = new Child();
}
```
* it prints child - the object is a Child at runtime, so it prints the overriden method from the parent constructor.
* remember, methods in constructor blocks are also polymorphic 
### Calling inherited class members
* child classes can use any public or protected members from the parent class
* if they are in the same package, they can also use any default (package-private) members
* they don't have direct access to any of the parent's private members, although encapsulation may provide getter/setter access
* parent members can be called directly, as in the method `getName()` below:
```java
class Parent{
	public String name = "Parent";
}

public class Child extends Parent{
	String getName(){
		return name;
	}
	
	public static void main(String... args){
		Child child = new Child();
		System.out.println(child.getName()); // -> Parent
	}
}
```
* if a member exists in the parent class but not the child class, `this.` will call it e.g.

```java
String getName(){
		return this.name;
	}
```

* you can use `super.` to call any accessible parent method e.g.

```java
String getName(){
		return super.name;
	}
```
 
* if a child method overrides a parent method, `this.` calls the child method and `super.` calls the parent method
* if a method doesn't exist in the parent class, trying to call it with `super.` will cause a compile error
* you can only call a overridden method in the next superclass - there is no way of calling an overridden method in the superclass two levels up (e.g. you can't do super.super)
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
3. the child method must not introduce any new checked exceptions unless they are **less broad** than the parent method's checked exceptions
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
* **there is no covariance in primitives or generics**:
```java
class Parent {
    public int method() {
        return 0;
    }
}

class Child extends Parent {
    public short method() { // compilation error
        return 0;
    }
}
```
```java
class Parent {
    public Collection<String> method() {
        return null;
    }
}

class Child extends Parent {
    public List<String> method() { // the object is covariant - List is a subtype of Collection - but the generic, String, must match
        return null;
    }
}
```
* covariance does not autobox/unbox"
```java
class Parent{
	Integer foo(){return 1;}
}
class Child extends Parent{
	int foo(){return 2;} // DOES NOT COMPILE -> return type int is not compatible with Integer
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
* checked exceptions must be **the same exception** or **a sub-exception**
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
### Overriding Equals
* all classes inherit the method `equals()` from Object
* its signature is `equals(Object obj)`
* its default implementation checks whether two instances are the same, so it is common to override it to check for logical equality
* when overriding equals, make sure the parameter is `Object`, not the class you are overriding equals in:
```java
class Cat {
	String name;
	int whiskers;
	public boolean equals(Object o){ // overrides the equals method in Object
		// implementation
	}
	
	// NOT:
	public boolean equals(Cat c){ // new method with its own signature, specific to this class
		// implementation
	}
}
```
* it's legal to implement equals any way you want, but the Java API defines a contract of how it *should ideally* be implemented:
> The equals method implements an equivalence relation on non-null object references:
>
> It is reflexive: for any non-null reference value x, x.equals(x) should return true.
> It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
> It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
> It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
> For any non-null reference value x, x.equals(null) should return false.
> The equals method for class Object implements the most discriminating possible equivalence relation on objects; that is, for any non-null reference values x and y, this method returns true if and only if x and y refer to the same object (x == y has the value true).>
>
> Note that it is generally necessary to override the hashCode method whenever this method is overridden, so as to maintain the general contract for the hashCode method, which states that equal objects must have equal hash codes.
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
* **redeclared private methods and hidden static methods use the method belonging to their reference - overridden methods always use the child method** (I *think*)
### Creating final methods
* final methods cannot be overriden
* the `final` keyword forbids methods from being hidden or overridden (but a `final private` method in the parent class can be redeclared in the child class)
### Inheriting variables
* variables can't be overriden, only hidden
* **final variables can be hidden**
* to hide a variable, define a variable in the child class with the same name as a variable in the parent class
* as with hidden methods, if you reference the variable from within the parent class, the variable defined in the parent class is used
* if referencing from within the child class, the variable defined in the child class is used
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
* another way of putting this would be:
  - Which variable (or static method) will be used depends on the class that the variable is declared of (i.e. what it's reference is)
  - Which instance method will be used depends on the actual class of the object that is referenced by the variable (i.e. the object in memory).
### Abstract Classes
* abstract classes allow you to create a blueprint parent class, for other classes to extend, without having to implement any of the methods in the parent class
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
* abstract classes may contain non-abstract members - these are inherited as concrete members by any subclasses (as they would be from any class)
* abstract methods may not contain abstract variables - **no variable can be marked abstract**
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
* an abstract class becomes useful when it is extended by a concrete subclass
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
* public static final for variables is assumed and therefore optional
* public abstract for methods is assumed and therefore optional
* classes may implement multiple interfaces, separated by comma:
```java
public class Rectangle implements isDrawable, isScalable{}
```
* in the above example, Rectangle is required to implement all abstract methods defined in isDrawable and isScalable (if any)
### Defining an Interface
1. interfaces cannot be instantiated directly
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
* like an abstract class, an interface may be extended using the `extends` keyword - the new child interface inherits all the abstract methods
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
#### Weird edge case
* Here is something that came up on a mock test: can a class implement an interface if it extends a class that already implements that interface?
```java
interface Interface{}
class Class1 implements Interface{}
class Class2 extends Class1 implements Interface{}
```
* yes it can, although implementing it again is redundant
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
* **(static) interface variables - child classes directly inherit interface variables so are available to call without a reference to the interface**
* **static interface methods - child classes do not inherit static interface methods so they can only be called with a reference to the name of the interface**
* what happens if a class inherits two interface variables with the same name?
```java
interface interface1 {
	int x = 1;
}
interface interface2 {
	int x = 2;
}
public class Implementer implements interface1, interface2 {
	public static void main(String[] args) {
		System.out.println(x); // DOES NOT COMPILE -> reference to x is too ambiguous
	}
}
```
* the class will compile unless you try to use the static variable without an explicit reference to which variable you mean - e.g. `interface1.x`
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

#### Rules
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
  - redeclare the method as abstract, requiring that classes implementing the new interface explicitly provide a method body
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
* static methods function nearly identically to static methods defined in classes, except that any classes that implement the interface do not inherit the static method
#### Rules:
1. the static method is assumed to be `public` - cannot be marked `private` or `protected`
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
* once the object has been assigned a new reference type, only the methods and variables available to that reference type are callable on the object without an explicit cast
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
#### Benefits of polymorphism (a mock question)
* It makes the code more reusable
* It makes the code more dynamic.
  - Polymophism allows the actual decision of which method is to be invoked to be taken at runtime based on the actual class of object
  - This is dynamic binding and makes the code more dynamic
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
* in order to get access to those properties again, we can use casting:
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
* all non-final, non-static and non-private methods are considered virtual methods
* if you call a method that overrides a method on an object, you get the overridden method, even if the call to the method is on a parent method or within the parent class
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
#### Why exceptions? (I was asked in a mock)
* It allows creation of new exceptions that are custom to a particular application domain
* It improves code because error handling code is clearly separated from the main program logic.
  - The error handling logic is put in the catch block, which makes the main flow of the program clean and easily understandable.
* Java Exceptions is a mechanism that you can use to determine what to do when something unexpected happens.
* Java Exceptions is a mechanism for logging unexpected behavior.
#### What exception does this method throw?
* This also came up in a mock and is pernickety at best:
```java
String s = "String";
char c = s.charAt(s.length());
```
* What exception is thrown if passed a value higher than or equal to the length of the string (or less than 0)?
  - `IndexOutOfBoundsException`
* What is printed when running the above code?
  - `StringIndexOutOfBoundsException`
* I *think* the difference is:
```java
//psuedoclass - this is not the source code but my interpretation of how it works (it's illustrative...)
class String {
	char charAt(int i) throws IndexOutOfBoundsException { // the method throws this (this would be in the API)
		if ((index < 0) || (index >= count)) {
			throw new StringIndexOutOfBoundsException(index); // but throws this (this is what is printed as the error message)
		}
	}
}
```
#### Exceptions and overridden methods
* The overriding method may choose to have no throws clause even if the overridden method has a throws clause.
* Whether a call needs to be wrapped in a try/catch or whether the enclosing method requires a throws clause depends on the class of the reference and not the class of the actual object. This is because it is the compiler that checks whether a call needs to have exception handling and the compiler knows only about the declared class of a variable. It doesn't know about the actual object referred to by that variable (which is known only to JVM at run time). 
* #### Key Subclasses of Throwable
![Key Subclasses of Throwable](https://github.com/sebastianchristopher/Java8OCANotes/blob/master/media/key-subclasses-of-throwable.png "Key Subclasses of Throwable")
* `Error` means something irrecoverable went wrong
* `RuntimeException` and its subclasses  are *runtime exceptions*  - unexpected but not fatal
		- e.g. accessing an invalid array index
* they are also know as **Unchecked Exceptions**
* **Checked Exceptions** include `Exception` and all subclasses that do not extend `RuntimeException`
* Java has a rule called *handle or declare*
* for checked exceptions, Java requires that the code either handle them, or declare them in the method signature
```java
public class Fail {
	void fail() {
		throw new Exception(); // DOES NOT COMPILE
	}
}
```

* throws **declares** it *might* throw a an exception
  - the `throws` keyword tells Java you want to have the option of throwing an exception
* `throw` tells Java to throw an exception
> An example of an unchecked (or runtime) exception is NullPointerException. This can happen in any method - but you don't see `throws NullPointerException` everywhere because it is unchecked
* **A method that declares an exception isn't required to throw one - but it must not throw a superclass of an exception it declares**
### Throwing an Exception
* there may be made up exceptions on the exam but treat them as real exceptions - e.g. `class MyMadeUpException extends Exception {}`
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
* **you can throw (and declare) Throwable and all of its subclasses:**
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

**OK for program to catch? means, is it good practice? It is legal for the program to catch an Error (bad book wording)**

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
* if the code in the try clause throws an exception, it stops running and excecution passes to the catch statement
* curly braces are **required**
* try statements and methods always require curly braces
* (if statements and loops allow you to omit curly braces if there is only one statement inside the code block)
* a `try` block needs to have `catch` and/or `finally`
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
* execution will always pass to finally:
```java
loop:
{
 try{
	for (i = 0; i < 5;  ++i){
	   if(i == 2) break loop; // on second iteration, break loop happens - but control is FIRST passed to finally block
	}
 } finally{
	System.out.println("In Finally"); // this gets executed
 }
}
```
* **except** when `System.exit(0);` is used - this is the only way to avoid control passing to the finally block (at least for the exam)
* **try, catch and finally all have their own scope** - e.g. a variable declared in the try block is not in scope in the catch block
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
* **check this every time there is a question with catch blocks, as you KEEP missing it**
* What is the output of this code?:
```java
12: int a = 123;
13: int b = 0;
14: try {
15: 	System.out.print(a / b);
16: 	System.out.print("1");
17: } catch (RuntimeException e) {
18: 	System.out.print("2");
19: } catch (ArithmeticException e) {
20: 	System.out.print("3");
21: } finally {
22: 	System.out.print("4");
23: }
```

* look at the catch blocks - `ArithmeticException` is after its superclass, `RuntimeException`, meaning it is unreachable - so it will not compile
* similarly, a catch block for an exception the where the exception will never be thrown, won't compile:

```java
import java.io.IOException;
public class BadExceptionHandling {
	public static void main(String args[]){
		try {
			System.out.println("Hello world"); // this method doesn't throw IOException
		} catch (IOException e) { // so this won't compile
			System.out.println("Bad maths");
		}
	}
}
```

```bash
> javac BadExceptionHandling.java
BadExceptionHandling.java:6: error: exception IOException is never thrown in body of corresponding try statement
                } catch (IOException e) {
                  ^
```

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
> because the finally block **always** has to run, the catch clause will execute up until the exception, then pass to finally - it can't throw two exceptions so has to go to finally
* if the catch block either throws an exception or returns a value, this will be passed back to the caller UNLESS the finally block also throws an exception or returns a value:
```java
class Returning {
	public static void main(String[] args) {
		System.out.println(returner1());
		System.out.println(returner2());
	}
	static String returner1(){
		try{
			throw new RuntimeException();
		}catch{RuntimeException e){
			System.out.println("In catch");
			return "Catch"
		}finally{
			System.out.println("In finally");
			return "Finally";
		}
	}
	static String returner2(){
		try{
			throw new RuntimeException();
		}catch{RuntimeException e){
			System.out.println("In catch");
			return "Catch"
		}finally{
			System.out.println("In finally");
		}
	}
}
```
* the above will print:
```bash
In catch
In finally
Finally

In catch
In finally
Catch
```
* if finally doesn't give us something else to return to the caller (a return or an exception), it goes back to the catch block and passes the return or thrown exception back to the caller

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

* careful when a second exception is thrown and goes to finally -  what is the output here?:

```java
public class SecondException {
	static void foo() {
		try {
			System.out.println("2");
			throw new RuntimeException();
		} catch(RuntimeException e) {
			System.out.println("3");
			throw e;
		} finally {
			System.out.println("4");
		}
	}
	
	public static void main(String args[]) {
		System.out.println("1");
		foo();
		System.out.println("5");
	}
}
```

```bash
> java SecondException
1
2
3
4
Exception in thread "main" java.lang.RuntimeException
        at SecondException.foo(SecondException.java:5)
        at SecondException.main(SecondException.java:16)
```

* control passes to the finally block, printing "4", then the exception is passed back to the caller (`main()`), which doesn't handle it so it throws the exception.
* if we do the same thing with a checked exception:

```java
public class SecondCheckedException {
	static void foo() {
		try {
			System.out.println("2");
			throw new Exception();
		} catch(Exception e) {
			System.out.println("3");
			throw e;
		} finally {
			System.out.println("4");
		}
	}
	
	public static void main(String args[]) {
		System.out.println("1");
		foo();
		System.out.println("5");
	}
}
```

```bash
> javac SecondCheckedException.java
SecondCheckedException.java:8: error: unreported exception Exception; must be caught or declared to be thrown
                        throw e;
                        ^
```

* this time, it won't compile, as the caller, `main()`, doesn't handle or declare the checked exception
#### Exceptions and inheritance
* a method in a superclass that declares it throws an exception doesn't have to declare that same exception in an overridden method:
```java
class Super{
	void foo() throws Exception {}
}
class Sub{
	void foo(){}
```
* as overridden methods are polymrophic, if the foo method is called from an object whose reference is parent, it will need to be handled or caught
* if it is called from an object whose reference is Child, it won't:
```java
class Super{
	void foo() throws Exception {}
}
class Sub{
	void foo(){}
}
public class Tester{
	public static void main(String[] args){
		Child child = new Child();
		child.foo(); // runs
		Parent parent = new Child();
		parent.foo(); // does not compile - must be caught or declared
	}
}
```
* also, if the overriding method actually *throws* an exception, it needs to declare it:
```java
class Super{
	void foo() throws Exception {}
}
class Sub{
	void foo(){throw new Exception()} // does not compile - must be caught or declared to be thrown
```

### Common Exception Types
* 3 types for the exam:
  1. runtime exceptions (also known as unchecked exceptions)
  2. checked exceptions
  3. errors
* the following are in the the `java.lang` library unless stated otherwise  
### Runtime Exceptions
* `ArithmeticException` - thrown by the JVM when code attempts to divide by zero e.g. `int x = 1/0;`
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
* `IllegalArgumentException` - thrown by the programmer (although usually by the application) to indicate that a method has been passed an illegal or inappropriate argument
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
* `IllegalStateException`  Usually thrown by the application. Signals that a method has been invoked at an illegal or inappropriate time. In other words, the Java environment or Java application is not in an appropriate state for the requested operation.
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
* `SecurityException` - thrown by the application programmer (the security manager) upon security violation. For example, when a java program runs in a sandbox (such as an applet) and it tries to use prohibited APIs such as File I/O, the security manager throws this exception.

### Checked Exceptions
* checked exceptions:
  - are subclasses of `Exception` but not of `RuntimeException`
  - must be handled or declared
  - can be thrown by the JVM or the programmer
* `java.io.FileNotFoundException` - subclass of `IOException`
* `java.io.IOException` - thrown **programmatically** when there's a problem reading/writing a file - not thrown by the JVM (you got this wrong so remember!)
* `CloneNotSupportedException` - thrown by JVM/programmer to indicate that the clone method in class Object has been called to clone an object, but that the object's class does not implement the Cloneable interface.
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
  - note that while `ArrayIndexOutOfBoundsException` caused the error, the error thrown at runtime is `ExceptionInInitializerError`
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

* Why doesn't this compile?

```java
import java.io.IOException;

public class ThisDoesntCompile {
	public static void main(String args[]){
		try {
			System.out.println("Hello world!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```
* The method called inside the try block (`System.out.println()`) doesn't declare an IOException to be thrown - the compiler realises that IOException would be an unreachable catch block. Here's the output when it's run:

```bash
> javac ThisDoesntCompile.java
ThisDoesntCompile.java:7: error: exception IOException is never thrown in body of corresponding try statement
                } catch (IOException e) {
                  ^
1 error
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
---
Consider the following code:
```java
class A{
   A() {  print();   }
   void print() { System.out.println("A"); }
}
class B extends A{
   int i =   4;
   public static void main(String[] args){
      A a = new B();
      a.print();
   }
   void print() { System.out.println(i); }
}
```
What will be the output when class B is run ?

It will print 0, 4
> Note that method print() is overridden in class B. Due to polymorphism, the method to be executed is selected depending on the class of the actual object. Here, when an object of class B is created, first B's default constructor (which is not visible in the code but is automatically provided by the compiler because B does not define any constructor explicitly) is called. The first line of this constructor is a call to super(), which invokes A's constructor. A's constructor in turn calls print(). Now, print is a non-private instance method and is therefore polymorphic, which means, the selection of the method to be executed depends on the class of actual object on which it is invoked. Here, since the class of actual object is B, B's print is selected instead of A's print. At this point of time, variable i has not been initialized (because we are still in the middle of initializing A), so its default value i.e. 0 is printed.   Finally, 4 is printed.
---
## Misc Notes

Here's something useful to know about compilation - a line **won't** fail to compile if its reference fails to compile - see example below:

```java
1.	public class Compilation {
2.	  public static void main(String[] args) {
3.		Test t = new Test();
4.		String x = t.foo;
5.		int y = t.bar;
6.	  }
7.	  
8.	}
9.	class Test{
10.	  private Test(){}
11.	  public String foo;
12.	  private int bar;
13.	}
```

```bash
> javac Compilation.java
Compilation.java:3: error: Test() has private access in Test
    Test t = new Test();
             ^
Compilation.java:5: error: bar has private access in Test
    int y = t.bar;
             ^
2 errors
```

Even though line 3, declaring and initializing `t`, fails to compile, line 4, which uses `t.foo`, is fine - each line checks for compile errors independently of all others.

***

It's possible to create a new object without assigning it - this is done a lot when passing arguments, but can also be done as a standalone line:

```java
public class Foo {
	public static void main(String[] args) {
		new Foo();
	}
}
```
***

You can pass `null` in place of any object (object, not primitives) - the test seems to like this:

```java
interface Iface {}
class Classy implements Iface {}
public class Tester {
	void foo(Iface interf) {}
	public static void main(string[] args) {
		foo(null);
	}
}
```

***

What is the output of this code?

```java
public class Careful {
	public static void main(String args[]){
		Person personOne = new Person();
		Person personTwo = new Person();
		
		personOne.name = "Jeremy";
		personTwo.name = "Mark";
		
		System.out.println(personOne.name);
	}
}
class Person {
	static String name;
}
```

Careful now it's:

```bash
> java Statics
Mark
```

`name` is static, so the instance variable is used as a reference to the class and it changes each time. Pay attention!

***

Which of the following is a reference variable (and not a primitive)? (Choose all that apply)

A.	int[] ints;

B.	long[] longs;

C.	String[] strings;

D.	Object[] objects;

E.	None of the above

Solution:
A, B, C, D. All array types are reference variables. Although `int` is a primitive type, `int[]` is still a reference type.

***

When compiling, one bytecode file is created per class/interface.

***

What is the output of the following program?

```java
1: public class ColorPicker {
2: 		public void pickColor() {
3: 			try {
4: 				System.out.print("A");
5: 				fail();
6: 			} catch (NullPointerException e) {
7: 				System.out.print("B");
8: 			} finally {
9: 				System.out.print("C");
10: 		}
11: 	}
12: 	public void fail() {
13: 		throw new ArithmeticException();
14: 	}
15: 	public static void main(String[] args) {
16: 		new ColorPicker().pickColor();
17: 		System.out.print("D");
18: 	}
19: }
```

A.	ABCD

B.	ABD

C.	A and a stack trace for ArithmeticException

D.	AC and a stack trace for ArithmeticExceptionYour selection is incorrect

E.	ACD and a stack trace for ArithmeticException

Solution:
D. The pickColor() method is invoked on line 16. A is output on line 4 and then fail() is invoked. An ArithmeticException is thrown and not caught within pickColor(), so the finally block executes and prints C. Then the exception is thrown to main(). Because main() does not catch it either, the method returns to the caller and a stack trace is printed for the ArithmeticException.

***
##### Single statements
Java is funny about curly braces `{}` - they are required around methods, try blocks, switch statements, catch blocks, and finally blocks even if there is only one statement inside.
They are optional for loops (both types of for loop, while loops, and do-while loops) **if** a single statement follows
What consitutes a "single statement" is a bit complicated - for example look at the following code:

```java
public class RemovingOptionalBraces {
	public static void main(String[] args) {
		int num1 = 8;
		int num2 = 8;
		for (int i = 0; i < 3; i++)
			if (num1 == num2)
				try {
					System.out.println("t");
				} catch (Exception e) {
					System.out.println("c");
				}
	}
}
```

This seems like it wouldn't compile, as the *if* spans several lines, as does the *try-catch*. But the if statement is a single statement, as it the try-catch statement. An if-else would also be a single statement.
Single statements include:

> IfThenStatement
> IfThenElseStatement
> WhileStatement
> ForStatement
> SwitchStatement
> DoStatement
> BreakStatement
> ContinueStatement
> ReturnStatement
> ThrowStatement
> TryStatement
> A block (that is, using curly braces)

Non-single statements include:
> LocalVariableDeclarationStatement

Therefore the following won't compile:

```java
public class LocalVariableDeclarationStatement {
	public static void main(String[] blabla) {
		// this compiles
		int x = 1;
		if(true)
			switch(x) {
				case 1:
				case 2:
				case 3:
				default:
			}
		
		// this won't compile
		if(true)
			int y = 2; // error: variable declaration not allowed here
		
	}	
}
```
---
A good example of order of precedence:
Consider the following method:
```java
static int mx(int s){
	for(int i=0; i<3; i++){
		s = s + i;
	}
	return s;
}  
```
and the following code snippet:
```java
int s = 5;
s += s + mx(s) + ++s;
System.out.println(s);
```
What will it print?
s += (expression) will be converted to s =  s + expression.
So the given expression will become: s = s + s + mx(s) + ++s;
s = 5 + 5 + mx(5) + 6;
s = 5 + 5+ 8 + 6;
s = 24;

---
Here is another:
What will be the result of trying to compile and execute the following program?
```java
public class TestClass{
    public static void main(String args[] ){
		int i = 0 ;
		int[] iA = {10, 20} ;
		iA[i] = i = 30 ;
		System.out.println(""+ iA[ 0 ] + " " + iA[ 1 ] + "  "+i) ;
	}
}
```
It will print 30 20 30
The statement iA[i] = i = 30 ; will be processed as follows:
iA[i] = i = 30; => iA[0] = i = 30 ;  =>  i = 30; iA[0] = i ; =>   iA[0] = 30 ;

Here is what JLS says on this:
1 Evaluate Left-Hand Operand First  
2 Evaluate Operands before Operation  
3 Evaluation Respects Parentheses and Precedence  
4 Argument Lists are Evaluated Left-to-Right  

For Arrays: First, the dimension expressions are evaluated, left-to-right. If any of the expression evaluations completes abruptly, the expressions to the right of it are not evaluated.
---
A snippet I don't know where to put:
> The `getClass()` method always returns the Class object for the actual object on which the method is called irrespective of the type of the reference. Since s refers to an object of class String, s.getClass returns Class object for String  and similarly list.getClass returns Class object for ArrayList.
```java
import java.util.*;
public class ClassnameTest {
     public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder("mrx");
		String s = sb.toString();
		list.add(s);
		System.out.println(s.getClass());
		System.out.println(list.getClass());
	}
}
```
Prints:
class java.lang.String
class java.util.ArrayList
---

## Exam Essentials

### Java Building Blocks


* Be able to write code using a main() method.
  - A main() method is usually written as public static void main(String[] args).
  - Arguments are referenced starting with args[0].
  - Accessing an argument that wasn’t passed in will cause the code to throw an exception.
* Understand the effect of using packages and imports.
  - Packages contain Java classes.
  - Classes can be imported by class name or wildcard.
  - Wildcards do not look at subdirectories.
  - In the event of a confl ict, class name imports take precedence.
* Be able to recognize a constructor.
  - A constructor has the same name as the class.
  - It looks like a method without a return type.
* Be able to identify legal and illegal declarations and initialization.
  - Multiple variables can be declared and initialized in the same statement when they share a type.
  - Local variables require an explicit initialization; others use the default value for that type.
  - Identifiers may contain letters, numbers, $, or _.
  - Identifiers may not begin with numbers.
  - Numeric literals may contain underscores between two digits and begin with 1–9, 0, 0x, 0X, 0b, and 0B.
* Be able to determine where variables go into and out of scope.
  - All variables go into scope when they are declared.
  - Local variables go out of scope when the block they are declared in ends.
  - Instance variables go out of scope when the object is garbage collected.
  - they do not go out of scope if they are returned (although the method ends immediately after, at which point it does go out of scope)
  - Class variables remain in scope as long as the program is running.
* Be able to recognize misplaced statements in a class.
  - Package and import statements are optional.
  - If present, both go before the class declaration in that order.
  - Fields and methods are also optional and are allowed in any order within the class declaration.
* Know how to identify when an object is eligible for garbage collection.
  - Draw a diagram to keep track of references and objects as you trace the code.
  - When no arrows point to a box (object), it is eligible for garbage collection.

### Operators and Statements

* Be able to write code that uses Java operators.
  - This chapter covered a wide variety of operator symbols.
  - Go back and review them several times so that you are familiar with them throughout the rest of the book. 

* Be able to recognize which operators are associated with which data types.
  - Some operators may be applied only to numeric primitives, some only to boolean values, and some only to objects.
  - It is important that you notice when an operator and operand(s) are mismatched, as this issue is likely to come up in a couple of exam questions.

* Understand Java operator precedence.
  - Most Java operators you’ll work with are binary, but the number of expressions is often greater than two.
  - Therefore, you must understand the order in which Java will evaluate each operator symbol. 

* Be able to write code that uses parentheses to override operator precedence.
  - You can use parentheses in your code to manually change the order of precedence. 

* Understand if and switch decision control statements.
  - The if-then and switch statements come up frequently throughout the exam in questions unrelated to decision control, so make sure you fully understand these basic building blocks of Java. 

* Understand loop statements.
  - Know the syntactical structure of all loops, including while, do-while, and for.
  - Each loop has its own special properties and structures.
  - Also, be familiar with the enhanced for-each loops that iterate over lists. 

* Understand how break and continue can change flow control.
  - Know how to change the fl ow control within a statement by applying a break or continue command.
  - Also know which control statements can accept break statements and which can accept continue statements.
  - Finally, understand how these statements work inside embedded loops or switch statements.

### Core Java APIs

* Be able to determine the output of code using `String`
  - Know the rules for concatenating Strings and how to use common String methods
  - Know that Strings are immutable
  - Pay special attention to the fact that indexes are zero based and that substring() gets the string up until right before the index of the second parameter
* Be able to determine the output of code using StringBuilder
  - Know that StringBuilder is mutable and how to use common StringBuilder methods
  - Know that substring() does not change the value of a StringBuilder whereas append(), delete(), and insert() do change it
  - Also note that most StringBuilder methods return a reference to the current instance of StringBuilder
* Understand the difference between `==` and `equals`
  - `==` checks object equality
  - `equals()` depends on the implementation of the object it is being called on
  - For Strings, `equals()` checks the characters inside of it
* Be able to determine the output of code using arrays
  - Know how to declare and instantiate one-dimensional and multidimensional arrays
  - Be able to access each element and know when an index is out of bounds
  - Recognize correct and incorrect output when searching and sorting
* Be able to determine the output of code using ArrayList
  - Know that ArrayList can increase in size
  - Be able to identify the different ways of declaring and instantiating an ArrayList
  - Identify correct output from ArrayList methods, including the impact of autoboxing
* Recognize invalid uses of dates and times
  - LocalDate does not contain time fi elds and LocalTime does not contain date fi elds
  - Watch for operations being performed on the wrong time
  - Also watch for adding or subtracting time and ignoring the result

### Methods and Encapsulation

* Be able to identify correct and incorrect method declarations
  - A sample method signature is `public static void method(String... args) throws Exception {}`
* Identify when a method or field is accessible
  - Recognize when a method or field is accessed when the access modifier (`private`, `protected`, `public`, or default access) does not allow it
* Recognize valid and invalid uses of static imports
  - Static imports import static members
  - They are written as import static, not static import
  - Make sure they are importing static methods or variables rather than classnames
* State the output of code involving methods
  - Identify when to call static rather than instance methods based on whether the classname or object comes before the method
  - Recognize the correct overloaded method
  - Exact matches are used fi rst, followed by wider primitives, followed by autoboxing, followed by varargs
  - Assigning new values to method parameters does not change the caller, but calling methods on them does
* Evaluate code involving constructors
  - Constructors can call other constructors by calling this() as the fi rst line of the constructor
  - Recognize when the default constructor is provided
  - Remember the order of initialization is the superclass, static variables/initializers, instance variables/initializers, and the constructor
* Be able to recognize when a class is properly encapsulated
  - Look for private instance variables and public getters and setters when identifying encapsulation
* Write simple lambda expressions
  - Look for the presence or absence of optional elements in lambda code
  - Parameter types are optional
  - Braces and the return keyword are optional when the body is a single statement
  - Parentheses are optional when only one parameter is specifi ed and the type is implicit
  - The Predicate interface is commonly used with lambdas because it declares a single method called test(), which takes one parameter
  
### Class Design

* Be able to write code that extends other classes
  - A Java class that extends another class inherits all of its public and protected methods and variables
  - The first line of every constructor is a call to another constructor within the class using this() or a call to a constructor of the parent class using the super() call
  - If the parent class doesn’t contain a noargument constructor, an explicit call to the parent constructor must be provided
  - Parent methods and objects can be accessed explicitly using the super keyword
  - Finally, all classes in Java extend java.lang.Object either directly or from a superclass
* Understand the rules for method overriding
  - The Java compiler allows methods to be overridden in subclasses if certain rules are followed: a method must have the same signature, be at least as accessible as the parent method, must not declare any new or broader exceptions, and must use covariant return types
* Understand the rules for hiding methods and variables
  - When a static method is recreated in a subclass, it is referred to as method hiding
  - Likewise, variable hiding is when a variable name is reused in a subclass
  - In both situations, the original method or variable still exists and is used in methods that reference the object in the parent class
  - For method hiding, the use of static in the method declaration must be the same between the parent and child class
  - Finally, variable and method hiding should generally be avoided since it leads to confusing and diffi cult-to-follow code
* Recognize the difference between method overriding and method overloading
  - Both method overloading and overriding involve creating a new method with the same name as an existing method
  - When the method signature is the same, it is referred to as method overriding and must follow a specifi c set of override rules to compile
  - When the method signature is different, with the method taking different inputs, it is referred to as method overloading and none of the override rules are required
* Be able to write code that creates and extends abstract classes
  - In Java, classes and methods can be declared as abstract
  - Abstract classes cannot be instantiated and require a concrete subclass to be accessed
  - Abstract classes can include any number, including zero, of abstract and nonabstract methods
  - Abstract methods follow all the method override rules and may only be defi ned within abstract classes
  - The first concrete subclass of an abstract class must implement all the inherited methods
  - Abstract classes and methods may not be marked as final or private
* Be able to write code that creates, extends, and implements interfaces
  - Interfaces are similar to a specialized abstract class in which only abstract methods and constant static final variables are allowed
  - New to Java 8, an interface can also defi ne default and static methods with method bodies
  - All members of an interface are assumed to be public
  - Methods are assumed to be abstract if not explicitly marked as default or static
  - An interface that extends another interface inherits all its abstract methods
  - An interface cannot extend a class, nor can a class extend an interface
  - Finally, classes may implement any number of interfaces
* Be able to write code that uses default and static interface methods
  - A default method allows a developer to add a new method to an interface used in existing implementations, without forcing other developers using the interface to recompile their code
  - A developer using the interface may override the default method or use the provided one
  - A static method in an interface follows the same rules for a static method in a class
* Understand polymorphism
  - An object in Java may take on a variety of forms, in part depending on the reference used to access the object
  - Methods that are overridden will be replaced everywhere they are used, whereas methods and variables that are hidden will only be replaced in the classes and subclasses that they are defi ned
  - It is common to rely on polymorphic parameters—the ability of methods to be automatically passed as a superclass or interface reference—when creating method defi nitions
* Recognize valid reference casting
  - An instance can be automatically cast to a superclass or interface reference without an explicit cast
  - Alternatively, an explicit cast is required if the reference is being narrowed to a subclass of the object
  - The Java compiler doesn’t permit casting to unrelated types
  - You should be able to discern between compiler-time casting errors and those that will not occur until runtime and that throw a CastClassException

### Exceptions

* Differentiate between checked and unchecked exceptions
  - Unchecked exceptions are also known as runtime exceptions and are subclasses of java.lang.RuntimeException
  - All other subclasses of java.lang.Exception are checked exceptions
* Understand the flow of a try statement
  - A try statement must have a catch or a finally block
  - Multiple catch blocks are also allowed, provided no superclass exception type appears in an earlier catch block than its subclass
  - The finally block runs last regardless of whether an exception is thrown
* Identify whether an exception is thrown by the programmer or the JVM
  - Illegal ArgumentException and NumberFormatException are commonly thrown by the programmer
  - Most of the other runtime exceptions are typically thrown by the JVM
* Declare methods that declare exceptions
  - The throws keyword is used in a method declaration to indicate an exception might be thrown
  - When overriding a method, the method is allowed to throw fewer exceptions than the original version
* Recognize when to use throw versus throws
  - The throw keyword is used when you actually want to throw an exception - for example, throw new RuntimeException()
  - The throws keyword is used in a method declaration

---

---
### Index

---
#### [A](#a) [B](#b) [C](#c) [D](#d) [E](#e) [F](#f) [G](#g) [H](#h) [I](#i) [J](#j) [K](#k) [L](#l) [M](#m) [N](#n) [O](#o) [P](#p) [Q](#q) [R](#r) [S](#s) [T](#t) [U](#u) [V](#v) [W](#w) [X](#x) [Y](#y) [Z](#z)

---

#### A
[Abstract Class Definition Rules](#abstract-class-definition-rules)

[Abstract Classes](#abstract-classes)

[Abstract Method Definition Rules](#abstract-method-definition-rules)

[Abstract Methods and Multiple Inheritance](#abstract-methods-and-multiple-inheritance)

[Advanced control flow usage](#advanced-control-flow-usage)

[Anatomy of a Method](#anatomy-of-a-method)

[Applying Access Modifiers](#applying-access-modifiers)

[Array Methods](#array-methods)

[ArrayList Methods](#arraylist-methods)

[ArrayList](#arraylist)

[Arrays](#arrays)

[Assignment operator](#assignment-operator)

[Autoboxing](#autoboxing)

#### B
[Benefits of Java](#benefits-of-java)

[break and continue with labelled statements](#break-and-continue-with-labelled-statements)

[break](#break)

#### C
[Caching and Wrapper Class Equality](#caching-and-wrapper-class-equality)

[Calling constructors](#calling-constructors)

[Calling inherited class members](#calling-inherited-class-members)

[Calling methods that throw exceptions](#calling-methods-that-throw-exceptions)

[Casting Objects](#casting-objects)

[Catching various types of exception](#catching-various-types-of-exception)

[Checked Exceptions](#checked-exceptions)

[Class Inheritance](#class-inheritance)

[Classes and Files](#classes-and-files)

[Classes, Interfaces and Keywords](#classes-interfaces-and-keywords)

[Code formatting on the exam](#code-formatting-on-the-exam)

[Comments](#comments)

[Common Exception Types](#common-exception-types)

[Compound assignment operator](#compound-assignment-operator)

[continue](#continue)

[Converting between array and List](#converting-between-array-and-list)

[Creating a concrete class](#creating-a-concrete-class)

[Creating constructors](#creating-constructors)

[Creating final methods](#creating-final-methods)

[Creating Immutable Classes](#creating-immutable-classes)

[Creating Objects](#creating-objects)

#### D
[Dates and Times](#dates-and-times)

[Declaring and initializing variables](#declaring-and-initializing-variables)

[Default constructor](#default-constructor)

[Default initialization of variables](#default-initialization-of-variables)

[Default Interface Methods](#default-interface-methods)

[Default Methods and Multiple Inheritance](#default-methods-and-multiple-inheritance)

[Defining an Interface](#defining-an-interface)

[Defining Constructors](#defining-constructors)

[Designing Static Methods and Fields](#designing-static-methods-and-fields)

[Destroying objects](#destroying-objects)

[do while](#do-while)

#### E
[Encapsulation](#encapsulation)

[Errors](#errors)

[Extra things](#extra-things)

#### F
[Final fields](#final-fields)

[Finally block](#finally-block)

[for each](#for-each)

[for](#for)

[Formatting Dates and Times](#formatting-dates-and-times)

#### H
[Hiding Static Methods](#hiding-static-methods)

#### I
[Identifiers](#identifiers)

[if else](#if-else)

[if....](#if)

[Implementing Interfaces](#implementing-interfaces)

[Inheriting an Interface](#inheriting-an-interface)

[Inheriting Methods](#inheriting-methods)

[Inheriting variables](#inheriting-variables)

[Interface Variables](#interface-variables)

#### L
[Labels](#labels)

[Lambdas](#lambdas)

[Logical complement (!) and negation (-) operators](#logical-complement--and-negation--operators)

[Logical operators](#logical-operators)

[Looping through an ArrayList](#looping-through-an-arrayList)

#### M
[main() method](#main-method)

[Manipulating Dates and Times](#manipulating-dates-and-times)

[Matching Overloaded Methods](#matching-overloaded-methods)

[More on hidden methods and variables](#more-on-hidden-methods-and-variables)

[More on lambdas and predicates](#more-on-lambdas-and-predicates)

[More on varargs](#more-on-varargs)

[Multidimensional Array](#multidimensional-array)

#### N
[Numeric promotion](#numeric-promotion)

#### O
[Object vs Reference](#object-vs-reference)

[Order of catch blocks](#order-of-catch-blocks)

[Order of elements in a class](#order-of-elements-in-a-class)

[Order of Initialization](#orderoof-initialization)

[Order of Precedence](#order-of-precedence)

[Overloading and Varargs](#overloading-and-varargs)

[Overloading constructors](#overloading-constructors)

[Overloading Methods](#overloading-methods)

[Overriding a method](#overriding-a-method)

[Overriding Equals](#overriding-equals)

[Overriding methods with an exception in the method declaration](#overriding-methods-with-an-exception-in-the-method-declaration)

[Overriding vs Hiding Methods](#overriding-vs-hiding-methods)

#### P
[Package declarations and imports](#package-declarations-and-imports)

[Parsing dates and Times](#parsing-dates-and-times)

[Pass by Value](#pass-by-value)

[Periods](#periods)

[Polymorphic Parameters](#polymorphic-parameters)

[Pre unary and post unary increment and decrement](#pre-unary-and-post-unary-increment-and-decrement)

[Predicates](#predicates)

[Primitive conversion](#primitive-conversion)

[Printing an exception](#printing-an-exception)

#### Q

[Querying Dates and Times](#querying-dates-and-times)

#### R
[Redeclaring Private Methods](#redeclaring-private-methods)

[Reference types and primitives](#reference-types-and-primitives)

[Reference Types](#reference-types)

[Reviewing constructor rules](#reviewing-constructor-rules)

[Rules for overriding a method](#rules-for-overriding-a-method)

[Runtime Exceptions](#runtime-exceptions)

#### S
[Static imports](#static-imports)

[Static initialization](#static-initialization)

[Static Interface Methods](#static-interface-methods)

[Static variables](#static-variables)

[Static vs Instance](#static-vs-instance)

[String Methods](#string-methods)

[StringBuilder Methods](#stringBuilder-methods)

[StringBuilder](#stringbuilder)

[Strings](#strings)

[switch](#switch)

#### T
[Ternary](#ternary)

[Throwing a second exception](#throwing-a-second-exception)

[Throwing an Exception](#throwing-an-exception)

[Try statement](#try-statement)

[Types of Exception](#types-of-exception)

#### U
[Understanding Equality](#understanding-equality)

[Understanding Polymorphism](#understanding-polymorphism)

#### V
[varargs](#varargs)

[Virtual Methods](#virtual-methods)

#### W
[What are exceptions](#what-are-exceptions)

[while](#while)

[Wrapper Classes and ArrayList](#wrapper-classes-and-arraylist)

---