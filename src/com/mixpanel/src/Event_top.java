package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
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

public class Event_top extends SherlockListActivity implements Callback,ActionBar.OnNavigationListener,OnSharedPreferenceChangeListener {
	static JSONObject json = null;
	 SharedPreferences prefs;

	 private static final String TAG_event = "events";
	private static final String amount = "amount";
	private static final String percent_change = "percent_change";
	private static final String event = "event";
	    public static String click_type="";
	    
		 
		 public static String limit="";

	    JSONArray event_data = null;
	
	    
	    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//for progress it will be passed before layout

		navigation();// calling navigation
		
	}
	    public void iamcallin(){
	    	 setContentView(R.layout.event_top);
	         setSupportProgressBarIndeterminateVisibility(true);//onload show

			 //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			  //setTheme(SampleList.THEME); //Used for theme switching in samples
			  prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
			  prefs.registerOnSharedPreferenceChangeListener(this);
			
			 
			 
			ParseJSON ParseJson_object = new ParseJSON();
			ParseJson_object.pass_values("event_top1");
			ParseJson_object.setListener(this);
			
			get_values_pref();
	    }
		@Override
			protected void onResume() {
			iamcallin();//calling the activity on resume only
				 getSupportActionBar().setSelectedNavigationItem(2);//getting the navigation 
				 iamcallin();
				super.onResume();
			}


public void  get_values_pref(){// getting values from preference  
		prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence

		 
		String limit1 =prefs.getString("top_event", "10");
		limit=limit1.replaceAll("\\s","");//removing spaces if user entered by mistake
		final TextView textViewToChange10 = (TextView) findViewById(R.id.name_label);//printing the text as heading
		textViewToChange10.setText("Top"+" "+limit+" "+"events of the day");
		 
		int check= Integer.parseInt(limit);
		// condition of api
				//event
		if(check>100 ){
			Toast.makeText(getApplicationContext(), "The maximum Limit can be is 100", Toast.LENGTH_LONG).show();
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

	                setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

 
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
		  
		  getSupportActionBar().setDisplayHomeAsUpEnabled (true);
		  getSupportActionBar().setDisplayShowTitleEnabled(false);
		  getSupportActionBar().setDisplayUseLogoEnabled  (true);		  
		  setTheme(SampleList.THEME); //Used for theme switching in samples
 		// starting of menu
		   Context context = getSupportActionBar().getThemedContext();
	        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
	        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

	        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	        getSupportActionBar().setListNavigationCallbacks(list, this);
	        
		// ending of menu
	  }

 
	@Override
	    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		  
		 switch (itemPosition){
			
			case 0:
				startActivity(new Intent(this, Home.class)); 
				return true;
			case 1:
				startActivity(new Intent(this, Event_activity.class));
				
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
        menu.add(Menu.NONE, R.id.refresh, Menu.NONE, R.string.refresh)
        .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	
        
        menu.add(Menu.NONE, R.id.event_top_setting, Menu.NONE, R.string.event_top_setting)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
       //  getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);

        return true;
    }
    

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		
		case R.id.event_top_setting:
			//startService(intentUpdater);
			 
			startActivity(new Intent(this, Prefrenceactivity_event_top.class));
			 
			return true;
		 
		case R.id.refresh:
			 Intent myIntent = new Intent(this ,Event_top.class);//refreshing
				startActivity(myIntent);
				finish();
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

 



 

	 
	
	
	
}
