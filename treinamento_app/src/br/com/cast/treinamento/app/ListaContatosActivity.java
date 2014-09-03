package br.com.cast.treinamento.app;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cast.treinamento.app.interfaces.IListaContatos;
import br.com.cast.treinamento.app.listeners.ContatoLongItemClickListener;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.service.ContatoService;

public class ListaContatosActivity extends BaseActivity implements IListaContatos {

	private ListView listViewContatos;
	private ContatoService service;
	private Contato contatoSelecionado;

	public ListaContatosActivity() {
		service = new ContatoService();
	}

	@Override
	public void setContatoSelecionado(Contato contato) {
		this.contatoSelecionado = contato;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_contatos);
		configurarListView();
	}

	@Override
	protected void onResume() {
		carregarListView();
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.lista_contatos_context, menu);
		super.onCreateContextMenu(menu, view, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_editar:
				Intent intent = carregarTelaNovo();
				intent.putExtra(ContatoActivity.CONTATO_EDICAO, contatoSelecionado);
				startActivity(intent);
				break;
			case R.id.action_excluir:
				criarConfirmacaoExclusao();
				break;
		}
		return super.onContextItemSelected(item);
	}

	private void criarConfirmacaoExclusao() {
		new AlertDialog.Builder(this).setMessage(R.string.confirm_msg_delete)
		.setPositiveButton(R.string.confirm_sim, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				service.excluir(contatoSelecionado);
				carregarListView();
			}

		}).setNegativeButton(R.string.confirm_nao, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}

		}).show();
	}

	private Intent carregarTelaNovo() {
		return new Intent(this, ContatoActivity.class);
	}

	private void configurarListView() {
		listViewContatos = recuperarControle(R.id.listViewContatos);
		registerForContextMenu(listViewContatos);
		listViewContatos.setOnItemLongClickListener(new ContatoLongItemClickListener(this));
	}

	private void carregarListView() {
		listViewContatos.setAdapter(criarAdapter());
	}

	private ArrayAdapter<Contato> criarAdapter() {
		return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carregarContatos());
	}

	private List<Contato> carregarContatos() {
		return service.listar();
	}

}
