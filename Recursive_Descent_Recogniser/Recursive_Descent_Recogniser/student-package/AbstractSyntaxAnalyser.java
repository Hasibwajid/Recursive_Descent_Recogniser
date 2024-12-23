import java.io.IOException;
import java.io.PrintStream;

public abstract class AbstractSyntaxAnalyser {
	public AbstractSyntaxAnalyser(LexicalAnalyser lex) {
		this.lex = lex;
		myGenerate = new Generate();
	}
	
    /** The lexical analyser to process input using. */
    LexicalAnalyser lex;
    /** A cache of the token to be processed next. */
    Token nextToken;
    /** A code generator, descendant of AbstractGenerate. */
    Generate myGenerate = null;

    /** Begin processing the first (top level) token. */
    public abstract void _statementPart_() throws IOException, CompilationException;

    /** Accept a token based on context. Requires implementation. */
    public abstract void acceptTerminal(int symbol) throws IOException, CompilationException;

    /** Parses the given PrintStream with this instance's LexicalAnalyser.
     *
     * @param ps The PrintStream object to read tokens from.
     * @throws IOException in the event that the PrintStream object can no longer read.
     */
    public void parse(PrintStream ps) throws IOException {
        myGenerate = new Generate();
        try {
            nextToken = lex.getNextToken();
            _statementPart_();
            acceptTerminal(Token.eofSymbol);
            myGenerate.reportSuccess();
        } catch (CompilationException ex) {
            ps.println("Compilation Exception");
            ps.println(ex.toTraceString());
        }
    } // end of method parse

    // ... existing code ...

}
