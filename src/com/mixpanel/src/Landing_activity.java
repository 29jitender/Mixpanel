package com.mixpanel.src;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;


 

public class Landing_activity extends SherlockActivity implements Callback,OnSharedPreferenceChangeListener,ActionBar.OnNavigationListener {
	 SharedPreferences prefs;
		public static String API_sceret= "";//defining variable 
		public static String API_key=""; 
		//for pie chart
		private static final String amount = "amount";
		private static final String percent_change = "percent_change";
		private static final String event = "event";
	    JSONArray event_data = null;
		static JSONObject json = null;
		 private static final String TAG_event = "events";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		navigation();
		
 
		
		
	}
	
	public void iamcallin(){//calling everything to start
		setContentView(R.layout.activity_landing_activity);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
	 	prefs.registerOnSharedPreferenceChangeListener(this);
		get_values_pref(); //getting values from pre
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_top");
		ParseJson_object.setListener(this);
		if(API_key.equals("nill") && API_sceret.equals("nill")){
			Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in Settings", Toast.LENGTH_LONG).show();
		}
		
	}
	 @Override
		protected void onResume() {
			 getSupportActionBar().setSelectedNavigationItem(0);//navigations

			 if(isNetworkOnline()==true){//starting settings if internet is not working
				 iamcallin();

			}
				 
			 else if(isNetworkOnline()==false){//starting settings if internet is not working
					Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
					//startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));

				}
				
			 super.onResume();
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
	
	  //for pie chart
	  

		@Override
		public void methodToCallback(String print) {
			// TODO Auto-generated method stub
	 
				// Hashmap for ListView
			 String[] name_value=new String[5];
			  String[] e_name=new String[5];
			  Float[] values_amount=new Float[5];
						 
	 
						try {
							json = new JSONObject(print);
							event_data = json.getJSONArray(TAG_event);
							
							// looping through All data
							for(int i = 0; i < event_data.length(); i++){
							    JSONObject c = event_data.getJSONObject(i);
							     
							    // Storing each json item in variable
							    String Amount = c.getString(amount);
							    String parcent_change = c.getString(percent_change);
							    String Event = c.getString(event);
							    
							    e_name[i]=  Event;//name of event
							    float k =Float.parseFloat(Amount);//amount to pass in pie chart
							    values_amount[i]=k;//amount ot pass in pie chart
							    name_value[i]=Amount;
							  
		 
							}
		  

							//adding text in layout
							final TextView textViewToChange1 = (TextView) findViewById(R.id.textView1);
							textViewToChange1.setText(e_name[0]);
							final TextView textViewToChange2 = (TextView) findViewById(R.id.textView2);
							textViewToChange2.setText(e_name[1]);
							final TextView textViewToChange3 = (TextView) findViewById(R.id.textView3);
							textViewToChange3.setText(e_name[2]);
							final TextView textViewToChange4 = (TextView) findViewById(R.id.textView4);
							textViewToChange4.setText(e_name[3]);
							final TextView textViewToChange5 = (TextView) findViewById(R.id.textView5);
							textViewToChange5.setText(e_name[4]);
							//printing amount
//							final TextView textViewToChange6 = (TextView) findViewById(R.id.textView6);
//							textViewToChange6.setText(name_value[0]);
//							final TextView textViewToChange7 = (TextView) findViewById(R.id.textView7);
//							textViewToChange7.setText(name_value[1]);
//							final TextView textViewToChange8 = (TextView) findViewById(R.id.textView8);
//							textViewToChange8.setText(name_value[2]);
//							final TextView textViewToChange9 = (TextView) findViewById(R.id.textView9);
//							textViewToChange9.setText(name_value[3]);
//							final TextView textViewToChange10 = (TextView) findViewById(R.id.textView10);
//							textViewToChange10.setText(name_value[4]);
//									   	
							
									
									
				
									
						/**
				       * Updating parsed JSON data into pie chart
				       * 
				       * */  PieGraph pg1 = (PieGraph)findViewById(R.id.piegraph);
			           PieSlice slice1 = new PieSlice();
			           slice1.setColor(Color.parseColor("#99CC00"));
			           slice1.setValue(values_amount[0]+1);//adding 1 to show the graph with value 
			           pg1.addSlice(slice1);
			           slice1 = new PieSlice();
			           slice1.setColor(Color.parseColor("#FFBB33"));
			           slice1.setValue(values_amount[1]+1);
			           pg1.addSlice(slice1);
			           slice1 = new PieSlice();
			           slice1.setColor(Color.parseColor("#AA66CC"));
			           slice1.setValue(values_amount[2]+1);
			           pg1.addSlice(slice1);
			           slice1 = new PieSlice();
			           slice1.setColor(Color.parseColor("#f41212"));
			           slice1.setValue(values_amount[3]+1);
			           pg1.addSlice(slice1);
			           slice1 = new PieSlice();
			           slice1.setColor(Color.parseColor("#25d3ee"));
			           slice1.setValue(values_amount[4]+1);
			           pg1.addSlice(slice1);  
			           
			           
			           
			           
			           //yesteday
			           PieGraph pg = (PieGraph)findViewById(R.id.piegraph1);
			           PieSlice slice = new PieSlice();
			           slice.setColor(Color.parseColor("#99CC00"));
			           slice.setValue(values_amount[0]+1);//adding 1 to show the graph with value 
			           pg.addSlice(slice);
			           slice = new PieSlice();
			           slice.setColor(Color.parseColor("#FFBB33"));
			           slice.setValue(values_amount[1]+1);
			           pg.addSlice(slice);
			           slice = new PieSlice();
			           slice.setColor(Color.parseColor("#AA66CC"));
			           slice.setValue(values_amount[2]+1);
			           pg.addSlice(slice);
			           slice = new PieSlice();
			           slice.setColor(Color.parseColor("#f41212"));
			           slice.setValue(values_amount[3]+1);
			           pg.addSlice(slice);
			           slice = new PieSlice();
			           slice.setColor(Color.parseColor("#25d3ee"));
			           slice.setValue(values_amount[4]+1);
			           pg.addSlice(slice);  
			           
			          
			           
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							
							e.printStackTrace();
						}
						
					//if(json.getJSONObject("error")==null){}	


           

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
        menu.add(Menu.NONE, R.id.refresh, Menu.NONE, R.string.refresh)
        .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	 
        
         getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);
         menu.add(Menu.NONE, R.id.landing, Menu.NONE, R.string.landing)
         .setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
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
		case R.id.landing:
			//make someting for about
		startActivity(new Intent(this, Event_top.class));
		
		return true;	
		case R.id.refresh:
			 Intent myIntent = new Intent(this ,Landing_activity.class);//refreshing
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
 				//restarting activity with new values
				
			
			
			
		}
		
}
