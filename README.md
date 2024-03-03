# Compiler Documentation

## Running the Compiler

1. Open the 'student-package' folder in Visual Studio Code.
2. Open the terminal in Visual Studio Code.
3. Navigate to the 'student-package' folder using the 'cd' command.
4. Run the following command to compile the Java files:
   ```bash
   javac Token.java CompilationException.java LexicalAnalyser.java AbstractGenerate.java AbstractSyntaxAnalyser.java Generate.java SyntaxAnalyser.java Compile.java
Press 'Enter' to execute the compilation command.

After successful compilation, run the following command to execute the compiler:

      java Compile
      
Press 'Enter' to run the compiler.


**Understanding Compilation Exceptions**
CompilationException - SyntaxError in Program 2

    Issue: Typo in the keyword "begim" instead of "begin".
    Cause: Incorrect keyword spelling.

CompilationException - SyntaxError in Program 7

    Issue: The "else" keyword doesn't have a corresponding "if" statement.
    Cause: Unmatched "else" keyword without a preceding "if" statement.

CompilationException - SyntaxError in Program 9

    Issue: Missing semicolon after x3 := x3 + x2 in the loop.
    Cause: Syntax error due to the missing semicolon.

CompilationException - SyntaxError in Program 10

    Issue: Incorrect loop syntax; missing the else keyword.
    Cause: Loop syntax error, lacking the required else keyword.

CompilationException - SyntaxError in Program 7

    Issue: Missing semicolon after x2 := a - b + c - d + e.
    Cause: Syntax error due to the missing semicolon.

CompilationException - SyntaxError in Program 7

    Issue: Missing then keyword after else.
    Cause: Syntax error in the conditional statement, lacking the required then keyword.

**Compilation Exceptions**
Caused by Expected symbol: EOF, but found:

    Issue: Unexpected symbol at the end of the program, possibly an extra character or syntax error.
    Cause: The program unexpectedly ends before reaching the expected end-of-file (EOF) symbol.

Caused by Expected symbol: EOF, but found: else

    Issue: Unexpected "else" keyword at the end of the program without a corresponding "if" statement.
    Cause: The "else" keyword lacks a preceding "if" statement, causing a syntax error.

Caused by Expected symbol: EOF, but found: IDENTIFIER

    Issue: Unexpected identifier at the end of the program, suggesting a possible variable or keyword error.
    Cause: The program expects the end-of-file (EOF) symbol but encounters an identifier instead.

**Note**
After implementing try-catch blocks, the specific symbols causing the issues have changed, but the general nature of the problems remains.

**Contact**

If you encounter any further issues, please feel free to contact us.
    
    Feel free to customize it further based on your needs!
