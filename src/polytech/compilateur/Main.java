package polytech.compilateur;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		//Création de l'objet File
		File file = new File(basePath+"\\src\\polytech\\compilateur\\source.txt"); 

		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) 
			System.out.println(st); 
	} 



}
