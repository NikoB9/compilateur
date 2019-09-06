package polytech.compilateur;

import java.util.ArrayList;
import java.util.HashMap;

public class AnalyseurSyntaxique {
	private AnalyseurLexical analyseurLexical; 
	private ArrayList<Node> nodeList = new ArrayList<Node>();
	
	
	public AnalyseurSyntaxique(AnalyseurLexical analyseurLexical) {
		this.analyseurLexical = analyseurLexical;
	}
	
	

	public Node Primaire() {
		//Renvoi un arbre contenant la constante si constante
		if(analyseurLexical.next().getType()=="tok_constant") {
			//Creation du noeud qui contiendra la valeur
			Node node = new Node("node_constant", analyseurLexical.next().getValue());
			//Token actuel traitï¿½ ! Passage au suivant. 
			analyseurLexical.skip();
			return node;
		}
		//Renvoi un arbre contenant l'ensemble des calculs mathematiques, expression boolean 
		if(analyseurLexical.next().getType()=="tok_openning_parenthesis") {
			//Passage au token suivant 
			analyseurLexical.skip();
			
			//node contient le noeud d'expression entre les parenthï¿½ses ( ) 
			Node node = this.Expresion(0);
			
			//vï¿½rification qu'aprï¿½s l'expression il y a une parenthï¿½se fermante
			String checkValidity = analyseurLexical.accept("tok_closing_parenthesis");
			if(checkValidity.equals("Erreur : Type non trouve")) {
				System.out.println("Erreur parenthese fermante manquante ... ");
//				System.exit(-1);
				return new Node();
			}
			return node;
		}
		//Renvoi un arbre contenant un moins et une expression mathï¿½matique avec au plus une exponentielle 
		if(analyseurLexical.next().getType()=="tok_minus") {
			//Passage au token suivant
			analyseurLexical.skip();
			Node node = new Node("node_minus_unary");
			node.addNodeChild(this.Expresion(7));
			return node;
		}
		System.out.println("Attention ! Paramï¿½tre attendu dans Primaire()");
		return new Node();
	}
	
	public Operator ChercherOp(Token token) {
		return this.nodeOperator.get(token.getType());
	}
	
	public Node Expresion(int minPriority) {
		//Recupération de la constante 
		Node A1 = this.Primaire();
		
		while(true) {
			Operator Op = this.ChercherOp(this.analyseurLexical.next());
			//Si l'opérateur qui suit la constante est de priorité inférieure 
			//A celle recherchée on retourne l'arbre déjà créé
			if(Op==null || Op.getPriority()<minPriority) {
				return A1; 
			}
			//Sinon on continue à parcourir l'expression pour construire l'arbre
			this.analyseurLexical.skip();
			Node A2 = this.Expresion(Op.getPriority()+Op.getAssociativity());

			//On créer le noeud opérateur 
			Node A = new Node(Op.getNodeName());
			A.addNodeChild(A1);
			A.addNodeChild(A2);
			
			A1=A;
		}
	}



	public String toString() {
		
		String ts = "Liste des noeuds : ";
		for(Node aNode : this.nodeList) {
			ts += "- " + aNode + "\n";
		}
		return ts;
	}

	
	
}
