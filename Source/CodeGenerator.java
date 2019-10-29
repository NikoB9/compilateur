package object.primary;

import java.util.HashMap;

import object.secondary.Node;

public class CodeGenerator {

    private static int flagCount = 0;

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


    public static String genCode(Node n){

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
            System.out.format("Erreur, le noeud (%s) n'est pas encore pris en charge (ligne : %d ; colonne : %d)\n", n.getType(), n.getLine(), n.getColumn());
        }
        else if (functionsMSM.containsKey(n.getType())){
            generatedCode += genCode(n.getChild(0));
            generatedCode += genCode(n.getChild(1));
            generatedCode += functionsMSM.get(n.getType()) + "\n";
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
            generatedCode += "jumpf l"+(flagCount*2)+"\n";
            generatedCode += genCode(n.getChild(1));
            generatedCode += "jump l"+(flagCount*2+1)+"\n";
            generatedCode += ".l"+(flagCount*2)+"\n";
            generatedCode += (n.nbChild()==2 ? "" : genCode(n.getChild(2)));
            generatedCode += ".l"+(flagCount*2+1)+"\n";
            flagCount++;
        }
        else if (n.getType() == "node_debug"){
            generatedCode += genCode(n.getChild(0));
            generatedCode += "dbg\n";
            generatedCode += "drop\n";
        }
        /*else if(n.getType() == "node_end_of_file"){
            System.out.println("fin de fichier");
        }*/
        else if(n.getType() == "node_declaration"){
            //on ne fait rien
        }
        else if(n.getType() == "node_var"){
            generatedCode += "get " + n.getSlot() + "\n";
        }
        else if(n.getType() == "node_assignment"){
            generatedCode += genCode(n.getChild(1));
            generatedCode += "dup" + "\n";
            generatedCode += "set " + n.getChild(0).getSlot() + "\n";
        }

        return generatedCode;
    }
    
}