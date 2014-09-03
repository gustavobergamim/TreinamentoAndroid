package br.com.cast.treinamento.app;

import android.os.Bundle;
import android.util.Log;

public abstract class LifeCycleLoggerActivity extends BaseActivity {

	private static final String TAG = "LIFECYCLE";

	public abstract String getActivityName();
	
	private void logEvent(String eventName) {
		Log.i(TAG, getActivityName() +  ": " + eventName);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logEvent("onCreate");
	}	

	@Override
	protected void onStart() {
		super.onStart();
		logEvent("onStart");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		logEvent("onPause");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		logEvent("onResume");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		logEvent("onStop");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		logEvent("onRestart");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		logEvent("onDestroy");
	}
}
