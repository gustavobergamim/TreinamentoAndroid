package br.com.cast.treinamento.adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.domain.Contato;

public class ContatoAdapter extends CustomBaseAdapter {

    private final Activity contexto;
    private List<Contato> contatos;

    public ContatoAdapter(Activity contexto, List<Contato> contatos) {
        this.contexto = contexto;
        this.contatos = contatos;
    }

    public List<Contato> getContatos() {
        if (contatos == null) {
            contatos = new ArrayList<Contato>();
        }
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    @Override
    public int getCount() {
        return getContatos().size();
    }

    @Override
    public Contato getItem(int indice) {
        return getContatos().get(indice);
    }

    @Override
    public long getItemId(int indice) {
        return getContatos().get(indice).getId();
    }

    @Override
    @SuppressLint({ "ViewHolder", "InflateParams", "ResourceAsColor",
            "InlinedApi" })
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = contexto.getLayoutInflater();
        View layoutItem = layoutInflater.inflate(
                R.layout.item_list_view_contato, null);
        preencherItem(layoutItem, position);

//        if (position % 2 == 0) {
//            int cor = contexto.getResources().getColor(
//                    android.R.color.secondary_text_dark);
//            layoutItem.setBackgroundColor(cor);
//        }

        return layoutItem;
    }

    private void preencherItem(View layoutItem, int position) {
        Contato contato = getItem(position);
        TextView lblNome = recuperarControle(layoutItem, R.id.lblNome);
        lblNome.setText(contato.getNome());
        TextView lblTelefone = recuperarControle(layoutItem, R.id.lblTelefone);
        lblTelefone.setText(contato.getTelefone());
    }

}
