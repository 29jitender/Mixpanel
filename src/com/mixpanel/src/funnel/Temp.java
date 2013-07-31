package com.mixpanel.src.funnel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.mixpanel.src.Event_top;
import com.mixpanel.src.Home;
import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.All_api_define;

import com.mixpanel.src.R;

 

public class Temp extends SherlockActivity implements   com.mixpanel.src.Callback      {
	 public static  String display1="lol";
	 static JSONArray json = null;
	 private Spinner   spinner2;
	 ArrayList<HashMap<String, String>> Event_list;
	 HashMap<String, String> map ;
	 public static String funnel_id=null;
	 public static String funnel_name=null;

	 public static	 String to_date1=null;
	 public static	 String from_date1=null;
	 public static int interval=0;
	 //////////////////date////////////////////
 
        private static final int from_date = 0;
        private static final int to_date = 1;
        

 	  
	 ////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_funnel_activity);
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("funnels_list");
		ParseJson_object.setListener(this);
  	}

	@Override
	public void methodToCallback(String response) {
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list = new ArrayList<String>();
 		 Event_list = new ArrayList<HashMap<String, String>>();

 		 map = new HashMap<String, String>();
         
 			try {
 				
				json = new JSONArray(response);
				for(int i=0;i<json.length();i++){
					String name=null;
					String id=null;
					JSONObject objectInArray = json.getJSONObject(i);
					name =objectInArray.getString("name");
					id =objectInArray.getString("funnel_id");
					map.put("name", name);
					map.put(name, id);
				    Event_list.add(map);
				    list.add(name);

					
				}
				 
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		

			
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(dataAdapter);
 			spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){
 				  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
 						Toast.makeText(parent.getContext(), 
 							"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString()+"lol "+map.get( parent.getItemAtPosition(pos).toString()) ,
 							Toast.LENGTH_SHORT).show();
 							funnel_id=map.get( parent.getItemAtPosition(pos).toString());
 							funnel_name=parent.getItemAtPosition(pos).toString();
 					  }
 					 
 					  @Override
 					  public void onNothingSelected(AdapterView<?> arg0) {
 						// TODO Auto-generated method stub
 					  }
     			
     		}); 
     	    
//////////////////////next activity ie funnel final//////////////////
 			
 			
					Button button= (Button) findViewById(R.id.funnel_final);
					button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date  date1=null;
						Date  date2=null;
						 try {
							  date1 = formatter.parse(from_date1);
							  date2 = formatter.parse(to_date1);

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						long diff = date2.getTime() - date1.getTime();
						  interval=(int) (diff / (1000 * 60 * 60 * 24)+1);////////////calculating interval
						  
						  Intent myIntent = new Intent(Temp.this ,Funnal_final.class);//refreshing
						 
		                    startActivity(myIntent);
						
					 }
					});
					/////////////////////////
						
						}
	
	 ///////////////////////////////////date picker/////////////////////////////////////////
	
 

 public void onDateDialogButtonClick(View v) {
         showDialog(from_date);
 }
 public void onDateDialogButtonClick1(View v) {
     showDialog(to_date);
}
 @Override
 protected Dialog onCreateDialog(int id) {
         switch (id) {    
         case from_date:
                 DatePickerDialog dateDlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                 public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		             Time chosenDate = new Time();        
		             chosenDate.set(dayOfMonth, monthOfYear, year);
		             long dtDob = chosenDate.toMillis(true);
		             CharSequence strDate = DateFormat.format("yyyy'-'MM'-'dd", dtDob);
		             from_date1=(String) strDate;
		             Toast.makeText(Temp.this, "Date picked: " + strDate, Toast.LENGTH_SHORT).show();
                 		}
		                 }, 2011,0, 1);
		     
		                 dateDlg.setMessage("Please Select From Date");
		
		                 return dateDlg;
         	case to_date:
		     DatePickerDialog dateDlg1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
		     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		         Time chosenDate = new Time();        
		         chosenDate.set(dayOfMonth, monthOfYear, year);
		         long dtDob = chosenDate.toMillis(true);
		         CharSequence strDate = DateFormat.format("yyyy'-'MM'-'dd", dtDob);
	             to_date1=(String) strDate;

		         Toast.makeText(Temp.this, "Date picked: " + strDate, Toast.LENGTH_SHORT).show();
		     		}
		             }, 2011,0, 1);
		 
		             dateDlg1.setMessage("Please Select To Date");
		
		             return dateDlg1;
 		
				     }
         return null;

 }

 @Override
 protected void onPrepareDialog(int id, Dialog dialog) {
         super.onPrepareDialog(id, dialog);
         switch (id) {
                  
         case from_date:
                 DatePickerDialog dateDlg = (DatePickerDialog) dialog;
                 int iDay,iMonth,iYear;

			     // Always init the date picker to today's date
			     Calendar cal = Calendar.getInstance();
			     iDay = cal.get(Calendar.DAY_OF_MONTH);
			     iMonth = cal.get(Calendar.MONTH);
			     iYear = cal.get(Calendar.YEAR);
			     dateDlg.updateDate(iYear, iMonth, iDay);
			                 break;
         case to_date:
             DatePickerDialog dateDlg1 = (DatePickerDialog) dialog;
             int iDay1,iMonth1,iYear1;

		     // Always init the date picker to today's date
		     Calendar cal1 = Calendar.getInstance();
		     iDay1 = cal1.get(Calendar.DAY_OF_MONTH);
		     iMonth1 = cal1.get(Calendar.MONTH);
		     iYear1 = cal1.get(Calendar.YEAR);
		     dateDlg1.updateDate(iYear1, iMonth1, iDay1);
		                 break;	                 
			                 
         }
         return;
 }
	
	
	/////////////////
 
 
 
		  
		  
}
 
 