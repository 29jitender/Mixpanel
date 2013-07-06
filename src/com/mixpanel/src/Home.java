package com.mixpanel.src;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class Home extends SherlockFragmentActivity implements ActionBar.OnNavigationListener,OnSharedPreferenceChangeListener {
	   HomeFragmentAdapter mAdapter;
	   LinearLayout linlaHeaderProgress;
	    ViewPager mPager;
	    PageIndicator mIndicator;
	    SharedPreferences prefs;
		public static String API_sceret= "";//defining variable 
		public static String API_key=""; 
		public Boolean internt_count=null;// to check the connectvity
	    JSONArray event_data = null;
		static JSONObject json = null;
		 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(isNetworkOnline()==true){//starting settings if internet is not working
        	internt_count=true; 
  		  setTheme(SampleList.THEME); //Used for theme switching in samples

        	iamcallin();//calling the function to build everything

		}
			 
		 else if(isNetworkOnline()==false){ 
				//Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
			 //setContentView(R.layout.nointernet);
			 internt_count= false;
			 RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
			  rlayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent myIntent = new Intent(Home.this ,Home.class);//refreshing
					startActivity(myIntent);
					finish();					
				}
			});
			 
 

			}
		navigation();
    }

	 
	public void iamcallin(){
		
		 setContentView(R.layout.activity_home);
		  linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);//progress

	        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());

	        mPager = (ViewPager)findViewById(R.id.pager);
	        mPager.setAdapter(mAdapter);

	        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
	        mIndicator.setViewPager(mPager);
	        prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
		 	prefs.registerOnSharedPreferenceChangeListener(this);
			get_values_pref(); //getting values from pre
			if(API_key.equals("nill") && API_sceret.equals("nill")){
				Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in Settings", Toast.LENGTH_LONG).show();
			}
	}
	
	 private void get_values_pref() {//api key and stuff
		  prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence
		  	
		   
			API_key =prefs.getString("api_key", "nill");
			API_sceret =prefs.getString("api_secret", "nill");
			Log.i("hi we are in pref",API_key);
			Log.i("hi we are in pref",API_sceret);
			Conf_API.setting();// calling conf api to update the api key and valyes
			new Check_api().execute();//checking api after the values are updated in conf_api
		
	}
	
	 private class Check_api extends AsyncTask<Void, Void, Integer> {

	        protected Integer doInBackground(Void... params) {
	        	String url = All_api_define.api_check();
	   	     
		    	DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet(url);// main request
				getRequest.setHeader("Accept", "application/json");
				getRequest.setHeader("Accept-Encoding", "gzip"); //
				int result = 0;
				try {
					HttpResponse response = (HttpResponse) httpclient.execute(getRequest);
					 result= response.getStatusLine().getStatusCode();
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 
		    	
				return result;
		    	 
	        	
	        }

	        
@Override
			protected void onPreExecute() {
    linlaHeaderProgress.setVisibility(View.VISIBLE);

 				super.onPreExecute();
			}


protected void onPostExecute(Integer result) {
    linlaHeaderProgress.setVisibility(View.GONE);//hiding the loader

				 if(result==200){
					 Log.i("working test",result+"");
				 }
				 else{
					 new Handler().postDelayed(new Runnable() {// opening menu when api key is wrong 
						   public void run() { 
						     openOptionsMenu(); 
						   } 
						}, 1000);

					 Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in Settings", Toast.LENGTH_LONG).show(); 
				 }

	            super.onPostExecute(result);
	        }
	    }
	
	
	 @Override
		protected void onResume() {
			 getSupportActionBar().setSelectedNavigationItem(0);//navigations

			  
				 
			   if(internt_count==false){//starting settings if internet is not working
//					Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
				   setContentView(R.layout.nointernet);
				   internt_count= false;
				   RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.relativeLayout);
				   rlayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(Home.this ,Home.class);//refreshing
						startActivity(myIntent);
						finish();					
					}
				});
				 

				}
				
			 super.onResume();
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
	
	
	
	
	
	//for navigation
	  public void navigation(){
		  getSupportActionBar().setDisplayHomeAsUpEnabled (true);
		  getSupportActionBar().setDisplayShowTitleEnabled(false);
		  getSupportActionBar().setDisplayUseLogoEnabled  (true);
		  setTheme(SampleList.THEME); //Used for theme switching in samples
		  //String[] mLocations = getResources().getStringArray(R.array.locations);// item location
		// starting of menu
		   Context context = getSupportActionBar().getThemedContext();
	        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
	        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

	        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	        getSupportActionBar().setListNavigationCallbacks(list, this);
	        
		// ending of menu
	  }
	



	@Override
	    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		  
		 switch (itemPosition){
			
			case 1:
				startActivity(new Intent(this, Event_activity.class)); 
				overridePendingTransition(R.anim.fadein,R.anim.fadeout);
				return true;
			case 2:
				startActivity(new Intent(this, Event_top.class));
				overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);//calling anim
				
				return true;
			case 3:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;
			case 4:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;
			case 5:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;
			case 6:
				//startActivity(new Intent(this, Event_activity.class));
				
				return true;	
			default:
				return false;
		 }
	    }
	
  @Override

  public boolean onCreateOptionsMenu(Menu menu) {
      //Used to put dark icons on light action bar
       
      
       getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);
       boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
       menu.add(Menu.NONE, R.id.refresh, Menu.NONE, R.string.refresh)
       .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
       .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	
      
      return true;
  }
  

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		
		case R.id.setting:
			//startService(intentUpdater);
			 
			startActivity(new Intent(this, Prefrenceactivity.class));
			 
			return true;
		case R.id.about:
				//make someting for about
			startActivity(new Intent(this, About.class));
			
			return true;
		case R.id.refresh:
			 Intent myIntent = new Intent(this ,Home.class);//refreshing
				startActivity(myIntent);
				finish();
			return true;	
	 
		default:
			return false;
			
			
		}

	 
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		get_values_pref();
		
	}
}
