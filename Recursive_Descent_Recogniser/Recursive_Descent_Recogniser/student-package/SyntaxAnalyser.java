import java.io.IOException;
import java.io.PrintStream;

public class SyntaxAnalyser extends AbstractSyntaxAnalyser {

    // Add your name in the comment below
    // author: Your Name

    private LexicalAnalyser lex;

    public SyntaxAnalyser(LexicalAnalyser lex) throws IOException {
        super(lex); // Call the constructor of the superclass with the LexicalAnalyser instance
        this.lex = lex;
    }

    @Override
    public void _statementPart_() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<statement part>");
        try {
            // Expecting the program to start with 'begin'
            acceptTerminal(Token.beginSymbol);

            _statementList();

            // Expecting the program to end with 'end'
            acceptTerminal(Token.endSymbol);

            myGenerate.finishNonterminal("<statement part>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _statementList() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<statement list>");
        try {
            while (nextToken.symbol != Token.endSymbol) {
                _statement();

                // Optional semicolon between statements
                if (nextToken.symbol == Token.semicolonSymbol) {
                    acceptTerminal(Token.semicolonSymbol);
                } else {
                    break;
                }
            }

            myGenerate.finishNonterminal("<statement list>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _statement() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<statement>");
        try {
            // Check the type of statement and proceed accordingly
            if (nextToken.symbol == Token.identifier) {
                _assignmentStatement();
            } else if (nextToken.symbol == Token.ifSymbol) {
                _ifStatement();
            } else if (nextToken.symbol == Token.whileSymbol) {
                _whileStatement();
            } else if (nextToken.symbol == Token.callSymbol) {
                _procedureStatement();
            } else if (nextToken.symbol == Token.doSymbol) {
                _untilStatement();
            } else if (nextToken.symbol == Token.forSymbol) {
                _forStatement();
            } else {
                // Handle unexpected symbols
                String expectedSymbolName = "identifier, if, while, call, do, or for";
                String actualSymbolName = Token.getName(nextToken.symbol);
                String errorMessage = "Expected: " + expectedSymbolName + ", but found: " + actualSymbolName;
                throw new CompilationException(errorMessage);
            }

            myGenerate.finishNonterminal("<statement>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _assignmentStatement() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<assignment statement>");

        try {
            // Expecting an identifier
            acceptTerminal(Token.identifier);

            // Check for both := and assignSymbol
            if (nextToken.symbol == Token.assignSymbol || nextToken.symbol == Token.becomesSymbol) {
                acceptTerminal(nextToken.symbol);
            } else {
                // Handle unexpected symbols
                String expectedSymbolName = "assignSymbol or becomesSymbol";
                String actualSymbolName = Token.getName(nextToken.symbol);
                String errorMessage = "Expected: " + expectedSymbolName + ", but found: " + actualSymbolName;
                throw new CompilationException(errorMessage);
            }
            // Either a stringConstant or an expression is expected
            if (nextToken.symbol == Token.stringConstant) {
                acceptTerminal(Token.stringConstant);
            } else {
                _expression();
            }

            myGenerate.finishNonterminal("<assignment statement>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _ifStatement() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<if statement>");
        try {
            // Expecting 'if' keyword
            acceptTerminal(Token.ifSymbol);
            // Checking and processing the condition
            _condition();
            // Expecting 'then' keyword
            acceptTerminal(Token.thenSymbol);
            // Processing the statements inside the if block
            _statementList();
            // Checking for an 'else' block
            if (nextToken.symbol == Token.elseSymbol) {
                acceptTerminal(Token.elseSymbol);
                // Processing the statements inside the else block
                _statementList();
            }
            // Expecting 'end' keyword to close the if statement
            acceptTerminal(Token.endSymbol);

            myGenerate.finishNonterminal("<if statement>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }

    }

    private void _whileStatement() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<while statement>");
        try {
            // Expecting 'while' keyword
            acceptTerminal(Token.whileSymbol);
            // Checking and processing the condition
            _condition();
            // Expecting 'loop' keyword
            acceptTerminal(Token.loopSymbol);
            // Processing the statements inside the while loop
            _statementList();
            // Expecting 'end' keyword to close the while loop
            acceptTerminal(Token.endSymbol);

            myGenerate.finishNonterminal("<while statement>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _procedureStatement() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<procedure statement>");
        try {
            // Expecting 'call' keyword
            acceptTerminal(Token.callSymbol);
            // Expecting an identifier for the procedure
            acceptTerminal(Token.identifier);
            // Expecting '(' for the argument list
            acceptTerminal(Token.leftParenthesis);
            // Processing the argument list
            _argumentList();
            // Expecting ')' to close the argument list
            acceptTerminal(Token.rightParenthesis);

            myGenerate.finishNonterminal("<procedure statement>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _untilStatement() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<until statement>");
        try {
            // Expecting 'do' keyword
            acceptTerminal(Token.doSymbol);
            // Processing the statements inside the until loop
            _statementList();
            // Expecting 'until' keyword
            acceptTerminal(Token.untilSymbol);
            // Checking and processing the condition
            _condition();

            myGenerate.finishNonterminal("<until statement>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _forStatement() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<for statement>");
        try {
            // Expecting 'for' keyword
            acceptTerminal(Token.forSymbol);
            // Expecting '(' to start the initialization part of the for loop
            acceptTerminal(Token.leftParenthesis);
            // Processing the initialization part
            _assignmentStatement();
            // Expecting ';' to separate initialization and condition
            acceptTerminal(Token.semicolonSymbol);
            // Checking and processing the condition
            _condition();
            acceptTerminal(Token.semicolonSymbol);
            _assignmentStatement();
            acceptTerminal(Token.rightParenthesis);
            acceptTerminal(Token.doSymbol);
            _statementList();
            acceptTerminal(Token.endSymbol);

            myGenerate.finishNonterminal("<for statement>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _argumentList() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<argument list>");
        try {
            // Checking if there is at least one identifier in the argument list
            if (nextToken.symbol == Token.identifier) {
                acceptTerminal(Token.identifier);

                while (nextToken.symbol == Token.commaSymbol) {
                    acceptTerminal(Token.commaSymbol);
                    acceptTerminal(Token.identifier);
                }
            }

            myGenerate.finishNonterminal("<argument list>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _condition() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<condition>");
        try {
            // Expecting an identifier for the condition
            acceptTerminal(Token.identifier);

            // Check for various conditional operators
            if (nextToken.symbol == Token.equalSymbol ||
                    nextToken.symbol == Token.notEqualSymbol ||
                    nextToken.symbol == Token.lessThanSymbol ||
                    nextToken.symbol == Token.lessEqualSymbol ||
                    nextToken.symbol == Token.greaterThanSymbol ||
                    nextToken.symbol == Token.greaterEqualSymbol) {
                acceptTerminal(nextToken.symbol);
            } else {
                // Handle the case where an unexpected operator is found
                String expectedSymbolNames = "=, /=, <, <=, >, >=";
                String actualSymbolName = Token.getName(nextToken.symbol);
                String errorMessage = "Expected one of: " + expectedSymbolNames + ", but found: " + actualSymbolName;
                throw new CompilationException(errorMessage);
            }

            if (nextToken.symbol == Token.numberConstant || nextToken.symbol == Token.stringConstant) {
                // Accept either numberConstant or stringConstant
                acceptTerminal(nextToken.symbol);
            } else {
                // Handle the case where neither numberConstant nor stringConstant is found
                String expectedSymbolName = "numberConstant or stringConstant";
                String actualSymbolName = Token.getName(nextToken.symbol);
                String errorMessage = "Expected: " + expectedSymbolName + ", but found: " + actualSymbolName;
                throw new CompilationException(errorMessage);
            }

            myGenerate.finishNonterminal("<condition>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _expression() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<expression>");
        try {
            // Processing the first term
            _term();
            // Handling additional terms if '+' or '-' is encountered
            while (nextToken.symbol == Token.plusSymbol || nextToken.symbol == Token.minusSymbol) {
                acceptTerminal(nextToken.symbol);
                // Processing the additional term
                _term();
            }

            myGenerate.finishNonterminal("<expression>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _term() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<term>");
        try {
            // Processing the first factor
            _factor();
            // Handling additional factors if '*' or '/' is encountered
            while (nextToken.symbol == Token.multiplySymbol || nextToken.symbol == Token.divideSymbol) {
                acceptTerminal(nextToken.symbol);
                _factor();
            }

            myGenerate.finishNonterminal("<term>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    private void _factor() throws IOException, CompilationException {
        myGenerate.commenceNonterminal("<factor>");
        try {

            if (nextToken.symbol == Token.identifier || nextToken.symbol == Token.numberConstant) {
                acceptTerminal(nextToken.symbol);
            } else if (nextToken.symbol == Token.leftParenthesis) {

                acceptTerminal(Token.leftParenthesis);
                _expression();
                acceptTerminal(Token.rightParenthesis);
            }

            else if (nextToken.symbol == Token.timesSymbol || nextToken.symbol == Token.stringSymbol) {
                acceptTerminal(nextToken.symbol);
            }

            else {
                // Handle any other cases or throw an exception
                String expectedSymbolName;

                if (nextToken.symbol == Token.identifier) {
                    expectedSymbolName = "identifier";
                } else if (nextToken.symbol == Token.numberConstant) {
                    expectedSymbolName = "numberConstant";
                } else if (nextToken.symbol == Token.leftParenthesis) {
                    expectedSymbolName = "(";
                } else if (nextToken.symbol == Token.timesSymbol) {
                    expectedSymbolName = "*";
                } else if (nextToken.symbol == Token.stringSymbol) {
                    expectedSymbolName = "string";
                } else {
                    expectedSymbolName = "someDefaultName"; // Update with appropriate handling
                }
                String actualSymbolName = Token.getName(nextToken.symbol);
                String errorMessage = "Expected: " + expectedSymbolName + ", but found: " + actualSymbolName;
                throw new CompilationException(errorMessage);
            }

            myGenerate.finishNonterminal("<factor>");
            myGenerate.reportSuccess();
        } catch (CompilationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acceptTerminal(int symbol) throws IOException, CompilationException {
        if (symbol == Token.conditionalOperator) {
            // Check if the next token is a valid conditionalOperator
            if (nextToken.symbol == Token.conditionalOperator) {
                myGenerate.insertTerminal(nextToken);
                nextToken = lex.getNextToken();
            } else {
                String expectedSymbolName = "conditionalOperator";
                String actualSymbolName = Token.getName(nextToken.symbol);
                String errorMessage = "Expected: " + expectedSymbolName + ", but found: " + actualSymbolName;
                throw new CompilationException(errorMessage);
            }
        } else if (nextToken.symbol == symbol) {
            myGenerate.insertTerminal(nextToken);
            nextToken = lex.getNextToken();
        } else {
            String expectedSymbolName = Token.getName(symbol);
            String actualSymbolName = Token.getName(nextToken.symbol);
            String errorMessage = "Expected symbol: " + expectedSymbolName + ", but found: " + actualSymbolName;
            throw new CompilationException(errorMessage);
        }
    }

    public static void main(String[] args) {
        try {
            // Initializing the LexicalAnalyser with the input program
            LexicalAnalyser lex = new LexicalAnalyser("program0");
            // Creating an instance of SyntaxAnalyser with the LexicalAnalyser instance
            SyntaxAnalyser syntaxAnalyser = new SyntaxAnalyser(lex);
            // Initiating the parsing process
            syntaxAnalyser.parse(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
