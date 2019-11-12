package object.primary;

import java.util.Stack;
import java.util.HashMap;

import object.secondary.Node;
import object.secondary.Symbol;

public class AnalyseurSemantique {

    //private static int nbVariables = 3; //"Servait pour l'operateur power"
    private static int nbVariables = 0;
    private static boolean error = false;

    private static Stack<HashMap<String, Symbol>> stack = new Stack<HashMap<String, Symbol>>();

    public AnalyseurSemantique() {
    }

    public static int getNbVariables() {
        return nbVariables;
    }
    public static void setNbVariables(int nbv){ nbVariables = nbv; }

    public static boolean isError() {
        return error;
    }

    public static void openBlock() {
        HashMap<String, Symbol> block = new HashMap<String, Symbol>();
        stack.push(block);
    }

    public static void closeBlock() {
        stack.pop();
    }

    public static Symbol declare(Node n, String type) {
        Symbol s = new Symbol(n.getLine(), n.getColumn());
        String name = n.getName();
        /*System.out.println("declare => name "+ name+ ": " + n.getName()+ " :"+n);*/
        HashMap<String, Symbol> block = new HashMap<String, Symbol>();
        if(!stack.empty()) block = stack.peek();
        if (block.containsKey(name)) {
            System.out.println("Erreur la "+type+" \"" + name + "\" est déjà déclaré dans ce bloc !");
            System.out.println("( Ligne " + n.getLine() + ", Colonne " + n.getColumn() + " )\n");
            error = true;
        } else {
            block.put(name, s);
        }
        return s;
    }

    public static Symbol search(Node n, String type) {
        Symbol symbole = new Symbol(n.getLine(), n.getColumn());
        Stack<HashMap<String, Symbol>> s = new Stack<HashMap<String, Symbol>>();
        String idSymbol = n.getName();

        while (!stack.empty()) {
            s.push(stack.pop());
            HashMap<String, Symbol> block = s.peek();
            if (block.containsKey(idSymbol) && block.get(idSymbol).getType() == type) {
                //réempiler la pile
                while (!s.empty()) {
                    stack.push(s.pop());
                }
                return block.get(idSymbol);
            }
        }

        //If symbol not found, re-stack
        //réempiler la pile
        while (!s.empty()) {
            stack.push(s.pop());
        }
        return symbole;
    }

    public static void nodeAnalyse(Node n) {
        Symbol s = new Symbol();
        switch (n.getType()) {
            case "node_block":
                openBlock();
                for (Node child : n.getChildList()) {
                    nodeAnalyse(child);
                }
                closeBlock();
                break;
            case "node_declaration":
                s = declare(n.getChild(0), "variable");
                s.setType("variable");
                s.setSlot(nbVariables);
                nbVariables++;
                break;
            case "node_var":
                s = search(n, "variable");
                if (s.getType() != "variable") {
                    System.out.println("Erreur, la variable \"" + n.getName() + "\" n'a pas encore été déclarée !");
                    System.out.println("( Ligne " + n.getLine() + ", Colonne " + n.getColumn() + " )\n");
                    error = true;
                }
                n.setSlot(s.getSlot());
                break;
            case "node_function":
                s = declare(n, "function");
                s.setType("function");
                //-1 car on reserve seulement le nombre d'arguments et non l'instruction qui est à la fin
                int nbArgs = n.getChild(0).nbChild()-1;
                s.setNbParameters(nbArgs);
                for (Node child : n.getChildList()) {
                    nodeAnalyse(child);
                }
                break;
            case "node_call_function":
                s = search(n, "function");
                if (s.getType() != "function") {
                    System.out.println("Erreur, la fonction \"" + n.getName() + "\" n'a pas encore été déclarée !");
                    System.out.println("( Ligne " + n.getLine() + ", Colonne " + n.getColumn() + " )\n");
                    error = true;
                }
                if (s.getNbParameters() != n.nbChild()){
                    System.out.println("Erreur, le nombre d'arguments passés à la fonction \"" + n.getName() + "\" est incorrect.");
                    System.out.println("La fonction attend " + s.getNbParameters() + " arguments.");
                    System.out.println(n.nbChild() + " arguments ont été donnés.\n");
                    System.out.println("( Ligne " + n.getLine() + ", Colonne " + n.getColumn() + " )\n");
                    error = true;
                }
                n.setSlot(s.getSlot());
                break;
            default:
                for (Node child : n.getChildList()) {
                    nodeAnalyse(child);
                }
                break;
        }
    }
}
