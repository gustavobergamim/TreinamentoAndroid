package br.com.cast.treinamento.app;

import java.util.Map.Entry;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.domain.exception.ExcecaoNegocio;
import br.com.cast.treinamento.service.ContatoService;

public class ContatoActivity extends BaseActivity {

	public static final String CONTATO_EDICAO = "CONTATO_EDICAO";

	private EditText txtNome, txtEndereco, txtSite, txtTelefone;
	private RatingBar barAvaliacao;
	private Button btnSalvar;
	private ContatoService service;
	private Contato contato;

	public Contato getContato() {
		if (contato == null) {
			contato = new Contato();
		}
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public ContatoActivity() {
		service = new ContatoService(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		recuperarControles();
		configurarSalvar();
		carregarContatoEdicao();
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
				ContatoActivity.this.salvar();
			}

		});
	}

	private void carregarContatoEdicao() {
		Contato contato = (Contato) getIntent().getSerializableExtra(CONTATO_EDICAO);
		setContato(contato);
		if (contato != null) {
			preencherCamposEdicao();
		}
	}

	private void preencherCamposEdicao() {
		Contato contato = getContato();
		txtNome.setText(contato.getNome());
		txtEndereco.setText(contato.getEndereco());
		txtSite.setText(contato.getSite());
		txtTelefone.setText(contato.getTelefone());
		if (contato.getAvaliacao() != null) {
			barAvaliacao.setRating(contato.getAvaliacao());
		}
	}

	private Contato recuperarContato() {
		Contato contato = getContato();
		contato.setNome(txtNome.getText().toString());
		contato.setEndereco(txtEndereco.getText().toString());
		contato.setSite(txtSite.getText().toString());
		contato.setTelefone(txtTelefone.getText().toString());
		contato.setAvaliacao(barAvaliacao.getRating());
		return contato;
	}

	private void salvar() {
		Contato contato = recuperarContato();
		try {
			service.salvar(contato);
			finish();
		} catch (ExcecaoNegocio ex) {
			for (Entry<Integer, Integer> erro : ex.getErros().entrySet()) {
				View campo = recuperarControle(erro.getKey());
				if (campo instanceof EditText) {
					((EditText) campo).setError(getString(erro.getValue()));
				}
			}
		}
	}
}
