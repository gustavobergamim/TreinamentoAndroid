package br.com.cast.treinamento.app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import br.com.cast.treinamento.app.listeners.ContatoItemClickListener;
import br.com.cast.treinamento.app.listeners.ContatoLongItemClickListener;
import br.com.cast.treinamento.app.listeners.ExcluirContatoListener;
import br.com.cast.treinamento.app.util.ContatosListViewManager;
import br.com.cast.treinamento.service.ContatoService;

public class ListaContatosActivity extends BaseActivity {

    private ListView listViewContatos;
    private ContatosListViewManager listViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);
        configurarListView();
        getSupportActionBar().setSubtitle(R.string.view_title_listar_contato);
    }

    @Override
    protected void onResume() {
        listViewManager.carregarListView();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_contatos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (listViewManager.isContatoSelecionado()) {
            menu.findItem(R.id.action_editar).setVisible(true);
            menu.findItem(R.id.action_excluir).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        definirAcao(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.lista_contatos_context, menu);
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        definirAcao(item);
        return super.onContextItemSelected(item);
    }

    private void definirAcao(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_novo:
                listViewManager.navegarTelaEdicao();
                break;
            case R.id.action_editar:
                listViewManager.editar();
                break;
            case R.id.action_excluir:
                criarConfirmacaoExclusao();
                break;
        }
    }

    private void criarConfirmacaoExclusao() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(R.string.confirm_msg_delete)
                .setPositiveButton(R.string.confirm_sim,
                        new ExcluirContatoListener(listViewManager))
                .setNeutralButton(R.string.confirm_nao, null).show();
    }

    private void configurarListView() {
        listViewContatos = recuperarControle(R.id.listViewContatos);
        listViewManager = new ContatosListViewManager(this, listViewContatos, new ContatoService(
                this));
        registerForContextMenu(listViewContatos);

        listViewContatos.setOnItemClickListener(new ContatoItemClickListener(listViewManager));

        listViewContatos.setOnItemLongClickListener(new ContatoLongItemClickListener(
                listViewManager));
    }

}
