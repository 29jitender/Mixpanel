package com.mixpanel.src;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

 

public class Landing_activity extends SherlockActivity implements OnSharedPreferenceChangeListener,ActionBar.OnNavigationListener {
	 SharedPreferences prefs;
		public static String API_sceret= "";//defining variable 
		public static String API_key=""; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_activity);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
	 	prefs.registerOnSharedPreferenceChangeListener(this);
		get_values_pref(); //getting values from pre
		
		if(API_key.equals("nill") && API_sceret.equals("nill")){
			Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in Settings", Toast.LENGTH_LONG).show();
		}
		navigation();
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

 protected void onPostExecute(Integer result) {

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
	
	//for navigation
	  public void navigation(){
		  getSupportActionBar().setDisplayHomeAsUpEnabled (true);
		  getSupportActionBar().setDisplayShowTitleEnabled(false);
		  getSupportActionBar().setDisplayUseLogoEnabled  (true);
		  setTheme(SampleList.THEME); //Used for theme switching in samples
		  String[] mLocations = getResources().getStringArray(R.array.locations);// item location
		// starting of menu
		   Context context = getSupportActionBar().getThemedContext();
	        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
	        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

	        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	        getSupportActionBar().setListNavigationCallbacks(list, this);
	        
		// ending of menu
	  }
	 @Override
	protected void onResume() {
		 getSupportActionBar().setSelectedNavigationItem(0);
		super.onResume();
	}

 
	@Override
	    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		  
		 switch (itemPosition){
			
			case 1:
				startActivity(new Intent(this, Event_activity.class)); 
				return true;
			case 2:
				//startActivity(new Intent(this, Funnel_activity.class));
				
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
			default:
				return false;
		 }
	    }
	
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar
        boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
         
         getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);

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
			
			
			return true;
	 	
		default:
			return false;
			
			
		}

	 
	}


		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			 
				get_values_pref();
 				//restarting activity with new values
				
			
			
			
		}
		
}
