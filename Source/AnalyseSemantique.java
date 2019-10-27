package object.primary;

import java.util.Stack;
import java.util.HashMap;

import object.secondary.Node;
import object.secondary.Symbol;

public class AnalyseSemantique {

    private static int nbVariables = 0;

    private static Stack<HashMap<String,Symbol>> stack = new Stack<HashMap<String,Symbol>>();
    
    public AnalyseSemantique(){
    }

    public static void openBlock(){
        HashMap<String,Symbol> block = new HashMap<String, Symbol>();
        stack.push(block);
    }

    public static void closeBlock(){
        stack.pop();
    }

    public static Symbol declarer(String nom){
        Symbol s = new Symbol();
        HashMap<String,Symbol> block = stack.peek();
        if(block.containsKey(nom)){
            System.out.println("Erreur la variable "+nom+" est déjà déclaré dans ce bloc !");
            //TODO debug info du symbole (ligne/colonne)
        }
        else{
            block.put(nom, s);
        }
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