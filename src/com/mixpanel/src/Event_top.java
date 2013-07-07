package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;

import net.simonvt.menudrawer.MenuDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class Event_top extends SherlockListActivity implements Callback ,OnSharedPreferenceChangeListener,View.OnClickListener {
	static JSONObject json = null;
	 SharedPreferences prefs;
	    public Boolean internt_count=null;// to check the connectvity

	 private static final String TAG_event = "events";
	private static final String amount = "amount";
	private static final String percent_change = "percent_change";
	private static final String event = "event";
	    public static String click_type="";
	    
		 
		 public static String limit="";

	    JSONArray event_data = null;
		 //navigation drawer variables
        private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
        private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
        private MenuDrawer mMenuDrawer; 
        private int mActiveViewId;
        //navigation
	    
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

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
           getActionBar().setDisplayHomeAsUpEnabled(true);
       }

      // mContentTextView = (TextView) findViewById(R.id.contentText);

       findViewById(R.id.item1).setOnClickListener(this);
       findViewById(R.id.item2).setOnClickListener(this);
       findViewById(R.id.item3).setOnClickListener(this);
      

       TextView activeView = (TextView) findViewById(mActiveViewId);
       if (activeView != null) {
           mMenuDrawer.setActiveView(activeView);
           //mContentTextView.setText("Active item: " + activeView.getText());
       } 
       // This will animate the drawer open and closed until the user manually drags it. Usually this would only be
       // called on first launch.
       mMenuDrawer.peekDrawer();
       //navigation
       
       if(isNetworkOnline()==true){//starting settings if internet is not working
           internt_count=true; 
           iamcallin();//calling the function to build everything

       }
            
        else if(isNetworkOnline()==false){ 
               //Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
             mMenuDrawer.setContentView(R.layout.nointernet);//giving new layout to drawer
             //setContentView(R.layout.nointernet);
            internt_count= false;
            RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout);// tap anywhere to refresh
             rlayout.setOnClickListener(new OnClickListener() {
               
               @Override
               public void onClick(View v) {
                   Intent myIntent = new Intent(Event_top.this ,Event_top.class);//refreshing
                   startActivity(myIntent);
                   finish();                   
               }
           });     
            }
		
 		 

 		
	}
	    public void iamcallin(){ 
	     	mMenuDrawer.setContentView(R.layout.event_top);//givin layout to drawer

	         setSupportProgressBarIndeterminateVisibility(true);//onload show
 
			  prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
			  prefs.registerOnSharedPreferenceChangeListener(this);
			
			 
			 
			ParseJSON ParseJson_object = new ParseJSON();
			ParseJson_object.pass_values("event_top1");
			ParseJson_object.setListener(this);
			
			get_values_pref();
	    }
	 

public void  get_values_pref(){// getting values from preference  
		prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence

		 
		String limit1 =prefs.getString("top_event", "10");
		limit=limit1.replaceAll("\\s","");//removing spaces if user entered by mistake
		final TextView textViewToChange10 = (TextView) findViewById(R.id.name_label);//printing the text as heading
		textViewToChange10.setText("Top"+" "+limit+" "+"events of the day");
		 
		int check= Integer.parseInt(limit);
		// condition of api
				//event
		if(check>100 ){
			Toast.makeText(getApplicationContext(), "The maximum Limit can be is 100", Toast.LENGTH_LONG).show();
		}
		
		
		
		 
	} 	   
	
	
	 
	
	 
   
	@Override
	public void methodToCallback(String print) {
		// TODO Auto-generated method stub
 
			// Hashmap for ListView

					ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
 
					try {
						json = new JSONObject(print);
						event_data = json.getJSONArray(TAG_event);
						 
						// looping through All Contacts
						for(int i = 0; i < event_data.length(); i++){
						    JSONObject c = event_data.getJSONObject(i);
						     
						    // Storing each json item in variable
						    String Amount = c.getString(amount);
						    String parcent_change = c.getString(percent_change);
						    String Event = c.getString(event);
						  
						 
						    // creating new HashMap
						    HashMap<String, String> map = new HashMap<String, String>();
						     
						    // adding each child node to HashMap key => value
						    map.put(amount, Amount);
						    map.put(percent_change, parcent_change);
						    map.put(event, Event);
						  
	 
						    // adding HashList to ArrayList
						    contactList.add(map);
	 
						}
	  
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	                setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

 
		/**
       * Updating parsed JSON data into ListView
       * */
					
      ListAdapter adapter = new SimpleAdapter(this, contactList,
					    R.layout.list_item,
					    new String[] { percent_change, event, amount }, new int[] {
					            R.id.p_change, R.id.e_date, R.id.E_amount });
 
      setListAdapter(adapter);
 
      // selecting single ListView item
      ListView lv = getListView();
     
      // Launching new screen on Selecting Single ListItem
      lv.setOnItemClickListener(new OnItemClickListener() {
 
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
					        int position, long id) {
					    // getting values from selected ListItem
					    String perc = ((TextView) view.findViewById(R.id.p_change)).getText().toString();
					    String name = ((TextView) view.findViewById(R.id.e_date)).getText().toString();
					    String amount1 = ((TextView) view.findViewById(R.id.E_amount)).getText().toString();
					    
					    // Starting new intent
					    Intent in = new Intent(getApplicationContext(), Event_top_click.class);
					    in.putExtra(percent_change, perc);
					    in.putExtra(event, name);
					    in.putExtra(amount, amount1);
					    
					    
					    startActivity(in);
					}
      });
				 
		 		
		
		
	}
	@Override
    protected void onResume() {

          
             
           if(internt_count==false){//starting settings if internet is not working
//              Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
               mMenuDrawer.setContentView(R.layout.nointernet);//giving new layout to drawer
               internt_count= false;
               RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout);
               rlayout.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Event_top.this ,Event_top.class);//refreshing
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
        
        menu.add(Menu.NONE, R.id.event_top_setting, Menu.NONE, R.string.event_top_setting)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
       //  getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);

        return true;
    }
    

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		
		case R.id.event_top_setting:
			//startService(intentUpdater);
			 
			startActivity(new Intent(this, Prefrenceactivity_event_top.class));
			 
			return true;
		 
		case R.id.refresh:
			 Intent myIntent = new Intent(this ,Event_top.class);//refreshing
				startActivity(myIntent);
				finish();
			return true;	
		case android.R.id.home: //on pressing home
            mMenuDrawer.toggleMenu();
            return true;	
		default:
			return false;
			
			
		}
	}


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

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) { // for the click view
    	 if(((TextView) v).getText().equals("Home")){
    		 mMenuDrawer.setActiveView(v);
    		  mMenuDrawer.closeMenu();
              startActivity(new Intent(this, Home.class));    		  
    	 }
    	 else if(((TextView) v).getText().equals("Event"))
    	 { mMenuDrawer.setActiveView(v);
		  mMenuDrawer.closeMenu();
          startActivity(new Intent(this, Event_activity.class));    
    		 
    	 }
    	 else if(((TextView) v).getText().equals("Event Top"))
    	 { mMenuDrawer.setActiveView(v);
		  mMenuDrawer.closeMenu();
          startActivity(new Intent(this, Event_top.class));    
    		 
    	 }
      
         
      
        mActiveViewId = v.getId();
    }
//navigaiton ending

 

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		get_values_pref();
		
	}

  
	
}
