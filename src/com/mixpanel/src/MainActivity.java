package com.mixpanel.src;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	static final String TAG= "MainActivity";//shotcut
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((Button) findViewById(R.id.showgraph)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startGraphActivity(Webview_graph.class);
			}
		});
		
	}
	private void startGraphActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(MainActivity.this, activity);
		 
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	 
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 
//		Intent intentUpdater = new Intent(this, UpdaterService.class);
		//Intent start_event = new Intent(this, ParseJSON.class);
	
		switch (item.getItemId()){
		
		case R.id.event:
			//startService(intentUpdater);
			
			startActivity(new Intent(this, Event_activity.class));
			
			return true;
		
		default:
			return false;
			
			
		}
		
		
		 
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
}
