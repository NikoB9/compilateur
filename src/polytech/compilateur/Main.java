package polytech.compilateur;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath+"\\src\\polytech\\compilateur\\source.txt"); 

		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) 

			//ATTENTION :
			//PASSER LES COMMENTAIRES (// ou /**/)
			//NOTER LES FINS DE LIGNES ET LA FIN DE FICHIER
			
			System.out.println(st); 
		
		
	} 



}
