package torquecompile;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        Lexer tcomp = new Lexer();
        String testCode = "int i = 2.0* j; string str = \"asdf\"; function func(int x, float y){ x = rejigger(x);return x+y+4; }";
        System.out.println(tcomp.parse(testCode));
    }
}