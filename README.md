# CSCI 1302 - Latin Squares v2019.sp

![Unrelated image from "The illustrated companion to the Latin dictionary and Greek lexicon"](https://i.imgur.com/XUnNC0D.jpg)

**DUE FRI 2019-02-01 (Feb 1) @ 11:55 PM**

This repository contains the skeleton code for the Latin Squares project
assigned to the students in the Spring 2019 CSCI 1302 classes
at the University of Georgia. 

**Please read the entirety of this file before beginning your project.**

**Seriously. Read this entire file *before* starting.**

## Table of Contents

* [Academic Honesty](#academic-honesty)
* [Updates](#updates)
* [Project Description](#project-description)
  * [What is a Latin Square?](#what-is-a-latin-square)
  * [What is the Latin Squares Game?](#what-is-the-latin-squares-game)
    * [Starting Configuration](#starting-configuration)
    * [Welcome Banner](#welcome-banner)
    * [Displaying the Square](#displaying-the-square)
    * [Prompting the User](#prompting-the-user)
    * [Win Message](#win-message)
    * [Game Loop](#game-loop)
* [Project Requirements & Grading](#project-requirements--grading)
  * [Functional Requirements](#functional-requirements)
  * [Non-Functional Requirements](#non-functional-requirements)
  * [Extra Credit Requirements](#extra-credit-requirements)
* [Suggestions](#suggestions)
* [How to Download the Project](#how-to-download-the-project)
* [Submission Instructions](#submission-instructions)
* [Appendix - Example Games](#appendix---example-games)

## Academic Honesty

You agree to the Academic Honesty policy as outlined in the course syllabus. 
In accordance with this notice, I must caution you **not** to 
fork this repository on GitHub if you have an account. Doing so will more than
likely make your copy of the project publicly visible. Please follow the 
instructions contained in the 
[How to Download the Project](#how-to-download-the-project)
section below in order to do your development on nike. Furthermore, you must adhere
 to the copyright notice and licensing information at the bottom of this document.

## Updates

Updates will be posted here.
If there has been an update and you have already cloned the project to Nike, 
then you can update your copy of the project using the <code>$ git pull</code>
command while inside of your project directory.

* **2019-02-25:** Added a clarification to the method height non-functional
  requirement pertaining to comments. Also added an additional paragraph to
  the [Suggestions](#suggestions) section concerning the refactoring of
  code blocks into their own methods.
  
* **2019-01-23:** Added a clarification to the extra credit concerning the order
  of the command-line arguments as well as the possible values for `k`.

## Project Description

This first project is meant to ensure that you are able to apply and extend
your prerequisite knowledge as well as introduce you to developing and testing
a Java application in a Linux environment (i.e., the Nike development
server). Many aspects of this project may be new to you. You will be asked
to do things that you have never been given explicit directions for before.
This is just a part of software development. Sometimes you need to research
how to solve a problem in order to implement a solution. That being said,
the material included in this document should hopefully answer the majority
of your questions.

Your goal is to develop an interactive, text-based version of a game called 
**Latin Squares**, which is based on a mathematical concept of the same name.
The next two subsections describe the general mathematical concept and the
game that is based on it, respectively.

### What is a Latin Square?

Let *n* denote some positive integer (i.e., *n* > 0). Suppose you have *n*-many 
characters. Then, an *n-by-n* *latin square* is an arrangement of all the
characters in an *n-by-n* grid such that no orthogonal (row or column) 
contains the same character twice. 

For example, consider *n = 2* and characters: `A`, `B`. There are two
possible latin squares that can be made with this information:

```
A B
B A
```

```
B A
A B
```

Here is another example. Consider *n = 3* and characters: `x`, `y`, `z`. 
There are actually twelve possible latin squares that can be made with 
this information, two of which are presented below:

```
x y z
z x y
y z x
```

```
x y z
y z x
z x y
```

For good measure, here is one more example. Consider *n = 4* and 
characters: `@`, `#`, `$`, `%`. There are 576 possible latin squares 
that can be made with this information! Here are two them:

```
@ # $ %
# @ % $
$ % @ #
% $ # @
```

```
@ # $ %
$ % @ #
# @ % $
% $ # @
```

Computer programs can store latin squares in memory in various
different ways, but a two-dimensional array of characters is
a common approach. The way that a latin square is stored should
be independent of how it is displayed to users. For example, 
each of the following outputs could use the exact same array
as storage since the formatting for the index values and 
surrounding characters can be printed as the contents of
the array are processed (i.e., while the array is looped 
through):

```
  0 1 2
0 x y z
1 z x y
2 y z x
```

```
    0   1   2
0 | x | y | z |
1 | y | z | x |
2 | z | x | y |
```

Another common approach for storing latin squares in memory
is using one one-dimensional array of characters for the characters 
and one two-dimensional array of integers for the square. 
For example, the same latin square in the last example might 
be stored using the following two arrays:

```
x y z
```

```
0 1 2
2 0 1
1 2 0
```

The second approach requires slightly more memory, but it likely
simplifies many algorithms the programmer might write for checking
whether or not the square is a latin square. 

It is very likely that some of you have seen a variation of the
latin square where the characters for an *n-by-n* square are
`1`, `2`, ..., `n`. This variation is often called *sudoku*
when `n` equals 9 and an additional requirement is imposed on 
the contents of the individual 3-by-3 squares within the greater 
square.

### What is the Latin Squares Game?

For the purposes of this assignment, the **Latin Squares** game is 
defined as an interactive, text-based game where users attempt to
complete a latin square. In the next couple of paragraphs, we will
discuss how the game is displayed to users as well as how users 
interact with the game. Later sections provide more detail about
requirements required to game's implementation. Although this is
mentioned later in this document, it is important to note here: all
game output should match the descriptions provided in this document
as closely as possible. 

If a user wishes to play a game of Latin Squares, they should be
able to by typing something like the following command at the 
shell prompt:
```
$ java -cp bin cs1302.game.LatinSquaresDriver some/path/to/config.txt 
```
where 

* `-cp bin` denotes that the class path to the compiled version
  of the game's default package is `bin`, 
* `cs1302.game.LatinSquaresDriver` denotes the fully qualified name of
  the game's driver class, and
* `some/path/to/config.txt` is the path to a text file that provides 
  the game's starting configuration. 
  
#### Starting Configuration

The starting configuration for a game is provided in some readable
text file adhering to a specific format, detailed below. For the
purposes of defining the specification, we will first describe
the file as containing *tokens* separated by *whitespace*. A token
is simply a string of non-whitespace characters, and whitespace
is defined as a string of whitespace characters. A whitespace
character is any character recognized by
[`Character.isWhitespace`](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#isWhitespace-char-).
If you use the 
[`Scanner`](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html)
class to parse the file, then tokens and whitespaces are handled
as described here. 

Here is the format for a file containing a starting configuration
for a Latin Square's game:

* The first token in the file represents *n*, the size of the game.
* The next *n*-many tokens represent the characters for the game.
* The next token represents *k*, the number of pre-determined locations
  in the latin square grid.
* The next *3k* tokens represent the placement and character for
  each of the pre-determined lcoations. Each location is made up
  of three tokens that provide the row, column, and character,
  respectively.
  
Here is an example of the same starting configuration saved
three different ways:

```
3
x y z
2
1 1 z
2 2 y
```

```
3 x y z 2 1 1 z 2 2 y
```

```
        3
 x     y 
z
    2
1                  1  z
2 
2 y
```
  
For the purposes of this assignment, you may safely assume that a
correctly formated file will be used to provide the game's 
starting configuration.
  
To read the file, let us assume that we have the path to the file
stored in a `String` variable called `config` and use the
following classes:

* [`File`](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) 
* [`Scanner`](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html)

Most of you have used the `Scanner` class to read from standard input.
Here, we will use it to read from a text file. This is accomplished using 
something similar to the following code snippet:

```java
try {
  File configFile = new File(config);
  Scanner configScanner = new Scanner(configFile);
  // use scanner here as usual
} catch (FileNotFoundException e) {
  // handle the exception here
  System.err.println(e);
  e.printStackTrace();
} // try
```

You may need to import 
[`FileNotFoundException`](https://docs.oracle.com/javase/8/docs/api/java/io/FileNotFoundException.html)
(or use its fully qualified name) if adapting the code snippet above. 
Information about the `try` and `catch` blocks is provided in 
Ch. 10 of the LDC4 textbook (see syllabus).

#### Welcome Banner

When the game first starts, the following text should be displayed
to standard output:

```
  _           _   _        _____
 | |         | | (_)      / ____|
 | |     __ _| |_ _ _ __ | (___   __ _ _   _  __ _ _ __ ___  ___
 | |    / _` | __| | '_ \ \___ \ / _` | | | |/ _` | '__/ _ \/ __|
 | |___| (_| | |_| | | | |____) | (_| | |_| | (_| | | |  __/\__ \
 |______\__,_|\__|_|_| |_|_____/ \__, |\__,_|\__,_|_|  \___||___/
                          CSCI 1302 | | v2019.sp
                                    |_|
n = 3 { x, y, z }
k = 2
```
where the text below the game title summarizes some of the
information about the game's starting configuration. In the
example above, the starting configuration specified a *3-by-3*
game with characters `x`, `y`, and `z`. Also, two pre-determined
locations are specified by the starting configuration as well.
The locations will be shown the first (and subsequent) times
the square is displayed to the user.

#### Displaying the Square

Irrespective of how a square's contents are stored, it should
be displayed to the user as follows:
```

    0   1   2
0 |   |   |   |
1 | y |[z]|   |
2 |   |   |[y]|

```
This example assumes the starting configuration that was
provided as an example earlier in this document. Locations
that are surrounded with square brackets are pre-determined.
The numbers along the top and left sides are location indices. 
Locations are separated column-wise using vertical bars in order
to help with readability. In this  particular example, the user 
has placed a `y` in location (1, 0), probably during their 
first move.

**NOTE:** The blank lines above and below the square in the example 
above are intentional. You should include blank lines in those 
locations in order to match expected output.

Let *p* denote the smallest integer such that *10<sup>p</sup> >= n*. 
Then, each column displayed in the output when a square is displayed
should take up *p+2* characters between each vertical bar (not including
the vertical bars). In this scenario, the 3 characters that make up 
the contents of a location should be aligned against the left vertical 
bar. A clear exception to this is when *n = 1*, in which case we 
assume that *p = 1*. 

Here is an example when *n = 12* (which makes *p = 2*):
```

     0    1    2    3    4    5    6    7    8    9    10   11
 0 |    |    |    |    |    |    |    |    |    |    |    |    |
 1 |    |[a] |    |    |    |    |    |    |    |    |    |    |  
 2 |    |    |[b] |    |    |    |    |    |    |    |    |    |
 3 |    |    |    |    |    |    |    |    |    |    |    |    |
 4 |    |    |    |    |    |    |    |    |    |    |    |    |
 5 |    |    |    |    |    |    |    |    |    |    |    |    |
 6 |    |    |    |    |    |    |    |    |    |    |    |    |
 7 |    |    |    |    |    |    |    |    |    |    |    |    |
 8 |    |    |    |    |    |    |    |    |    |    |    |    |
 9 |    |    |    |    |    |    |    |    |    |    |    |    |
10 |    |    |    |    |    |    |    |    |    |    |    |    |
11 |    |    |    |    |    |    |    |    |    |    |    |[k] |

```
The corresponding starting configuration for this might be:
```
12 a b c d e f g h i j k l 3 1 1 a 2 2 b 11 11 k
```

**NOTE:** Examples of complete game input/output are provided in the appendix 
of this document.

#### Prompting the User

Your game will display a prompt to the user at which the
user will type in their input. The prompt should look this:
```
latin-squares: 
```
where there is a single space after the `:`. When the user
types in their input, it should appear on the same line
as the prompt. 

For this game, there are only two valid commands:

1. `latin-squares: q` <br>
   This should quit the game immediately using `System.exit(0)`.
   
2. `latin-squares: row col char` <br>
   This should update the specified location to be the specified
   character. Users are allowed to repeately specifiy the same
   location. However, users are not allowed to specify any of the
   pre-determined locations specified by the game's starting
   configuration. 

If valid input is entered by the user, then the game should proceed
as normal. If invalid input is entered by the user, then the game
should display the following message, then re-prompt the user:
```
error: invalid input!
```
This is repeated until valid input is entered. In addition to
obviously invalid input (e.g., invalid location or character),
the inclusion of extra tokens is also considered invalid input.

**NOTE:** Examples of complete game input/output are provided in the appendix 
of this document.

#### Win Message

Every time the contents of the square is updated, the game
should check to see if it is a latin square. If so, the
the game is over. Before the game exits, the final square is 
displayed, followed by this congratulatory text:
```
                                 .''.
       .''.             *''*    :_\/_:     . 
      :_\/_:   .    .:.*_\/_*   : /\ :  .'.:.'.
  .''.: /\ : _\(/_  ':'* /\ *  : '..'.  -=:o:=-
 :_\/_:'.:::. /)\*''*  .|.* '.\'/.'_\(/_'.':'.'
 : /\ : :::::  '*_\/_* | |  -= o =- /)\    '  *
  '..'  ':::'   * /\ * |'|  .'/.\'.  '._____
      *        __*..* |  |     :      |.   |' .---"|
       _*   .-'   '-. |  |     .--'|  ||   | _|    |
    .-'|  _.|  |    ||   '-__  |   |  |    ||      |
    |' | |.    |    ||       | |   |  |    ||      |
 ___|  '-'     '    ""       '-'   '-.'    '`      |____
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    CONGRATULATIONS! YOU COMPLETED THE LATIN SQUARE!
```
Take note that some of the characters in the text above
may need to be escaped when stored in a string literal
in Java.

**NOTE:** Examples of complete game input/output are provided in the appendix 
of this document.

#### Game Loop

From the user's perspective, the game should progress according
to the following pseudocode:

1. `WELCOME BANNER`
2. `LOOP UNTIL GAME OVER:`
   1. `DISPLAY SQUARE`
   2. `DISPLAY GAME PROMPT`
3. `DISPLAY WIN MESSAGE`

**NOTE:** Examples of complete game input/output are provided in the appendix 
of this document.

## Project Requirements & Grading

This assignment is worth 100 points. The lowest possible grade is 0, and the 
highest possible grade is 105 (due to extra credit).

### Functional Requirements

A functional requirement is *added* to your point total if satisfied.
There will be no partial credit for any of the requirements that simply 
require the presence of a method related a particular functionality. 
The actual functionality is tested using test cases.

* **(35 points) `cs1302.game.LatinSquaresGame` Class**: Instances of
  this class represent a game of Latin Squares. You need to implement
  all of the methods listed below. Unless stated otherwise, each
  method is assumed to have public visibility.
  
  * **(5 points) `LatinSquaresGame(String config)`**: In this constructor, 
    you should read the contents of the file whose path is stored in `config`
    and initialize variables, as needed, to setup the game according
    to the starting configuration specified by the file.
  
  * **(5 points) `void printWelcome()`:** This method should print
    the welcome banner to standard output, as described earler in
    this document. 
  
  * **(5 points) `void displaySquare()`:** This method should print
    the current contents of the square to standard output, as
    described earlier in this document.
    
  * **(5 points) `void promptUser()`:** This method should print
    the game prompt to standard output and interpret user input
    from standard input, as described earlier in this document.
    Instead of writing the code to check the square in this method,
    you should call the `isLatinSquare` method described in these
    requirements instead. 
    
  * **(5 points) `boolean isLatinSquare()`:** This method should 
    return `true` if, and only if, all locations in the square are
    filled and the square is a latin square, as defined earlier in
    this document.
    
  * **(5 points) `void printWin()`:** This method should print
    the win message to standard output, as described earler in
    this document.
    
  * **(5 points) `void play()`:** This method should provide the
    main game loop by invoking other instance methods, as needed.
  
  **NOTE:** Please see the [Suggestions](#suggestions) section of this
  document before writing the code to implement these methods.
  
  **NOTE:** You are not only free but encouraged to implement other methods, 
  as needed, to help with readability, code reuse, etc.  

* **(5 points) `cs1302.game.LatinSquaresDriver` Class**: This class
  should only contain the `main` method:
  
  * `void main(String[] args)`: This public, static method should 
    do the following :
    
    1. Interpret `args[0]` as `config`, a string that specifies the 
       path to some file that provides a starting configuration.
    2. Create a `LatinSquaresGame` object by passing `config` into
       the constructor.
    3. Invoke the `play` method on the `LatinSquaresGame` object.
    
    For the purposes of this assignment, you may safely assume that
    valid input will be provided for the driver's command line
    arguments.
    
    Additional code may be required if you are attempting one of the
    extra credit requirements listed later in this document. 

* **(60 points) Test Cases**: The bulk of this project will be graded
  based on 30 test cases, each worth 2 points. A single test case can 
  be described by three things: i) some starting configuration; ii) a 
  sequence of user input; and iii) the program output given (i) and (ii).
  A few of these test cases are provided with the project.
  
  When a regular user plays the game, they specify a file with the
  starting configuration, e.g.,
  ```
  $ java -cp bin cs1302.game.LatinSquaresDriver tc01.config.txt
  ```
  In this scenario, the user enters their commands into standard input
  and the game prints its output to standard output. 
  
  When the grader wants to check your game, they will not manually
  type in commands into standard input. Instead, they will use the shell
  to redirect standard input to a file that contains user input. From the
  program's perspective, it stil thinks it's reading from standard input.
  It's just that standard input now refers to an actual file on disk
  instead of keyboard input. This is accomplished using the shell
  input redirection operator `<` or pipe `|`. For example, the grader 
  might type either of the following to accomplish the same thing:
  ```
  $ java -cp bin cs1302.game.LatinSquaresDriver tc01.config.txt < tc01.in.txt
  ```
  ```
  $ cat tc01.in.txt | java -cp bin cs1302.game.LatinSquaresDriver tc01.config.txt
  ```
  In this example, the shell forces the program to interpret standard input
  as the file `tc01.in.txt`. Instead of halting for user input, any method
  calls to your program's `Scanner` object for `System.in` return immediately
  with a token from the file. Once the program has stopped producing output,
  the grader then compares that output to a file containing the expected
  output for that test case (e.g., `tc01.out.txt`).
  
  You can test your program manually or using a similar automating procedure
  as described above. All of the examples provided in the 
  [Appendix](#appendix---example-games) are test cases. Their associated
  test case files are located in the `tests` directory provided with
  this project. 

### Non-Functional Requirements

A non-functional requirement is *subtracted* from your point total if
not satisfied. In order to emphasize the importance of these requirements,
non-compliance results in the full point amount being subtracted from your
point total. That is, they are all or nothing. 

* **(100 points) Project Directory Structure:** The location of the default
  package for the source code should be a direct subdirectory of 
  `cs1302-latin-squares` called `src`. When the project is compiled, 
  the `-d` option should be used with `javac` to make the default package 
  for compiled code a direct subdirectory of `cs1302-latin-squares` 
  called `bin`. 
  
  If you follow this structure, then you would type the following to compile 
  your code, assuming you are in the top-level project 
  directory `cs1302-latin-squares`:
  ```
  $ javac -cp bin -d bin src/cs1302/game/LatinSquaresGame.java
  $ javac -cp bin -d bin src/cs1302/game/LatinSquaresDriver.java
  ```
  Remember, when you compile `.java` files individually, there might be 
  dependencies between the files. In such cases, the order in which you
  compile the code matters.

* **(100 points) Development Environment:** This project must be implemented 
  in Java 8, and it *must compile and run* correctly on Nike using the specific
  version of Java 8 that is setup according to the instructions provided
  by your instructor. For Spring 2019, these instructions were posted on
  Piazza [@29](https://piazza.com/class/jpupoaxnvvs497?cid=29).
  
* **(100 points) One Scanner for Standard Input:** Only one `Scanner` 
  object for `System.in` (i.e., for standard input) should be created. 
  You are free to make `Scanner` objects for other input sources as 
  needed. Please note that if you create a new  `Scanner` object at
  the beginning of a method or loop, then more than one object will
  be created if the method is called more than once or if the loop
  iterates more than once. 
  
* **(100 points) No Static Variables:** Use of static variables is 
  not allowed for this assignment. However, static constants are permitted.
  
* **(25 points) Code Style Guidelines:** You should be consistent with the style 
  aspect of your code in order to promote readability. All of the individual code
  style guidelines listed below are part of a single non-functional requirement
  that, like the others, is all or nothing. Besides consistency, the
  following conventions will be enforced:
  
  * **Reference type names are written in _UpperCamelCase_.** Class names are  
    typically nouns or noun phrases. For example, `Character` or `ImmutableList`. 
    Interface names may also be nouns or noun phrases (for example, `List`), but 
    may sometimes be adjectives or adjective phrases instead (for example, 
    `Readable`).
  
  * **Method names are written in _lowerCamelCase_.** Method names are also 
    typically verbs or verb phrases. For example, `sendMessage` or `stop`.
  
  * **Braces are always used where optional.** Braces should be used with `if`, 
    `else`, `for`, `do`, and `while` statements, even when the body is empty or 
    contains only a single statement.
    
  * **Column limit: 100.** You should limit the number of characters, including
    whitespace, on any given line to 100 characters. Except as noted below, any 
    line that would exceed this limit must be manually line-wrapped in a
    consistent manner. Exceptions to the column limit include:
    
    * Lines where obeying the column limit is not possible (for example, a long 
      URL in Javadoc, or a long JSNI method reference).
    * In `package` and `import` statements.
    * Command line input examples in a comment that may be cut-and-pasted into 
      a shell.
      
    If you use Emacs, you can add the following to your `~/.emacs` file to 
    highlight characters that exceed the column limit:
    ```
    ;; check for lines that exceed some column limit
    (setq-default
     whitespace-line-column 100
     whitespace-style '(face lines-tail))
    (add-hook 'prog-mode-hook #'whitespace-mode)
    ```
    You can create the `~/.emacs` file if it does not exist.
      
  * **Method height <= window height.** You should limit the number of lines for
    a method so that the entire method can be seen on the screen at once. This
    includes the line(s) with the method's signature and opening curly brace, all
    lines in the body of the mthod (including blank lines), and the line with
    the method's ending curly brace. The method height does not include a
    method's Javadoc comment, however, it does include any comments contained
    within the body of the method. 
    
    Of all the style guidelines, this is the probably the most subjective and 
    hardest to grade because everyone might have a different window size due
    to different terminal emulator and physical screen size configurations. 
    Therefore, graders will be checking for compliance with the spirit
    of this guideline, which is: methods that are too big and/or repetitive 
    should be refactored to include proper looping constructs and/or broken
    up into smaller methods to improve readability. 

* **Javadoc Documentation (25 points):** All methods and classes needs to be documented
  using Javadoc comments. At the very least, your comment should provide a description
  of the method's functionality in the first sentence of the comment.  This sentence
  needs to be a grammatically correct English sentence with proper punctuation. Further 
  description can be provided in subsequent sentence. The basic formatting of Javadoc 
  blocks is as seen in this example:
  ```java
  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  public int method(String p1) { ... }
  ```
  ... or in this single-line example:
  ```java
  /** An especially short bit of Javadoc. */
  ```
  The basic form is always acceptable. The single-line form may be substituted when 
  the entirety of the Javadoc block (including comment markers) can fit on a single
  line. More information about Javadoc can be found
  [here](https://www.oracle.com/technetwork/java/javase/documentation/index-137868.html).
  It should be noted that we do expect you to provide a Javadoc comment for each class
  in addition to a comment for each method within a class. The Javadoc comment
  for a class is placed directly above the class declaration as seen in the examples
  provided in the link referenced earlier. 
  You are free to provide more information in your Javadoc comments (e.g., such as
  parameter and return value documentation) as long as they are done using 
  proper Javadoc syntax. We will cover Javadoc comments in much more detail in a 
  later assignment.

* **In-line Documentation (25 points):** Code blocks should be adequately documented
  using in-line comments. This is especially necessary when a block of code
  is not immediately understood by a reader (e.g., yourself or the grader). 

### Extra Credit Requirements

An extra credit requirement is an extra functional requirement that is *added* 
to your point total if satisfied. If you want the graders to check for any 
extra credit requirements, then you must include an extra text file with your 
submission called `EXTRA.md`. In that file, you need to provide a brief 
description of each extra credit that should be checked.

* **Config Generator (5 points):** Allow users to generate config files that
  specifiy random, valid starting configurations by specifying a `--gen` 
  option to the driver class. Here is a synopsis that should be followed:
  ```
  $ java -cp bin cs1302.game.LatinSquaresDriver --gen file n k c1 c2 c3 ...
  ```
  where 

  * `-cp bin` denotes that the class path to the compiled version
    of the game's default package is `bin`, 
  * `cs1302.game.LatinSquaresDriver` denotes the fully qualified name of
    the game's driver class,
  * `file` is the path to a text file to which the starting configuration
    will be written,
  * `n` denotes the size of the square,
  * `k` denotes the number of pre-determined locations to generate, and
  * `c1 c2 c3 ...` denote the *n*-many characters that will be used.
  
  You may assume the order of the command-line arguments as presented
  above. Additionally, you may assume valid input of command-line arguments
  and that the value of `k` will be strictly less than `0.5 * n * n`.
  
  Use of the
  [`PrintWriter`](https://docs.oracle.com/javase/8/docs/api/java/io/PrintWriter.html)
  class is reccommended. You are likely familiar with printing to standard
  output via `System.out`. The `PrintWriter` class allows you to create an
  object for arbitrary file output with the same interface that you are used
  to (i.e., it provides methods like `print`, `println`, etc.). 
  
  If this extra credit is mentioned in `EXTRA.md`, then it will be tested
  using five simple test cases. 
  
## Suggestions

You should plan out your class before you begin to write it. While you already
know a few things from the requirements that should be included in the class,
there is a great deal more to actually consider. A class represents a kind of
template or schematic for objects. This means that it needs to describe all of
the things that an object would need in order to function properly. This
includes both behavior, which is captured by methods, as well as state, which
is captured by instance variables.

You will need to create instance variables (i.e., non-static variables of the 
class) in order to maintain the state information of your Latin Squares game. 
All of these instance variables should be declared near the top of the class 
body (but not initialized there) and initialized in the class's constructor.

I encourage you to consider the different ways to store a latin square,
which were described earlier in this document, when designing your class.
Your choice will directly impact the work done in various methods throughout
the class.

**NOTE:** You should make the reference variable for standard input
`Scanner` object an instance variable. This will help you stay in
compliance with one of the non-functional requirements for this
project. 

Finally, if you can point to a block of code in a method and describe what it
does at a high level, then that block of code is a perfect candidate for
refactoring into its own method. If you give the method a good name and a
good Javadoc comment, then calling that method where the original block
was placed will make your code more readable. If someone wants the details,
then they can refer to the documentation for the method. For complicated
loops, consider creating a method (with a good name) that handles a single
iteration of the loop, then calling that method in your loop with appropriate
parameter values. In many cases, this may be prefered over moving the loop
itself into another method. The idea is to make your code easier to read for
others as well as yourself.

## How to Download the Project

On Nike, execute the following terminal command in order to download the project
files into sub-directory within your present working directory:

```
$ git clone https://github.com/cs1302uga/cs1302-latin-squares.git
```

This should create a directory called <code>cs1302-latin-squares</code> in
your present working directory that contains the project files. For this
project, the only files that are included with the project download
are listed near the top of the page 
[here](https://github.com/cs1302uga/cs1302-latin-squares).

If any updates to the project files are announced by your instructor, you can
merge those changes into your copy by changing into your project's directory
on Nike and issuing the following terminal command:

```
$ git pull
```

If you have any problems with any of these procedures, then please contact
your instructor via Piazza. 

## Submission Instructions

You will still be submitting your project via Nike. Make sure your project files
are on <code>nike.cs.uga.edu</code>. Change into the parent directory of your
project directory. If you've followed the
instructions provided in this document, then the name of your project directory
is likely <code>cs1302-latin-squares</code>. While in your project parent
directory, execute the following command: 

```
$ submit cs1302-latin-squares cs1302a
```

It is also a good idea to email a copy to yourself. To do this, simply execute 
the following command, replacing `your@email` with your actual email address:

```
$ tar zcvf cs1302-latin-squares.tar.gz cs1302-latin-squares
$ mutt -s "[cs1302] cs1302-latin-squares" -a cs1302-latin-squares.tar.gz -- your@email.com < /dev/null
```

If you have any problems submitting your project then please make a private Piazza
post to "Instructors" as soon as possible. 

## Appendix - Example Games

All of the examples provided in this appendix are also test cases. Their associated
test case files are located in the `tests` directory provided with this project.
Please read the [Functional Requirements](#functional-requirements) section for
information on how to automate the running of test cases. 

### Test Case 01 (`tc01`)

This test case creates a *2-by-2* game with no pre-determined locations, then
has the user immediately quit.

* Starting Configuration: [`tc01.config.txt`](tests/tc01.config.txt)
* User Input: [`tc01.in.txt`](tests/tc01.in.txt)
* Expected Output: [`tc01.out.txt`](tests/tc01.out.txt)

```
$ java -cp bin cs1302.game.LatinSquaresDriver tests/tc01.config.txt < tests/tc01.in.txt
```

### Test Case 03 (`tc02`)

This test case creates a *2-by-2* game with two pre-determined locations, then
has the user immediately quit.

* Starting Configuration: [`tc02.config.txt`](tests/tc02.config.txt)
* User Input: [`tc02.in.txt`](tests/tc02.in.txt)
* Expected Output: [`tc02.out.txt`](tests/tc02.out.txt)

```
$ java -cp bin cs1302.game.LatinSquaresDriver tests/tc02.config.txt < tests/tc02.in.txt
```

### Test Case 03 (`tc03`)

This test case creates a *2-by-2* game with two pre-determined locations, then
has the user complete the latin square.

* Starting Configuration: [`tc03.config.txt`](tests/tc03.config.txt)
* User Input: [`tc03.in.txt`](tests/tc03.in.txt)
* Expected Output: [`tc03.out.txt`](tests/tc03.out.txt)

```
$ java -cp bin cs1302.game.LatinSquaresDriver tests/tc03.config.txt < tests/tc03.in.txt
```

<hr/>

[![License: CC BY-NC-ND 4.0](https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg)](http://creativecommons.org/licenses/by-nc-nd/4.0/)

<small>
Copyright &copy; Michael E. Cotterell and the University of Georgia.
This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a> to students and the public.
The content and opinions expressed on this page do not necessarily reflect the views of nor are they endorsed by the University of Georgia or the University System of Georgia.
</small>
