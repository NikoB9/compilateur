package object.secondary;

import java.util.ArrayList;

public class Symbol {
	private String type;
	private int slot;

	public Symbol(String type, int slot) {
		this.type = type;
		this.slot = slot;
	}

	public Symbol() {
		this.type = "";
		this.slot = 0;
	}

	public String getType() {
		return type;
	}

	public int getSlot() {
		return slot;
	}

	public void setType(String t) {
		this.type = t;
	}

	public void setSlot(int s) {
		this.slot = s;
	}

	@Override
	public String toString() {
		String ts = "symbole";
		
		return ts;
	}
	
	
}
