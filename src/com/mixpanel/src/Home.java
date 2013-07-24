package com.mixpanel.src;
import java.io.IOException;

import net.simonvt.menudrawer.MenuDrawer;

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
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class Home extends SherlockFragmentActivity implements  OnSharedPreferenceChangeListener,View.OnClickListener {
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
        //navigation drawer variables
        private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
        private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
        private MenuDrawer mMenuDrawer; 
        private int mActiveViewId;
        private int anmi=0;
        //navigation
         
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            // this is for the color of title bar
            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
            getSupportActionBar().setBackgroundDrawable(bg);

        }
     
 
        findViewById(R.id.item1).setOnClickListener(this);
        findViewById(R.id.item2).setOnClickListener(this);
        findViewById(R.id.item3).setOnClickListener(this);
        findViewById(R.id.item4).setOnClickListener(this);

        

        TextView activeView = (TextView) findViewById(mActiveViewId);
        if (activeView != null) {
            mMenuDrawer.setActiveView(activeView);
            //mContentTextView.setText("Active item: " + activeView.getText());
        } 
        // This will animate the drawer open and closed until the user manually drags it. Usually this would only be
        // called on first launch.
        mMenuDrawer.peekDrawer();
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
                    Intent myIntent = new Intent(Home.this ,Home.class);//refreshing
                    Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();

                    startActivity(myIntent);
                    finish();                   
                }
            });     
             }
     }

     
    public void iamcallin(){ //main screen loading
    		// navigation
    	
    	mMenuDrawer.setContentView(R.layout.activity_home);//givin layout to drawer

         //setContentView(R.layout.activity_home);
          linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);//progress

            mAdapter = new HomeFragmentAdapter(getSupportFragmentManager());

            mPager = (ViewPager)findViewById(R.id.pager);
            mPager.setAdapter(mAdapter);

//            mIndicator = (TitlePageIndicator)findViewById(R.id.indicator); //to use when i will use more fragments in home screen in future
//            mIndicator.setViewPager(mPager);
            prefs = PreferenceManager.getDefaultSharedPreferences(this);///Getting preference
            prefs.registerOnSharedPreferenceChangeListener(this);
            get_values_pref(); //getting values from pre
            if(API_key.equals("nill") && API_sceret.equals("nill")){
                Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in MODIFY", Toast.LENGTH_LONG).show();
            }
    }
    
     private void get_values_pref() {//api key and stuff
          prefs = PreferenceManager.getDefaultSharedPreferences(this);//geting prefrence
            
           
            API_key =prefs.getString("api_key", "nill");
            API_sceret =prefs.getString("api_secret", "nill");
             
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
                               startActivity(new Intent(Home.this, Prefrenceactivity.class));
                           } 
                        }, 1000);

                     Toast.makeText(getApplicationContext(), "Please enter your api sceret and Key in MODIFY", Toast.LENGTH_LONG).show(); 
                 }

                super.onPostExecute(result);
            }
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

  public boolean onCreateOptionsMenu(Menu menu) {
      //Used to put dark icons on light action bar
       
      
       //getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);
       boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
       menu.add(Menu.NONE, R.id.refresh, Menu.NONE, R.string.refresh)
       .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
       .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);   
       menu.add(Menu.NONE, R.id.landing, Menu.NONE, "Modify")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
      
      
      return true;
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
//            Toast.makeText(getApplicationContext(), "Please Check your Network connection", Toast.LENGTH_LONG).show();
             mMenuDrawer.setContentView(R.layout.nointernet);//giving new layout to drawer
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()){
        
        case R.id.landing:
            //startService(intentUpdater);
             
            startActivity(new Intent(this, Prefrenceactivity.class));
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
            anmi=2;
            return true;
     
            
         case R.id.refresh:
             Intent myIntent = new Intent(this ,Home.class);//refreshing
                startActivity(myIntent);
                finish();
            return true;    
        case android.R.id.home:
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
    	 if(((TextView) v).getText().equals("Overview")){
    		 mMenuDrawer.setActiveView(v);
    		  mMenuDrawer.closeMenu();
              startActivity(new Intent(this, Home.class));    		  
    	 }
    	 else if(((TextView) v).getText().equals("All Events"))
    	 { mMenuDrawer.setActiveView(v);
 		  
          startActivity(new Intent(this, Event_activity.class));
          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
          anmi=1;
          //overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);//calling anim
    		 
    	 }
    	 else if(((TextView) v).getText().equals("Event Top"))
    	 { mMenuDrawer.setActiveView(v);
		  //mMenuDrawer.closeMenu();
          startActivity(new Intent(this, Event_top.class));    
          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
          anmi=1;
          //overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
    		 
    	 }
    	 else if(((TextView) v).getText().equals("About")){
    		 mMenuDrawer.setActiveView(v);
   		 // mMenuDrawer.closeMenu();
           startActivity(new Intent(this, About.class));
           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
           anmi=1;
    	 }
      
         
      
        mActiveViewId = v.getId();
    }
//navigaiton ending
    
    
    /// for shared pref on change call it again to update
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
        get_values_pref();
        
    }


	 
}
