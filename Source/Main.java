import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import object.primary.AnalyseurLexical;
import object.primary.AnalyseurSyntaxique;
import object.primary.AnalyseurSemantique;
import object.primary.CodeGenerator;
import object.secondary.Node;

public class Main {

	public static void main(String[] args) throws IOException {

	    boolean debugMode = false;


	    String fileName = "";
	    ArrayList<String> libraries = new ArrayList<String>();
	    //traitement des parametres
	    for(int a = 0; a < args.length; a++){
			if (args[a] == "-l") libraries.add(args[a+1]);
	    	if (args[a] == "-f") fileName = args[a+1];
			if (args[a] == "-d") debugMode=true;
		}

		Scanner in = new Scanner(System.in);

	    if (debugMode){
            System.out.println("\n\n_____________________Début_Programme________________________\n");
            System.out.print(System.getProperty("line.separator"));
        }

		if(fileName == ""){
		    System.out.print("Entrez le nom de fichier avec l'extension .txt : ");
		    fileName = in.nextLine();
        }

		String basePath = new File("").getAbsolutePath();



		//LIBRAIRIES
		//ON PAR DU PRINCIPE QUE LES LIBRAIRIES SONT IMPLEMENTEES PAR NOUS ET NE COMPORTENT DONC PAS D'ERREUR
		String flow="";
		for (int f = 0; f<libraries.size(); f++){

			File lib = new File(basePath+"\\"+libraries.get(f));
			AnalyseurLexical alLib = new AnalyseurLexical(lib);
			AnalyseurSyntaxique asLib = new AnalyseurSyntaxique(alLib);

			while(true){
				Node principalNodeLib = asLib.Function();
				AnalyseurSemantique.nodeAnalyse(principalNodeLib);

				flow += CodeGenerator.genCode(principalNodeLib);
				AnalyseurSemantique.setNbVariables(3);


				if (alLib.accept("tok_end_of_file")) {
					return ;
				}
				asLib.setError(false);
			}

		}


		//FICHIER CODE à COMPILER
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
