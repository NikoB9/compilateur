---Pré-requis---
Installation du jdk sur la machine 
java et javac doivent être renseignés dans les variables d'environnement

---Compilation du programme---
Dans un terminal :

1- cd CHEMIN_VERS_LE_PROJET/compilateur/Source & javac -d ../Execution -encoding UTF-8 *.java 

---Exécution du programme---

ATTENTION : le fichier avec le code à exécuter doit être mit avec le Main.class dans le dossier "Executer"

Dans le même terminal à la suite :

1- cd ../Execution & java Main
2- Nom_fichier_de_code_à_lire

OU mode debug (avec arbres)

1- cd ../Execution & java Main [Nom_fichier_de_code_à_lire] [debug:true|false]

OU pour générer directement le fichier du code compilé pour msm

1- cd ../Execution & java Main Nom_fichier_de_code_à_lire > Exec

---FAIT---
Analyseur lexical : cré des tokens en fonctions des caractères rencontrés. Ignore les espaces et les commentaires(//|/**/).
Analyseur syntaxique : Gère les erreurs; Créer des arbres en fonctions des lignes d'opérations rencontrées. 
Gère les Instructions, les expressions et les primaires
Les conditionnelles sont fonctionnels ainsi que l'affectation de variables et boucles (conditionnelles et itératives).
Boucles prise en charge : while; for; do-while
Analyseur sémantique : permet de gérer la déclaration des variables
Générateur de code : Gènère le code adapté à msm


---INFOS IMPORTANTES---
-UN POINT VIRGULE DOIT SEPARER CHAQUE EXPRESSION (voir exemple Execution/test.txt)
-Il faut utiliser le mot clé "var" pour déclarer une variable
-Dans la gestion d'erreurs les lignes sont comptées sans prendre en compte les lignes vident 

---NON FAIT---
fonctions
variables string
Nombre de type flottant (implique qu'un exposant négatif poue un calcul de puissance donnera 0)
tableaux