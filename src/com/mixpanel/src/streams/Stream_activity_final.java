package com.mixpanel.src.streams;
 
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.mixpanel.src.Callback;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;



public class Stream_activity_final extends SherlockListActivity implements Callback ,View.OnClickListener {
	ArrayList<HashMap<String, String>> stream_list_page;
	ArrayList<HashMap<String, String>> stream_list_event;
	ListAdapter adapter_event;
	ListAdapter adapter_page;
	 
 	 public Boolean internt_count=null;// to check the connectvity

	     private int anmi=0;
	    
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
       mCustomView = mInflater.inflate(R.layout.menu, null);
       mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
       mTitleTextView.setText("Stream final");
       mTitleTextView.setTextSize(20);

       mActionBar.setCustomView(mCustomView);
       mActionBar.setDisplayShowCustomEnabled(true); 
       /////////////////////////////////////////////
       
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//           getActionBar().setDisplayHomeAsUpEnabled(true);
           // this is for the color of title bar
    	   ColorDrawable colorDrawable = new ColorDrawable();
           colorDrawable.setColor(Color.parseColor("#3BB0AA"));
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
	 
					 Intent myIntent = new Intent(Stream_activity_final.this ,Stream_activity_final.class);//refreshing

                    startActivity(myIntent);
                    finish();  
	 
				}
	 
			});	     
            }
		
 		 

 		
	}
	    public void iamcallin(){ 
	       setContentView(R.layout.stream_final);//givin layout to drawer

	         setSupportProgressBarIndeterminateVisibility(true);//onload show
  
			  ParseJSON ParseJson_object = new ParseJSON();
			  ParseJson_object.pass_values("stream_user");
			  ParseJson_object.setListener(this);
			
			
	    }
	 

 
	 
	
	 
   
	@Override
	public void methodToCallback(String print) {
		 
 		 
					  stream_list_page = new ArrayList<HashMap<String, String>>();
					  stream_list_event = new ArrayList<HashMap<String, String>>();
					  try {
						  
						JSONArray array1 = new JSONArray(print);
						JSONObject obj1= array1.getJSONObject(0);
						JSONArray array2 = obj1.getJSONArray("history");
						
						for(int i=0;i<array2.length();i++){
							 HashMap<String, String>  map = new HashMap<String, String>();

							JSONObject obj2 =array2.getJSONObject(i);
							
							String name=obj2.getString("event");
							if(name.equals("mp_page_view")){//if it is a page view
								JSONObject obj3 = obj2.getJSONObject("properties");
								String referrer =obj3.getString("referrer");
								String page = obj3.getString("page");
								map.put("name","page view");
								map.put("referrer", referrer);
								map.put("page", page);
								String last_seen = obj2.getString("ts");
								map.put("last_seen", last_seen);
								stream_list_page.add(map);							
								
							}
							else{// if it is a defined event
								String event_name = obj2.getString("event");
								String event_last_seen=obj2.getString("ts");
								Log.i("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeea1111",event_name);
								map.put("event_name", event_name);
								map.put("event_last_seen", event_last_seen);
								stream_list_event.add(map);							

								
							}
							
						}
						  Log.i("finalaaaaa",array2+"");

						
						
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
					  
					  
					  
	                setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

 
		/**
       * Updating parsed JSON data into ListView
       * */
					 
        adapter_page = new SimpleAdapter(this, stream_list_page,
					    R.layout.list_stream_frist,
					    new String[] { "name","last_seen","page","referrer"}, new int[] {
					            R.id.stream_user_name,R.id.stream_first_time,R.id.stream_first_view,R.id.stream_first_came_from });
        adapter_event = new SimpleAdapter(this, stream_list_event,
			    R.layout.stream_event_list,
			    new String[] { "event_name","event_last_seen"}, new int[] {
			            R.id.stream_event_name,R.id.stream_event_seen  });
      setListAdapter(adapter_page);///////////defult

      //////changing list on button click/////////////////
      Button page =(Button) findViewById(R.id.stream_page);
      Button event =(Button) findViewById(R.id.stream_event);
      
      page.setOnClickListener(new View.OnClickListener() {
    	    @Override
    	    public void onClick(View v) {
    	        setListAdapter(adapter_page);///////////defult

     	    }
    	});
      
      event.setOnClickListener(new View.OnClickListener() {
    	    @Override
    	    public void onClick(View v) {
    	        setListAdapter(adapter_event);///////////defult

     	    }
    	});
 
    	 
		 		/////////////////////////////
		
		
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
  	 
  					 Intent myIntent = new Intent(Stream_activity_final.this ,Stream_activity_final.class);//refreshing

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
			 Intent myIntent = new Intent(this ,Stream_activity_final.class);//refreshing
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
 