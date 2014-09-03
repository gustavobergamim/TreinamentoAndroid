package br.com.cast.treinamento.app.listeners;

import br.com.cast.treinamento.app.interfaces.IListaContatos;
import br.com.cast.treinamento.domain.Contato;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ContatoLongItemClickListener implements OnItemLongClickListener {

	private final IListaContatos listaContatos;

	public ContatoLongItemClickListener(IListaContatos listaContatos) {
		this.listaContatos = listaContatos;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
		Contato contato = (Contato) adapter.getItemAtPosition(posicao);
		listaContatos.setContatoSelecionado(contato);
		return false;
	}

}
