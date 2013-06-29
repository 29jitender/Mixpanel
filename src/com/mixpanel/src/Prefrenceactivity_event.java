package com.mixpanel.src;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Prefrenceactivity_event extends PreferenceActivity{
static final String TAG="PrefsActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	 
		super.onCreate(savedInstanceState);
		//addPreferencesFromResource(R.xml.pref_event);//adding preference from prefs.xml
		addPreferencesFromResource(R.xml.pref_event);
		
	}

	
}

