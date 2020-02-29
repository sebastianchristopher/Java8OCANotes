# Java OCA

**Table of contents**

---
[Chapter 1 - Java Building Blocks](#chapter-1---java-building-blocks)

[Chapter 2 - Operators and Statements](#chapter-2---operators-and-statements)

[Chapter 3 - Java Core APIs](#chapter-3---java-core-apis)

[Chapter 4 - Methods and Encapsulation](#chapter-4---methods-and-encapsulation)

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
* spaces are used to separate the arguments
* if a string has spaces, surround it with quotes e.g. `> java Foo 'arg one' two three`
* trying to access an argument not supplied will throw a runtime error
* `args` can be any legal identifier e.g. `public static void main(String... blarguments){}`

### Package declarations and imports
* classes in the same package do not need to be imported to be used
* (java.lang.* is already imported)
```java
import java.util.*; // imports all classes in java.util
```
* doesn't import child packages e.g. java.util.concurrent
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
### Code formatting on the exam
* if the code starts after line 1, assume all imports are correct
* if it starts on line 1, or there are no line numbers, make sure there are no imports missing
* if there is no `main()` method, assume that the main() method, the class definition, and all imports are present
### Benefits of Java
* OOP
* Encapsulation
* Platform-independent
* Robust - prevents memory leaks with automatic garbage collection
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
```java
double d1 = _0.1; // illegal
double d2 = 0._1; // illegal
double d3 = 100.01_; // illegal
double d4 = 101_.001; // illegal
double d5 = 1_0_1_1.0_123_5_0; // legal!
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
System.out.println(c.length()); //DOES NOT COMPILE
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
* must begin with a letter or _ or $
* subsequent characters may be numbers
* must not be a Java reserved word (case-sensitive)
```java
String Class = "Class"; // legal
String class = "class"; // DOES NOT COMPILE
```
### Default initialization of variables
* *local variables* (i.e. variables in a method) must be initialized before use
* they do not have a default value
* the compiler won't let you read an uninitialized local variable value:
```java
public int foo() {
    int x = 1;
	int y;
	int z = x + y; //DOES NOT COMPILE
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
| logical               | `&`, `|`, `^`                      |
| short-circuit logical | `&&`, `||`                         |
| ternary               | `x == true ? y : z`                |
| assignment            | `=`, `+=`, `-=`                    |


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
* Strings are *immutable*
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
** all immutable**
* `java.time.LocalDate`
* `java.time.LocalTime`
* `java.time.LocalDateTime`
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
  - `(``optional parameter type` `parameter name``)` `arrow` `{``return keyword` `body``;``}`
  - `(Clothing a) -> {return a.isHat();}`
  

