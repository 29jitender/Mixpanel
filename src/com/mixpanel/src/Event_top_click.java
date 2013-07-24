package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.simonvt.menudrawer.MenuDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class Event_top_click  extends SherlockListActivity implements Callback,View.OnClickListener  {
    public Boolean internt_count=null;// to check the connectvity

	// JSON node keys
	 
	 private static String TAG_event = "values";
	 private static String  key = "";
	
	 private static String KEY1= "temp";
	 private static String VALUE1= "temp1";
	  
	// private static final String[] series={};   
	    
	 
	 JSONObject event_data = null;
	 static JSONObject json = null;
	public static String name="";  // defining event name
	public static String interval="7";// defining event interval
	
	 //navigation drawer variables
    private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
    private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
    private MenuDrawer mMenuDrawer; 
    private int mActiveViewId;
    //navigation
	@Override
    public void onCreate(Bundle savedInstanceState) { 
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
       findViewById(R.id.item4).setOnClickListener(this);


       TextView activeView = (TextView) findViewById(mActiveViewId);
       if (activeView != null) {
           mMenuDrawer.setActiveView(activeView);
           //mContentTextView.setText("Active item: " + activeView.getText());
       } 
       
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
                   Intent myIntent = new Intent(Event_top_click.this ,Event_top_click.class);//refreshing
                   startActivity(myIntent);
                   finish();                   
               }
           });     
            }
		
 		 
		
         
    }
	 
	public void iamcallin(){

        getIntent().setAction("Already created");// activity already alive
    	mMenuDrawer.setContentView(R.layout.top_event_click);//givin layout to drawer 
        setSupportProgressBarIndeterminateVisibility(true);//onload show
        // getting intent data
        Intent in = getIntent();
         // Get JSON values from previous intent
        name = in.getStringExtra("event");
         // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        lblName.setText(name);
//        TextView lblName1 = (TextView) findViewById(R.id.event_duration);
//        lblName1.setText(interval);
    	ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event");
		ParseJson_object.setListener(this);
 
	}

	@Override
	public void methodToCallback(String print) {
		float[] data_map=new float[100];//defing array 
		ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
		
	 
		
		try {
			 json = new JSONObject(print);
			 JSONArray series = json.getJSONArray("series");//taking the series 
			
			event_data = json.getJSONObject(TAG_event);
			
			 JSONObject c = event_data.getJSONObject(name);
			 		
			 	
			 	
			 	
			 	
			 	int i = 0;
			 	for (Iterator<?> keys = c.keys(); keys.hasNext(); i++) {
			 		 //key = (String)keys.next();
		             // adding each child node to HashMap key => value
		             	key=series.getString(i);// taking the key value from the series 
		            HashMap<String, String> map = new HashMap<String, String>();
		            
		            String mkey=key;
		            String mvalue= (String) c.getString(key);
		            
		            //map.put(mkey, mvalue); 
		            map.put(KEY1, mkey);// all dates
		            
		            map.put(VALUE1, mvalue);// all values
		            Log.i("key",mkey);
		            Log.i("value",mvalue);
		            float k =Float.parseFloat(mvalue);//converting the string into float
		            
		            data_map[i]=k;//storing values into data_maps which will be used in graphs
		            Log.i("k",k+"");
		            // data_map[0]=Integer.parseInt(mvalue);// storing all the values
				    // adding HashList to ArrayList
				    Event_list.add(map);
		            	if( c.get(key) instanceof JSONObject ){
		            	 
		            	} 
			 	}
			 	
		       
		        
		       
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 
		
        setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

/**
* Updating parsed JSON data into ListView
* */
		ListAdapter adapter = new SimpleAdapter(this, Event_list,  R.layout.top_event_click_list,
				new String[] {VALUE1,KEY1}, new int[] {
		             R.id.e_name_amount,R.id.e_date1});
 
			setListAdapter(adapter);
	 
		
	
// selecting single ListView item
ListView lv = getListView();

// Launching new screen on Selecting Single ListItem
lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		    // getting values from selected ListItem
//		    
//		    String amount1 = ((TextView) view.findViewById(R.id.E_amount)).getText().toString();
//		    
//		    // Starting new intent
//		    Intent in = new Intent(getApplicationContext(), Top_event_click.class);
//		    
//		    in.putExtra(key, amount1);
//		    
//		    
//		    startActivity(in);
		}
});
		 
	}
	 @Override
     protected void onResume() {

           
              
            if(internt_count==false){//starting settings if internet is not working
//               Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
                mMenuDrawer.setContentView(R.layout.nointernet);//giving new layout to drawer
                internt_count= false;
                RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout);
                rlayout.setOnClickListener(new OnClickListener() {
                 
                 @Override
                 public void onClick(View v) {
                     Intent myIntent = new Intent(Event_top_click.this ,Event_top_click.class);//refreshing
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

	 
	// this is the option
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 
		return true;
		
	}

	 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
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
    		 // mMenuDrawer.closeMenu();
              startActivity(new Intent(this, Home.class));    	
              overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    	 }
    	 else if(((TextView) v).getText().equals("All Events"))
    	 { mMenuDrawer.setActiveView(v);
		 // mMenuDrawer.closeMenu();
          startActivity(new Intent(this, Event_activity.class)); 
          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    		 
    	 }
    	 else if(((TextView) v).getText().equals("Event Top"))
    	 { mMenuDrawer.setActiveView(v);
		  mMenuDrawer.closeMenu();
          startActivity(new Intent(this, Event_top.class));    
    		 
    	 }
    	 else if(((TextView) v).getText().equals("About")){
    		 mMenuDrawer.setActiveView(v);
   		  //mMenuDrawer.closeMenu();
           startActivity(new Intent(this, About.class));
           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    	 }
         
      
        mActiveViewId = v.getId();
    }
//navigaiton ending
 	  
}
