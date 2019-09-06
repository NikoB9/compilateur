package polytech.compilateur;

public class Operator {

	private String nodeName;
	private int priority;
	private int associativity;
	
	
	public Operator(String nodeName, int priority, int associativity) {
		super();
		this.nodeName = nodeName;
		this.priority = priority;
		this.associativity = associativity;
	}


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public int getAssociativity() {
		return associativity;
	}


	public void setAssociativity(int associativity) {
		this.associativity = associativity;
	}


	@Override
	public String toString() {
		return "Operator [nodeName=" + nodeName + ", priority=" + priority + ", associativity=" + associativity + "]";
	}
	
	
	
}
