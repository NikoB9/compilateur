package object.secondary;

import java.util.ArrayList;

public class Node {
	private String type = ""; 
	private int value = 0; 
	private String name = "";
    private int slot = 0;
    private int line = 0;
    private int column = 0;
	private ArrayList<Node> childList = new ArrayList<Node>();
	
	//d�claration de variable 
	public Node(String type, int value, String name, ArrayList<Node> childList, int line, int column) {
		this.type = type;
		this.value = value;
		this.name = name;
		this.childList = childList;
		this.line = line;
		this.column = column;
	}

	//d�claration d'op�rateur 
	public Node(String type, ArrayList<Node> childList, int line, int column) {
		this.type = type;
		this.childList = childList;
		this.value = 0;
		this.name = "";
        this.line = line;
        this.column = column;
	}

	//d�claration constante 
	public Node(String type, int value, int line, int column) {
		this.type = type;
		this.value = value;
		this.childList = new ArrayList<Node>();
        this.line = line;
        this.column = column;
	}
	
	//Initialisation � vide 
	public Node(int line, int column) {
		this.type = "";
		this.childList = new ArrayList<Node>();
		this.name = "";
		this.value = 0;
        this.line = line;
        this.column = column;
	}

	public Node(String type, int line, int column) {
		this.type = type;
		this.childList = new ArrayList<Node>();
		this.name = "";
		this.value = 0;
        this.line = line;
        this.column = column;
	}

	public Node(String type, String name, int line, int column) {
		this.type = type;
		this.childList = new ArrayList<Node>();
		this.name = name;
		this.value = 0;
        this.line = line;
        this.column = column;
	}

    public int getColumn() {
        return column;
    }
    public int getLine() {
        return line;
    }
    public void setLine(int line){ this.line = line; }
    public void setColumn(int column){ this.column = column; }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public Node getChild(int num){ return childList.get(num); }

	public void setSlot(int s){
	    this.slot = s;
    }

    public int getSlot() { return this.slot; }

    public int nbChild(){ return childList.size(); }

	public ArrayList<Node> getChildList() {
		return childList;
	}

	public void setChildList(ArrayList<Node> childList) {
		this.childList = childList;
	}

	//Ajout d'un noeud � ce noeud 
	public void addNodeChild(Node node) {
		this.childList.add(node);
	}
	

	public static void print(Node n, int l) {
		
		for(int i = 1; i < l; i++) {
			System.out.print("    ");
		}
		System.out.println(n);
		for(Node nc : n.getChildList()) {
			print(nc, l+1);
		}

	}

	@Override
	public String toString() {
		String ts = type;
		
		if(type == "node_constant") {
			ts += " => Valeur : " + value;
		}
		else if(type == "node_identifier") {
			ts += " => Nom de variable : " + name;
		}
		
		return ts;
	}
}
