package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
 
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//interface Callback {
//    void methodToCallback(String response);
//}

public class Event_activity extends SherlockActivity implements OnSharedPreferenceChangeListener, Callback,ActionBar.OnNavigationListener {
	 SharedPreferences prefs;
	
	 public static String click_type="";
	 public static String event_interval="";//global 
	 public static String event_unit="";
	 public static String event_type="";
	 public static  String display1="lol";
	// List view
		private ListView lv;
 
		
		// Listview Adapter
 
		ArrayAdapter<String> adapter;
		
		// Search EditText
		EditText inputSearch;
		
		
		// ArrayList for Listview
		ArrayList<HashMap<String, String>> productList;

	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  setTheme(SampleList.THEME); //Used for theme switching in samples

		super.onCreate(savedInstanceState);		
		getActionBar().setDisplayShowTitleEnabled(false);
		 setContentView(R.layout.activity_event_activity);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
	 	prefs.registerOnSharedPreferenceChangeListener(this);
		get_values_pref(); 
		Log.i("in async sucess",display1);
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_name");
		ParseJson_object.setListener(this);
		navigation();// calling navigation
 		 
	}
	
	
	
	@Override
	public void methodToCallback(String display12) {
		// TODO Auto-generated method stub
		//String display1 =ParseJson_object.pass_values("event_name");
		String result =  display12.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
		String[]  array = result.split(", ");//to get the result in list without ", "
		lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        
        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item1, R.id.event_activity_list, array);
        lv.setAdapter(adapter);
        
        // this is to search the items in list
        inputSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				Event_activity.this.adapter.getFilter().filter(cs);	
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub							
			}
		});
		
		
		
		
 		click_action();

	}



public void  get_values_pref(){// getting values from preference  
		prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence

		event_interval =prefs.getString("Interval", "7");
		event_unit =prefs.getString("Unit", "day");
		event_type =prefs.getString("Type", "general");
		
		
		// condition of api
				//event
		if(event_type.equals("unique") && event_unit.equals("hour")){
			Toast.makeText(getApplicationContext(), "You cannot get hourly uniques please change the event setting again", Toast.LENGTH_LONG).show();
		}
		
		
		
		//return event_interval,event_unit,event_type;
	} 
	
	public String click_action(){
		 
        lv.setTextFilterEnabled(true);
        
		 lv.setOnItemClickListener(new OnItemClickListener() {
	        	
	        	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	        		  Log.i("item",""+lv.getItemAtPosition(position));
	        		  startGraphActivity(event_final.class);//open graph
	        		  click_type=(String) lv.getItemAtPosition(position);
				}
	        	
	        });      
		
		return click_type;
	}
	
	private void startGraphActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(Event_activity.this, activity);
		 
		startActivity(intent);
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
			protected void onResume() {// setting defult values
				 getSupportActionBar().setSelectedNavigationItem(1);
				super.onResume();
			}
	  
	  
	  @Override
	    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		  
		 switch (itemPosition){
			
			case 0:
				startActivity(new Intent(this, Event_top.class)); 
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
	
	
	// this is the option
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;

	        menu.add(Menu.NONE, R.id.event_filter, Menu.NONE, R.string.event_filter)
         
         .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	
         //getSherlock().getMenuInflater().inflate(R.menu.event_activity, menu);
		return true;
		
	}

	 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
		
		case R.id.event_filter:
			//startService(intentUpdater);
			
			startActivity(new Intent(this, Prefrenceactivity_event.class));
			 
			return true;
		
		default:
			return false;
			
			
		}
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		get_values_pref();
		
	}




	
	
}
