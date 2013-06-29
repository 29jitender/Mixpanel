package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

public class event_final extends ListActivity implements Callback {
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
        
        
        
        
    }
        
    

	@Override
	public void methodToCallback(String print) {
		int[] data_map=new int[100];//defing array 
		ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
		
	 
		
		try {
			   
			
			json = new JSONObject(print);
 
			
			event_data = json.getJSONObject(TAG_event);
			 JSONObject c = event_data.getJSONObject(name);
			
			 	
			 	
			 	int i = 0;
			 	for (Iterator<?> keys = c.keys(); keys.hasNext(); i++) {
			 		 key = (String)keys.next();
		             // adding each child node to HashMap key => value
		             	
		            HashMap<String, String> map = new HashMap<String, String>();
		            
		            String mkey=key;
		            String mvalue= (String) c.getString(key);
		            
		            //map.put(mkey, mvalue); 
		            map.put(KEY1, mkey);// all dates
		            
		            map.put(VALUE1, mvalue);// all values
		            Log.i("key",mkey);
		            Log.i("value",mvalue);
		            int k =Integer.parseInt(mvalue);//converting the string into int
		            
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
		int range= Integer.parseInt(All_api_define.interval1);//converting into int this is the inteval
		 for(int i=1;i<=range;i++){
			p = new LinePoint();
			p.setX(i);
			p.setY(data_map[i-1]);
			Log.i("data_map",data_map[i-1]+"");
			graph.addPoint(p);
		}
		
		 
		 
		graph.setColor(Color.parseColor("#FFBB33"));

		LineGraph li = (LineGraph)findViewById(R.id.graph);
		li.addLine(graph);
		li.setRangeY(0, range);
		li.setLineToFill(2);//change filling
		
		
		
		
		
		
		
		

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