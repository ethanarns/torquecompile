package torquecompile;

import java.util.ArrayList;

public class TorqueCompile {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        TorqueCompile tcomp = new TorqueCompile();
        String testCode = "string str = \"asdf\"; string str2 = \"fdsa\";";
        tcomp.parseStrings(testCode);
    }

    public TorqueCompile() {

    }

    public void parseStrings(String li) {
        li = li.trim();
        ArrayList<Token> tokens = new ArrayList<Token>();
        boolean inString = false;
        String built = "";
        for (int i = 0; i < li.length(); i++) {
            char c = li.charAt(i);
            built += c;
            if (c == '"') {
                tokens.add(new Token(inString ? Lexeme.STRING : Lexeme.UNPARSED, built));
                inString = !inString;
                built = "";
            }
        }
        tokens.add(new Token(inString ? Lexeme.STRING : Lexeme.UNPARSED, built));
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
