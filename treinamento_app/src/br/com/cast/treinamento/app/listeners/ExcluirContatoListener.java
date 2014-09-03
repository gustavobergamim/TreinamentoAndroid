package br.com.cast.treinamento.app.listeners;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import br.com.cast.treinamento.app.util.ContatosListViewManager;

public class ExcluirContatoListener implements OnClickListener {

	private final ContatosListViewManager listViewManager;

	public ExcluirContatoListener(ContatosListViewManager listViewManager) {
		this.listViewManager = listViewManager;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		listViewManager.excluir();
	}
}
