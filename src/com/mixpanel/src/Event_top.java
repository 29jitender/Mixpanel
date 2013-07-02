package com.mixpanel.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class Event_top extends SherlockListActivity implements Callback,OnSharedPreferenceChangeListener,ActionBar.OnNavigationListener {
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
		get_values_pref(); //getting values from pre
		new Check_api().execute();//checking api
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_top");
		ParseJson_object.setListener(this);
		
		if(API_key.equals("nill") && API_sceret.equals("nill")){
			Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in Settings", Toast.LENGTH_LONG).show();
		}
		navigation();// calling navigation
		
	}

	   
	
	
	
	  private void get_values_pref() {//api key and stuff
		  prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence
		  	
		   
			API_key =prefs.getString("api_key", "nill");
			API_sceret =prefs.getString("api_secret", "nill");
			Log.i("hi we are in pref",API_key);
			Log.i("hi we are in pref",API_sceret);
			
		
	}
	
	
	  private class Check_api extends AsyncTask<Void, Void, Integer> {

	        protected Integer doInBackground(Void... params) {
	        	String url = All_api_define.api_check();
	   	     
		    	DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet(url);// main request
				getRequest.setHeader("Accept", "application/json");
				getRequest.setHeader("Accept-Encoding", "gzip"); //
				int result = 0;
				try {
					HttpResponse response = (HttpResponse) httpclient.execute(getRequest);
					 result= response.getStatusLine().getStatusCode();
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 
		    	
				return result;
		    	 
	        	
	        }

  protected void onPostExecute(Integer result) {

				 if(result==200){
					 Log.i("working test",result+"");
				 }
				 else{
					 new Handler().postDelayed(new Runnable() {// opening menu when api key is wrong 
						   public void run() { 
						     openOptionsMenu(); 
						   } 
						}, 1000);

					 Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in Settings", Toast.LENGTH_LONG).show(); 
				 }

	            super.onPostExecute(result);
	        }
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

	  
	//for navigation
	  public void navigation(){
		  
		  getSupportActionBar().setDisplayShowTitleEnabled(false);
		  
		  setTheme(SampleList.THEME); //Used for theme switching in samples
		  String[] mLocations = getResources().getStringArray(R.array.locations);// item location
		// starting of menu
		   Context context = getSupportActionBar().getThemedContext();
	        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
	        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

	        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	        getSupportActionBar().setListNavigationCallbacks(list, this);
	        
		// ending of menu
	  }
	 @Override
	protected void onResume() {
		 getSupportActionBar().setSelectedNavigationItem(0);
		super.onResume();
	}

 
	@Override
	    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		  
		 switch (itemPosition){
			
			case 1:
				startActivity(new Intent(this, Event_activity.class)); 
				return true;
			case 2:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;
			case 3:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;
			case 4:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;
			case 5:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;	
			default:
				return false;
		 }
	    }
	
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar
        boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
         getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);

        return true;
    }
    

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		
		case R.id.setting:
			//startService(intentUpdater);
			 
			startActivity(new Intent(this, Prefrenceactivity.class));
			 
			return true;
		case R.id.about:
				//make someting for about
			
			
			return true;
		default:
			return false;
			
			
		}
	}

 




	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		 
			get_values_pref();
			 
			//restarting activity with new values
			
		
		
		
	}

	 
	
	
	
}
