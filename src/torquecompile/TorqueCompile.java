package torquecompile;

import java.util.ArrayList;

public class TorqueCompile {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        TorqueCompile tcomp = new TorqueCompile();
        String testCode = "string str = \"asdf\"; string str2 = \"fd\\\"sa\";";
        Token tokens[] = tcomp.parseStrings(testCode);

    }

    public TorqueCompile() {}

    public Token[] parseStrings(String li) {
        li = li.trim();
        ArrayList<Token> tokens = new ArrayList<Token>();
        boolean inString = false;
        String built = "";
        for (int i = 0; i < li.length(); i++) {
            char c = li.charAt(i);
            built += c;
            if (c == '"') {
                // Make sure double quotes are escaped properly
                if (li.charAt(i-1) != '\\') {
                    tokens.add(new Token(inString ? Lexeme.STRING : Lexeme.UNPARSED, built));
                    inString = !inString;
                    built = "";
                }
            }
        }
        tokens.add(new Token(inString ? Lexeme.STRING : Lexeme.UNPARSED, built));
        if (inString) {
            System.out.println("Strings did not terminate properly!");
        }
        return (Token[])tokens.toArray();
    }
}
