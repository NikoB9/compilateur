package polytech.compilateur;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyseurLexical {

	private ArrayList<Token> tokenList;
	private File fileToCompile ;
	private HashMap<String,String> character = new HashMap<String,String>(); 
	private HashMap<String,String> keywords = new HashMap<String,String>(); 
	private int listIndex = 0;
	
	public AnalyseurLexical(File fileToCompile) {
		this.tokenList = new ArrayList<Token>();
		this.fileToCompile = fileToCompile;
		
		//Remplissage du tableau de caractères
		character.put("==","tok_equal");
		character.put(">=","tok_sup_equal");
		character.put("<=","tok_inf_equal");
		character.put("!=","tok_different");
		character.put("+","tok_plus");
		character.put("-","tok_minus");
		character.put("/","tok_divide");
		character.put("%","tok_remaindor");
		character.put("*","tok_multiply");
		character.put("^","tok_power");
		character.put("<","tok_inf");
		character.put(">","tok_sup");
		character.put("(","tok_openning_parenthesis");
		character.put(")","tok_closing_parenthesis");
		character.put("&","tok_and");
		character.put("|","tok_or");
		character.put("=","tok_equal");
	}
	
	public void skip() {
		this.listIndex++;
	}
	
	public Token next() {
		return this.tokenList.get(this.listIndex);
	}
	
	public String accept(String type) {
		if(this.next().getType() == type) {
			this.skip();
			return "Type trouve";
		}
		else {
			return "Erreur : Type non trouve";
		}
	}
}
