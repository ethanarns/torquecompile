package torquecompile;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        Lexer tcomp = new Lexer();
        String testCode = "int i = 2* j; string str = \"asdf\"; function(int x, float y){ x = rejigger(x);return x*y; }";
        System.out.println(tcomp.parse(testCode));
    }
}