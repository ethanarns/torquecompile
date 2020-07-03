package torquecompile;

import java.util.ArrayList;

public class TorqueCompile {
    public static void main(String[] args) throws Exception {
        TorqueCompile tcomp = new TorqueCompile();
        String testCode = "string str = \"as\\\"df\";";
        tcomp.parseStrings(testCode);
    }

    public TorqueCompile() {

    }

    public void parseStrings(String li) {
        li = li.trim();
        String strList[] = li.split("");
        ArrayList<Token> tokens = new ArrayList<Token>();
        for (int i = 0; i < strList.length; i++){
            String s = strList[i];
            System.out.println(li);
        }
    }
}
