package object.primary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import object.secondary.Token;

public class AnalyseurLexical {

	private ArrayList<Token> tokenList = new ArrayList<Token>();
	private File fileToCompile ;
	private HashMap<String,String> character = new HashMap<String,String>();
	private HashMap<String,String> keywords = new HashMap<String,String>();
	private int listIndex = 0;

	public AnalyseurLexical(File fileToCompile) throws IOException {
		this.fileToCompile = fileToCompile;

		//Remplissage du tableau de caract�res
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
		character.put("=","tok_assignment");
		character.put("!","tok_not");

		//Remplissage du tableau de mots cl�s
		keywords.put("for", "tok_for");
		keywords.put("if", "tok_if");
		keywords.put("else", "tok_else");
		keywords.put("while", "tok_while");
		keywords.put("debug", "tok_debug");
        keywords.put("var", "tok_declaration");
	}

	public void skip() {
		if (this.next().getType() != "tok_end_of_file")this.listIndex++;
	}

	public Token next() {
		return this.tokenList.get(this.listIndex);
	}

	public boolean accept(String type) {
		if(this.next().getType() == type) {
			this.skip();
			return true;
		}
		else {
			return false;
		}
	}

	public void analyse() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(this.fileToCompile));

		String line;
		int lineIndex = 0;
		int columnIndex = 0;
		boolean blockComment = false;
		while ((line = reader.readLine()) != null) {
			//ATTENTION :
			//PASSER LES COMMENTAIRES (// ou /**/)
			//NOTER LES FINS DE LIGNES ET LA FIN DE FICHIER
			//ESPACE : 32

			for(columnIndex = 0; columnIndex < line.length(); columnIndex++) {

				char actualChar = line.charAt(columnIndex);
				int asciiChar = (int) actualChar;



				//Si on est face � un commentaire de ligne
				//ASCII "/" : 47
				if(asciiChar == 47 && line.codePointAt(columnIndex+1) == 47) {
					//On passe � la ligne suivante
					columnIndex = line.length() - 1;
				}
				//on est face � un commentaire de bloque
				//ASCII "/*" : 47 et 42
				else if((asciiChar == 47 && line.codePointAt(columnIndex+1) == 42) || blockComment) {

					blockComment = true;

					int endIndex = columnIndex+1;
					//Tant qu'il n'y a pas de fin de commentaire de block on avance dans le fichier sans rien faire
					//ASCII "*/" : 42 et 47
					while(endIndex+1 < line.length() && line.codePointAt(endIndex) != 42 && line.codePointAt(endIndex+1) != 47){
						endIndex++;
					}

					if(line.codePointAt(endIndex) == 42 && line.codePointAt(endIndex+1) == 47) {
						blockComment = false;
					}

					//On se replace au bon endroit dans le fichier
					columnIndex = endIndex+1;
				}
				//on est face � un chiffre
				//ASCII NUMBERS : 48 TO 57
				else if(asciiChar >= 48 && asciiChar <= 57) {

					int endIndex = columnIndex+1;
					//TANT QU'IL Y A DES CHIFFRES ET QU'ON EST PAS EN FIN DE LIGNE
					while(endIndex < line.length() && line.codePointAt(endIndex) >= 48 && line.codePointAt(endIndex) <= 57){
						endIndex++;
					}

					int value = Integer.parseInt(line.substring(columnIndex, endIndex));
					//On cr� un token a avec le nombre r�cup�r�
					this.tokenList.add(new Token("tok_constant", value, lineIndex, columnIndex));

					//On se replace au bon endroit dans le fichier
					columnIndex = endIndex-1;
				}
				//on est face � une lettre ou un underscore : cr�ation d'une variable
				//ASCII MINUS  : 97 - 122
				//ASCII MAJUSCULE : 65 - 90
				//UNDERSCORE : 95
				else if((asciiChar >= 97 && asciiChar <= 122) || (asciiChar >= 65 && asciiChar <= 90) || asciiChar == 95) {

					int endIndex = columnIndex+1;
					//TANT QU'IL Y A DES lettres ou un underscore ET QU'ON EST PAS EN FIN DE LIGNE
					while(endIndex < line.length() && ((line.codePointAt(endIndex) >= 97 && line.codePointAt(endIndex) <= 122) || (line.codePointAt(endIndex) >= 65 && line.codePointAt(endIndex) <= 90) || line.codePointAt(endIndex) == 95)){
						endIndex++;
					}

					//On r�cup�re le texte
					String name = line.substring(columnIndex, endIndex);

					//Si c'est un mot cl� pour une instruction on cr� un token qui est li� � ce mot cl�
					if(this.keywords.containsKey(name)) {
						this.tokenList.add(new Token(this.keywords.get(name), name, lineIndex, columnIndex));
						endIndex = endIndex-1;
					}
					//Sinon c'est une variable donc on cr� un token identificateur
					else{
						//On créé un token a avec la variable
						this.tokenList.add(new Token("tok_identifier", name, lineIndex, columnIndex));
						endIndex = endIndex-1;
					}


					//On se replace au bon endroit dans le fichier
					columnIndex = endIndex;
				}
				else {


					//Si �a commence par "="
					if(asciiChar == 61 && line.codePointAt(columnIndex+1) == 61) {
						//On r�cup�re le texte
						String name = line.substring(columnIndex, columnIndex+2);
						this.tokenList.add(new Token(this.character.get(name), name, lineIndex, columnIndex));
						columnIndex++;
					}
					//Si �a commence par "<"
					else if(asciiChar == 60 && line.codePointAt(columnIndex+1) == 61) {
						//On r�cup�re le texte
						String name = line.substring(columnIndex, columnIndex+2);
						this.tokenList.add(new Token(this.character.get(name), name, lineIndex, columnIndex));
						columnIndex++;
					}
					//Si �a commence par ">"
					else if(asciiChar == 62 && line.codePointAt(columnIndex+1) == 61) {
						//On r�cup�re le texte
						String name = line.substring(columnIndex, columnIndex+2);
						this.tokenList.add(new Token(this.character.get(name), name, lineIndex, columnIndex));
						columnIndex++;
					}
					//Si �a commence par "!"
					else if(asciiChar == 33 && line.codePointAt(columnIndex+1) == 61) {
						//On r�cup�re le texte
						String name = line.substring(columnIndex, columnIndex+2);
						this.tokenList.add(new Token(this.character.get(name), name, lineIndex, columnIndex));
						columnIndex++;
					}
					//Soit c'est un op�rateur soit c'est un espace
					else if(this.character.containsKey(""+actualChar)) {
						this.tokenList.add(new Token(this.character.get(""+actualChar), actualChar, lineIndex, columnIndex));
					}
					else{
						if (asciiChar == 59) {
							this.tokenList.add(new Token("tok_separator", lineIndex, columnIndex));
						}
						else if (asciiChar != 32) {
							this.tokenList.add(new Token("tok_unknown", lineIndex, columnIndex));
							System.out.println("tok_unknown : "+asciiChar);
						}
					}
				}

			}


			lineIndex += 1;
		}

		this.tokenList.add(new Token("tok_end_of_file", lineIndex, columnIndex));

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
