package com.mixpanel.src;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Event_activity extends ListActivity{
	 SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		ParseJSON ParseJson_object = new ParseJSON();
		String display1 =ParseJson_object.pass_values("event_name");
		String result =  display1.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
		String[]  array = result.split(", ");//to get the result in list without ", "
		
		
 		
 		
			Log.i("display in event_activity", display1);		
		
		
	        setListAdapter (new ArrayAdapter<String>(this, R.layout.activity_event_activity, array));
	      
	        
	        ListView lv = getListView();
	        lv.setTextFilterEnabled(true);
	        
	        lv.setOnItemClickListener(new OnItemClickListener() {
	        	
	        	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	    		//When clicked, show a toast with the TextView text
	        		startGraphActivity(Webview_graph.class);//open graph
	        		//((All_api_define)getApplication()).event("");//calling the intent
	    		//Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
					
				}
	        	
	        });      
		
		
		 
            
		
	}
	private void startGraphActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(Event_activity.this, activity);
		 
		startActivity(intent);
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
