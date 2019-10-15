package object.secondary;

public class Token {
	private String type = "";
	private String name = "";
	private int value = 0;
	private int line = 0;
	private int column = 0;
	
	
	public Token(String type, String name, int line, int column) {
		this.type = type;
		this.name = name;
		this.line = line;
		this.column = column;
	}
	
	public Token(String type, int value, int line, int column) {
		this.type = type;
		this.value = value;
		this.line = line;
		this.column = column;
	}

	public Token(String type, int line, int column) {
		this.type = type;
		this.line = line;
		this.column = column;
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
	public int getColumn() {
		return column;
	}
	public int getLine() {
		return line;
	}

	@Override
	public String toString() {
		return "Token [type=" + type + ", name=" + name + ", value=" + value + ", line=" + line + ", column=" + column
				+ "]";
	}
	
	
	
}
