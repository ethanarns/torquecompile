package torquecompile;

import java.util.ArrayList;

public class Lexer {

    public Lexer() {}

    private ArrayList<Token> parseStrings(String li) {
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
        for (int j = 0; j < tokens.size(); j++) {
            Token t = tokens.get(j);
            if (t.lex == Lexeme.UNPARSED) {
                t.value = t.value.replace("\"","");
                t.value = t.value.trim();
            } else if (t.lex == Lexeme.STRING) {
                // Remove last character, which will always be a double quote
                t.value = t.value.substring(0,t.value.length()-1);
            }
        }
        return tokens;
    }

    private ArrayList<Token> parseLine(String str) {
        // This function is to be run after parseStrings
        ArrayList<Token> finalTokens = new ArrayList<Token>();
        String built = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '+':
                case '-':
                case '/':
                case '*':
                case '%':
                case '=':
                    if (built.length() != 0) {
                        finalTokens.add(new Token(Lexeme.UNPARSED, built.trim()));
                    }
                    built = "";
                    finalTokens.add(new Token(Lexeme.OPERATOR, String.valueOf(c)));
                    break;
                case '(':
                case ')':
                case '{':
                case '}':
                    if (built.length() != 0) {
                        finalTokens.add(new Token(Lexeme.UNPARSED, built.trim()));
                    }
                    built = "";
                    finalTokens.add(new Token(Lexeme.CONTROL, String.valueOf(c)));
                    break;
                case '\n':
                case ';':
                    if (built.length() != 0) {
                        finalTokens.add(new Token(Lexeme.UNPARSED, built.trim()));
                    }
                    built = "";
                    finalTokens.add(new Token(Lexeme.NEWLINE, "N/A"));
                    break;
                default:
                    built += c;
                    break;
            }
        }
        if (built.length() != 0) {
            finalTokens.add(new Token(Lexeme.UNPARSED,built.trim()));
        }
        return finalTokens;
    }

    private boolean isDouble(String f) {
        try {
            Double.parseDouble(f);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String f) {
        try {
            Integer.parseInt(f);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public ArrayList<Token> parse(String input) {
        ArrayList<Token> finalTokens = new ArrayList<Token>();
        input = input.replace("\n",";");
        ArrayList<Token> tokens = parseStrings(input);
        // Split up post-string Tokens
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (t.lex == Lexeme.STRING) {
                finalTokens.add(t);
            } else {
                ArrayList<Token> subTokens;
                String subTokenData = t.value;
                subTokens = parseLine(subTokenData);
                finalTokens.addAll(subTokens);
            }
        }
        // Split up and name the rest
        finalTokens = parseTokens(finalTokens);
        return finalTokens;
    }

    private ArrayList<Token> parseTokens(ArrayList<Token> tokens) {
        ArrayList<Token> finalTokens = new ArrayList<Token>();
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (t.lex == Lexeme.UNPARSED) {
                String valueString = t.value.trim();
                // First, try Types
                if (valueString.startsWith("global") ||
                valueString.startsWith("string") ||
                valueString.startsWith("int") ||
                valueString.startsWith("float")) {
                    finalTokens.add(new Token(Lexeme.DECLARE,valueString));
                // Next, just functions
                } else if (valueString.startsWith("function")) {
                    valueString = valueString.replace("function","").trim();
                    finalTokens.add(new Token(Lexeme.FUNCTIONDEF,valueString));
                // Is it a value?
                } else {
                    if(isInteger(valueString)) {
                        finalTokens.add(new Token(Lexeme.INTEGER,valueString));
                    } else if (isDouble(valueString)) {
                        finalTokens.add(new Token(Lexeme.DOUBLE,valueString));
                    } else {
                        finalTokens.add(new Token(Lexeme.UNPARSED,valueString));
                    }
                }
            } else {
                finalTokens.add(t);
            }
        }
        return finalTokens;
    }
}
