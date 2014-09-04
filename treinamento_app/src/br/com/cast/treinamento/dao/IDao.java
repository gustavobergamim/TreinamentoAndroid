package br.com.cast.treinamento.dao;

import java.util.List;

import br.com.cast.treinamento.domain.IEntidade;

public interface IDao<T extends IEntidade> {

    public List<T> listar();

    public void inserir(T entidade);

    public void alterar(T entidade);

    public void excluir(T entidade);

    public T recuperar(Long id);    
}
