package object.primary;

import java.util.ArrayList;
import java.util.HashMap;

import object.secondary.Node;
import object.secondary.Operator;
import object.secondary.Token;

public class AnalyseurSyntaxique {
	private AnalyseurLexical analyseurLexical;
	private Node principalNode;
	private HashMap<String, Operator> nodeOperators = new HashMap<String, Operator>();
	private boolean error = false;
	//Indicates what is the last type of loop we're working on
	//It helps with generating specific node_continue for each type of loop
	private String typeOfLoop = "";

	public AnalyseurSyntaxique(AnalyseurLexical analyseurLexical) {
		this.analyseurLexical = analyseurLexical;

		//Remplissage des op�rateurs
		nodeOperators.put("tok_power", new Operator("node_power", 7, 0));

		nodeOperators.put("tok_divide", new Operator("node_divide", 6, 1));
		nodeOperators.put("tok_remaindor", new Operator("node_remaindor", 6, 1));
		nodeOperators.put("tok_multiply", new Operator("node_multiply", 6, 1));

		nodeOperators.put("tok_plus", new Operator("node_plus_binary", 5, 1));
		nodeOperators.put("tok_minus", new Operator("node_minus_binary", 5, 1));

		nodeOperators.put("tok_inf", new Operator("node_inf", 4, 1));
		nodeOperators.put("tok_sup", new Operator("node_sup", 4, 1));
		nodeOperators.put("tok_equal", new Operator("node_equal", 4, 1));
		nodeOperators.put("tok_sup_equal", new Operator("node_sup_equal", 4, 1));
		nodeOperators.put("tok_inf_equal", new Operator("node_inf_equal", 4, 1));
		nodeOperators.put("tok_different", new Operator("node_different", 4, 1));

		nodeOperators.put("tok_and", new Operator("node_and", 3, 1));

		nodeOperators.put("tok_or", new Operator("node_or", 2, 1));

		nodeOperators.put("tok_assignment", new Operator("node_assignment", 1, 0));
	}

	public boolean isError(){
		return this.error;
	}


	public void setError(boolean error) {
		this.error = error;
	}

