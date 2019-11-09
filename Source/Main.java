import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import object.primary.AnalyseurLexical;
import object.primary.AnalyseurSyntaxique;
import object.primary.AnalyseurSemantique;
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
        String flow="";

        /*****Debut de programme : ouverture de bloc*******/
        AnalyseurSemantique.openBlock();

        while(true){

			//System.out.println("\n\nArbre : "+tree+"\n\n");

			Node principalNode = analyseurSyntaxique.Function();
            AnalyseurSemantique.nodeAnalyse(principalNode);

			if (debugMode) Node.print(principalNode, 1);

			if (!analyseurSyntaxique.isError() && !AnalyseurSemantique.isError()) {

                    nbTreeOk ++;

					flow += CodeGenerator.genCode(principalNode);
                    AnalyseurSemantique.setNbVariables(3);
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
					//if (debugMode) flow = "\n\n_________________________Code_généré_avec_succès_:_____________________________\n\n.start\nresn "+AnalyseurSemantique.getNbVariables()+"\n"+flow;
                    if (debugMode) flow = "\n\n_________________________Code_généré_avec_succès_:_____________________________\n\n"+flow;
					//flow = "\n\n.start\nresn "+AnalyseurSemantique.getNbVariables()+"\n"+flow;
					//flow += "halt\n";
                    else flow += ".start\nprep Main\ncall 0\nhalt\n";
				}
				System.out.println(flow);
				return ;
			}

			analyseurSyntaxique.setError(false);
		}
	}
}
