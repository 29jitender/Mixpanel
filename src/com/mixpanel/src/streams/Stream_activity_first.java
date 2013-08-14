package com.mixpanel.src.streams;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import net.simonvt.menudrawer.MenuDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.mixpanel.src.About;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.Callback;
import com.mixpanel.src.Event_activity;
import com.mixpanel.src.Event_top;
import com.mixpanel.src.Home;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;
import com.mixpanel.src.funnel.Funnel_activity;
import com.mixpanel.src.live.live_first;



public class Stream_activity_first extends SherlockListActivity implements Callback ,View.OnClickListener {
	ArrayList<HashMap<String, String>> stream_list;
	ArrayList<String> user_name;
 	ArrayList<String> user_id;
    private EditText search;

    SimpleAdapter adapter;
	static JSONObject json = null;
 	 public Boolean internt_count=null;// to check the connectvity

	 
	 private static final String username = "percent_change";
 	 public static String click_type="";
 
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
       mTitleTextView.setText("Stream");
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
    	   int myColor = this.getResources().getColor(R.color.menu6);
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


       TextView activeView = (TextView) findViewById(mActiveViewId);
       if (activeView != null) {
           mMenuDrawer.setActiveView(activeView);
           //mContentTextView.setText("Active item: " + activeView.getText());
       } 
       
       Display display = getWindowManager().getDefaultDisplay(); 		 
 		int width = display.getWidth();  
       mMenuDrawer.setMenuSize(width/3);//size of menu
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
	 
					 Intent myIntent = new Intent(Stream_activity_first.this ,Stream_activity_first.class);//refreshing

