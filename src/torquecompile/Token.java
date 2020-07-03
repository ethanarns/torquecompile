package torquecompile;

public class Token {
    public Lexeme lex;
    public String value;

    public Token(Lexeme lexeme, String value) {
        this.lex = lexeme;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + lex + ",\"" + value + "\")";
    }
}