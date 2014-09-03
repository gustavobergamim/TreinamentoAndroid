package br.com.cast.treinamento.app.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import br.com.cast.treinamento.app.util.ContatosListViewManager;
import br.com.cast.treinamento.domain.Contato;

public class ContatoLongItemClickListener implements OnItemLongClickListener {

	private final ContatosListViewManager listViewManager;

	public ContatoLongItemClickListener(ContatosListViewManager listViewManager) {
		this.listViewManager = listViewManager;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
		Contato contato = (Contato) adapter.getItemAtPosition(posicao);
		listViewManager.setContato(contato);
		return false;
	}

}
