package interpreter;

import java.util.ArrayList;
import java.util.List;

public class AnalisadorSemantico {

	private No raiz;
	private List<Simbolo> simbolos;
	private List<String> erros = new ArrayList<>();

	public AnalisadorSemantico(No raiz, List<Simbolo> simbolos) {
		this.raiz = raiz;
		this.simbolos = simbolos;
	}

	public void analisar() {
		analisar(raiz);
	}

	private Object analisar(No no) {
		if (no.getTipo().equals("NO_LIST_CMD")) {
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
		} else if (no.getTipo().equals("NO_LEITURA")) {
			return leitura(no);
		} else if (no.getTipo().equals("NO_ESCRITA")) {
			return escrita(no);
		} else if (no.getTipo().equals("NO_COND")) {
			return cond(no);
		} else if (no.getTipo().equals("NO_SENAO")) {
			return senao(no);
		} else if (no.getTipo().equals("NO_LACO")) {
			return laco(no);
		} else if (no.getTipo().equals("NO_EXP_LOG")) {
			return expLog(no);
		} else if (no.getTipo().equals("NO_EXP_REL")) {
			return expRel(no);
		} else if (no.getTipo().equals("NO_OP_LOG")) {
			return opLog(no);
		} else if (no.getTipo().equals("NO_OP_REL")) {
			return opRel(no);
		} else if (no.getTipo().equals("NO_ATRIB")) {
			return atrib(no);
		} else if (no.getTipo().equals("NO_EXP_ARIT")) {
			return expArit(no);
		} else if (no.getTipo().equals("NO_OP_ARIT")) {
			return opArit(no);
		} else if (no.getTipo().equals("NO_OPERAN")) {
			return operan(no);
		} else {
			System.err.println("No desconhecido" + no);
			return null;
		}
	}

	// <list_cmd> ::= <cmd> <list_cmd> |
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

		
		for (Token id : listId) {
			if (simbolos.test != null) {
				erros.add("id redeclarado"+id);
			} else {
				simbolos.setTipo(id, tipo);
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
		List<Token> listId2 = (List<Token>) analisar(no.getFilhos().get(1));
		Token id = no.getFilhos().get(0).getToken();
		listId2.add(0,id);
		return listId2;
	}

	// <list_id2> ::= <list_id> |
	private Object listId2(No no) {
		if(no.getFilhos().isEmpty()){
			return new ArrayList<Token>();
		}else{
			return analisar(no.getFilhos().get(0));
		}
	}

	// <leitura> ::= 'le' id
	private Object leitura(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <escrita> ::= 'mostra' <operan>
	private Object escrita(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <cond> ::= 'se' <exp_log> '{' <list_cmd> '}' <senao>
	private Object cond(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <senao> ::= '{' <list_cmd> '}' |
	private Object senao(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <laco> ::= 'enquanto' <exp_log> <list_cmd>
	private Object laco(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <exp_log> ::= '{' <exp_rel> '}'
	private Object expLog(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <exp_rel> ::= <op_rel> <operan> <operan> | <op_log> '{' <exp_rel> '}' '{'
		// <exp_rel> '}'
	private Object expRel(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <op_log> ::= '&&' | '||'
	private Object opLog(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <op_rel> ::= '>' | '<' | '>=' | '<=' | '==' | '!='
	private Object opRel(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <atrib> ::= '=' id <exp_arit>
	private Object atrib(No no) {
		// TODO 
		// 1 - Verificar se "a" foi declarado, if(simbolos.getTipo == null) � erro.
		// 2 - Se "a" tem tipo, a express�o do lado direito � igual ao tipo do identificador do lado esquedo.
		return null;
	}

	// <exp_arit> ::= <operan> | '{' <op_arit> <exp_arit> <exp_arit> '}'
	private Object expArit(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// <op_arit> ::= '+' | '-' | '*' | '/' | '.'
	private Object opArit(No no) {
		// TODO 
		// Ficar de olho no operador ".", deve-se converter tudo para string.
		return null;
	}

	// <operan> ::= id | cli | clr | cls
	private Object operan(No no) {
		// TODO Auto-generated method stub
		return null;
	}

	// FIM DAS REGRAS

	public boolean temErros() {
		return !erros.isEmpty();
	}

	public void mostraErros() {
		for (String erro : erros) {
			System.out.println(erro);
		}
	}
	
	
}
