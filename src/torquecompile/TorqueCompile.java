package torquecompile;

import java.util.ArrayList;

public class TorqueCompile {
    public static void main(String[] args) throws Exception {
        TorqueCompile tcomp = new TorqueCompile();
        String testCode = "string str = \"asdf\";";
        tcomp.parseStrings(testCode);
    }

    public TorqueCompile() {

    }

    public int endOfString(int curIndex, String str) {
        int finalIndex = curIndex;
        System.out.println("Starting index: " + curIndex);
        for (int i = curIndex + 1; i < str.length(); i++) {
            if (str.charAt(i) == '"') {
                return finalIndex;
            }
        }
        return -1;
    }

    public void parseStrings(String li) {
        li = li.trim();
        ArrayList<Token> tokens = new ArrayList<Token>();
        for (int i = 0; i < li.length(); i++) {
            char c = li.charAt(i);
            if (c == '"') {
                int endIndex = endOfString(i, li);
                String sub = li.substring(i, endIndex-1);
                System.out.println(sub);
            }
        }
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
