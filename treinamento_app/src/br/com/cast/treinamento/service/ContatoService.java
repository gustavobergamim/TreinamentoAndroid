package br.com.cast.treinamento.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.dao.ContatoDAO;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.domain.exception.ExcecaoNegocio;

public class ContatoService extends BaseService<Contato> {

	public ContatoService(Context context) {
		super(new ContatoDAO(context));
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
}
