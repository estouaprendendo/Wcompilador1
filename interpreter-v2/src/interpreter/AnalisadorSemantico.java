package interpreter;

import java.util.ArrayList;
import java.util.List;

public class AnalisadorSemantico {

	private No raiz;
	private List<Simbolo> simbolos;
	private List<String> erros = new ArrayList<String>();
	private Simbolo simbolo;

	public AnalisadorSemantico(No raiz, List<Simbolo> simbolos) {
		this.setRaiz(raiz);
		this.setSimbolos(simbolos);
		//System.out.println("passei aqui..." + simbolos.get(0).getImagem());
	}

	public void analisar() {
		analisar(raiz);
	}

	public void addErro(String erro) {
		erros.add("Erro semântico: " + erro + ". ");
	}

	private Object analisar(No no) {
		System.out.println("passei aqui..." + simbolo.getTipo());
		if (no.getTipo().equals("NO_TOKEN")) {
			return null;
		} else if (no.getTipo().equals("NO_LIST_CMD")) {
			return listCmd(no);
		} else if (no.getTipo().equals("NO_CMD")) {
			return cmd(no);
		} else if (no.getTipo().equals("NO_CMD_INTER")) {
			return cmdInter(no);
		} else if (no.getTipo().equals("NO_DECL")) {
			return decl(no);
		} else if (no.getTipo().equals("NO_TIPO")) {
			return tipo(no);
		} else if (no.getTipo().equals("NO_LIST_ID")) {
			return listId(no);
		} else if (no.getTipo().equals("NO_LIST_ID_2")) {
			return listId2(no);
		} else {
			addErro("Erro: NO desconhecido = " + no.getTipo());
			return null;
		}
	}

	// <list_cmd> ::= <cmd> <list_cmd> | &
	private Object listCmd(No no) {
		if (!no.getFilhos().isEmpty()) {
			analisar(no.getFilhos().get(0));
			analisar(no.getFilhos().get(1));
		}
		return null;
	}

	// <cmd> ::= '{' <cmd_inter> '}'
	private Object cmd(No no) {
		analisar(no.getFilhos().get(0));
		return null;
	}

	// <cmd_inter> ::= <decl> | <atrib> | <laco> | <cond> | <escrita> |
	// <leitura>
	private Object cmdInter(No no) {
		analisar(no.getFilhos().get(0));
		return null;
	}

	// <decl> ::= <tipo> <list_id>
	private Object decl(No no) {
		String tipo = (String) analisar(no.getFilhos().get(0));
		List<Token> listId = (List<Token>) analisar(no.getFilhos().get(1));
		
		for(Token id : listId){
			if(simbolo.getTipo() != null){
				addErro("Erro: ID redeclarado = " + id);
			}else{
				simbolo.setTipo(tipo);
			}
		}
		
		return null;
	}

	// <tipo> ::= 'int' | 'real' | 'texto' | 'logico'
	private Object tipo(No no) {
		return no.getFilhos().get(0).getToken().getImagem();
	}

	// <list_id> ::= id <list_id2>
	private Object listId(No no) {
		Token id = no.getFilhos().get(0).getToken();
		List<Token> listId2 = (List<Token>) analisar(no.getFilhos().get(1));
		listId2.add(0, id);

		return listId2;
	}

	// <list_id2> ::= <list_id> |
	private Object listId2(No no) {
		if (no.getFilhos().isEmpty()) {
			return new ArrayList<Token>();
		} else {
			return analisar(no.getFilhos().get(0));
		}
	}

	public No getRaiz() {
		return raiz;
	}

	public void setRaiz(No raiz) {
		this.raiz = raiz;
	}

	public List<Simbolo> getSimbolos() {
		return simbolos;
	}

	public void setSimbolos(List<Simbolo> simbolos) {
		this.simbolos = simbolos;
	}

	public boolean temErros() {
		return !erros.isEmpty();
	}

	public void mostraErros() {
		for (String erro : erros) {
			System.out.println(erro);
		}
	}
}
