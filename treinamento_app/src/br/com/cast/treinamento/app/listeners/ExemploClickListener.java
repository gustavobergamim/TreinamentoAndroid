package br.com.cast.treinamento.app.listeners;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import br.com.cast.treinamento.app.R;

public class ExemploClickListener implements OnClickListener {

	private EditText txtValor;
	
	public ExemploClickListener(EditText txtValor) {
		this.txtValor = txtValor;
	}
	
	@Override
	public void onClick(View targetView) {
		Context contexto = targetView.getContext();
		String formatoMensagem = contexto.getString(R.string.toast_voce_digitou);
		String mensagem = String.format(formatoMensagem, txtValor.getText().toString());
		Toast.makeText(contexto, mensagem, Toast.LENGTH_LONG).show();
	}

}
