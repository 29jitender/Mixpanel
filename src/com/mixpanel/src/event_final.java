package com.mixpanel.src;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
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
    public static String name1="";
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//for progress it will be passed before layout
		///to check the flag (where it came from)
		 Intent in = getIntent();
        // Get flag values from previous intent
         name1 = in.getStringExtra("flag");
		
		
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
	       mTitleTextView.setText(All_api_define.event);
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
        if (savedInstanceState != null) {
            mActiveViewId = savedInstanceState.getInt(STATE_ACTIVE_VIEW_ID);
        }
        	
     
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        	//getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(android.R.color.transparent);//to remove the icon from action bar

            // this is for the color of title bar
             ColorDrawable colorDrawable = new ColorDrawable();
             if(name1.equals("Top")){//to check from where it came from
            	 colorDrawable.setColor(Color.parseColor("#3BB0AA"));
             }
             else  if(name1.equals("all"))
             {
            	 colorDrawable.setColor(Color.parseColor("#44C19F"));
             }
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
	 
					 Intent myIntent = new Intent(event_final.this ,event_final.class);//refreshing

                    startActivity(myIntent);
                    finish();  
	 
				}
	 
			});	     
             }
          
    }
	
	public void iamcallin(){
    setContentView(R.layout.event_final_view);//givin layout to drawer 
        setSupportProgressBarIndeterminateVisibility(true);//onload show
      //layout for diff screen 
      		Display display = getWindowManager().getDefaultDisplay(); 		 
      		int height = display.getHeight();  // deprecated
      		
      		RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout_eventfinal);
      	    rl.getLayoutParams().height = (int) (height/2.6);
       	  
      	  ///
        //name = Event_activity.click_type;
      	    name= All_api_define.event;//getting the name from all api define
         // Displaying all values on the screen
        
        TextView lblName1 = (TextView) findViewById(R.id.event_activity_name);
        //SpannableString spanString = new SpannableString(name);//underline
		 // spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		 // spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		 //spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  //lblName1.setText(spanString);
        if(name1.equals("Top")){//to check from where it came from
        	lblName1.setTextColor(Color.parseColor("#3BB0AA"));
        	 
        }
        else  if(name1.equals("all"))
        {
        	lblName1.setTextColor(Color.parseColor("#44C19F"));
        }
        lblName1.setText(name);
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
		final float[] data_map=new float[1000];//defing array 
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
 		           ////////////////////////// ///date format
		            SimpleDateFormat formatter ; 
		            Date date = null ;
		            String newFormat=null;
		               
		               try {
 						if(event_unit.equals("minute")){
				               formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				               date = formatter.parse(mkey);

							SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMMM KK:mm a");
					        newFormat = formatter1.format(date);
 						}
						else if(event_unit.equals("hour")){
							formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
							date = formatter.parse(mkey);
								SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMMM KK a");
						        newFormat = formatter1.format(date);
							}
						else if(event_unit.equals("day")){
							formatter = new SimpleDateFormat("yyyy-MM-dd");
							 date = formatter.parse(mkey);
								SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMMM");
						        newFormat = formatter1.format(date);
							}
						else if(event_unit.equals("week")){
							formatter = new SimpleDateFormat("yyyy-MM-dd");
							date = formatter.parse(mkey);
								SimpleDateFormat formatter1 = new SimpleDateFormat("F");								
								SimpleDateFormat formatter2 = new SimpleDateFormat("MMMM yyyy");
								SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy");
								SimpleDateFormat formatter4 = new SimpleDateFormat("MMMM");
								String temp=formatter1.format(date);
								String temp1=formatter2.format(date);
								String temp2=formatter3.format(date);
								String temp3=formatter4.format(date);
								
								if(temp2.equals("2013")){
									if(temp.equals("1")){
								        newFormat = temp+"st week of "+temp3;
								        }
										else if(temp.equals("2")){
									        newFormat = temp+"nd week of "+temp3;
									        }
										else if(temp.equals("3")){
									        newFormat = temp+"rd week of "+temp3;
									        }
										else {
									        newFormat = temp+"th week of "+temp3;
									        }
								}
								else{
									if(temp.equals("1")){
								        newFormat = temp+"st week of "+temp1;
								        }
										else if(temp.equals("2")){
									        newFormat = temp+"nd week of "+temp1;
									        }
										else if(temp.equals("3")){
									        newFormat = temp+"rd week of "+temp1;
									        }
										else {
									        newFormat = temp+"th week of "+temp1;
									        }
								}
								
							}
						  else if(event_unit.equals("month")){
							formatter = new SimpleDateFormat("yyyy-MM-dd");
							date = formatter.parse(mkey);
								SimpleDateFormat formatter1 = new SimpleDateFormat("MMMM yyyy");
						        newFormat = formatter1.format(date);
							}
						
						
 				        
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		            /////////////////////////////////////////////////////////////////
		            String mvalue= (String) c.getString(key);
		            
		            
		            //map.put(mkey, mvalue); 
		            //map.put(KEY1, mkey);// all dates
		            map.put(KEY1, newFormat);
		            map.put(VALUE1, mvalue);// all values
		          
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
		
		// this is to check  if all values are same and 0//////////////////////////////////////////////////////////////////////
		boolean flag = true;
		float first = 0;
		for(int i = 0; i < range && flag; i++)
		{
		  if (data_map[i] != first) flag = false;
		}
		if (flag){ 
            RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventfinal);

            rlayout.setVisibility(RelativeLayout.GONE);//hiding the graph
            RelativeLayout rlayout1 = (RelativeLayout) findViewById(R.id.relativeLayout_nill);

            rlayout1.setVisibility(RelativeLayout.VISIBLE);//show to msg
 		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
		 
		 if(name1.equals("Top")){//to check from where it came from and set that color to graph :P
      		graph.setColor(Color.parseColor("#3BB0AA"));

         }
         else  if(name1.equals("all"))
         {
      		graph.setColor(Color.parseColor("#44C19F"));

         }
 
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
		             R.id.e_name_amount,R.id.e_date1});
 
			setListAdapter(adapter);
	 
		
	
// selecting single ListView item
ListView lv = getListView();

// Launching new screen on Selecting Single ListItem
lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
 
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
            	 setContentView(R.layout.nointernet);//giving new layout to drawer
                 //setContentView(R.layout.nointernet);
                internt_count= false;
                Button button = (Button) findViewById(R.id.nointernet_refresh);
    			 
                button.setOnClickListener(new OnClickListener() {
    	 
    				@Override
    				public void onClick(View arg0) {
    	 
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
		  				 myIntent.putExtra("flag", name1);
		  				  overridePendingTransition(0, 0);
		  	             myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		  	             finish();
		  	             overridePendingTransition(0, 0);

		  	               startActivity(myIntent);

						return true;
		  			case R.id.event_filter:
		  				//startService(intentUpdater);
		  				
		  				Intent prefin = new Intent(this ,Prefrenceactivity_event.class);//refreshing
		  				prefin.putExtra("flag", name1);
		  				startActivity(prefin);
		  				
		  				
		  				//startActivity(new Intent(this, Prefrenceactivity_event.class));
		  	            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
		  	            	anmi=2;
		  				return true;
//		  			case android.R.id.home:
//		  			    finish();
//
// 		  	            return true; 
					default:
						return false;	
		  						
		  		}
		  	
	        
	       
	    }
	 
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			// TODO Auto-generated method stub
			get_values_pref();
			
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}

	
	 
}