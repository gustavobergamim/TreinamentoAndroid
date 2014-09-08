package br.com.cast.treinamento.app.listeners;


import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import br.com.cast.treinamento.app.util.ContatosListViewManager;
import br.com.cast.treinamento.domain.Contato;

public class ContatoClickListener implements OnItemClickListener, OnItemLongClickListener {

    private final ContatosListViewManager listViewManager;

    public ContatoClickListener(ContatosListViewManager listViewManager) {
        this.listViewManager = listViewManager;
    }
    
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
        Contato contato = (Contato) adapter.getItemAtPosition(posicao);
        listViewManager.setContato(contato);
    }  
    
    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
        Contato contato = (Contato) adapter.getItemAtPosition(posicao);
        listViewManager.setContato(contato);
        return false;
    }
}
