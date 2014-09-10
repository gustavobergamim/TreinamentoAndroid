package br.com.cast.treinamento.service;

import java.util.HashMap;
import java.util.List;

import br.com.cast.treinamento.dao.IDao;
import br.com.cast.treinamento.domain.IEntidade;

public class BaseService<T extends IEntidade> {

	protected IDao<T> dao;

	public BaseService(IDao<T> dao) {
		this.dao = dao;
	}

	public List<T> listar() {
		return dao.listar();
	}

	public void salvar(T entidade) {
		if (entidade.getId() != null && entidade.getId() > 0) {
			dao.alterar(entidade);
		} else {
			dao.inserir(entidade);
		}
	}

	public void excluir(T entidade) {
		dao.excluir(entidade);
	}

	public T recuperar(Long id) {
		return dao.recuperar(id);
	}
	
	public List<T> pesquisar(HashMap<String, String> filtro) {
        return dao.pesquisar(filtro);
    }

	public long count(HashMap<String, String> filtro) {
        return dao.count(filtro);
    }
}
