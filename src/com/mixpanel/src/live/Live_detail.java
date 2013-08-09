package com.mixpanel.src.live;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.R;

public class Live_detail extends SherlockListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent in = getIntent();
		String data=in.getStringExtra("object");
		String event_name=in.getStringExtra("event_name");
		 ////////////////////////////////////////////////////
	       // Action bar
	         ActionBar mActionBar;
	       LayoutInflater mInflater;
	       View mCustomView;
	        TextView mTitleTextView;
	       mActionBar = getSupportActionBar();
	       mActionBar.setDisplayShowHomeEnabled(false);
	       mActionBar.setDisplayShowTitleEnabled(false);
	       mInflater = LayoutInflater.from(this);
	       mCustomView = mInflater.inflate(R.layout.final_menu, null);
	       mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
	       mTitleTextView.setText(event_name);
	       mTitleTextView.setTextSize(20);

	       mActionBar.setCustomView(mCustomView);
	       mActionBar.setDisplayShowCustomEnabled(true);
	       // mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_header_bg));
	       TextView ibItem1 = (TextView)  findViewById(R.id.arrow);
	       ibItem1.setOnClickListener(new View.OnClickListener() {
	           @Override
	           public void onClick(View view) {
	        	   finish();
	           }
	       });
	       
	       /////////////////////////////////////////////
	  	 //navigation
	       
	     
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	        	//getActionBar().setDisplayHomeAsUpEnabled(true);
	            getSupportActionBar().setIcon(android.R.color.transparent);//to remove the icon from action bar

	            // this is for the color of title bar
	             ColorDrawable colorDrawable = new ColorDrawable();
 	            	 colorDrawable.setColor(Color.parseColor("#3BB0AA"));
	             
	            android.app.ActionBar actionBar = getActionBar();
	            actionBar.setBackgroundDrawable(colorDrawable);

	        }

		setContentView(R.layout.live_detail_main);
	
 		ArrayList<HashMap<String, String>> live_list = new ArrayList<HashMap<String, String>>();
		String timediff;

		
		/////////////sorting
		 
 		try {
 			JSONObject obj1=new JSONObject(data);

			JSONObject obj2 = obj1.getJSONObject("properties");
		
		Iterator<?> keys = obj2.keys();		

        while( keys.hasNext() ){
            String key = (String)keys.next();
   		 HashMap<String, String>  map = new HashMap<String, String>();
   		 
   		 if(key.equals("time")){
				 String time=obj1.getString("$ts");//time in seconds	  
   		///caluclation difference in time
 				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					cal.set(Calendar.SECOND, 0);
					long timenow = cal.getTimeInMillis();					 
						try {
						long recivetime= (long) Double.parseDouble(time);					 
						long diff = (timenow-recivetime)/1000; 
 
						 int day = (int)TimeUnit.SECONDS.toDays(diff);        
						 long hours = TimeUnit.SECONDS.toHours(diff) - (day *24);
						 long minute = TimeUnit.SECONDS.toMinutes(diff) - (TimeUnit.SECONDS.toHours(diff)* 60);
						 long second = TimeUnit.SECONDS.toSeconds(diff) - (TimeUnit.SECONDS.toMinutes(diff) *60);
						 
						 if(day==0){
							 if(hours==0){
								 if(minute==0){
									 timediff=second +" Second ago";

								 }
								 else{
									 timediff=minute +" Minute ago";

								 }
								 
								 
							 }
							 else{
								 timediff=hours +" Hour ago";

							 }
							 
							 
							 
						 }
						 else{
							 timediff=day +" Day ago";
						 }
						  
						 
							map.put("value", timediff);
						 } catch (NumberFormatException e) {
							 	// TODO Auto-generated catch block
								e.printStackTrace();
							 	}
	 						
   			 
   			 
   		 }
   		 else{
   	         map.put("value", obj2.getString(key));				            

   		 }
   		 

            //key = key.replace("$", "");
            //////////for different keys
         	if(key.equals("$region")){
         		key="Region";         		
         	}
         	if(key.equals("$name")){
         		key="Name";         		
         	}
         	if(key.equals("$initial_referring_domain")){
         		key="Initial Referring Domain";         		
         	}
         	if(key.equals("$email")){
         		key="Email";         		
         	}
         	if(key.equals("$initial_referrer")){
         		key="Intial Referrer";         		
         	}
         	if(key.equals("$search_engine")){
         		key="Search Engine";         		
         	}
         	if(key.equals("$os")){
         		key="OS";         		
         	}
         	if(key.equals("$referring_domain")){
         		key="Referring Domain";         		
         	}
         	if(key.equals("$city")){
         		key="City";         		
         	}
         	if(key.equals("$browser")){
         		key="Browser";         		
         	}
         	if(key.equals("$referrer")){
         		key="Referrer";         		
         	}
         	if(key.equals("mp_country_code")){
         		key="Country";         		
         	}
         	if(key.equals("time")){
         		key="Time";         		
         	}
         
         
            key = key.replace("$", "");
            map.put("key",key);
            
            
            live_list.add(map);
            
        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		///////////////////////////////////////////////////////
 ////////////	now updating it into list
        Collections.reverse(live_list);//reversing the order of list
	        ListAdapter adapter = new Customeadapter_simpleadapter(this, live_list,
						    R.layout.live_detail,
						    new String[] { "key","value"}, new int[] {
						            R.id.live_detail_id,R.id.live_detail });
	        
		      setListAdapter(adapter); 
		      
 		
	}

}
