import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import object.primary.AnalyseurLexical;
import object.primary.AnalyseurSyntaxique;
import object.secondary.Node;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);

		System.out.println("_____________________Debut_Programme________________________");
		System.out.print(System.getProperty("line.separator"));
		System.out.print("Entrez le nom de fichier avec l'extension .txt : ");
		String fileName = in.nextLine();

		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath+"\\"+fileName);

		System.out.print(System.getProperty("line.separator"));
		System.out.println("_____________________Analyse_lexicale________________________");
		AnalyseurLexical analyseurLexical = new AnalyseurLexical(file);
		analyseurLexical.analyse();
		System.out.println(analyseurLexical);


		System.out.print(System.getProperty("line.separator"));
		AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(analyseurLexical);
		System.out.println("_____________________Analyse_syntaxique________________________");

		int tree = 1;
		while(true){

			System.out.println("\n\nArbre : "+tree+"\n\n");

			Node principalNode = analyseurSyntaxique.Expression(0);
			if (!analyseurSyntaxique.getError()) {
					Node.print(principalNode, 1);
			}
			if (!analyseurLexical.accept("tok_end_of_file")) {
				analyseurLexical.skip();
				tree++;
			}
			else{
				return ;
			}

		}
	}
}
