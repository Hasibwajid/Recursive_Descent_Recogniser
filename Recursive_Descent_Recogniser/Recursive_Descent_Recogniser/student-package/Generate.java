import java.io.PrintStream;
import java.io.IOException;

public class Generate extends AbstractGenerate {

    // Add your name in the comment below
    // author: Your Name

    @Override
    public void insertTerminal(Token token) {
        // Outputting a message when a terminal is inserted
        System.out.println("Terminal Inserted: " + token.getText());
    }

    @Override
    public void commenceNonterminal(String nonTerminalName) {
        // Outputting a message when a nonterminal begins
        System.out.println("Commence Nonterminal: " + nonTerminalName);
    }

    @Override
    public void finishNonterminal(String nonTerminalName) {
        // Outputting a message when a nonterminal finishes
        System.out.println("Finish Nonterminal: " + nonTerminalName);
    }

    @Override
    public void reportSuccess() {
        // Outputting a success message when parsing is successful
        System.out.println("Parsing successful!");
    }

    @Override
    public void reportError(Token token, String explanatoryMessage) throws CompilationException {
        String expectedSymbolName;
        // Handling various token symbols for generating error messages
        switch (token.symbol) {
            case Token.becomesSymbol:
                expectedSymbolName = ":=";
                break;
            case Token.beginSymbol:
                expectedSymbolName = "begin";
                break;
            case Token.callSymbol:
                expectedSymbolName = "call";
                break;
            case Token.colonSymbol:
                expectedSymbolName = ":";
                break;
            case Token.commaSymbol:
                expectedSymbolName = ",";
                break;
            case Token.divideSymbol:
                expectedSymbolName = "/";
                break;
            case Token.doSymbol:
                expectedSymbolName = "do";
                break;
            case Token.endSymbol:
                expectedSymbolName = "end";
                break;
            case Token.elseSymbol:
                expectedSymbolName = "else";
                break;
            case Token.eofSymbol:
                expectedSymbolName = "EOF";
                break;
            case Token.equalSymbol:
                expectedSymbolName = "=";
                break;
            case Token.errorSymbol:
                expectedSymbolName = "ERROR";
                break;
            case Token.floatSymbol:
                expectedSymbolName = "float";
                break;
            case Token.greaterEqualSymbol:
                expectedSymbolName = ">=";
                break;
            case Token.greaterThanSymbol:
                expectedSymbolName = ">";
                break;
            case Token.identifier:
                expectedSymbolName = "IDENTIFIER";
                break;
            case Token.ifSymbol:
                expectedSymbolName = "if";
                break;
            case Token.integerSymbol:
                expectedSymbolName = "integer";
                break;
            case Token.isSymbol:
                expectedSymbolName = "is";
                break;
            case Token.leftParenthesis:
                expectedSymbolName = "(";
                break;
            case Token.lessEqualSymbol:
                expectedSymbolName = "<=";
                break;
            case Token.lessThanSymbol:
                expectedSymbolName = "<";
                break;
            case Token.loopSymbol:
                expectedSymbolName = "loop";
                break;
            case Token.minusSymbol:
                expectedSymbolName = "-";
                break;
            case Token.notEqualSymbol:
                expectedSymbolName = "/=";
                break;
            case Token.numberConstant:
                expectedSymbolName = "NUMBER";
                break;
            case Token.plusSymbol:
                expectedSymbolName = "+";
                break;
            case Token.procedureSymbol:
                expectedSymbolName = "procedure";
                break;
            case Token.rightParenthesis:
                expectedSymbolName = ")";
                break;
            case Token.semicolonSymbol:
                expectedSymbolName = ";";
                break;
            case Token.stringConstant:
                expectedSymbolName = "STRING";
                break;
            case Token.stringSymbol:
                expectedSymbolName = "string";
                break;
            case Token.timesSymbol:
                expectedSymbolName = "*";
                break;
            case Token.thenSymbol:
                expectedSymbolName = "then";
                break;
            case Token.untilSymbol:
                expectedSymbolName = "until";
                break;
            case Token.whileSymbol:
                expectedSymbolName = "while";
                break;
            case Token.forSymbol:
                expectedSymbolName = "for";
                break;
            case Token.assignSymbol:
                expectedSymbolName = "assignSymbol";
                break;
            case Token.conditionalOperator:
                expectedSymbolName = "conditionalOperator";
                break;
            case Token.multiplySymbol:
                expectedSymbolName = "multiplySymbol";
                break;
            default:
                throw new RuntimeException("Unexpected token symbol: " + token.symbol);
        }

        // Constructing and outputting the error message
        String actualSymbolName = Token.getName(token.symbol);
        String errorMessage = "Expected symbol: " + expectedSymbolName + ", but found: " + actualSymbolName
                + " (\"" + token.getText() + "\") at line " + token.getLineNumber() + ": " + explanatoryMessage;

        System.out.println(errorMessage);
        // Throwing a CompilationException to indicate the error
        throw new CompilationException(errorMessage);
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
