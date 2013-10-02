package com.mixpanel.revenue;
import java.math.BigDecimal;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.R;
 

public class Revenue_custom extends SherlockActivity   {
      public Boolean internt_count=null;// to check the connectvity
      TextView  revenue_cust;
      TextView  revenue_avg;
      TextView  revenue_total;
      RelativeLayout progress;
      LinearLayout mainlayout;
	 //navigation drawer variables
    private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
    private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
    private MenuDrawer mMenuDrawer; 
    private int mActiveViewId;
    private int anmi=0;
    String from_date;
    String to_date;
    //navigation
 	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//for progress it will be passed before layout
		///to check the flag (where it came from)
        // Get flag values from previous intent
		 Intent in = getIntent();
		   from_date=in.getStringExtra("from_date");
		   to_date=in.getStringExtra("to_date");
		
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
	       mTitleTextView.setText("Revenue");
	       mTitleTextView.setTextSize(20);

	       mActionBar.setCustomView(mCustomView);
	       mActionBar.setDisplayShowCustomEnabled(true);
	       // mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_header_bg));
	       TextView ibItem1 = (TextView)  findViewById(R.id.arrow);
	       ibItem1.setOnClickListener(new View.OnClickListener() {
	           @Override
	           public void onClick(View view) {
	        	   Revenue_first.anmi=1;
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
              
            	 int myColor = this.getResources().getColor(R.color.menu4);
                 colorDrawable.setColor(myColor);            
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
	 
					 Intent myIntent = new Intent(Revenue_custom.this ,Revenue_custom.class);//refreshing

                    startActivity(myIntent);
                    finish();  
	 
				}
	 
			});	     
             }
          
    }

    public float roundof(Float f){
    	 BigDecimal bd = new BigDecimal(Float.toString(f));
    	    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    	    return bd.floatValue();
    }
  
	public void iamcallin(){
		

    setContentView(R.layout.revenue_display);//givin layout to drawer 
	revenue_cust =  (TextView)findViewById(R.id.revenue_cust);
	revenue_avg =  (TextView) findViewById(R.id.revenue_avg);
	revenue_total =  (TextView) findViewById(R.id.revenue_total);
	progress =  (RelativeLayout) findViewById(R.id.progress);
	mainlayout =  (LinearLayout) findViewById(R.id.main);
	progress.setVisibility(View.VISIBLE);
		mainlayout.setVisibility(View.GONE);
	AsyncHttpClient client = new AsyncHttpClient();
      client.get(All_api_define.revenu_home(from_date,to_date),  new AsyncHttpResponseHandler() {
      	@Override
          public void onSuccess(String response) {	      	            	
 			   callin_ui(response);
           } });
	 
        
	}
 
	public void callin_ui(String data){
		
		JSONObject obj;
		try {
			obj = new JSONObject(data);
			JSONObject  obj1=obj.getJSONObject("results");
				JSONObject obj2=obj1.getJSONObject("$overall");
				revenue_cust.setText(obj2.getString("paid_count"));
				if(Integer.parseInt(obj2.getString("paid_count"))==0){
					revenue_avg.setText("0");

				}
				else{
					revenue_avg.setText(roundof(Float.parseFloat(obj2.getString("amount"))/Float.parseFloat(obj2.getString("paid_count")))+"");

				}
				revenue_total.setText(obj2.getString("amount"));
				  progress.setVisibility(View.GONE);
		   			mainlayout.setVisibility(View.VISIBLE);
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
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
    	 
    					 Intent myIntent = new Intent(Revenue_custom.this ,Revenue_custom.class);//refreshing

                        startActivity(myIntent);
                        finish();  
    	 
    				}
    	 
    			});	
              

             }
             
          super.onResume();
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
  
  
  

	
	 
}