package br.com.cast.treinamento.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import br.com.cast.treinamento.app.listeners.ContatoClickListener;
import br.com.cast.treinamento.app.listeners.ExcluirContatoListener;
import br.com.cast.treinamento.app.util.ContatosListViewManager;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.service.ContatoService;

public class ListaContatosActivity extends BaseActivity {

    public static final String CONTATO_FILTRO = "CONTATO_FILTRO";
    
    private ListView listViewContatos;
    private ContatosListViewManager listViewManager;
    private Contato filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);
        filtro = (Contato) getIntent().getSerializableExtra(CONTATO_FILTRO);
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
        getMenuInflater().inflate(R.menu.context_contatos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_novo).setVisible(true);
        boolean isContatoSelecionado = listViewManager.isContatoSelecionado();
        menu.setGroupVisible(R.id.group_manter, isContatoSelecionado);
        menu.setGroupVisible(R.id.group_acoes, isContatoSelecionado);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        definirAcao(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_contatos, menu);
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        definirAcao(item);
        return super.onContextItemSelected(item);
    }

    private void definirAcao(MenuItem item) {
        String nome, telefone, site;
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
            case R.id.action_ligar:
                telefone = listViewManager.getContato().getTelefone();
                startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", telefone, null)));
                break;
            case R.id.action_sms:
                nome = listViewManager.getContato().getNome();
                telefone = listViewManager.getContato().getTelefone();
                Intent intentSms = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", telefone, null));
                intentSms.putExtra("sms_body", String.format(getString(R.string.sms_prefix), nome));
                startActivity(intentSms);  
                break;
            case R.id.action_site:
                site = listViewManager.getContato().getSite();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + site))); 
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
        listViewManager = new ContatosListViewManager(
                this, listViewContatos, new ContatoService(this), filtro);
        registerForContextMenu(listViewContatos);

        ContatoClickListener listener = new ContatoClickListener(listViewManager);
        listViewContatos.setOnItemClickListener(listener);
        listViewContatos.setOnItemLongClickListener(listener);
    }

}
