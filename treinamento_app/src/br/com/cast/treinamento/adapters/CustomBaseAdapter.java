package br.com.cast.treinamento.adapters;

import android.view.View;
import android.widget.BaseAdapter;

public abstract class CustomBaseAdapter extends BaseAdapter {
    
    @SuppressWarnings("unchecked")
    protected <T extends View> T recuperarControle(View contexto, int id) {
        return (T) contexto.findViewById(id);
    }
    
}
