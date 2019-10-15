import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import object.primary.AnalyseurLexical;
import object.primary.AnalyseurSyntaxique;
import object.primary.CodeGenerator;
import object.secondary.Node;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);

		System.out.println("_____________________Début_Programme________________________");
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
		int nbTreeErr = 0;
        int nbTreeOk = 0;
		while(true){

			System.out.println("\n\nArbre : "+tree+"\n\n");

			Node principalNode = analyseurSyntaxique.Expression(0);
			if (!analyseurSyntaxique.getError()) {
					Node.print(principalNode, 1);

                    nbTreeOk ++;

					System.out.println("\n\n_________________________Code_généré_____________________________\n\n");
                    System.out.println(".start");
					CodeGenerator.genCode(principalNode);
                    System.out.println("dbg");
                    System.out.println("halt");
			}
			else {
			    nbTreeErr ++;
            }


			if (!analyseurLexical.accept("tok_end_of_file")) {
				analyseurLexical.skip();
				tree++;
			}
			else{
                System.out.println("\n\n_____Arbres Sans Erreurs___:___"+nbTreeOk+"/4\n\n");
                System.out.println("\n\n_____Arbres Avec Erreurs___:___"+nbTreeErr+"/11\n\n");
				return ;
			}

			analyseurSyntaxique.setError(false);
		}
	}
}
