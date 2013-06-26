package com.mixpanel.src;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class Event_top extends Activity implements Callback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen_value);
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_top");
		ParseJson_object.setListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_top, menu);
		return true;
	}

	@Override
	public void methodToCallback(String print) {
		// TODO Auto-generated method stub
		Log.i("check in event top",print);
		
		TextView view = (TextView) findViewById(R.id.textView1);
    	view.setText(print);
		
	}

}
