package polytech.compilateur;

import java.util.HashMap;

public class AnalyseurSyntaxique {
	private AnalyseurLexical analyseurLexical; 
	private HashMap<String,String> nodeKeywords = new HashMap<String,String>();
	
	
	
	
	public AnalyseurSyntaxique(AnalyseurLexical analyseurLexical) {
		this.analyseurLexical = analyseurLexical;
		
		//Remplissage du tableau de caract�res
		nodeKeywords.put("+","tok_plus");
		nodeKeywords.put("-","tok_minus");
		nodeKeywords.put("(","tok_openning_parenthesis");
		nodeKeywords.put(")","tok_closing_parenthesis");
	}

	public Node Primaire() {
		if(analyseurLexical.next().getType()=="tok_constante") {
			//Creation du noeud qui contiendra la valeur
			Node node = new Node("node_constante", analyseurLexical.next().getValue());
			//Token actuel trait� ! Passage au suivant. 
			analyseurLexical.skip();
			return node;
		}
		if(analyseurLexical.next().getType()=="tok_openning_parenthesis") {
			//Passage au token suivant 
			analyseurLexical.skip();
			
			//node contient le noeud d'expression entre les parenth�ses ( ) 
			Node node = this.Expresion(0);
			
			//v�rification qu'apr�s l'expression il y a une parenth�se fermante
			String checkValidity = analyseurLexical.accept("tok_closing_parenthesis");
			if(checkValidity.equals("Erreur : Type non trouve")) {
				System.out.println("Erreur parenthese fermante manquante ... ");
//				System.exit(-1);
				return new Node();
			}
			return node;
		}
		if(analyseurLexical.next().getType()=="tok_minus") {
			//Passage au token suivant
			analyseurLexical.skip();
			Node node = new Node("tok_minus");
			node.addNode(this.Expresion(7));
			return node;
		}
		System.out.println("Attention ! Param�tre attendu dans Primaire()");
		return new Node();
	}
	
	public Node Expresion(int priority) {
		//TODO
		return new Node();
	}
	
	
}
