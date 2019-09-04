package polytech.compilateur;

import java.util.ArrayList;

public class AnalyseurSyntaxique {
	private AnalyseurLexical analyseurLexical; 
	private ArrayList<Node> nodeList = new ArrayList<Node>();
	
	
	public AnalyseurSyntaxique(AnalyseurLexical analyseurLexical) {
		this.analyseurLexical = analyseurLexical;
		
	}
	
	

	public Node Primaire() {
		if(analyseurLexical.next().getType()=="tok_constant") {
			//Creation du noeud qui contiendra la valeur
			Node node = new Node("node_constant", analyseurLexical.next().getValue());
			//Token actuel traité ! Passage au suivant. 
			analyseurLexical.skip();
			return node;
		}
		if(analyseurLexical.next().getType()=="tok_openning_parenthesis") {
			//Passage au token suivant 
			analyseurLexical.skip();
			
			//node contient le noeud d'expression entre les parenthèses ( ) 
			Node node = this.Expresion(0);
			
			//vérification qu'après l'expression il y a une parenthèse fermante
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
			Node node = new Node("node_minus_unary");
			node.addNode(this.Expresion(7));
			return node;
		}
		System.out.println("Attention ! Paramètre attendu dans Primaire()");
		return new Node();
	}
	
	public Node Expresion(int priority) {
		//TODO
		return new Node();
	}



	public String toString() {
		
		String ts = "Liste des noeuds : ";
		for(Node aNode : this.nodeList) {
			ts += "- " + aNode + "\n";
		}
		return ts;
	}

	
	
}
