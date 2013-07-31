package com.mixpanel.src.funnel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mixpanel.src.All_api_define;
import com.mixpanel.src.R;


public class Funnel_pref extends SherlockPreferenceActivity{
static final String TAG="PrefsActivity";
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
	 
		super.onCreate(savedInstanceState);
		Intent in = getIntent();
		funnel_id=in.getStringExtra("funnel_id");

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
	       mTitleTextView.setText("Funnel Edit");
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
	            colorDrawable.setColor(Color.parseColor("#3BB0AA"));
	            android.app.ActionBar actionBar = getActionBar();
	            actionBar.setBackgroundDrawable(colorDrawable);
	 
	        }
		//addPreferencesFromResource(R.xml.pref_event);//adding preference from prefs.xml
		addPreferencesFromResource(R.xml.funnel);
		Preference button = (Preference)findPreference("button");
 		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
 		                @Override
 		                public boolean onPreferenceClick(Preference arg0) { 
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
 							  interval=(int) (diff / (1000 * 60 * 60 * 24)+2);////////////calculating interval
 		                	


 		                	
 		                	  All_api_define.funnel_id=funnel_id;
 			        		  All_api_define.to_date=to_date1;
 			        		  All_api_define.from_date=from_date1;
 			        		  All_api_define.interval_funnel=Integer.toString(interval);
 			        		  All_api_define.funnels();//callin it once
 		                	Intent myIntent = new Intent(Funnel_pref.this ,Funnal_final.class);//refreshing
 		                	myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//ending all activity
 		        		  	myIntent.putExtra("funnel_id", funnel_id);

 		                   startActivity(myIntent);
	 		   			    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

 		                   finish(); 		                  
 		                   return true;
 		                }
 		            });
 		
 		
 		Preference from = (Preference)findPreference("funnel_pref_from");
 		from.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
 		                @Override
 		                public boolean onPreferenceClick(Preference arg0) { 
					        showDialog(from_date);

 		                   return true;
 		                }
 		            });
 		
 		Preference To = (Preference)findPreference("funnel_pref_to");
 		To.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
 		                @Override
 		                public boolean onPreferenceClick(Preference arg0) { 
					    	showDialog(to_date);
     
 		                   return true;
 		                }
 		            });
 		
 		
 		
 		
 		
		
	}
	
	

							 ///////////////////////////////////date picker/////////////////////////////////////////
							
						
//						
//						public void onDateDialogButtonClick(View v) {
//						        showDialog(from_date);
//						}
//						public void onDateDialogButtonClick1(View v) {
//						    	showDialog(to_date);
//						}
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
								             Toast.makeText(Funnel_pref.this, "Date picked: " + strDate, Toast.LENGTH_SHORT).show();
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
						
								         Toast.makeText(Funnel_pref.this, "Date picked: " + strDate, Toast.LENGTH_SHORT).show();
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

