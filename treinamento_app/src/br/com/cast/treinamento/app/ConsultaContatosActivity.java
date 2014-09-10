package br.com.cast.treinamento.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.service.ContatoService;

public class ConsultaContatosActivity extends BaseActivity {

    private EditText txtNome, txtTelefone;
    private Button btnPesquisar;
    private final ContatoService service;

    public ConsultaContatosActivity() {
        service = new ContatoService(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_contatos);
        getSupportActionBar().setSubtitle(R.string.view_title_consultar_contato);
        recuperarControles();
        configurarPesquisar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_contatos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_novo).setVisible(true);
        menu.setGroupVisible(R.id.group_manter, false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_novo:
                Intent intent = new Intent(this, ContatoActivity.class);
                this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void recuperarControles() {
        txtNome = recuperarControle(R.id.txtNome);
        txtTelefone = recuperarControle(R.id.txtTelefone);
        btnPesquisar = recuperarControle(R.id.btnPesquisar);
    }

    private void configurarPesquisar() {
        btnPesquisar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato filtro = recuperarContatoFiltro();
                long count = service.count(filtro);
                if (count == 0) {
                    Toast.makeText(ConsultaContatosActivity.this, R.string.toast_contato_empty,
                            Toast.LENGTH_SHORT).show();
                } else {
                    navegarTelaListagem(filtro);
                }
            }
        });
    }

    private Contato recuperarContatoFiltro() {
        Contato filtro = new Contato();
        filtro.setNome(txtNome.getText().toString());
        filtro.setTelefone(txtTelefone.getText().toString());
        return filtro;
    }

    public void navegarTelaListagem(Contato filtro) {
        Intent intent = new Intent(this, ListaContatosActivity.class);
        if (filtro != null) {
            intent.putExtra(ListaContatosActivity.CONTATO_FILTRO, filtro);
        }
        startActivity(intent);
    }
}
