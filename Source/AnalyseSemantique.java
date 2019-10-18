package object.primary;

import java.util.Stack;
import java.util.HashMap;

import object.secondary.Node;
import object.secondary.Symbol;

public class AnalyseSemantique {

    private static int nbVariables = 0;

    private static HashMap<String,Symbol> Symbols = new HashMap<String, Symbol>();
    private static Stack

    
    public AnalyseSemantique(){
    }


    /*public static String genCode(Node n){

        String generatedCode = "";

        if (n.getType() == "node_constant"){
            generatedCode += "push " + n.getValue() + "\n";
        }
        else if(n.getType() == "node_minus_unary"){
            generatedCode += "push 0" + "\n";
            generatedCode += genCode(n.getChild(0));
            generatedCode += "sub" + "\n";
        }
        else if (n.getType() == "node_not"){
            generatedCode += genCode(n.getChild(0));
            generatedCode += "not" + "\n";
        }
        else if (n.getType() == "node_power"){
            System.out.format("Erreur, le noeud (%s) n'est pas encore pris en charge\n", n.getType());
        }
        else if (functionsMSM.containsKey(n.getType())){
            generatedCode += genCode(n.getChild(0));
            generatedCode += genCode(n.getChild(1));
            generatedCode += functionsMSM.get(n.getType()) + "\n";
        }
        else if (n.getType() == "node_var"){
            generatedCode += "get 0" + "\n";
        }
        else if (n.getType() == "node_assignment"){
            generatedCode += genCode(n.getChild(1));
            generatedCode += "dup" + "\n";
            generatedCode += "set 0" + "\n";
        }
        else if (n.getType() == "node_block"){
            for (int i = 0; i < n.nbChild(); i++){
                generatedCode += genCode(n.getChild(i));
            }
        }
        else if (n.getType() == "node_expression"){
            generatedCode += genCode(n.getChild(0));
            generatedCode += "drop\n";
        }
        else if (n.getType() == "node_condition"){
            generatedCode += genCode(n.getChild(0));
            generatedCode += "jumpf [f"+(flagCount*2)+"]\n";
            generatedCode += genCode(n.getChild(1));
            generatedCode += "jump [f"+(flagCount*2+1)+"]\n";
            generatedCode += ".f"+(flagCount*2)+"\n";
            generatedCode += (n.nbChild()==2 ? "" : genCode(n.getChild(2)));
            generatedCode += ".f"+(flagCount*2+1)+"\n";
            flagCount++;
        }
        else if (n.getType() == "node_debug"){
            generatedCode += genCode(n.getChild(0));
            generatedCode += "dbg\n";
            generatedCode += "drop\n";
        }

        return generatedCode;
    }*/
    
}