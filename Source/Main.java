import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import object.primary.AnalyseurLexical;
import object.primary.AnalyseurSyntaxique;
import object.primary.CodeGenerator;
import object.secondary.Node;

public class Main {

	public static void main(String[] args) throws IOException {

	    boolean debugMode = false;

	    if (args.length == 2){
	        debugMode = Boolean.parseBoolean(args[1]);
        }

		Scanner in = new Scanner(System.in);

	    if (debugMode){
            System.out.println("\n\n_____________________Début_Programme________________________\n");
            System.out.print(System.getProperty("line.separator"));
        }

        String fileName;
		if(args.length == 0){
		    System.out.print("Entrez le nom de fichier avec l'extension .txt : ");
		    fileName = in.nextLine();
        }
		else{
		    fileName = args[0];
        }


		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath+"\\"+fileName);

        if (debugMode){
            System.out.print(System.getProperty("line.separator"));
            System.out.println("_____________________Analyse_lexicale________________________");
        }

		AnalyseurLexical analyseurLexical = new AnalyseurLexical(file);
		analyseurLexical.analyse();

        if (debugMode) System.out.println(analyseurLexical);



		//System.out.lineSeparator();
		AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(analyseurLexical);
		//System.out.println("_____________________Analyse_syntaxique________________________");

		int tree = 1;
		int nbTreeErr = 0;
        int nbTreeOk = 0;
        String flow;
        if (debugMode) flow = "\n\n_________________________Code_généré_avec_succès_:_____________________________\n\n.start\nresn 1\n";
		else flow = "\n\n.start\nresn 1\n";
        while(true){

			//System.out.println("\n\nArbre : "+tree+"\n\n");

			Node principalNode = analyseurSyntaxique.Instruction();
			if (!analyseurSyntaxique.getError()) {
                    if (debugMode) Node.print(principalNode, 1);

                    nbTreeOk ++;

					flow += CodeGenerator.genCode(principalNode);
			}
			else {
			    nbTreeErr ++;
            }


			if (!analyseurLexical.accept("tok_end_of_file")) {
				tree++;
			}
			else{
                //System.out.println("\n\n_____Arbres Sans Erreurs___:___"+nbTreeOk+"\n\n");
                //System.out.println("\n\n_____Arbres Avec Erreurs___:___"+nbTreeErr+"\n\n");
				if (nbTreeErr >= 1){
					flow = "\n\nL'analyseur à rencontré des erreurs. Le code compilé ne sera pas généré ! \n\n";
				}
				else {
					flow += "halt\n";
				}
				System.out.println(flow);
				return ;
			}

			analyseurSyntaxique.setError(false);
		}
	}
}
