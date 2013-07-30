package com.mixpanel.src.funnel;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;

public class Funnal_final  extends SherlockActivity implements   com.mixpanel.src.Callback  {
 	 ArrayList<HashMap<String, String>> Event_list;
 	ArrayList<String> event_name;
 	ArrayList<String> event_value;
	 HashMap<String, String> map ;
	 public   JSONArray jarray2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("funnels");
		ParseJson_object.setListener(this);
 
	}



	@Override
	public void methodToCallback(String response) {

	 		 map = new HashMap<String, String>();
	 		 Event_list = new ArrayList<HashMap<String, String>>();
	 		event_name = new ArrayList<String>();
	 		event_value = new ArrayList<String>();

 			try {
 				JSONObject obj1=new JSONObject(response);
 				JSONObject obj2=obj1.getJSONObject("meta");
 				JSONArray jarray1= obj2.getJSONArray("dates");
 				String date_json=jarray1.getString(0);///this is the date
 				JSONObject obj3=obj1.getJSONObject("data");
 				JSONObject obj4=obj3.getJSONObject(date_json);
  				jarray2=obj4.getJSONArray("steps");

 				
 				for(int i=0;i<jarray2.length();i++){
					String count=null;
					String event=null;
					JSONObject obj5 = jarray2.getJSONObject(i);
					count=obj5.getString("count");
					event=obj5.getString("event");
					event_name.add(event);
					event_value.add(count); 
					
				}
					 
 				 
 				  Intent myIntent = new Intent(Funnal_final.this ,Funnel_bar_graph.class);//starting funnel bar after getting values
  				  myIntent.putExtra("event_value", event_value);
  				  myIntent.putExtra("event_name", event_name);


                  startActivity(myIntent);
                  finish();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
 				
 			
	}

	
}
