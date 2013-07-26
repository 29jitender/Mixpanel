package com.mixpanel.src;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Temp extends Activity  {
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.nointernet);//giving new layout to drawer
		 Button button = (Button) findViewById(R.id.nointernet_refresh);
			 
			button.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
	 
	                Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();

	 
				}
	 
			});	
			
	}


	 
}