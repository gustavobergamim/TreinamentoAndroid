package br.com.cast.treinamento.app;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cast.treinamento.app.listeners.ContatoLongItemClickListener;
import br.com.cast.treinamento.entidades.Contato;
import br.com.cast.treinamento.service.ContatoService;

public class ListaContatosActivity extends BaseActivity {

	private ContatoService service;

	public ListaContatosActivity() {
		service = new ContatoService();
	}

	private ListView listViewContatos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_contatos);
		listViewContatos = recuperarControle(R.id.listViewContatos);
	}
	
	@Override
	protected void onResume() {
		configurarListView();
		super.onResume();
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

	private List<Contato> carregarContatos() {
		return service.listar();
	}

	private ArrayAdapter<Contato> criarAdapter() {
		return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carregarContatos());
	}
	
	private void configurarListView(){
		listViewContatos.setAdapter(criarAdapter());
		listViewContatos.setOnItemLongClickListener(new ContatoLongItemClickListener());
	}

}
