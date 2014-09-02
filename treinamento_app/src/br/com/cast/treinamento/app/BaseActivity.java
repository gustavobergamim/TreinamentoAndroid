package br.com.cast.treinamento.app;

import android.support.v7.app.ActionBarActivity;
import android.view.View;

public abstract class BaseActivity extends ActionBarActivity {
	
	@SuppressWarnings("unchecked")
	protected <T extends View> T recuperarControle(int id) {
		return (T) findViewById(id);
	}
}
