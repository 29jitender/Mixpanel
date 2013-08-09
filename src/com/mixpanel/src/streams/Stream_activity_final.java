package com.mixpanel.src.streams;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.mixpanel.src.HomeFragment;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;
import com.mixpanel.src.funnel.Funnel_pref;
import com.mixpanel.src.live.Customeadapter_simpleadapter;



public class Stream_activity_final extends SherlockListActivity implements Callback ,View.OnClickListener {
	public static ArrayList<HashMap<String, String>> stream_list_page;
	public static ArrayList<HashMap<String, String>> stream_list_event;
	public static ListAdapter adapter_event;
	public static ListAdapter adapter_page;
	int adapter_type=0;
	 int more_count=0;
 	 public Boolean internt_count=null;// to check the connectvity
 	public static ListView lv;
 	     private int anmi=0;
	     View footerView;
	     public static ProgressDialog dialog ;
	    		        
	     
	     
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
					mTitleTextView.setText(All_api_define.stream_username);
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
						  JSONArray array2= obj1.getJSONArray("history");

						
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
			        		
			        	 String timediff;
								 try {
									 Date date7=formatter.parse(last_seen);
									 String  now=formatter.format(date);
					        		  String getting=formatter.format(date7);
					        		  Date date1 = formatter.parse(now);
					        		  Date date2 = formatter.parse(getting);
					        		  long diff = date1.getTime() - date2.getTime();
					        		  diff=diff/1000;
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
											 timediff=day +" D ago";
										 }
										  
										 
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
											 
											 if(obj3.has("platform")){
												 String platform = obj3.getString("platform");											 
												 map.put("platform", platform);
											 }
											 else{
												 String platform = "N/A";											 
												 
												 map.put("platform", platform);
												 
											 }
											 
											 
											 if(obj3.has("browser")){
												 String browser = obj3.getString("browser");											 
												 map.put("browser", browser);
											 }
											 else{
												 String browser = "N/A";											 
												 
												 map.put("browser", browser);
												 
											 }
											 
											 	
  
								 }
								 
								 
								 ///////////////////////
								
								
								
								stream_list_page.add(map);							
								
							}
							else{// if it is a defined event
								String event_name = obj2.getString("event");
								String event_last_seen=obj2.getString("ts");
 								map.put("event_name", event_name);
								
								
								
								
					//////////////
													///caluclation difference in time
												 	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
													formatter.setTimeZone(TimeZone.getTimeZone("GMT"));////////setting utc time zone
									        		  Date date = new Date(); 
									        			String timediff;

								        	 
													 try {
														 Date date7=formatter.parse(event_last_seen);
														 String  now=formatter.format(date);
										        		  String getting=formatter.format(date7);
										        		  Date date1 = formatter.parse(now);
										        		  Date date2 = formatter.parse(getting);
										        		  long diff = date1.getTime() - date2.getTime();
										        		  diff=diff/1000;
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
																 timediff=day +" D ago";
															 }
															  
															 
																map.put("event_last_seen", timediff);

										        		  
													} catch (ParseException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													 
													
													 
							 
								stream_list_event.add(map);							

								
							}
							
						}
 
						
						
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
					  
					  
					  
	                setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon
	                ///////////////writing platform
	                
	                RelativeLayout temp =(RelativeLayout) findViewById(R.id.stream_final_rel);
	                temp.setVisibility(View.VISIBLE);//showing layout after loading
	                TextView platform= (TextView) findViewById(R.id.platform);
	                TextView browser= (TextView) findViewById(R.id.browser);
	                
	                try {
						HashMap<String, String>  map = stream_list_page.get(0);
						 
							 platform.setText(map.get("platform")); 
							 browser.setText(map.get("browser"));
					} catch (Exception e) {
						platform.setText("N/A"); 
						 browser.setText("N/A");
						 e.printStackTrace();
					}
  
		/**
       * Updating parsed JSON data into ListView
       * */
					 
        adapter_page = new Customeadapter_simpleadapter(this, stream_list_page,
					    R.layout.stream_list_finalpage,
					    new String[] { "last_seen","page","referrer"}, new int[] {
					             R.id.stream_first_time,R.id.stream_first_view,R.id.stream_first_came_from });
        adapter_event = new Customeadapter_simpleadapter(this, stream_list_event,
			    R.layout.stream_event_list,
			    new String[] {  "event_last_seen","event_name"}, new int[] {
			            R.id.stream_event_seen,R.id.stream_event_name  });
  
      setListAdapter(adapter_page);///////////defult
      
      lv =getListView();
      
        footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_more, null, false);
  
        lv.addFooterView(footerView);
      adapter_type=10;
      
      setListAdapter(adapter_page);///////////defult

  
      //////changing list on button click/////////////////
      Button page =(Button) findViewById(R.id.stream_page);
      Button event =(Button) findViewById(R.id.stream_event);
      page.setOnClickListener(new View.OnClickListener() {
    	    @Override
    	    public void onClick(View v) {
    	        adapter_type=10;
      	        setListAdapter(adapter_page);///////////defult

     	    }
    	});
      
      event.setOnClickListener(new View.OnClickListener() {
    	    @Override
    	    public void onClick(View v) {
    	        adapter_type=20;

     	        setListAdapter(adapter_event);///////////defult

     	    }
    	});
 
      page.performClick();

		 		/////////////////////////////
      /////////////list update button////////////////////
      dialog=new ProgressDialog(Stream_activity_final.this);	
      
      footerView.setOnClickListener(new View.OnClickListener() {
  	    @Override
  	    public void onClick(View v) {
  	    	 dialog.setMessage("Getting your data... Please wait...");
  	         dialog.show();
   	    	more_count= more_count+1;
  	    	All_api_define.stream_user_update_page=Integer.toString(more_count);//assing value to all api deifne
		    All_api_define.stream_user_update();//callin it onece
		    //setting the type of adapter
		    Stream_activity_list_update.type=adapter_type;
		    
  	    	Stream_activity_list_update objectrefresh = new Stream_activity_list_update();
  	    	objectrefresh.thecall();
  	    	 
    	    	
   	    }
  	});
    

      
      
 		
		
	}
	
//	public   void list_update(){
// 	    	((SimpleAdapter)getListView().getAdapter()).notifyDataSetChanged();
//
//
//	}
//	
	
	////////////////////
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
 