package object.secondary;

import java.util.ArrayList;

public class Symbol {
	private static int count = 0;
	private int id;
	private String name;
	private String type;
	private int slot;

	public Symbol(String type, int slot, String name) {
		this.name = name;
		this.type = type;
		this.slot = slot;
		this.id = ++count;
	}

	public Symbol(String name) {
		this.name = name;
		this.type = "";
		this.slot = "";
		this.id = ++count;
	}

	public Symbol() {
		this.name = "";
		this.type = "";
		this.slot = "";
		this.id = ++count;
	}

	public String getType() {
		return type;
	}

	public int getSlot() {
		return slot;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setType(String t) {
		this.type = t;
	}

	public void setSlot(int s) {
		this.slot = s;
	}

	public void setId(int i) {
		this.id = i;
	}

	public void setName(String n) {
		this.name = n;
	}
	


	@Override
	public String toString() {
		String ts = "symbole "+id;
		
		return ts;
	}
	
	
}
