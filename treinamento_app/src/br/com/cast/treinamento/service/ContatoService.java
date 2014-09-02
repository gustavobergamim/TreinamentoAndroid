package br.com.cast.treinamento.service;

import br.com.cast.treinamento.dao.ContatoDAO;
import br.com.cast.treinamento.entidades.Contato;

public class ContatoService extends BaseService<Contato> {

	private ContatoDAO dao;

	public ContatoService() {
		super(ContatoDAO.getINSTANCIA());
		dao = ContatoDAO.getINSTANCIA();
	}
}
