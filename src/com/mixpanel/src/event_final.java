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
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.actionbarsherlock.view.Window;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

public class event_final extends SherlockListActivity implements Callback,OnSharedPreferenceChangeListener,View.OnClickListener {
 	 SharedPreferences prefs;
     public Boolean internt_count=null;// to check the connectvity

	 private static String TAG_event = "values";
	 private static String  key = "";	
	 private static String KEY1= "temp";
	 private static String VALUE1= "temp1";
	 public static String event_interval="";//global 
	 public static String event_unit="";
	 public static String event_type="";		 
	 JSONObject event_data = null;
	 static JSONObject json = null;
	public static String name="";  // defining event name
	public JSONArray series= null;
	 //navigation drawer variables
    private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
    private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
    private MenuDrawer mMenuDrawer; 
    private int mActiveViewId;
    private int anmi=0;
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
                    Intent myIntent = new Intent(event_final.this ,event_final.class);//refreshing
                    startActivity(myIntent);
                    finish();                   
                }
            });     
             }
          
    }
	
	public void iamcallin(){
    	mMenuDrawer.setContentView(R.layout.event_final_view);//givin layout to drawer 
        setSupportProgressBarIndeterminateVisibility(true);//onload show
      //layout for diff screen 
      		Display display = getWindowManager().getDefaultDisplay(); 		 
      		int height = display.getHeight();  // deprecated
      		
      		RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout_eventfinal);
      	    rl.getLayoutParams().height = (int) (height/2.3);
       	  
      	  ///
        name = Event_activity.click_type;
         // Displaying all values on the screen
        
        TextView lblName1 = (TextView) findViewById(R.id.event_activity_name);
        SpannableString spanString = new SpannableString(name);//underline
		  spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  lblName1.setText(spanString);
       // lblName1.setText(name);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
	 	prefs.registerOnSharedPreferenceChangeListener(this);
	 	
	 	//prefs.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		get_values_pref(); 
    	ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event1");
		ParseJson_object.setListener(this);
	}
 
   
	public void  get_values_pref(){// getting values from preference  
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence
		String event_interval1=prefs.getString("Interval", "7");
		event_interval =event_interval1.replaceAll("\\s","");//to remove spaces
		Log.i("i am checking interval",event_interval);
		event_unit =prefs.getString("Unit", "day");
		event_type =prefs.getString("Type", "general");
		
		
		// condition of api
				//event
		if(event_type.equals("unique") && event_unit.equals("hour")){
			Toast.makeText(getApplicationContext(), "You cannot get hourly uniques please change the event setting again", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void methodToCallback(String print) {
		final float[] data_map=new float[100];//defing array 
		ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
		
	 
		
		try {
			 json = new JSONObject(print);
			  series = json.getJSONArray("series");//taking the series 
			
			event_data = json.getJSONObject(TAG_event);
			
			 JSONObject c = event_data.getJSONObject(name);
			 		
			 	
			 	
			 	
			 	
			 	int i = 0;
			 	for (Iterator<?> keys = c.keys(); keys.hasNext(); i++) {
			 		 //key = (String)keys.next();
		             // adding each child node to HashMap key => value
		             	key=series.getString(i);// taking the key value from the serioues 
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

		/**
		* Updating parsed JSON data into graphs
		* */
		
        setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

		
		Line graph = new Line();
		LinePoint p = new LinePoint();
		int range= Integer.parseInt(All_api_define.interval1);//converting into float this is the inteval
		 for(int i=1;i<=range;i++){
			p = new LinePoint();
			p.setX(i);
			p.setY(data_map[i-1]);
			Log.i("data_map",data_map[i-1]+"");
			graph.addPoint(p);
		}
		
		 float rangeY=0;//getting the range for y-axis
		 for(int i= 0;i<data_map.length;i++){
			 if(data_map[i]>rangeY){
				 rangeY=data_map[i];
			 }
			 
		 }
		 
		 
		graph.setColor(Color.parseColor("#FFBB33"));

		LineGraph li = (LineGraph)findViewById(R.id.graph);
		li.addLine(graph);
		li.setRangeY(0, rangeY);
		li.setLineToFill(2);//change filling
		
		li.setOnPointClickedListener(new OnPointClickedListener(){

			@Override
			public void onClick(int lineIndex, int pointIndex) {
 				try {
					Toast.makeText(getApplicationContext(), ":"+series.getString(pointIndex)+":----:"+Math.round(data_map[pointIndex])+":", Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		
		
		
		
		

/**
* Updating parsed JSON data into ListView
* */
		ListAdapter adapter = new SimpleAdapter(this, Event_list,  R.layout.top_event_click_list,
				new String[] {VALUE1,KEY1}, new int[] {
		             R.id.e_name_amount,R.id.e_date});
 
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

		 if(anmi==1){
			   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}
			else if(anmi==2){
				
			    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

			} 
              
            if(internt_count==false){//starting settings if internet is not working
//               Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
                mMenuDrawer.setContentView(R.layout.nointernet);//giving new layout to drawer
                internt_count= false;
                RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout);
                rlayout.setOnClickListener(new OnClickListener() {
                 
                 @Override
                 public void onClick(View v) {
                     Intent myIntent = new Intent(event_final.this ,event_final.class);//refreshing
                     startActivity(myIntent);
                     finish();                   
                 }
             });
              

             }
             
          super.onResume();
     }
	 
	
    //creating the buttons for the period and blah
	  
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        //Used to put dark icons on light action bar
	        boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
   
	        menu.add(Menu.NONE, R.id.refresh, Menu.NONE, R.string.refresh)
            .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	
 
	        menu.add(Menu.NONE, R.id.event_filter, Menu.NONE, R.string.event_filter)
         
         .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	

	        return true;
	    }

      //checking internet connection
      
   


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
	    public boolean onOptionsItemSelected(MenuItem item) {
	        //This uses the imported MenuItem from ActionBarSherlock
		     
		  		switch(item.getItemId()){
		  		 
		  			case R.id.refresh:
		  				 Intent myIntent = new Intent(this ,event_final.class);//refreshing
		  				startActivity(myIntent);
		  				finish();

						return true;
		  			case R.id.event_filter:
		  				//startService(intentUpdater);
		  				
		  				startActivity(new Intent(this, Prefrenceactivity_event.class));
		  	            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
		  	            	anmi=2;
		  				return true;
		  			case android.R.id.home:
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
	    		  //mMenuDrawer.closeMenu();
	              startActivity(new Intent(this, Home.class));
	              overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	              anmi=1;

	    	 }
	    	 else if(((TextView) v).getText().equals("Event"))
	    	 { mMenuDrawer.setActiveView(v);
			  mMenuDrawer.closeMenu();
	          startActivity(new Intent(this, Event_activity.class));    
	    		 
	    	 }
	    	 else if(((TextView) v).getText().equals("Event Top"))
	    	 { mMenuDrawer.setActiveView(v);
			 // mMenuDrawer.closeMenu();
	          startActivity(new Intent(this, Event_top.class));   
	          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	          anmi=1;
	    		 
	    	 }
	    	 else if(((TextView) v).getText().equals("About")){
	    		 mMenuDrawer.setActiveView(v);
	   		  //mMenuDrawer.closeMenu();
	           startActivity(new Intent(this, About.class));
	           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	           anmi=1;
	    	 }
	         
	      
	        mActiveViewId = v.getId();
	    }
	//navigaiton ending

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			// TODO Auto-generated method stub
			get_values_pref();
			
		}

	
	 
}