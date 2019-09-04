package polytech.compilateur;


import java.io.File;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath+"\\src\\polytech\\compilateur\\source.txt"); 
		
		AnalyseurLexical analyseurLexical = new AnalyseurLexical(file);
		analyseurLexical.analyse();
		System.out.println(analyseurLexical);
	}
}
