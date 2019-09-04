package polytech.compilateur;


import java.io.File;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath+"\\src\\polytech\\compilateur\\source.txt"); 
		
		System.out.println("_____________________Analyse_lexicale________________________");
		AnalyseurLexical analyseurLexical = new AnalyseurLexical(file);
		analyseurLexical.analyse();
		System.out.println(analyseurLexical);
		System.out.println("_____________________Analyse_syntaxique________________________");
		AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(analyseurLexical);
		analyseurSyntaxique.Primaire();
		System.out.println(analyseurSyntaxique);
	}
}
