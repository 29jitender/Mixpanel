package com.mixpanel.src.funnel;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
 
public class Funnel_display extends SherlockFragmentActivity implements View.OnClickListener{
    private int anmi=0;

///////////////////getting data
	   public Boolean internt_count=null;// to check the connectvity

		public static ArrayList<String> event_name;
		public static ArrayList<String> event_value;
		public static ArrayList<String> event_overall_conv_ratio;
		public static ArrayList<String> event_step_conv_ratio;
		public static ArrayList<String> event_avg_time;
		public static  String funnel_id=null; 
	///////////////////////
	Funnel_display_fragment mAdapter;
	    ViewPager mPager;
	    PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	       mCustomView = mInflater.inflate(R.layout.funnel_final_menu, null);
	       mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
	       mTitleTextView.setText(Funnel_activity.funnel_name);
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
		 
						 Intent myIntent = new Intent(Funnel_display.this ,Funnel_display.class);//refreshing

	                    startActivity(myIntent);
	                    finish();  
		 
					}
		 
				});	     
	             }
	          
		
		 
		
          
    }
		
	public void iamcallin(){
		setContentView(R.layout.activity_funnel_display);
		//getting all data
		event_name = new ArrayList<String>();
 		event_value = new ArrayList<String>();
 		event_overall_conv_ratio = new ArrayList<String>();
 		event_step_conv_ratio = new ArrayList<String>();
 		event_avg_time = new ArrayList<String>();

		Intent in = getIntent();
		funnel_id=in.getStringExtra("funnel_id");
 		event_name=(ArrayList<String>)in.getSerializableExtra("event_name");
 		event_value=(ArrayList<String>)in.getSerializableExtra("event_value");
 		
 		event_overall_conv_ratio=(ArrayList<String>)in.getSerializableExtra("overall_conv_ratio");
 		event_step_conv_ratio=(ArrayList<String>)in.getSerializableExtra("step_conv_ratio");
 		event_avg_time=(ArrayList<String>)in.getSerializableExtra("event_avg_time");
		
		
	 	//////
		mAdapter = new Funnel_display_fragment(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager1);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator1);
        mIndicator.setViewPager(mPager);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator1);
        indicator.setViewPager(mPager);
         mIndicator = indicator;

        final float density = getResources().getDisplayMetrics().density;
        indicator.setBackgroundColor(0x383BB0AA);
        indicator.setFooterColor(0xFF007ab7);
        indicator.setFooterLineHeight(1 * density); //1dp
        indicator.setFooterIndicatorHeight(3 * density); //3dp
        indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
        indicator.setTextColor(0xAA000000);
        indicator.setSelectedColor(0xFF000000);
        indicator.setSelectedBold(true);

		
	}
	
	 ////////////////////////////////////////////////////////rest activity function
	
	
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
				 
				Intent myIntent = new Intent(this, Funnel_pref.class);//refreshing
           	myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//ending all activity

  		  	myIntent.putExtra("funnel_id", funnel_id);
  		  	startActivity(myIntent);
				
				overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
		            anmi=2;
				return true;
			 
			 
			default:
				return false;
				
				
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	
	
	}

 
 
