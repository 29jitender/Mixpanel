package com.mixpanel.src.people;

 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsPromptResult;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.Callback;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;
import com.mixpanel.src.live.Live_detail;


public class People_second extends SherlockListActivity implements Callback ,View.OnClickListener {
	public static ArrayList<HashMap<String, String>> people_second_list;
	SimpleAdapter adapter;
 	 public Boolean internt_count=null;// to check the connectvity
	    private int anmi=0;
	    JSONArray array1;
 	     
	    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//for progress it will be passed before layout
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
					mTitleTextView.setText(All_api_define.people_name);
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
					int myColor = this.getResources().getColor(R.color.menu6);
			           colorDrawable.setColor(myColor);					
					android.app.ActionBar actionBar = getActionBar();
					actionBar.setBackgroundDrawable(colorDrawable);
					
					}

 
      
       
       if(isNetworkOnline()==true){//starting settings if internet is not working
           internt_count=true; 
           iamcallin();//calling the function to build everything

       }
            
        else if(isNetworkOnline()==false){ 
        	 setContentView(R.layout.nointernet);//giving new layout to drawer
             //setContentView(R.layout.nointernet);
            internt_count= false;
            Button button = (Button) findViewById(R.id.nointernet_refresh);
			 
            button.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
	 
					 Intent myIntent = new Intent(People_second.this ,People_second.class);//refreshing

                    startActivity(myIntent);
                    finish();  
	 
				}
	 
			});	     
            }
		 
 		
	}
	    public void iamcallin(){ 
	         setContentView(R.layout.people_second);//givin layout to drawer

	         setSupportProgressBarIndeterminateVisibility(true);//onload show
  
			  ParseJSON ParseJson_object = new ParseJSON();
			  ParseJson_object.pass_values("people_data");
			  ParseJson_object.setListener(this);
			
			
	    }
	 
	@Override
	public void methodToCallback(String print) {
		String timediff;
		Date daterecive;
		  people_second_list = new ArrayList<HashMap<String, String>>();

		try {
			JSONObject obj1 = new JSONObject(print);
			JSONObject objresult= obj1.getJSONObject("results");
			  array1= objresult.getJSONArray("events");
			
			for(int i=0;i<array1.length();i++){
				 HashMap<String, String>  map = new HashMap<String, String>();

				JSONObject obj2=array1.getJSONObject(i);
				
				String event=obj2.getString("event");///event
				map.put("eventname", event);
				
				///time
				
				JSONObject obj3 =obj2.getJSONObject("properties");
				
				
				
				String time =obj3.getString("time");
 					
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				cal.set(Calendar.SECOND, 0);
				long timenow = cal.getTimeInMillis();
				 
					try {
					long recivetime= (long) Double.parseDouble(time);					 
					  Timestamp stamp = new Timestamp(recivetime*1000);
					    daterecive = new Date(stamp.getTime());


					long diff = (timenow/1000)-recivetime; 
					
					 int day = (int)TimeUnit.SECONDS.toDays(diff);        
					 long hours = TimeUnit.SECONDS.toHours(diff) - (day *24);
					 long minute = TimeUnit.SECONDS.toMinutes(diff) - (TimeUnit.SECONDS.toHours(diff)* 60);
					 long second = TimeUnit.SECONDS.toSeconds(diff) - (TimeUnit.SECONDS.toMinutes(diff) *60);
					 
					 if(day==0){
						 if(hours==0){
							 if(minute==0){
								 timediff=second +" S ago";

							 }
							 else{
								 timediff=minute +" M ago";

							 }
							 
							 
						 }
						 else{
							 timediff=hours +" H ago";

						 }
						 
						 
						 
					 }
					 else{
						 if(day>3){
							 timediff=daterecive+"";
						 }
						 else{
							 timediff=day +" D ago";
						 }
						 
					 }
					  
					 
						map.put("time", timediff);

					
				 } catch (NumberFormatException e) {
				 	// TODO Auto-generated catch block
					e.printStackTrace();
				 	}
					
				

		 	//// 
				
				
				
 				
				people_second_list.add(map);
				
				
			}
			
		 	
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 
 		///adding data into list
		
        setSupportProgressBarIndeterminateVisibility(false);//onload show
        Collections.reverse(people_second_list);//reversing the order of list

		 adapter = new SimpleAdapter(this, people_second_list,
				    R.layout.stream_event_list,
				    new String[] {  "time","eventname"}, new int[] {
				            R.id.stream_event_seen,R.id.stream_event_name  });
		  	setListAdapter(adapter);

		  	
		  	
		  	ListView lv = getListView();
	      	lv.setOnItemClickListener(new OnItemClickListener() {
	      		 
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
				        int position, long id) {
				    // getting values from selected ListItem
				    
				     
				    // Starting new intent
				    Intent in = new Intent(getApplicationContext(), People_detail.class); 
				    try {
						in.putExtra("object", array1.getJSONObject(array1.length()-position-1).toString());///because have applied sort
						in.putExtra("event_name", array1.getJSONObject(array1.length()-position-1).getString("event"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    startActivity(in);
			          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				    anmi=1;
				}
  });
		  	
		  	
		  	
		  	
		  	
		  	
		  	
		  	
		  	
	}
 

 
	@Override
	public void onBackPressed() {
	   this.finish();
	}
	@Override
    protected void onResume() {

		if(anmi==1){
			   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}
			else if(anmi==2){
				
			    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
			}
             
           if(internt_count==false){//starting settings if internet is not working
        	   setContentView(R.layout.nointernet);//giving new layout to drawer
               //setContentView(R.layout.nointernet);
              internt_count= false;
              Button button = (Button) findViewById(R.id.nointernet_refresh);
  			 
              button.setOnClickListener(new OnClickListener() {
  	 
  				@Override
  				public void onClick(View arg0) {
  	 
  					 Intent myIntent = new Intent(People_second.this ,People_second.class);//refreshing

                      startActivity(myIntent);
                      finish();  
  	 
  				}
  	 
  			});	
             

            }
            
         super.onResume();
    }

 public boolean isNetworkOnline() {
     boolean status=false;
     try{
         ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo netInfo = cm.getNetworkInfo(0);
         if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
             status= true;
         }else {
             netInfo = cm.getNetworkInfo(1);
             if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                 status= true;
         }
     }catch(Exception e){
         e.printStackTrace();  
         return false;
     }
     return status;

     }  


	  
	 
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
		
		switch (item.getItemId()){
		 
		 
		case R.id.refresh:
			 Intent myIntent = new Intent(this ,People_second.class);//refreshing
			  overridePendingTransition(0, 0);
	            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	             finish();
	            overridePendingTransition(0, 0);

	               startActivity(myIntent);
			return true;	
		 
 			
			
		}
		return internt_count;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}


	 
 
 
  
	
}

