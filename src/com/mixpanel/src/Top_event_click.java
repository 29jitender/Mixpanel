package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
 

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Top_event_click  extends ListActivity implements Callback  {
	
	// JSON node keys
	 
	 private static String TAG_event = "values";
	 private static String  key = "";
	 private static String[] key_print = new String[100];
	 private static String KEY1= "temp";
	 private static String VALUE1= "temp1";
	 private static String flag=null;
	 
	// private static final String[] series={};   
	    
	 
	 JSONObject event_data = null;
	 static JSONObject json = null;
	public static String name="";  // defining event name
	public static String interval="10";// defining event interval
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         flag="1";
         getIntent().setAction("Already created");// activity already alive
        setContentView(R.layout.top_event_click);
        
        // getting intent data
        Intent in = getIntent();
         // Get JSON values from previous intent
        name = in.getStringExtra("event");
         // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        lblName.setText(name);
    	ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event");
		ParseJson_object.setListener(this);
		
         
    }
	 
	
	
	
	@Override
	public void methodToCallback(String print) {
			  
		ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();

		try {
			   
			
			json = new JSONObject(print);
 
			
			event_data = json.getJSONObject(TAG_event);
			 JSONObject c = event_data.getJSONObject(name);
			
			 Iterator<?> keys = c.keys();

		        while( keys.hasNext() ){
		            key = (String)keys.next();
		           
		            // adding each child node to HashMap key => value
		             	
		            HashMap<String, String> map = new HashMap<String, String>();
		            String mkey=key;
		            String mvalue= (String) c.getString(key);
		            
		            map.put(mkey, mvalue); 
		            map.put(KEY1, mkey);
		            map.put(VALUE1, mvalue);
		            Log.i("key",mkey);
		            Log.i("value",mvalue);
				    // adding HashList to ArrayList
				    Event_list.add(map);
		            	if( c.get(key) instanceof JSONObject ){
		            	 
		            	}
		            
		            }
		        
		       
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

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
	  
}
