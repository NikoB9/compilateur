---Pré-requis---
======================================================
Installation du jdk sur la machine 
java et javac doivent être renseignés dans les variables d'environnement
======================================================

---Compilation du programme---

======================================================
Dans un terminal :

1- cd CHEMIN_VERS_LE_PROJET/compilateur/Source & javac -d ../Execution -encoding UTF-8 *.java 
======================================================

---Exécution du programme---

======================================================
ATTENTION : le fichier avec le code à exécuter ainsi que les librairies standart doivent être misent avec le Main.class dans le dossier "Execution"

Dans le même terminal à la suite :

1- cd ../Execution & java Main
2- Nom_fichier_de_code_à_lire

OU commande parametrée

Paramètres :
-f <nom_fichier> : fichier du code à compiler
-d : mode debug
-l <nom_fichier> : ajout d'une librairie à importer avant la compilation du code
(le parametre -l peut se répetter plusieurs fois pour ajouter plusieurs librairies)

1- cd ../Execution & java Main [-f Nom_fichier_de_code_à_lire] [-d] [-l librairie.lib]

OU pour générer directement le fichier du code compilé pour msm

1- cd ../Execution & java Main -f Nom_fichier_de_code_à_lire -l standard.lib > Exec
======================================================

--- Librairie : standard.lib ---

======================================================
------------------------------------------------------
Power(a, b);
->Prend en paramètres un nombre a et un nombre b
->la fonction retourne la partie entière de a exposant b
------------------------------------------------------
printx(a);
->seulement appelé par la fonction print utilisé par le développeur
->Prend en paramètres un nombre a
->la fonction écrit le nombre dans le terminal
------------------------------------------------------
print(a);
->Prend en paramètres un nombre a
->la fonction écrit le nombre dans le terminal grâce à printx(a)
------------------------------------------------------
alloc(n);
->Prend en paramètres un nombre n
->la fonction alloue n bits d'espace pour un tableau
->elle est aussi utilisée dans la fonction init qui appelle le Main afin d'allouer de la mémoire au programme
------------------------------------------------------
factorial(a);
->Prend en paramètres un nombre a positif
->la fonction retourne !a
------------------------------------------------------
======================================================

---FAIT---

======================================================
Analyseur lexical : crée des tokens en fonction des caractères rencontrés. Ignore les espaces et les commentaires(//|/**/).
Analyseur syntaxique : Gère les erreurs; Crée des arbres en fonctions des lignes d'opérations rencontrées.
Gère les Instructions, les expressions et les primaires
Les conditionnelles sont fonctionnels ainsi que l'affectation de variables et boucles (conditionnelles et boucles itératives).
Boucles prise en charge : while; for; do-while
Analyseur sémantique : permet de gérer et vérifier la déclaration des variables
Générateur de code : Gènère le code adapté à msm
Librairies standards acceptées/ import lors de la compilation
Fonctions prises en charge
Pointeurs pris en charge
post incrément et post décrément
======================================================

---INFOS IMPORTANTES---

======================================================
-UN POINT VIRGULE DOIT SEPARER CHAQUE EXPRESSION (voir exemple Execution/test.txt)
-Il faut utiliser le mot clé "var" pour déclarer une variable
-Les variables doivent être déclarées avant leur utilisation en temps que paramètre (exemple pour la boucle for)
-Dans la gestion d'erreurs les lignes sont comptées sans prendre en compte les lignes vident 
-le code principal doit être contenu dans la fonction "function main(){...code...}"
-la fonction init doit être instancié en dessous du main :
function init(){
    alloc(70000);
    main();
}
======================================================

---NON FAIT---

======================================================
prise en charge des chaînes de caractères
Nombre de type flottant (implique qu'un exposant négatif pour un calcul de puissance donnera 0)