	public Node Primaire() {
		//Renvoi un arbre contenant la constante si constante
		if(analyseurLexical.next().getType()=="tok_constant") {
			//Creation du noeud qui contiendra la valeur
			Node node = new Node("node_constant", analyseurLexical.next().getValue(), analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			//Token actuel trait� ! Passage au suivant.
			analyseurLexical.skip();
			return node;
		}
        else if(analyseurLexical.next().getType() == "tok_declaration"){
            System.out.println("La déclaration de variable doit se faire en dehors de toute expression et de boucles.\n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
            this.error = true;
        }
        //Renvoi un arbre variable ou fonction si on a un token identifier
		if (analyseurLexical.next().getType() == "tok_identifier"){
			Node n = new Node("node_var", analyseurLexical.next().getName(), analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			analyseurLexical.skip();
            if (analyseurLexical.accept("tok_openning_parenthesis")){
                n.setType("node_call_function");
                //Tant qu'il y a des arguments on les traites
                while (analyseurLexical.next().getType() != "tok_closing_parenthesis" && analyseurLexical.next().getType() != "tok_end_of_file"){

                    Node E = Expression(0);

                    if(!analyseurLexical.accept("tok_comma") && analyseurLexical.next().getType() != "tok_closing_parenthesis"  && analyseurLexical.next().getType() != "tok_end_of_file"){
                        System.out.println("Il manque une virgule pour séparer les arguments passé à la fonction ','\n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                        this.error = true;
                    }
                    n.addNodeChild(E);
                }

                if(!analyseurLexical.accept("tok_closing_parenthesis")){
                    System.out.println("Il manque une parenthèse fermente ')'\n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                    this.error = true;
                }
            }
			return n;
		}
		//Renvoi un arbre contenant l'ensemble des calculs mathematiques, expression boolean
		if(analyseurLexical.next().getType()=="tok_openning_parenthesis") {
			//Passage au token suivant
			analyseurLexical.skip();

			//node contient le noeud d'expression entre les parenth�ses ( )
			Node node = this.Expression(0);

			//v�rification qu'apr�s l'expression il y a une parenth�se fermante
			boolean checkValidity = analyseurLexical.accept("tok_closing_parenthesis");
			if(!checkValidity) {
				System.out.println("Erreur parenthèse fermante manquante ...  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				this.error = true;
//				System.exit(-1);
				return new Node(analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			}
			return node;
		}
		//Renvoi un arbre contenant un moins et une expression math�matique avec au plus une exponentielle
		if(analyseurLexical.next().getType()=="tok_minus") {
			//Passage au token suivant
			analyseurLexical.skip();
			Node node = new Node("node_minus_unary", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			node.addNodeChild(this.Expression(7));
			return node;
		}
		if(analyseurLexical.next().getType()=="tok_not") {
			//Passage au token suivant
			analyseurLexical.skip();
			Node node = new Node("node_not", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			node.addNodeChild(this.Expression(7));
			return node;
		}
		if (analyseurLexical.next().getType()=="tok_unknown") {
			System.out.println("Erreur : caractère non accepté. \n ( Ligne "+ analyseurLexical.next().getLine() + " ; Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
			this.error = true;
			return new Node(analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
		}
		if (analyseurLexical.next().getType()=="tok_end_of_file") {
			return new Node(analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
		}
		System.out.println("Erreur : caractère non primaire. \n ( Ligne "+ analyseurLexical.next().getLine() + " ; Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
		this.error = true;
		return new Node(analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
	}

	public Operator ChercherOp(Token token) {
		if (analyseurLexical.next().getType()=="tok_unknown" || analyseurLexical.next().getType()=="tok_power") {
			System.out.println("Erreur : opérateur non accepté. \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
			if (analyseurLexical.next().getType()=="tok_power")
                System.out.println("Veuillez utiliser la fonction Power(a, b) de la bibliothèque Operator.lib tel que a^b");

            this.error = true;
			return null;
		}

		return this.nodeOperators.get(token.getType());
	}

	public Node Expression(int minPriority) {
		//Recup�ration de la constante
		Node A1 = this.Primaire();

		while(true) {
			Operator Op = this.ChercherOp(this.analyseurLexical.next());
			//Si l'op�rateur qui suit la constante est de priorit� inf�rieure
			//A celle recherch�e on retourne l'arbre d�j� cr��
			if(Op==null || Op.getPriority()<minPriority) {
				return A1;
			}
			
			//Sinon on continue � parcourir l'expression pour construire l'arbre
			this.analyseurLexical.skip();
			Node A2 = this.Expression(Op.getPriority()+Op.getAssociativity());

			//On cr�er le noeud op�rateur
			Node A = new Node(Op.getNodeName(), analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			A.addNodeChild(A1);
			A.addNodeChild(A2);

			A1=A;
		}
	}

	public Node Instruction(){

		Node N;

	    if (analyseurLexical.next().getType() == "tok_if"){
			analyseurLexical.skip();

			if (!analyseurLexical.accept("tok_openning_parenthesis")){
                System.out.println("Erreur parenthèse ouvrante manquante ...  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				this.error = true;
			}

                Node Nexp = Expression(0);

            if (!analyseurLexical.accept("tok_closing_parenthesis")){
                System.out.println("Erreur parenthèse fermante manquante ...  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				this.error = true;
            }

                Node Nins = Instruction();

            N = new Node("node_condition", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            N.addNodeChild(Nexp);
            N.addNodeChild(Nins);

            //Si suivi d'un else on le rajoute
            if (analyseurLexical.next().getType() == "tok_else"){

                analyseurLexical.skip();

                Nins = Instruction();

                N.addNodeChild(Nins);

            }
        }
	    else if(analyseurLexical.next().getType() == "tok_declaration"){
			N = new Node("node_declaration", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			analyseurLexical.skip();
            if(analyseurLexical.next().getType() == "tok_identifier"){
                N.addNodeChild(new Node("node_var", analyseurLexical.next().getName(), analyseurLexical.next().getLine(), analyseurLexical.next().getColumn()));
            }else{
                System.out.println("Il manque un identifiant de variable  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }
        }
	    else if (analyseurLexical.next().getType() == "tok_open_brace"){

            N = new Node("node_block", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            analyseurLexical.skip();

            while (analyseurLexical.next().getType() != "tok_close_brace" && analyseurLexical.next().getType() != "tok_end_of_file"){
				N.addNodeChild(Instruction());
            }

			if (!analyseurLexical.accept("tok_close_brace")){
				System.out.println("Il manque une accolade fermante '}'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				this.error = true;
			}

        }
		else if (analyseurLexical.next().getType() == "tok_debug"){

			N = new Node("node_debug", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());

			analyseurLexical.skip();

			N.addNodeChild(Expression(0));

			if (!analyseurLexical.accept("tok_separator")){
				System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				this.error = true;
			}

		}
		else if(analyseurLexical.next().getType() == "tok_for"){
			analyseurLexical.skip();
			typeOfLoop = "for";

			N = new Node("node_block", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());

			if(! analyseurLexical.accept("tok_openning_parenthesis"))
				System.out.println("Il manque une parenthèse ouvrante '('  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");

			Node I = Expression(0);

			if(! analyseurLexical.accept("tok_separator")){
                System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

			Node T = Expression(0);

			if(! analyseurLexical.accept("tok_separator")){
                System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

			Node V = Expression(0);

			if(! analyseurLexical.accept("tok_closing_parenthesis")){
                System.out.println("Il manque une parenthèse fermente ')'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }


			Node B = Instruction();

			Node blockBodyFor = new Node("node_block", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			blockBodyFor.addNodeChild(B);
			blockBodyFor.addNodeChild(new Node("node_flag_continue", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn()));
			blockBodyFor.addNodeChild(V);

			Node cond = new Node("node_condition", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			cond.addNodeChild(T);
			cond.addNodeChild(blockBodyFor);
			cond.addNodeChild(new Node("node_break", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn()));

			Node loop = new Node("node_loop", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			loop.addNodeChild(cond);

			N.addNodeChild(I);
			N.addNodeChild(loop);

		}
		else if(analyseurLexical.next().getType() == "tok_while"){
			analyseurLexical.skip();
			typeOfLoop = "while";

			N = new Node("node_loop", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());

			if(! analyseurLexical.accept("tok_openning_parenthesis")){
                System.out.println("Il manque une parenthèse ouvrante '('  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

			Node T = Expression(0);

			if(! analyseurLexical.accept("tok_closing_parenthesis")){
                System.out.println("Il manque une parenthèse fermente ')'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

			Node B = Instruction();

			Node cond = new Node("node_condition", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			cond.addNodeChild(T);
			cond.addNodeChild(B);
			cond.addNodeChild(new Node("node_break", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn()));

			N.addNodeChild(cond);

		}
        else if(analyseurLexical.next().getType() == "tok_do"){
            analyseurLexical.skip();
			typeOfLoop = "while";

            Node bloc = Instruction();

            if(! analyseurLexical.accept("tok_while")){
                System.out.println("Il manque une accolade fermante '}'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

            if(! analyseurLexical.accept("tok_openning_parenthesis")){
                System.out.println("Il manque une parenthèse ouvrante '('  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

            Node T = Expression(0);

            if(! analyseurLexical.accept("tok_closing_parenthesis")){
                System.out.println("Il manque une parenthèse fermente ')'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

            Node cond = new Node("node_condition", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            cond.addNodeChild(T);
            cond.addNodeChild(bloc);
            cond.addNodeChild(new Node("node_break", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn()));

            Node loop = new Node("node_loop", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());

            loop.addNodeChild(cond);

            N = new Node("node_block", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            N.addNodeChild(bloc);
            N.addNodeChild(loop);
        }
        else if (analyseurLexical.next().getType() == "tok_return"){
            analyseurLexical.skip();
            N = new Node("node_return", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            N.addNodeChild(Expression(0));
			if (!analyseurLexical.accept("tok_separator")){
				System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				analyseurLexical.skip();
				this.error = true;
			}
        }
        else if(analyseurLexical.next().getType() == "tok_continue"){
            analyseurLexical.skip();
			if (!analyseurLexical.accept("tok_separator")){
				System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				analyseurLexical.skip();
				this.error = true;
			}
			String nodeName = "";
			//indicates what type of loop the continue is called for specific action in codeGenerator
			if(typeOfLoop=="for"){
				nodeName = "node_continue_for";
			}
			else{ //while loop
				nodeName = "node_continue_while";
			}
            N = new Node(nodeName, analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
        }
		else if(analyseurLexical.next().getType() == "tok_break"){
			analyseurLexical.skip();
			if (!analyseurLexical.accept("tok_separator")){
				System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
				analyseurLexical.skip();
				this.error = true;
			}
			N = new Node("node_break", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
		}
        else if(analyseurLexical.next().getType() == "tok_send"){
            analyseurLexical.skip();

            Node Nexp = Expression(0);
            if (!analyseurLexical.accept("tok_separator")){
                System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                analyseurLexical.skip();
                this.error = true;
            }

            Node NexpPadre = new Node("node_expression", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            NexpPadre.addNodeChild(Nexp);

            N = new Node("node_send", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            N.addNodeChild(NexpPadre);
        }
		/*****expressions******/
	    else {

	        Node Nexp = Expression(0);
            if (!analyseurLexical.accept("tok_separator")){
            	System.out.println("Il manque un séparteur ';'  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
            	analyseurLexical.skip();
            	this.error = true;
			}
			N = new Node("node_expression", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
			N.addNodeChild(Nexp);

        }

	    return N;

    }


    public Node Function(){

	    Node fct = new Node(analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());

        //Renvoi un arbre création fonction si on a un token identifier
        if (analyseurLexical.next().getType() == "tok_function_declaration"){

            //Récupération du nom de la fonction et création du block
            analyseurLexical.skip();
            if(analyseurLexical.next().getType() == "tok_identifier"){
                fct = new Node("node_function", analyseurLexical.next().getName(), analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());
            }else{
                System.out.println("Il manque un identifiant de fonction  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }
            analyseurLexical.skip();
            Node block = new Node("node_block", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());

            //Vérifie qu'on ouvre bien la parenthèse après l'appel de fonction
            if(!analyseurLexical.accept("tok_openning_parenthesis")){
                System.out.println("Il manque une parenthèse ouvrante '('\nVotre fihcier doit être composé de fonctions.  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                System.out.println("RAPPEL : Le code principal de l'application doit se trouver dans la fonction Main");
                //System.out.println("Aucune instruction n'est acceptée en dehors des fonctions");
                this.error = true;
            }

            //Tant qu'il y a des déclarations d'arguments on les traites
            while (analyseurLexical.next().getType() != "tok_closing_parenthesis" && analyseurLexical.next().getType() != "tok_end_of_file"){

				Node arg = new Node("node_declaration", analyseurLexical.next().getLine(), analyseurLexical.next().getColumn());

                if(analyseurLexical.next().getType() == "tok_identifier"){
                    arg.addNodeChild(new Node("node_var", analyseurLexical.next().getName(), analyseurLexical.next().getLine(), analyseurLexical.next().getColumn()));
                }else{
                    System.out.println("Il manque un identifiant pour les paramètres de la fonctions  \n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                    this.error = true;
                }
                analyseurLexical.skip();

                if(!analyseurLexical.accept("tok_comma") && analyseurLexical.next().getType() != "tok_closing_parenthesis" && analyseurLexical.next().getType() != "tok_end_of_file"){
                    System.out.println("Il manque une virgule pour séparer la déclaration des paramètres la fonction ','\n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                    this.error = true;
                }
                block.addNodeChild(arg);
            }

			if(!analyseurLexical.accept("tok_closing_parenthesis")){
				System.out.println("Il manque une parenthèse fermente ')'\n ( Ligne "+ analyseurLexical.next().getLine() + ", Colonne " + analyseurLexical.next().getColumn() + " : token " + analyseurLexical.next().getType() + " )\n");
                this.error = true;
            }

            block.addNodeChild(Instruction());
            fct.addNodeChild(block);

        }
        //Si on n'accepte pas les instruction à l'extérieur des fonctions
        else {
            System.out.println("RAPPEL : Le code principal de l'application doit se trouver dans la fonction Main");
            System.out.println("Aucune instruction n'est acceptée en dehors des fonctions");
            this.error = true;
			analyseurLexical.skip();
		}
        //Si on accepte
        /*else {
            fct = Instruction();
        }*/

        return fct;

    }


	public String toString() {

		String ts = "Liste des noeuds : ";
//		for(Node aNode : this.nodeList) {
			ts += "- " + principalNode + "\n";
//		}
		return ts;
	}



}
