package polytech.compilateur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyseurLexical {

	private ArrayList<Token> tokenList = new ArrayList<Token>();
	private File fileToCompile ;
	private HashMap<String,String> character = new HashMap<String,String>(); 
	private HashMap<String,String> keywords = new HashMap<String,String>(); 
	private int listIndex = 0;
	
	public AnalyseurLexical(File fileToCompile) throws IOException {
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
		character.put("{","tok_open_brace");
		character.put("}","tok_close_brace");
		character.put("&","tok_and");
		character.put("|","tok_or");
		character.put("=","tok_equal");
		
		//Remplissage du tableau de mots clés
		keywords.put("for", "tok_for");
		keywords.put("if", "tok_if");
		keywords.put("while", "tok_while");
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
	
	public void analyse() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(this.fileToCompile)); 

		String line; 
		int lineIndex = 0;
		while ((line = reader.readLine()) != null) {
			//ATTENTION :
			//PASSER LES COMMENTAIRES (// ou /**/)
			//NOTER LES FINS DE LIGNES ET LA FIN DE FICHIER

			//ASCII NUMBERS : 48 TO 57
			//ASCII MINUS  : 97 - 122
			//ASCII MAJUSCULE : 65 - 90 
			//UNDERSCORE ET TIRETS DANS WHILE VARIABLE
			//ESPACE : 32
			
			for(int columnIndex = 0; columnIndex < line.length(); columnIndex++) {
				
				char actualChar = line.charAt(columnIndex);
				int asciiChar = (int) actualChar;
				
				if(asciiChar >= 48 && asciiChar <= 57) {
					
					int endIndex = columnIndex+1;
					while(line.codePointAt(endIndex) >= 48 && line.codePointAt(endIndex) <= 57){
						endIndex++;
					}
					
					int value = Integer.parseInt(line.substring(columnIndex, endIndex));
					
					this.tokenList.add(new Token("tok_constante", value, lineIndex, columnIndex));
						
				}
				
			}
			
			
			lineIndex += 1;
		}

		reader.close();


	}

	@Override
	public String toString() {
		String ts = "Liste de tokens : \n";
		
		for(Token aToken : this.tokenList) {
			ts += aToken + "\n";
		}
		
		return ts;
	} 
	
	
	
}
