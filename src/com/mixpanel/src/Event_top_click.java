package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class Event_top_click  extends SherlockListActivity implements Callback,ActionBar.OnNavigationListener  {
	
	// JSON node keys
	 
	 private static String TAG_event = "values";
	 private static String  key = "";
	
	 private static String KEY1= "temp";
	 private static String VALUE1= "temp1";
	  
	// private static final String[] series={};   
	    
	 
	 JSONObject event_data = null;
	 static JSONObject json = null;
	public static String name="";  // defining event name
	public static String interval="7";// defining event interval
	@Override
    public void onCreate(Bundle savedInstanceState) {
		  setTheme(SampleList.THEME); //Used for theme switching in samples

        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//for progress it will be passed before layout

         getIntent().setAction("Already created");// activity already alive
        setContentView(R.layout.top_event_click);
        setSupportProgressBarIndeterminateVisibility(true);//onload show

        // getting intent data
        Intent in = getIntent();
         // Get JSON values from previous intent
        name = in.getStringExtra("event");
         // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        lblName.setText(name);
//        TextView lblName1 = (TextView) findViewById(R.id.event_duration);
//        lblName1.setText(interval);
    	ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event");
		ParseJson_object.setListener(this);
		navigation();// calling navigation

		
         
    }
	 
	

	@Override
	public void methodToCallback(String print) {
		float[] data_map=new float[100];//defing array 
		ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
		
	 
		
		try {
			 json = new JSONObject(print);
			 JSONArray series = json.getJSONArray("series");//taking the series 
			
			event_data = json.getJSONObject(TAG_event);
			
			 JSONObject c = event_data.getJSONObject(name);
			 		
			 	
			 	
			 	
			 	
			 	int i = 0;
			 	for (Iterator<?> keys = c.keys(); keys.hasNext(); i++) {
			 		 //key = (String)keys.next();
		             // adding each child node to HashMap key => value
		             	key=series.getString(i);// taking the key value from the series 
		            HashMap<String, String> map = new HashMap<String, String>();
		            
		            String mkey=key;
		            String mvalue= (String) c.getString(key);
		            
		            //map.put(mkey, mvalue); 
		            map.put(KEY1, mkey);// all dates
		            
		            map.put(VALUE1, mvalue);// all values
		            Log.i("key",mkey);
		            Log.i("value",mvalue);
		            float k =Float.parseFloat(mvalue);//converting the string into float
		            
		            data_map[i]=k;//storing values into data_maps which will be used in graphs
		            Log.i("k",k+"");
		            // data_map[0]=Integer.parseInt(mvalue);// storing all the values
				    // adding HashList to ArrayList
				    Event_list.add(map);
		            	if( c.get(key) instanceof JSONObject ){
		            	 
		            	} 
			 	}
			 	
		       
		        
		       
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 
		
        setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

/**
* Updating parsed JSON data into ListView
* */
		ListAdapter adapter = new SimpleAdapter(this, Event_list,  R.layout.top_event_click_list,
				new String[] {VALUE1,KEY1}, new int[] {
		             R.id.e_name_amount,R.id.e_date});
 
			setListAdapter(adapter);
	 
		
	
// selecting single ListView item
ListView lv = getListView();

// Launching new screen on Selecting Single ListItem
lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    // getting values from selected ListItem
//		    
//		    String amount1 = ((TextView) view.findViewById(R.id.E_amount)).getText().toString();
//		    
//		    // Starting new intent
//		    Intent in = new Intent(getApplicationContext(), Top_event_click.class);
//		    
//		    in.putExtra(key, amount1);
//		    
//		    
//		    startActivity(in);
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
			protected void onResume() {// setting defult values
				 getSupportActionBar().setSelectedNavigationItem(2);
				super.onResume();
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
	
	// this is the option
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 
		return true;
		
	}

	 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
		
		 
		
		default:
			return false;
			
			
		}
	}	
	
 	  
}
