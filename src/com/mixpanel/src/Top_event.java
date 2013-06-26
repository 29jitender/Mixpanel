package com.mixpanel.src;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Top_event extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen_values);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_event, menu);
		return true;
	}

}
