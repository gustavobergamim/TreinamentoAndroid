package br.com.cast.treinamento.domain.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExcecaoNegocio extends RuntimeException {

	private static final long serialVersionUID = -4854183896045130432L;

	private final Map<Integer, Integer> erros;

	public ExcecaoNegocio() {
		erros = new LinkedHashMap<Integer, Integer>();
	}

	public void adicionarErro(Integer id, Integer mensagem) {
		erros.put(id, mensagem);
	}

	public Map<Integer, Integer> getErros() {
		return erros;
	}
}
