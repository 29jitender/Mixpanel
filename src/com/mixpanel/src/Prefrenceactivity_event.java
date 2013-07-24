package com.mixpanel.src;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;


public class Prefrenceactivity_event extends SherlockPreferenceActivity{
static final String TAG="PrefsActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	 
		super.onCreate(savedInstanceState);
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	            // this is for the color of title bar
	            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
	            getSupportActionBar().setBackgroundDrawable(bg);

	        }
		//addPreferencesFromResource(R.xml.pref_event);//adding preference from prefs.xml
		addPreferencesFromResource(R.xml.pref_event);
		Preference button = (Preference)findPreference("button");
 		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
 		                @Override
 		                public boolean onPreferenceClick(Preference arg0) { 
 		                	Intent myIntent = new Intent(Prefrenceactivity_event.this ,event_final.class);//refreshing
 		                   startActivity(myIntent);
 		                   finish(); 		                  
 		                   return true;
 		                }
 		            });
	}

	
}

