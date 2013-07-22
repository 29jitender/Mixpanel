package com.mixpanel.src;

import java.util.ArrayList;
import java.util.HashMap;

import net.simonvt.menudrawer.MenuDrawer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

//interface Callback {
//    void methodToCallback(String response);
//}

public class Event_activity extends SherlockActivity implements   Callback,View.OnClickListener  {
    public Boolean internt_count=null;// to check the connectvity

	 public static String click_type="";	 
	 public static  String display1="lol";
	 // List view
		private ListView lv;		
		// Listview Adapter 
		ArrayAdapter<String> adapter;		
		// Search EditText
		EditText inputSearch;		
		// ArrayList for Listview
		ArrayList<HashMap<String, String>> productList;
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
    	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //open only when user click

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
                    Intent myIntent = new Intent(Event_activity.this ,Event_activity.class);//refreshing
                    startActivity(myIntent);
                    finish();                   
                }
            });     
             }
		
  		 
	}
	
	public void iamcallin(){
    	mMenuDrawer.setContentView(R.layout.activity_event_activity);//givin layout to drawer
 	     setSupportProgressBarIndeterminateVisibility(true);//onload show loading
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_name");
		ParseJson_object.setListener(this);
	}
	
	@Override
	public void methodToCallback(String display12) {
		// TODO Auto-generated method stub
		//String display1 =ParseJson_object.pass_values("event_name");
		
		String result =  display12.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
		String[]  array = result.split(", ");//to get the result in list without ", "
        setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

		lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        
        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item1, R.id.event_activity_list, array);
        lv.setAdapter(adapter);
        
        // this is to search the items in list
        inputSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				Event_activity.this.adapter.getFilter().filter(cs);	
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
		
		 click_action();

	}

  public String click_action(){
		 
        lv.setTextFilterEnabled(true);
        
		 lv.setOnItemClickListener(new OnItemClickListener() {
	        	
	        	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	        		  Log.i("item",""+lv.getItemAtPosition(position));
	        		  startGraphActivity(event_final.class);//open graph
	        		  click_type=(String) lv.getItemAtPosition(position);
				}
	        	
	        });      
		
		return click_type;
	}
	
	private void startGraphActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(Event_activity.this, activity);
		 
		startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        anmi=1;
	}
	
	   
    @Override
       protected void onResume() {

    	if(anmi==1){
    		   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    		}
                
              if(internt_count==false){//starting settings if internet is not working
//                 Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
                  mMenuDrawer.setContentView(R.layout.nointernet);//giving new layout to drawer
                  internt_count= false;
                  RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout);
                  rlayout.setOnClickListener(new OnClickListener() {
                   
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Event_activity.this ,Event_activity.class);//refreshing
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
    		  //mMenuDrawer.closeMenu();
              startActivity(new Intent(this, Home.class));    		  
    	 }
    	 else if(((TextView) v).getText().equals("Event"))
    	 { mMenuDrawer.setActiveView(v);
		  mMenuDrawer.closeMenu();
          startActivity(new Intent(this, Event_activity.class));
          anmi=1;
    		 
    	 }
    	 else if(((TextView) v).getText().equals("Event Top"))
    	 { mMenuDrawer.setActiveView(v);
		  //mMenuDrawer.closeMenu();
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




	
	
}
