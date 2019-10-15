package object.primary;

import java.util.HashMap;

import object.secondary.Node;

public class CodeGenerator {
    
    private static HashMap<String,String> functionsMSM = new HashMap<String, String>();
    static {
        functionsMSM.put("node_divide","div");
        functionsMSM.put("node_remaindor", "mod");
        functionsMSM.put("node_multiply","mul");

        functionsMSM.put("node_plus_binary", "add");
        functionsMSM.put("node_minus_binary", "sub");

        functionsMSM.put("node_inf", "cmplt");
        functionsMSM.put("node_sup", "cmpgt");
        functionsMSM.put("node_equal", "cmpeq");
        functionsMSM.put("node_sup_equal", "cmpge");
        functionsMSM.put("node_inf_equal", "cmple");
        functionsMSM.put("node_different", "cmpne");

        functionsMSM.put("node_and", "and");

        functionsMSM.put("node_or", "or");
    }
    
    public CodeGenerator(){
    }


    public static void genCode(Node n){
        if (n.getType() == "node_constant"){
            System.out.println("push " + n.getValue());
        }
        else if(n.getType() == "node_minus_unary"){
            System.out.println("push 0");
            genCode(n.getChild(0));
            System.out.println("sub");
        }
        else if (n.getType() == "node_not"){
            genCode(n.getChild(0));
            System.out.println("not");
        }
        else if (n.getType() == "node_assigment" || n.getType() == "node_power"){
            System.out.format("Erreur, ce noeud (%s) n'est pas encore pris en charge\n", n.getType());
        }
        else if (functionsMSM.containsKey(n.getType())){
            genCode(n.getChild(0));
            genCode(n.getChild(1));
            System.out.println(functionsMSM.get(n.getType()));
        }
    }
    
}