package com.mixpanel.src;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Home_graph_call implements Callback{
	JSONArray event_data = null;
	static JSONObject json = null;
	 private static final String TAG_event = "events";
	 public static String[] e_name=new String[5];
	 
	 public void tocall(){
		 
		 	ParseJSON ParseJson_object = new ParseJSON();
			ParseJson_object.pass_values("event_top");
			ParseJson_object.setListener(this);
	 }
	 
	@Override
	public void methodToCallback(String print) {
		 		 try {
						json = new JSONObject(print);  
						event_data = json.getJSONArray(TAG_event);
						
						// looping through All data
						for(int i = 0; i < event_data.length(); i++){
						    JSONObject c = event_data.getJSONObject(i);		     
						    // Storing each json item in variable
						    String Event = c.getString("event");						    
						    e_name[i]=  Event;//name of event 
							 Log.i("in home graph call",e_name[i]);

						}					
						All_api_define.event_name_array=e_name;//sending this to all api define
						All_api_define.event_top_value();
						 
						 	 
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					}

}


}
