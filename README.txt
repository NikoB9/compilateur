---Pré-requis---
Installation du jdk sur la machine 
java et javac doivent être renseignés dans les variables d'environnement

---Compilation du programme---
Dans un terminal :

1- cd CHEMIN_VERS_LE_PROJET/compilateur/Source
2- javac -d ../Execution -encoding UTF-8 *.java 

---Exécution du programme---

ATTENTION : le fichier avec le code à exécuter doit être mit avec le Main.class dans le dossier "Executer"

Dans le même terminal à la suite :

3- cd ../Execution
4- java Main
5- Nom_fichier_de_code_à_lire

---FAIT---
Analyseur lexical : cré des tokens en fonctions des caractères rencontrés. Ignore les espaces et les commentaires.
Analyseur syntaxique : Gère les erreurs; Créer des arbres en fonctions des lignes d'opérations rencontrées. 
Gère les Instructions, les expressions et les primaire
Les conditionnelles sont fonctionnels 
Analyseur sémantique :
Générateur de code : 

---INFOS IMPORTANTES---
-UN POINT VIRGULE DOIT SEPARER CHAQUE INSTRUCTION.
-Dans la gestion d'erreurs les lignes sont comptez sans prendre en compte les lignes vides 
---NON FAIT---
Affectation de variables et boucles (conditionnelles et itératives).
Node Power
Boucles (while for)