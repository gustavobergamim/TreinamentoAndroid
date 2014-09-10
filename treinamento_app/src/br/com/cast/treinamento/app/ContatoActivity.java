package br.com.cast.treinamento.app;

import java.io.File;
import java.util.Map.Entry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.domain.exception.ExcecaoNegocio;
import br.com.cast.treinamento.service.ContatoService;

public class ContatoActivity extends BaseActivity {

    private static final int REQUEST_CODE_CAMERA = 12345;

    public static final String CONTATO_EDICAO = "CONTATO_EDICAO";

    private EditText txtNome, txtEndereco, txtSite, txtTelefone;
    private ImageView imgFoto;
    private RatingBar barAvaliacao;
    private Button btnSalvar;
    private ContatoService service;
    private Contato contato;
    private String caminhoFoto;

    public Contato getContato() {
        if (contato == null) {
            contato = new Contato();
        }
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public boolean isEdicao() {
        return getContato() != null && getContato().getId() != null && getContato().getId() > 0;
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
        configurarFoto();
        carregarContatoEdicao();
        getSupportActionBar().setSubtitle(
                isEdicao() ? R.string.view_title_editar_contato
                        : R.string.view_title_incluir_contato);
    }

    private void recuperarControles() {
        txtNome = recuperarControle(R.id.txtNome);
        txtEndereco = recuperarControle(R.id.txtEndereco);
        txtSite = recuperarControle(R.id.txtSite);
        txtTelefone = recuperarControle(R.id.txtTelefone);
        barAvaliacao = recuperarControle(R.id.barAvaliacao);
        btnSalvar = recuperarControle(R.id.btnSalvar);
        imgFoto = recuperarControle(R.id.imgFoto);
    }

    private void configurarSalvar() {
        btnSalvar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                ContatoActivity.this.salvar();
            }

        });
    }

    private void configurarFoto() {
        imgFoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                caminhoFoto = Environment.getExternalStorageDirectory().toString();
                caminhoFoto += File.separator;
                caminhoFoto += System.currentTimeMillis();
                caminhoFoto += ".png";
                File arquivoFoto = new File(caminhoFoto);
                Uri uriFoto = Uri.fromFile(arquivoFoto);
                Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
                startActivityForResult(intentFoto, REQUEST_CODE_CAMERA);
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
        if (contato.getFoto() != null && contato.getFoto().length() > 0) {
            carregarFoto();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                processarRetornoCamera(resultCode, data);
                break;
        }
    }

    private void processarRetornoCamera(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            contato.setFoto(caminhoFoto);
            carregarFoto();
        } else {

        }
    }

    private void carregarFoto() {
        Bitmap fotoOriginal = BitmapFactory.decodeFile(contato.getFoto());
        Bitmap foto = Bitmap.createScaledBitmap(fotoOriginal, 204, 204, true);
        imgFoto.setImageBitmap(foto);
    }
}
