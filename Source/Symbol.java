package object.secondary;

import java.util.ArrayList;

public class Symbol {
	private String type;
	private int slot;
    private int line = 0;
    private int column = 0;
    private int nbParameters = 0;

	public Symbol(String type, int slot, int line, int column) {
		this.type = type;
		this.slot = slot;
		this.line = line;
		this.column = column;
	}

	public Symbol() {
		this.type = "";
		this.slot = 0;
	}

    public Symbol(int line, int column) {
        this.type = "";
        this.slot = 0;
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

    public int getNbParameters() {
        return nbParameters;
    }

    public void setNbParameters(int nbParameters) {
        this.nbParameters = nbParameters;
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
