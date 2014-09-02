package br.com.cast.treinamento.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaContatosActivity extends BaseActivity {

	private ListView listViewContatos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_contatos);
		listViewContatos = recuperarControle(R.id.listViewContatos);

		String[] contatos = { "André", "Baca", "Glini" };

		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contatos);

		listViewContatos.setAdapter(adapter);

		listViewContatos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
				String mensagem = getString(R.string.toast_click_posicao, posicao);
				Toast.makeText(view.getContext(), mensagem, Toast.LENGTH_LONG).show();
			}

		});

		listViewContatos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				String mensagem = getString(R.string.toast_click_nome, adapter.getItemAtPosition(posicao));
				Toast.makeText(view.getContext(), mensagem, Toast.LENGTH_LONG).show();
				return true;
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
			case R.id.action_novo:
				intent = carregarTelaNovo();
				break;
		}
		if (intent != null) {
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private Intent carregarTelaNovo() {
		return new Intent(this, ContatoActivity.class);
	}

}