                    startActivity(myIntent);
                    finish();  
	 
				}
	 
			});	     
            }
		
 		 

 		
	}
	    public void iamcallin(){ 
	     	mMenuDrawer.setContentView(R.layout.live);//givin layout to drawer

	         setSupportProgressBarIndeterminateVisibility(true);//onload show
  
			  ParseJSON ParseJson_object = new ParseJSON();
			  ParseJson_object.pass_values("stream_list");
			  ParseJson_object.setListener(this);
			
			
	    }
	 
 
	
	 
   
	@Override
	public void methodToCallback(String print) {
		// TODO Auto-generated method stub
 
  		user_id = new ArrayList<String>();
  		user_name = new ArrayList<String>();
		String timediff;
		 
					  stream_list = new ArrayList<HashMap<String, String>>();
		 				try {
							JSONArray array1=new JSONArray(print);
							for(int i=0;i<array1.length();i++){
								JSONObject obj1=array1.getJSONObject(i);
								String name=obj1.getString("name_tag");
								String id=obj1.getString("distinct_id");
								String time=obj1.getString("ts");
								
								
								
 								if (user_id.contains(id)) {
								    System.out.println("Account found");
								} 
 								
 								
 								else {
									 HashMap<String, String>  map = new HashMap<String, String>();
									 	user_id.add(id);
									 	
									 	if(name.equals("")){///////if there is no name 
									 		int id_int=0;
									 		for(int z=0;z<id.length();z++){
									 			char temp=id.charAt(z);									 			
									 			int temp2=(int)temp;
									 			id_int=id_int+(temp2*(id.length()-z));
									 		}
									 		
									 		map.put("name", "Guest #"+Integer.toString(id_int));	
									 		user_name.add("Guest #"+Integer.toString(id_int));
									 	}
									 	else{
									 		map.put("name", name);
									 		user_name.add(name);
									 	}
									 	///caluclation difference in time
									 	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										formatter.setTimeZone(TimeZone.getTimeZone("GMT"));////////setting utc time zone
						        		  Date date = new Date(); 
					        		
					        	 
										 try {
											 Date date7=formatter.parse(time);
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
												  
												 
					   							map.put("time", timediff); 

							        		  
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										 
										
										 
									 	
									 	
				//////////////////////////				 	/////////////////////////////////
										 ///getting notes
										 if(obj1.has("properties")){
											  JSONObject obj2 =obj1.getJSONObject("properties");
												 if(obj2.has("referrer")){
													 String referrer = obj2.getString("referrer");
													 String page = obj2.getString("page");
													 map.put("referrer", referrer);
													 map.put("page", page);

												 }
												 else{
													 String referrer = "";
													 String page = obj2.getString("page");
													 map.put("referrer", referrer);
													 map.put("page", page);
												 }
										 }
										 else{
											search: for(int j=0;j<array1.length();j++){
												JSONObject objtemp=array1.getJSONObject(j);
												String tempid=objtemp.getString("distinct_id");

													if(objtemp.has("properties")&&tempid.equals(id)){
														JSONObject obj2 =objtemp.getJSONObject("properties");
														 if(obj2.has("referrer")){
															 String referrer = obj2.getString("referrer");
															 String page = obj2.getString("page");
															 map.put("referrer", referrer);
															 map.put("page", page);

														 }
														 else{
															 String referrer = "";
															 String page = obj2.getString("page");
															 map.put("referrer", referrer);
															 map.put("page", page);
														 }
														
														
														
														break search;
													}
												 
											 }
										 }
										 
										 ///////////////////////
										   
										stream_list.add(map);
										
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
					 
        adapter = new SimpleAdapter(this, stream_list,
					    R.layout.list_stream_frist,
					    new String[] { "name","time","page"}, new int[] {
					            R.id.stream_user_name,R.id.stream_first_time,R.id.stream_first_view });
 
      setListAdapter(adapter);
 
      // selecting single ListView item
      ListView lv = getListView();
     
      // Launching new screen on Selecting Single ListItem
      lv.setOnItemClickListener(new OnItemClickListener() {
 
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
					        int position, long id) {
					    // getting values from selected ListItem
					    
					    String distinct_id = user_id.get(position);
					    String username = user_name.get(position);

					    // Starting new intent
					    Intent in = new Intent(getApplicationContext(), Stream_activity_final.class);
 
 					    All_api_define.distinct_ids=distinct_id;//assing value to all api deifne
 					    All_api_define.stream_username=username;
 					 
					    All_api_define.stream_user();//callin it onece
					    startActivity(in);
				          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

					    anmi=1;
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
  	 
  					 Intent myIntent = new Intent(Stream_activity_first.this ,Stream_activity_first.class);//refreshing

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

    	
        menu.add(Menu.NONE, R.id.refresh, Menu.NONE, R.string.refresh)
        .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	
        
        
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
		 
		case R.id.refresh:
			 Intent myIntent = new Intent(this ,Stream_activity_first.class);//refreshing
			  overridePendingTransition(0, 0);
	            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	             finish();
	            overridePendingTransition(0, 0);

	               startActivity(myIntent);
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
					Stream_activity_first.this.adapter.getFilter().filter(cs);	
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

        super.onBackPressed();
    }
    @Override
    public void onClick(View v) { // for the click view
    	
    	switch(v.getId()){
    	case R.id.item1:
    		 mMenuDrawer.setActiveView(v);
             // mMenuDrawer.closeMenu();
                  Intent myIntent = new Intent(Stream_activity_first.this ,Home.class);//refreshing
                  myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                  Home.mMenuDrawer.closeMenu();

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
   		mMenuDrawer.setActiveView(v);
 		 // mMenuDrawer.closeMenu();
         startActivity(new Intent(this, Event_top.class));
         overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
         anmi=1;
   		break;
   	case R.id.item4:
   		
   		 mMenuDrawer.setActiveView(v);
      		 // mMenuDrawer.closeMenu();
              startActivity(new Intent(this, live_first.class));
              overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
              anmi=1;
   		
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
     mMenuDrawer.closeMenu();
    // startActivity(new Intent(this, Event_top.class)); 
     
  		
  		break;
   	case R.id.item7:
   		
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
 