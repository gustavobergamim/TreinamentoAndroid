package br.com.cast.treinamento.app.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.cast.treinamento.app.util.ContatosListViewManager;
import br.com.cast.treinamento.domain.Contato;

public class ContatoItemClickListener implements OnItemClickListener {

    private final ContatosListViewManager listViewManager;

    public ContatoItemClickListener(ContatosListViewManager listViewManager) {
        this.listViewManager = listViewManager;
    }
    
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
        Contato contato = (Contato) adapter.getItemAtPosition(posicao);
        listViewManager.setContato(contato);
    }    
}
