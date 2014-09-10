package br.com.cast.treinamento.dao;

import java.util.HashMap;
import java.util.List;

import br.com.cast.treinamento.domain.IEntidade;

public interface IDao<T extends IEntidade> {

    List<T> listar();

    void inserir(T entidade);

    void alterar(T entidade);

    void excluir(T entidade);

    T recuperar(Long id);

    List<T> pesquisar(HashMap<String, String> filtro);
    
    long count(HashMap<String, String> filtro);
}
