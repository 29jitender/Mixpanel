package com.mixpanel.src;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class Prefrenceactivity_event extends SherlockPreferenceActivity{
static final String TAG="PrefsActivity";
public String name1="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	 
		super.onCreate(savedInstanceState);
		 Intent in = getIntent();
         // Get flag values from previous intent
          name1 = in.getStringExtra("flag");
          

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
 	       mTitleTextView.setText(All_api_define.event);
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
          	getActionBar().setDisplayHomeAsUpEnabled(true);
              getSupportActionBar().setIcon(android.R.color.transparent);//to remove the icon from action bar

              // this is for the color of title bar
               ColorDrawable colorDrawable = new ColorDrawable();
               if(name1.equals("Top")){//to check from where it came from
              	 colorDrawable.setColor(Color.parseColor("#3BB0AA"));
               }
               else  if(name1.equals("all"))
               {
              	 colorDrawable.setColor(Color.parseColor("#44C19F"));
               }
              android.app.ActionBar actionBar = getActionBar();
              actionBar.setBackgroundDrawable(colorDrawable);

          }
		//addPreferencesFromResource(R.xml.pref_event);//adding preference from prefs.xml
		addPreferencesFromResource(R.xml.pref_event);
		Preference button = (Preference)findPreference("button");
 		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
 		                @Override
 		                public boolean onPreferenceClick(Preference arg0) { 
 		                	
 		                	Intent myIntent = new Intent(Prefrenceactivity_event.this ,event_final.class);//refreshing

 		                	myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
 		                	myIntent.putExtra("flag", name1);
 			  				 
 		                   startActivity(myIntent);
 	 		   			    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

 		                   finish(); 		                  
 		                   return true;
 		                }
 		            });
	}

	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	         return true;
	    }
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        //This uses the imported MenuItem from ActionBarSherlock
		     
		  		switch(item.getItemId()){
		  		 
		  			 
		  			case android.R.id.home:
		  			    finish();

		  	            return true; 
					default:
						return false;	
		  						
		  		}
		  	
	  	}
}

