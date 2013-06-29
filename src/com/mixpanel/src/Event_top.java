package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Event_top extends ListActivity implements Callback,OnSharedPreferenceChangeListener {
	  static JSONObject json = null;
	  private static final String TAG_event = "events";
	    private static final String amount = "amount";
	    private static final String percent_change = "percent_change";
	    private static final String event = "event";
	    public static String click_type="";
	    static String API_sceret= "";//defining variable 
		 static String API_key=""; 
		 SharedPreferences prefs;

	    JSONArray event_data = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen_value);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
	 	prefs.registerOnSharedPreferenceChangeListener(this);
		get_values_pref(); 
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_top");
		ParseJson_object.setListener(this);
		if(API_key.equals("nill") && API_sceret.equals("nill")){
			Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in Settings", Toast.LENGTH_LONG).show();
		}
		
	}
 
	  private void get_values_pref() {//api key and stuff
		  prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence
		  	
		  
		  
			API_key =prefs.getString("api_key", "");
			API_sceret =prefs.getString("api_secret", "");
			Log.i("hi we are in pref",API_key);
			Log.i("hi we are in pref",API_sceret);
			
		
	}
	
	
	
	@Override
	public void methodToCallback(String print) {
		// TODO Auto-generated method stub
 
			// Hashmap for ListView
		     
					ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
 
					try {
						json = new JSONObject(print);
						event_data = json.getJSONArray(TAG_event);
						 
						// looping through All Contacts
						for(int i = 0; i < event_data.length(); i++){
						    JSONObject c = event_data.getJSONObject(i);
						     
						    // Storing each json item in variable
						    String Amount = c.getString(amount);
						    String parcent_change = c.getString(percent_change);
						    String Event = c.getString(event);
						  
						 
						    // creating new HashMap
						    HashMap<String, String> map = new HashMap<String, String>();
						     
						    // adding each child node to HashMap key => value
						    map.put(amount, Amount);
						    map.put(percent_change, parcent_change);
						    map.put(event, Event);
						  
	 
						    // adding HashList to ArrayList
						    contactList.add(map);
	 
						}
	  
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
 
		/**
       * Updating parsed JSON data into ListView
       * */
					
      ListAdapter adapter = new SimpleAdapter(this, contactList,
					    R.layout.list_item,
					    new String[] { percent_change, event, amount }, new int[] {
					            R.id.p_change, R.id.e_date, R.id.E_amount });
 
      setListAdapter(adapter);
 
      // selecting single ListView item
      ListView lv = getListView();
     
      // Launching new screen on Selecting Single ListItem
      lv.setOnItemClickListener(new OnItemClickListener() {
 
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
					        int position, long id) {
					    // getting values from selected ListItem
					    String perc = ((TextView) view.findViewById(R.id.p_change)).getText().toString();
					    String name = ((TextView) view.findViewById(R.id.e_date)).getText().toString();
					    String amount1 = ((TextView) view.findViewById(R.id.E_amount)).getText().toString();
					    
					    // Starting new intent
					    Intent in = new Intent(getApplicationContext(), Event_top_click.class);
					    in.putExtra(percent_change, perc);
					    in.putExtra(event, name);
					    in.putExtra(amount, amount1);
					    
					    
					    startActivity(in);
					}
      });
				 
		 		
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_top, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
		
		case R.id.setting:
			//startService(intentUpdater);
			
			startActivity(new Intent(this, Prefrenceactivity.class));
			 
			return true;
		case R.id.event:
			
			startActivity(new Intent(this, Event_activity.class));
			
			return true;
		default:
			return false;
			
			
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		 
			get_values_pref();
		
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 
		
	}

	
	
	
	
	
}
