package object.primary;

import java.util.HashMap;

import object.secondary.Node;
import object.primary.AnalyseurSemantique;

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
            //Init de l'accumulateur
            // res =1;
            generatedCode += "push 1\n";
            //generatedCode += "dup\n";
            generatedCode += "set " + AnalyseurSemantique.getPowerResultSlot() + "\n";

            //i=0;
            //Init de la variable d'incrément
            generatedCode += "push 0\n";
            //generatedCode += "dup\n";
            generatedCode += "set " + AnalyseurSemantique.getPowerIncrementSlot() + "\n";

            //Init getPowerMultiplyValue qui est la valeur absolue de la puissance
            generatedCode += genCode(n.getChild(1));
            generatedCode += genCode(n.getChild(1));

            generatedCode += "push 0\n";
            generatedCode += "cmpge\n";
            //exp valeur>=0
            generatedCode += "jumpt l"+flagCount+"\n";
            generatedCode += "push 0\n";
            generatedCode += "push 1\n";
            generatedCode += "sub\n";
            generatedCode += "mul\n";
            generatedCode += ".l" + flagCount + "\n";
            generatedCode += "set " + AnalyseurSemantique.getPowerMultiplyValueSlot() + "\n";
            flagCount++;

            generatedCode += ".l" + (flagCount) + "\n";
            flagCount++;
            generatedCode += "get "+ AnalyseurSemantique.getPowerIncrementSlot() + "\n";
            generatedCode += "get " + AnalyseurSemantique.getPowerMultiplyValueSlot() + "\n";
            generatedCode += "cmplt\n";
            generatedCode += "jumpf l" + (flagCount) + "\n";
            flagCount++;
            generatedCode += "get " + AnalyseurSemantique.getPowerResultSlot() + "\n";
            generatedCode += genCode(n.getChild(0));
            //Vérification signe puissance
            //Si positif => mul sinon div
            generatedCode += genCode(n.getChild(1));
            generatedCode += "push 0\n";
            generatedCode += "cmpgt\n";
            generatedCode += "jumpf l" + (flagCount) + "\n";
            flagCount++;
            generatedCode += "mul\n";
            generatedCode += "jump l" + (flagCount) + "\n";
            generatedCode += ".l" + (flagCount-1) + "\n";
            flagCount++;
            generatedCode += "div\n";
            generatedCode += ".l" + (flagCount-1) + "\n";
            //generatedCode += "push -1\ndbg\ndup\ndbg\n";//DEBUG
            generatedCode += "set " + AnalyseurSemantique.getPowerResultSlot() + "\n";

            //incrément
            generatedCode += "get " + AnalyseurSemantique.getPowerIncrementSlot() + "\n";
            generatedCode += "push 1\n";
            generatedCode += "add\n";
            //generatedCode += "dup\ndbg\n";
            generatedCode += "set " + AnalyseurSemantique.getPowerIncrementSlot() + "\n";

            generatedCode += "jump l" + (flagCount-4) + "\n";
            generatedCode += ".l" + (flagCount-3) + "\n";
            generatedCode += "get " + AnalyseurSemantique.getPowerResultSlot() + "\n";
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
            generatedCode += "jumpf l"+(flagCount)+"\n";
            flagCount++;
            generatedCode += genCode(n.getChild(1));
            generatedCode += "jump l"+(flagCount)+"\n";
            generatedCode += ".l"+(flagCount-1)+"\n";
            generatedCode += (n.nbChild()==2 ? "" : genCode(n.getChild(2)));
            generatedCode += ".l"+(flagCount)+"\n";
            flagCount++;
        }
        else if (n.getType() == "node_debug"){
            generatedCode += genCode(n.getChild(0));
            generatedCode += "dbg\n";
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
        else if(n.getType() == "node_loop"){
            Node cond = n.getChild(0);
            generatedCode += ".l"+flagCount+"\n";
            flagCount++;
            generatedCode += genCode(n.getChild(0));
            generatedCode += "jump l"+(flagCount-3)+"\n";
            generatedCode += ".l"+flagCount+"\n";
            flagCount++;
        }
        else if(n.getType() == "node_break"){
            generatedCode += "jump l"+(flagCount+1)+"\n";
        }

        return generatedCode;
    }
    
}