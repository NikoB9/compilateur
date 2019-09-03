package polytech.compilateur;


public class Token {
	private String type = "";
	private String name = "";
	private int value = 0;
	private int line = 0;
	private int column = 0;
	
	
	public Token(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public Token(String type, int value) {
		this.type = type;
		this.value = value;
	}

	public Token(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
