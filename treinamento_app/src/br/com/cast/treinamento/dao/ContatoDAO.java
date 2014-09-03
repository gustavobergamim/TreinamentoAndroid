package br.com.cast.treinamento.dao;

import br.com.cast.treinamento.domain.Contato;

public class ContatoDAO extends BaseMemoryDAO<Contato> {

	private static ContatoDAO INSTANCIA;

	static {
		INSTANCIA = new ContatoDAO();		
		Contato contato = new Contato();
		contato.setNome("Teste");
		contato.setEndereco("Teste");
		contato.setSite("www.teste.com");
		contato.setTelefone("111111");
		contato.setAvaliacao(2.5F);
		INSTANCIA.inserir(contato);
	}

	public static ContatoDAO getINSTANCIA() {
		return INSTANCIA;
	}

	private ContatoDAO() {

	}
}
