package polytech.compilateur;

import java.util.ArrayList;

public class Node {
	private String type = ""; 
	private int value = 0; 
	private String name = "";
	private ArrayList<Node> childList = new ArrayList<Node>();
	
	//déclaration de variable 
	public Node(String type, int value, String name, ArrayList<Node> childList) {
		this.type = type;
		this.value = value;
		this.name = name;
		this.childList = childList;
	}

	//déclaration d'opérateur 
	public Node(String type, ArrayList<Node> childList) {
		this.type = type;
		this.childList = childList;
		this.value = 0;
		this.name = "";
	}

	//déclaration constante 
	public Node(String type, int value) {
		this.type = type;
		this.value = value;
		this.childList = new ArrayList<Node>();
	}
	
	//Initialisation à vide 
	public Node() {
		this.type = "";
		this.childList = new ArrayList<Node>();
		this.name = "";
		this.value = 0;
	}

	public Node(String type) {
		this.type = type;
		this.childList = new ArrayList<Node>();
		this.name = "";
		this.value = 0;
	}
	
	//Ajout d'un noeud à ce noeud 
	public void addNodeChild(Node node) {
		this.childList.add(node);
	}

	@Override
	public String toString() {
		String ts = "Node [type=" + type + ", value=" + value + ", name=" + name + ", childList=\n";
		for(Node n : this.childList) {
			ts += "    - " + n +"\n";
		}
		
		return ts;
	}
	
	
}
