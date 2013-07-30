package com.mixpanel.src.funnel;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;

public class Funnal_final extends SherlockActivity implements   com.mixpanel.src.Callback  {
 	 ArrayList<HashMap<String, String>> Event_list;
	 HashMap<String, String> map ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_funnel_final);

		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("funnels");
		ParseJson_object.setListener(this);
	}

	@Override
	public void methodToCallback(String response) {
 		 
			TextView text= (TextView) findViewById(R.id.funnel_final_text);
			 

 			try {
 				JSONObject obj1=new JSONObject(response);
 				JSONObject obj2=obj1.getJSONObject("meta");
 				JSONArray jarray1= obj2.getJSONArray("dates");
 				String date_json=jarray1.getString(0);///this is the date
 				JSONObject obj3=obj1.getJSONObject("data");
 				JSONObject obj4=obj3.getJSONObject(date_json);
  				JSONArray jarray2=obj4.getJSONArray("steps");
  		 		text.setText(jarray2+"");

 				
 				for(int i=0;i<jarray2.length();i++){
					String count=null;
					String event=null;
					JSONObject obj5 = jarray2.getJSONObject(i);
					count=obj5.getString("count");
					event=obj5.getString("event");
					Log.i("countttttttttttttttttttt",count);
					Log.i("eventttttttttttttttttttt",event);
					map.put("event", event);
					map.put(event, count);
				    Event_list.add(map);
 
					
				}
				 
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		

			
	 	

		
	}

	
}
