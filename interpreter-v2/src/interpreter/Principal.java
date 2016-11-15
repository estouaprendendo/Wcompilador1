package interpreter;

import java.io.IOException;

public class Principal {
	public static void main(String[] args) throws IOException{
		
		AnalisadorLexico analisadorLexico = new AnalisadorLexico();
		analisadorLexico.analisar();
		
		if (analisadorLexico.temErros()) {
			analisadorLexico.mostraErros();
			System.exit(0);
		}
		
		AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(analisadorLexico.getTokens());
		analisadorSintatico.analisar();

		if (analisadorSintatico.temErros()) {
			analisadorSintatico.mostraErros();
			System.exit(0);
		}
		
		analisadorSintatico.mostraArvore();
		
		
	}
}
