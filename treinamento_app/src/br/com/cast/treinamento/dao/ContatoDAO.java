package br.com.cast.treinamento.dao;

import android.content.Context;
import br.com.cast.treinamento.dao.mapping.ContatoMap;
import br.com.cast.treinamento.domain.Contato;

public class ContatoDAO extends BaseDAO<Contato> implements IDao<Contato> {

    public ContatoDAO(Context context) {
        super(context, new ContatoMap());
    }
}
