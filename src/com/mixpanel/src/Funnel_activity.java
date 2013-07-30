package com.mixpanel.src;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

//interface Callback {
//    void methodToCallback(String response);
//}

public class Funnel_activity extends SherlockActivity implements Callback ,OnItemSelectedListener  {
 	
	 public static String click_type="";
 
	 public static  String display1="lol";
	 static JSONArray json = null;

		 
	 
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(SampleList.THEME);
		super.onCreate(savedInstanceState);		
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_funnel_activity);
		Log.i("in async sucess",display1);
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("funnels_list");
		ParseJson_object.setListener(this);
 		
		 
		
	}
	
	
	
	@Override
	public void methodToCallback(String display12) {
		// TODO Auto-generated method stub
		//String display1 =ParseJson_object.pass_values("event_name");
		 JSONObject event_data = null;

		String result =  display12.replaceAll("\\[", "").replaceAll("\\]", "");
		
		String[]  array = result.split(", ");//to get the result in list without ", "
		try {
			json = new JSONArray(display12);
			for(int i=0;i<json.length();i++){//looping inside the json array
				event_data = json.getJSONObject(i);
				 JSONObject c = event_data.getJSONObject("funnel_id");
				Log.i("json object",event_data+"");
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
        Spinner my_spin=(Spinner)findViewById(R.id.spinner2);
        my_spin.setOnItemSelectedListener(this);
        
        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,array);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        my_spin.setAdapter(aa);
 		 
       

	}


	@Override
	public void onItemSelected(AdapterView arg0, View arg1, int pos,
			long arg3) {
		//userSelection.setText(items[pos]);
	}
	@Override
	public void onNothingSelected(AdapterView arg0) {
		// TODO Auto-generated method stub
		//userSelection.setText("");
	}
	
 
 
	
	 
 
	 


 
	 
 
	
	
}
