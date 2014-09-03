package br.com.cast.treinamento.adapters;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.cast.treinamento.domain.Contato;

public class ContatoAdapter extends BaseAdapter {

	private List<Contato> contatos;

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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
