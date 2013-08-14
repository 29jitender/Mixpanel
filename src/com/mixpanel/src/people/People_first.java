package com.mixpanel.src.people; 
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.simonvt.menudrawer.MenuDrawer;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
import com.mixpanel.src.streams.Stream_activity_final;



public class People_first extends SherlockListActivity implements Callback ,View.OnClickListener {
	public static ArrayList<HashMap<String, String>> people_list;
	ArrayList<String> people_name;
 	ArrayList<String> people_id;
    private EditText search;

    SimpleAdapter adapter;
  	 public Boolean internt_count=null;// to check the connectvity

 	 
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
       mTitleTextView.setText("People");
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
	 
					 Intent myIntent = new Intent(People_first.this ,People_first.class);//refreshing

                    startActivity(myIntent);
                    finish();  
	 
				}
	 
			});	     
            }
		
 		 

 		
	}
	    public void iamcallin(){ 
	     	mMenuDrawer.setContentView(R.layout.people_first);//givin layout to drawer

	         setSupportProgressBarIndeterminateVisibility(true);//onload show
  
			  ParseJSON ParseJson_object = new ParseJSON();
			  ParseJson_object.pass_values("people_list");
			  ParseJson_object.setListener(this);
			
			
	    }
	 
 
	
	 
   
	@Override
	public void methodToCallback(String print) {
		  people_list = new ArrayList<HashMap<String, String>>();
		  people_id = new ArrayList<String>();
	  	  people_name = new ArrayList<String>();
		  
		  try {
			JSONObject obj1 = new JSONObject(print);
			
			JSONArray array1 =obj1.getJSONArray("results");
			
			for(int i=0;i<array1.length();i++){
				
				JSONObject obj2=array1.getJSONObject(i);
				String distinct_id =obj2.getString("$distinct_id");
				people_id.add(distinct_id);//adding id
				
				JSONObject obj3=obj2.getJSONObject("$properties");
				 HashMap<String, String>  map = new HashMap<String, String>();
				 
				 String username=obj3.getString("$name");//username
				 people_name.add(username);
				 map.put("name", username);
				 
				 
				 String email =obj3.getString("$email");//email
				 map.put("email", email);
				 
				 String lastseen=obj3.getString("$last_seen");//last seen
				 lastseen = lastseen.replace("T", " @ ");

				 map.put("lastseen", lastseen);
				 
				 ///now location
				 String region;
				 String city;
				try {
					  city = obj3.getString("$city")+"-";
				} catch (Exception e) {
					  city = "";				}
				
				 try {
					  region = obj3.getString("$region") + ",";
				} catch (Exception e) {
					  region ="";}				 
				
				 	String country=obj3.getString("$country_code");
				
				 	String location=city+region+country;
				 	map.put("location", location);
				 	
				 	//////
					people_list.add(map);

			}
			
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	         setSupportProgressBarIndeterminateVisibility(false); 

		//////////updating data into list 
		  
		  adapter = new SimpleAdapter(this, people_list,
				    R.layout.people_first_list,
				    new String[] { "name","email","lastseen","location"}, new int[] {
				            R.id.people_first_name,R.id.people_first_email,R.id.people_first_time,R.id.people_first_location });

		  	setListAdapter(adapter);
		
		
		    // selecting single ListView item
		      ListView lv = getListView();
		     
		      // Launching new screen on Selecting Single ListItem
		      lv.setOnItemClickListener(new OnItemClickListener() {
		 
							@Override
							public void onItemClick(AdapterView<?> parent, View view,
							        int position, long id) {
							    // getting values from selected ListItem
							    
							    String people_id1 = people_id.get(position);
							    
							    people_id1="[\""+people_id1+"\"]";
							    String people_name1 = people_name.get(position);

							    // Starting new intent
							    Intent in = new Intent(getApplicationContext(), People_second.class);
		 
		 					    All_api_define.people_id=people_id1;//assing value to all api deifne
		 					    All_api_define.people_name=people_name1;
		 					 
							    All_api_define.people_data();//callin it onece
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
  	 
  					 Intent myIntent = new Intent(People_first.this ,People_first.class);//refreshing

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
					People_first.this.adapter.getFilter().filter(cs);	
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
                  Intent myIntent = new Intent(People_first.this ,Home.class);//refreshing
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
 
