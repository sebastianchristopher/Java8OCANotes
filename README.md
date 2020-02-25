# Java OCA

**Table of contents**

---
[Chapter 1 - Java Building Blocks](#chapter-1---java-building-blocks)

[Chapter 2 - Operators and Statements](#chapter-2---operators-and-statements)

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