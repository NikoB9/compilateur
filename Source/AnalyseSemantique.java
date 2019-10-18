package object.primary;

import java.util.Stack;
import java.util.HashMap;

import object.secondary.Node;
import object.secondary.Symbol;

public class AnalyseSemantique {

    private static int nbVariables = 0;

    private static HashMap<String,Symbol> block = new HashMap<String, Symbol>();
    private static Stack<HashMap<String,Symbol>> stack = new Stack<HashMap<String,Symbol>>();
    
    public AnalyseSemantique(){
    }

    public static void openBlock(){

    }

    public static void closeBlock(){

    }

    public static Symbol declarer(String varIdSymbol){
        Symbol s = new Symbol();

        return s;
    }

    public static Symbol chercher(String varIdSymbol){
        Symbol s = new Symbol();

        return s;
    }

    public static Node nodeAnalyse(Node n) {

        return n;
    }
    
}