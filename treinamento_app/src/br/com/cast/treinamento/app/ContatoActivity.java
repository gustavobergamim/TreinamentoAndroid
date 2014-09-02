package br.com.cast.treinamento.app;

import br.com.cast.treinamento.entidades.Contato;
import br.com.cast.treinamento.service.ContatoService;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class ContatoActivity extends BaseActivity {

	private EditText txtNome, txtEndereco, txtSite, txtTelefone;
	private RatingBar barAvaliacao;
	private Button btnSalvar;
	private ContatoService service;

	public ContatoActivity() {
		service = new ContatoService();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		recuperarControles();
		configurarSalvar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_novo) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void recuperarControles() {
		txtNome = recuperarControle(R.id.txtNome);
		txtEndereco = recuperarControle(R.id.txtEndereco);
		txtSite = recuperarControle(R.id.txtSite);
		txtTelefone = recuperarControle(R.id.txtTelefone);
		barAvaliacao = recuperarControle(R.id.barAvaliacao);
		btnSalvar = recuperarControle(R.id.btnSalvar);
	}

	private void configurarSalvar() {
		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Contato contato = recuperarContato();
				service.salvar(contato);
				ContatoActivity.this.finish();
			}

		});
	}

	private Contato recuperarContato() {
		Contato contato = new Contato();
		contato.setNome(txtNome.getText().toString());
		contato.setEndereco(txtEndereco.getText().toString());
		contato.setSite(txtSite.getText().toString());
		contato.setTelefone(txtTelefone.getText().toString());
		contato.setAvaliacao(barAvaliacao.getRating());
		return contato;
	}
}
