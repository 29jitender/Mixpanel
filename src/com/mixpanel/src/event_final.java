package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

public class event_final extends SherlockListActivity implements Callback,ActionBar.OnNavigationListener {
    /** Called when the activity is first created. */
	 
	 private static String TAG_event = "values";
	 private static String  key = "";
	
	 private static String KEY1= "temp";
	 private static String VALUE1= "temp1";
	  
	// private static final String[] series={};   
	    
	 
	 JSONObject event_data = null;
	 static JSONObject json = null;
	public static String name="";  // defining event name
	 
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_final_view);
    
        name = Event_activity.click_type;
         // Displaying all values on the screen
        
        TextView lblName1 = (TextView) findViewById(R.id.event_activity_name);
        lblName1.setText(name);
    	ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event1");
		ParseJson_object.setListener(this);
		navigation();
         
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
		             	key=series.getString(i);// taking the key value from the serioues 
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

		/**
		* Updating parsed JSON data into graphs
		* */
		
		
		
		Line graph = new Line();
		LinePoint p = new LinePoint();
		int range= Integer.parseInt(All_api_define.interval1);//converting into float this is the inteval
		 for(int i=1;i<=range;i++){
			p = new LinePoint();
			p.setX(i);
			p.setY(data_map[i-1]);
			Log.i("data_map",data_map[i-1]+"");
			graph.addPoint(p);
		}
		
		 float rangeY=0;//getting the range for y-axis
		 for(int i= 0;i<data_map.length;i++){
			 if(data_map[i]>rangeY){
				 rangeY=data_map[i];
			 }
			 
		 }
		 
		 
		graph.setColor(Color.parseColor("#FFBB33"));

		LineGraph li = (LineGraph)findViewById(R.id.graph);
		li.addLine(graph);
		li.setRangeY(0, rangeY);
		li.setLineToFill(2);//change filling
		
//		li.setOnPointClickedListener(new OnPointClickedListener(){
//
//			@Override
//			public void onClick(int lineIndex, int pointIndex) {
// 				//Toast.makeText(getApplicationContext(), ""+lineIndex, Toast.LENGTH_LONG).show();
//			}
//			
//		});
		
		
		
		
		
		

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
	
    //creating the buttons for the period and blah
	  
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        //Used to put dark icons on light action bar
	        boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
   
	        menu.add(Menu.NONE, R.id.refresh, Menu.NONE, R.string.refresh)
            .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	

	        return true;
	    }
	  
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        //This uses the imported MenuItem from ActionBarSherlock
		     
		  		switch(item.getItemId()){
		  		 
		  			case R.id.refresh:
		  				 Intent myIntent = new Intent(this ,event_final.class);//refreshing
		  				startActivity(myIntent);
		  				finish();

						return true;	
					default:
						return false;	
		  						
		  		}
		  	
	        
	       
	    }

	
	 
}