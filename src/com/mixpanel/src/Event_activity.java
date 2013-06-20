package com.mixpanel.src;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Event_activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_activity);
		ParseJSON ParseJson_object = new ParseJSON();
		//ParseJson_object.pass_values("event_name");
		TextView view = (TextView) findViewById(R.id.result);
		String display1 =ParseJson_object.pass_values("event_name");
		view.setText(display1);
		Log.i("display in event_activity", display1);
		
		 
            
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_activity, menu);
		return true;
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
		
		case R.id.event_setting:
			//startService(intentUpdater);
			
			startActivity(new Intent(this, Prefrenceactivity.class));
			 
			return true;
		
		default:
			return false;
			
			
		}
	}

	
}
