# Java OCA
## Chapter 1 - Java Building Blocks
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
* (java.lang.* is already imported)
```java
import java.util.*; // imports all classes in java.util
```
* doesn't import child packages e.g. java.util.concurrent
---
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
