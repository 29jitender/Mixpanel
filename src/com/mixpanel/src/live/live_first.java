package com.mixpanel.src.live;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import net.simonvt.menudrawer.MenuDrawer;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.mixpanel.src.About;
import com.mixpanel.src.Callback;
import com.mixpanel.src.Event_activity;
import com.mixpanel.src.Event_top;
import com.mixpanel.src.Home;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;
import com.mixpanel.src.funnel.Funnel_activity;
import com.mixpanel.src.people.People_first;
import com.mixpanel.src.streams.Stream_activity_first;



public class live_first extends SherlockListActivity implements Callback ,View.OnClickListener {
	public static ArrayList<HashMap<String, String>> live_list;
	 
	    SimpleAdapter adapter;
	   private Timer autoUpdate;
	   JSONArray array1;
  	 public Boolean internt_count=null;// to check the connectvity
  	private EditText search;

 	 
 
 		 //navigation drawer variables
        private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
        private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
        private MenuDrawer mMenuDrawer; 
        private int mActiveViewId;
        //navigation
	    private int anmi=0;
	    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//for progress it will be passed before layout
		
		 //navigation
       if (savedInstanceState != null) {
           mActiveViewId = savedInstanceState.getInt(STATE_ACTIVE_VIEW_ID);
       }

       mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
       mMenuDrawer.setMenuView(R.layout.menu_scrollview);// this is the layout for 
       
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
       mTitleTextView.setText("Live");
       mTitleTextView.setTextSize(20);

       mActionBar.setCustomView(mCustomView);
       mActionBar.setDisplayShowCustomEnabled(true);
       // mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_header_bg));
       ImageButton ibItem1 = (ImageButton)  findViewById(R.id.btn_slide);
       ibItem1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mMenuDrawer.toggleMenu();
           }
       });
       
       /////////////////////////////////////////////
       
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//           getActionBar().setDisplayHomeAsUpEnabled(true);
           // this is for the color of title bar
    	   ColorDrawable colorDrawable = new ColorDrawable();
    	   int myColor = this.getResources().getColor(R.color.menu4);
           colorDrawable.setColor(myColor);
           android.app.ActionBar actionBar = getActionBar();
           actionBar.setBackgroundDrawable(colorDrawable);

       }

      // mContentTextView = (TextView) findViewById(R.id.contentText);
       findViewById(R.id.item1).setOnClickListener(this);
       findViewById(R.id.item2).setOnClickListener(this);
       findViewById(R.id.item3).setOnClickListener(this);
       findViewById(R.id.item4).setOnClickListener(this);
       findViewById(R.id.item5).setOnClickListener(this);
       findViewById(R.id.item6).setOnClickListener(this);
       findViewById(R.id.item7).setOnClickListener(this);
       findViewById(R.id.item8).setOnClickListener(this);

       TextView activeView = (TextView) findViewById(mActiveViewId);
       if (activeView != null) {
           mMenuDrawer.setActiveView(activeView);
           //mContentTextView.setText("Active item: " + activeView.getText());
       } 
       
       Display display = getWindowManager().getDefaultDisplay(); 		 
 		int width = display.getWidth();  
        mMenuDrawer.setMenuSize(width/4);//size of menu
       mMenuDrawer.setDropShadow(android.R.color.transparent);//removin showdo
       //navigation
       
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
	 
					 Intent myIntent = new Intent(live_first.this ,live_first.class);//refreshing

                    startActivity(myIntent);
                    finish();  
	 
				}
	 
			});	     
            }
		
 		 

 		
	}
	    public void iamcallin(){ 
	     	mMenuDrawer.setContentView(R.layout.live);//givin layout to drawer
	         setSupportProgressBarIndeterminateVisibility(true);//onload show

   
			  
	          
			
			
	    }
	 public void listcall(){

		  ParseJSON ParseJson_object = new ParseJSON();
		  ParseJson_object.pass_values("live");
		  ParseJson_object.setListener(this);
	 }
 
	
	 
   
	@Override
	public void methodToCallback(String print) {
		String timediff;

		  live_list = new ArrayList<HashMap<String, String>>();
		
		try {
			  array1=new JSONArray(print);
			for(int i=0;i<array1.length();i++){
				JSONObject obj1=array1.getJSONObject(i);
				 HashMap<String, String>  map = new HashMap<String, String>();
				 
				 String event=obj1.getString("event");
				 map.put("name", event);
				 map.put("fulldata",obj1.toString());
				///caluclation difference in time
 				 String time=obj1.getString("$ts");//time in seconds	  
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
							  
							 
   							map.put("time", timediff);

							
						 } catch (NumberFormatException e) {
						 	// TODO Auto-generated catch block
							e.printStackTrace();
						 	}
 						
 					
 
				 	////
				 /////////adding region/////////
 						
 						
 						
 						
 						///////////////////
 						String location;
 						JSONObject obj2 = null;
						try {
							  obj2 = obj1.getJSONObject("properties");
							
							String region=obj2.getString("$region");	
							String country =obj2.getString("mp_country_code");							
							location = region+","+country;
						} catch (Exception e) {
							if(obj2.has("mp_country_code"))
							{
								location = obj2.getString("mp_country_code");						
							}
							else{
								location = "N/A";
							}
							
							e.printStackTrace();
						}
 						map.put("location", location);
/////////////////////
 						
				
				 live_list.add(map);

			}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	///////////updateing data into list
        ListAdapter temp = null;
	      setListAdapter(temp);
        Collections.reverse(live_list);//reversing the order of list
 	        adapter = new SimpleAdapter(this, live_list,
						    R.layout.list_live_first,
						    new String[] { "name","time","location","fulldata"}, new int[] {
						            R.id.live_name,R.id.live_time,R.id.live_location });
  		 setadapter();
	}
