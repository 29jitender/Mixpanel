package com.mixpanel.src.streams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mixpanel.src.Callback;
import com.mixpanel.src.ParseJSON;

public class Stream_activity_list_update implements Callback{
	public static int type=0;
	  ArrayList<HashMap<String, String>> stream_list_page_update;
	 ArrayList<HashMap<String, String>> stream_list_event_update;
	public   void thecall(){
		
		ParseJSON ParseJson_object = new ParseJSON();
		  ParseJson_object.pass_values("stream_update");
		  ParseJson_object.setListener(this);
		
	}

	@Override
	public void methodToCallback(String response) {
		  stream_list_page_update = new ArrayList<HashMap<String, String>>();
		  stream_list_event_update = new ArrayList<HashMap<String, String>>();
		  try {
			 JSONObject obj= new JSONObject(response);
 
			JSONArray array2 = obj.getJSONArray("history");
			
			for(int i=0;i<array2.length();i++){
				 HashMap<String, String>  map = new HashMap<String, String>();

				JSONObject obj2 =array2.getJSONObject(i);
				
				String name=obj2.getString("event");
				if(name.equals("mp_page_view")){//if it is a page view
					map.put("name","page view");
					///last seen/////////////////////////////
					String last_seen = obj2.getString("ts");
					
					
					
					//////////////
					///caluclation difference in time
				 	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					formatter.setTimeZone(TimeZone.getTimeZone("GMT"));////////setting utc time zone
	        		  Date date = new Date(); 
        		
        	 
					 try {
						 Date date7=formatter.parse(last_seen);
						 String  now=formatter.format(date);
		        		  String getting=formatter.format(date7);
		        		  Date date1 = formatter.parse(now);
		        		  Date date2 = formatter.parse(getting);
		        		  long diff = date1.getTime() - date2.getTime();
		        		  
		        		  double diffInHours = diff / ((double) 1000 * 60 * 60);
			        		  double diffinmin=(diffInHours - (int)diffInHours)*60;
			        		 BigDecimal bd = new BigDecimal(diffinmin).setScale(1, RoundingMode.HALF_EVEN);
			        		diffinmin = bd.doubleValue();
			        		String timediff;
			        		  timediff= (int)diffInHours+ "Hours "  +  diffinmin + "Minutes "+" ago";
								map.put("last_seen", timediff);
		        		  
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					 
				 	
				 	
				 	////
			
				 	////
					 ///getting notes
					 if(obj2.has("properties")){

						  JSONObject obj3 =obj2.getJSONObject("properties");
					         //Toast.makeText(Stream_activity_final.this, "Date picked: "+obj3 , Toast.LENGTH_SHORT).show();

							 
								 if(obj3.has("referrer")){
									 String referrer = obj3.getString("referrer");
									 map.put("referrer", referrer);
								 }
								 else{
									 String referrer = "N/A";
									 map.put("referrer", referrer);
								 }
								 if(obj3.has("page")){
									 String page = obj3.getString("page");											 
									 map.put("page", page);
								 }
								 else{
									 String page = "N/A";											 
									 
									 map.put("page", page);
									 
								 }
								 	

					 }
					 
					 
					 ///////////////////////
					
					
					
					stream_list_page_update.add(map);							
					
				}
				else{// if it is a defined event
					String event_name = obj2.getString("event");
					String event_last_seen=obj2.getString("ts");
					Log.i("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeea1111",event_name);
					map.put("event_name", event_name);
					
					
					
					
		//////////////
										///caluclation difference in time
									 	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										formatter.setTimeZone(TimeZone.getTimeZone("GMT"));////////setting utc time zone
						        		  Date date = new Date(); 
					        		
					        	 
										 try {
											 Date date7=formatter.parse(event_last_seen);
											 String  now=formatter.format(date);
							        		  String getting=formatter.format(date7);
							        		  Date date1 = formatter.parse(now);
							        		  Date date2 = formatter.parse(getting);
							        		  long diff = date1.getTime() - date2.getTime();
							        		  
							        		  double diffInHours = diff / ((double) 1000 * 60 * 60);
								        		  double diffinmin=(diffInHours - (int)diffInHours)*60;
								        		 BigDecimal bd = new BigDecimal(diffinmin).setScale(1, RoundingMode.HALF_EVEN);
								        		diffinmin = bd.doubleValue();
								        		String timediff;

								        		  timediff= (int)diffInHours+ "Hours "  +  diffinmin + "Minutes "+" ago";
		 	 										map.put("event_last_seen", timediff);

							        		  
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										 
										 
									 	
									 	
									 	////
		 					
									 	////
					
					
					
					
					
					
					
					
					stream_list_event_update.add(map);							

					
				}
				
			}
 
			
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  ///////updateing array list of stream final
		  for(int i=0;i<stream_list_event_update.size();i++){
				 HashMap<String, String>  map = new HashMap<String, String>();
				 map= stream_list_event_update.get(i);			 
				 
			  Stream_activity_final.stream_list_event.add(map);  
			  
		  }
		  for(int i=0;i<stream_list_page_update.size();i++){
				 HashMap<String, String>  map = new HashMap<String, String>();
				 map= stream_list_page_update.get(i);				 
			   Stream_activity_final.stream_list_page.add(map);  
			  

		  }
		  Log.i("rrefrewhshs",type+"");
		  	Stream_activity_final obj = new Stream_activity_final();
		  	if(type==10){
			  	((BaseAdapter) obj.adapter_page).notifyDataSetChanged();

		  	}
		  	else if (type==20){
			  	((BaseAdapter) obj.adapter_event).notifyDataSetChanged();

		  	}
		  	obj.dialog.dismiss();
 		  
	}
}
