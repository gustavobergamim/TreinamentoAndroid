package br.com.cast.treinamento.service;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.dao.ContatoDAO;
import br.com.cast.treinamento.dao.mapping.ContatoMap;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.domain.exception.ExcecaoNegocio;

public class ContatoService extends BaseService<Contato> {

    public ContatoService(Context context) {
        super(new ContatoDAO(context));
    }

    protected ContatoDAO getDao() {
        return (ContatoDAO) super.dao;
    }

    @Override
    public void salvar(Contato contato) throws ExcecaoNegocio {
        ExcecaoNegocio excecao = new ExcecaoNegocio();

        validarCampos(contato, excecao);

        if (!excecao.getErros().isEmpty()) {
            throw excecao;
        }

        super.salvar(contato);
    }

    public List<Contato> pesquisar(Contato filtro) {
        return getDao().pesquisar(recuperarFiltro(filtro));
    }

    private void validarCampos(Contato contato, ExcecaoNegocio excecao) {
        if (TextUtils.isEmpty(contato.getNome())) {
            excecao.adicionarErro(R.id.txtNome, R.string.campo_obrigatorio);
        }

        if (TextUtils.isEmpty(contato.getEndereco())) {
            excecao.adicionarErro(R.id.txtEndereco, R.string.campo_obrigatorio);
        }

        if (TextUtils.isEmpty(contato.getSite())) {
            excecao.adicionarErro(R.id.txtSite, R.string.campo_obrigatorio);
        } else if (!Patterns.WEB_URL.matcher(contato.getSite()).matches()) {
            excecao.adicionarErro(R.id.txtSite, R.string.valor_invalido);
        }

        if (TextUtils.isEmpty(contato.getTelefone())) {
            excecao.adicionarErro(R.id.txtTelefone, R.string.campo_obrigatorio);
        } else if (!Patterns.PHONE.matcher(contato.getTelefone()).matches()) {
            excecao.adicionarErro(R.id.txtTelefone, R.string.valor_invalido);
        }
    }

    private HashMap<String, String> recuperarFiltro(Contato filtro) {
        HashMap<String, String> where = new HashMap<String, String>();
        if (filtro != null) {
            if (!TextUtils.isEmpty(filtro.getNome())) {
                where.put(String.format("%s LIKE ?", ContatoMap.COLUMN_NOME),
                        "%" + filtro.getNome() + "%");
            }
            if (!TextUtils.isEmpty(filtro.getTelefone())) {
                where.put(String.format("%s LIKE ?", ContatoMap.COLUMN_TELEFONE),
                        "%" + filtro.getTelefone() + "%");
            }
        }
        return where;
    }
}
