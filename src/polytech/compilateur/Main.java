package polytech.compilateur;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("_____________________Debut_Programme________________________");
		System.out.print(System.getProperty("line.separator"));
		System.out.print("Entrez le nom de fichier avec l'extension .txt : ");
		String fileName = in.nextLine();
		
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath+"\\src\\"+fileName); 

		System.out.print(System.getProperty("line.separator"));
		System.out.println("_____________________Analyse_lexicale________________________");
		AnalyseurLexical analyseurLexical = new AnalyseurLexical(file);
		analyseurLexical.analyse();
		System.out.println(analyseurLexical);
		

		System.out.print(System.getProperty("line.separator"));
		System.out.println("_____________________Analyse_syntaxique________________________");
		AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(analyseurLexical);
		analyseurSyntaxique.Primaire();
		System.out.println(analyseurSyntaxique);
	}
}