// 
	@Override
	 public void onPause() {
	  autoUpdate.cancel();
	  super.onPause();
	 } 
	
	
	public void setadapter(){
		
	      setListAdapter(adapter);///////setting adapter on refresh
	        setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

	      	final ListView lv = getListView();
	      	lv.setOnItemClickListener(new OnItemClickListener() {
	      		 
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
				        int position, long id) {
 				    
					Object o = lv.getItemAtPosition(position);
	 				HashMap<String,String> click_data=(HashMap<String, String>) o;

					
					
				    // Starting new intent
				    Intent in = new Intent(getApplicationContext(), Live_detail.class); 
 						in.putExtra("object", click_data.get("fulldata"));///because have applied sort
						in.putExtra("event_name", click_data.get("name"));
					 
				    startActivity(in);
			          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				    anmi=1;
				}
  });
	}
	
	@Override
    protected void onResume() {
	      listcall();//call it once

		autoUpdate = new Timer();
		  autoUpdate.schedule(new TimerTask() {
		   @Override
		   public void run() {
		    runOnUiThread(new Runnable() {
		     public void run() {
		      listcall();
              		      
		     }
		    });
		   }
		  }, 0, 30000); // updates each 60 secs
		  
		  
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
  	 
  					 Intent myIntent = new Intent(live_first.this ,live_first.class);//refreshing

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

    	  menu.add(Menu.NONE, R.id.search, Menu.NONE, "Search")
          .setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.ic_search)
          .setActionView(R.layout.collapsible_edittext)
          .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        return true;
    }
    

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		 
		case R.id.search: //on pressing home
			search = (EditText) item.getActionView();
            search.addTextChangedListener(filterTextWatcher);
            search.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			
			
			
			return true;    
		 
		case android.R.id.home: //on pressing home
            mMenuDrawer.toggleMenu();
            return true;	
		default:
			return false;
			
			
		}
	}
	private TextWatcher filterTextWatcher = new TextWatcher() {
	    public void afterTextChanged(Editable s) {
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    }

	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	    	
	    	// this is to search the items in list
	    	search.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
					// When user changed the Text
					live_first.this.adapter.getFilter().filter(cs);	
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub							
				}
			});
	    	
 	    }

	};

	//rest functinality for of navigation
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        mMenuDrawer.restoreState(inState.getParcelable(STATE_MENUDRAWER));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.saveState());
        outState.putInt(STATE_ACTIVE_VIEW_ID, mActiveViewId);
    }
 
    @Override
    public void onBackPressed() {
    	
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }
        else{
            this.finish();

        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) { // for the click view
    	
    	switch(v.getId()){
    	case R.id.item1:
    		 mMenuDrawer.setActiveView(v);
             // mMenuDrawer.closeMenu();
                  Intent myIntent = new Intent(live_first.this ,Home.class);//refreshing
                  myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
 
                  startActivity(myIntent);
                  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                 anmi=1;
   		break;
   	case R.id.item2:
   		 mMenuDrawer.setActiveView(v);
   		  
            startActivity(new Intent(this, Event_activity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            anmi=1;
            //overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);//calling anim
   		
   		break;
   	case R.id.item3:
   	  startActivity(new Intent(this, Event_top.class));
      overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
      anmi=1;
   		break;
   	case R.id.item4:
   		
		   	 mMenuDrawer.setActiveView(v);
		     mMenuDrawer.closeMenu();
		    // startActivity(new Intent(this, Event_top.class)); 
		     
		    		
    		
   		break;
   	case R.id.item5:
   		
  		 mMenuDrawer.setActiveView(v);
     		 // mMenuDrawer.closeMenu();
             startActivity(new Intent(this, Funnel_activity.class));
             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
             anmi=1;
  		
  		break;
   	case R.id.item6:
   		
  		 mMenuDrawer.setActiveView(v);
     		 // mMenuDrawer.closeMenu();
             startActivity(new Intent(this, Stream_activity_first.class));
             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
             anmi=1;
  		
  		break;
   	case R.id.item7:
		
  		 mMenuDrawer.setActiveView(v);
     		 // mMenuDrawer.closeMenu();
             startActivity(new Intent(this, People_first.class));
             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
             anmi=1;
  		
  		break;
   	case R.id.item8:
   		
     		 mMenuDrawer.setActiveView(v);
        		 // mMenuDrawer.closeMenu();
                startActivity(new Intent(this, About.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                anmi=1;
     		
     		break;

   	}
      
        mActiveViewId = v.getId();
    }
//navigaiton ending


 
 
  
	
}
 