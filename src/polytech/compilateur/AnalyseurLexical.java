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
