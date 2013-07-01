package com.mixpanel.src;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Temp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.temp, menu);
		return true;
	}

}
