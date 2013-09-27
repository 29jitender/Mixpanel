package com.mixpanel.revenue;
import net.simonvt.menudrawer.MenuDrawer;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mixpanel.src.About;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.Event_activity;
import com.mixpanel.src.Event_top;
import com.mixpanel.src.Home;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;
import com.mixpanel.src.funnel.Funnel_activity;
import com.mixpanel.src.funnel.Funnel_pref;
import com.mixpanel.src.live.live_first;
import com.mixpanel.src.people.People_first;
import com.mixpanel.src.streams.Stream_activity_first;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
 
  
        public class Revenue_first extends SherlockFragmentActivity implements  View.OnClickListener    {
        	  RevenueFragmentAdapter mAdapter;
        	  	 public Boolean internt_count=null;// to check the connectvity
        	    ViewPager mPager;
        	    PageIndicator mIndicator;
                int position=0;
                public static String data;
              //navigation drawer variables
                private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
                private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
                private MenuDrawer mMenuDrawer; 
                private int mActiveViewId;
                //navigation
        	    public static int anmi=0;
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
              mTitleTextView.setText("Revenue");
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
//                  getActionBar().setDisplayHomeAsUpEnabled(true);
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
       	 
       					 Intent myIntent = new Intent(Revenue_first.this ,Revenue_first.class);//refreshing

                           startActivity(myIntent);
                           finish();  
       	 
       				}
       	 
       			});	     
                   }
       		
                
            }

 public void iamcallin(){
	 mMenuDrawer.setContentView(R.layout.revenue_first); 
      
		AsyncHttpClient client = new AsyncHttpClient();
	        client.get(All_api_define.revenu_home(Revenuefragment.date_call(6),Revenuefragment.date_call(0)),  new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String response) {
            	 Log.i("yayyy",response);
            	data=response;
				 mAdapter = new RevenueFragmentAdapter(getSupportFragmentManager());

	                mPager = (ViewPager)findViewById(R.id.pager);
	                mPager.setAdapter(mAdapter);

	                TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
	                mIndicator = indicator;
	                indicator.setViewPager(mPager);

	                final float density = getResources().getDisplayMetrics().density;
	                indicator.setBackgroundColor(0x18FF0000);
	                indicator.setFooterColor(0xFFAA2222);
	                indicator.setFooterLineHeight(1 * density); //1dp
	                indicator.setFooterIndicatorHeight(3 * density); //3dp
	                indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
	                indicator.setTextColor(0xAA000000);
	                indicator.setSelectedColor(0xFF000000);
	                indicator.setSelectedBold(true);
	             } });
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
	 
					 Intent myIntent = new Intent(Revenue_first.this ,Revenue_first.class);//refreshing

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
  
 
 	  
 	 menu.add(Menu.NONE, R.id.event_top_setting, Menu.NONE, R.string.event_top_setting)
     .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
     return true;
 }
 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		 
 		   
		 
		case android.R.id.home: //on pressing home
         mMenuDrawer.toggleMenu();
         return true;	
		case R.id.event_top_setting:
			//startService(intentUpdater);
			 
			Intent myIntent = new Intent(this, Revenue_pref.class);//refreshing
			//myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//ending all activity

 		  	startActivity(myIntent);
			
			overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
	            anmi=2;
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
                  Intent myIntent = new Intent(Revenue_first.this ,Home.class);//refreshing
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