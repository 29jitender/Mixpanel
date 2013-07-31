  package com.mixpanel.src.funnel;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.R;
import com.mixpanel.src.event_final;

public class Funnel_bar_graph extends SherlockActivity{
	
    public Boolean internt_count=null;// to check the connectvity

	ArrayList<String> event_name;
	ArrayList<String> event_value;
	ArrayList<String> event_overall_conv_ratio;
 	ArrayList<String> event_step_conv_ratio;
 	
	ArrayList<Bar> points ;
	BarGraph g;
	 int position=0;
	 int total=0;
	 Button next;
	 Button previous;
	 TextView lable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 
 		super.onCreate(savedInstanceState);
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
	       mCustomView = mInflater.inflate(R.layout.funnel_final_menu, null);
	       mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
	       mTitleTextView.setText(Funnel_activity.funnel_name);
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
	        	//getActionBar().setDisplayHomeAsUpEnabled(true);
	            getSupportActionBar().setIcon(android.R.color.transparent);//to remove the icon from action bar

	            // this is for the color of title bar
	             ColorDrawable colorDrawable = new ColorDrawable();
 	            	 colorDrawable.setColor(Color.parseColor("#3BB0AA")); 
 	            	 android.app.ActionBar actionBar = getActionBar();
 	            	 	actionBar.setBackgroundDrawable(colorDrawable);

	        }

	       
	        
	        if(isNetworkOnline()==true){//starting settings if internet is not working
	            internt_count=true; 
	            iamcallin();//calling the function to build everything

	        }
	             
	         else if(isNetworkOnline()==false){ 
	        	 setContentView(R.layout.nointernet);//giving new layout to drawer
	             //setContentView(R.layout.nointernet);
	            internt_count= false;
	            Button button = (Button) findViewById(R.id.nointernet_refresh);
				 
	            button.setOnClickListener(new OnClickListener() {
		 
					@Override
					public void onClick(View arg0) {
		 
						 Intent myIntent = new Intent(Funnel_bar_graph.this ,Funnel_bar_graph.class);//refreshing

	                    startActivity(myIntent);
	                    finish();  
		 
					}
		 
				});	     
	             }
	          
 		
 		
 		
 		
 		
 		
 		
 		
 	
	}

				public void iamcallin(){
					setContentView(R.layout.activity_funnel_final);
			 		event_name = new ArrayList<String>();
			 		event_value = new ArrayList<String>();
			 		event_overall_conv_ratio = new ArrayList<String>();
			 		event_step_conv_ratio = new ArrayList<String>();
			 		
			 		Intent in = getIntent();
			 		event_name=(ArrayList<String>)in.getSerializableExtra("event_name");
			 		event_value=(ArrayList<String>)in.getSerializableExtra("event_value");
			 		
			 		event_overall_conv_ratio=(ArrayList<String>)in.getSerializableExtra("overall_conv_ratio");
			 		event_step_conv_ratio=(ArrayList<String>)in.getSerializableExtra("step_conv_ratio");
			  	 
			 	 	bar_graph_call();
					
					
				}
	
	
	public void bar_graph_call(){
		////////////////////////////bar graph////////////////////////////////////
		points = new ArrayList<Bar>();
		g = (BarGraph)findViewById(R.id.bargraph);
		  next= (Button)findViewById(R.id.funnel_next);
		  previous= (Button)findViewById(R.id.funnel_previous);
		  lable= (TextView)findViewById(R.id.funnel_text_lable);
		  
		  ///////////////////////showing overall conversion and date////////////////////////////////
		  TextView overall =(TextView)findViewById(R.id.funnel_overall);
		  
		  		 float con=Float.parseFloat(event_overall_conv_ratio.get(1));
		  		 
			  overall.setText(Float.toString(con*100)); 
		  
			  TextView date =(TextView)findViewById(R.id.funnel_date);
			  
			  date.setText(Funnel_activity.from_date1+"-"+Funnel_activity.to_date1);
		  /////////////////////////////////////////////////////////////////////////////////
		  
		  
		previous.setVisibility(View.INVISIBLE);////no previous initally
		Float a =(float) (event_name.size()/4.0);
		  total=(int)Math.ceil(a);
		  Log.i("total",total+"");
		  Log.i("event size",event_name.size()+"");
		  
		  
		  
		if(event_name.size()<4){
					for (int i=0; i< event_name.size(); i++)
					{
						Bar d = new Bar();
						d.setColor(Color.parseColor("#99CC00"));
						d.setName(event_name.get(i));
						d.setValue(Integer.parseInt(event_value.get(i))); 
				 		points.add(d);
					}
						g.setBars(points);					
		}
		else{
			
					for (int i=0; i< 4; i++)
					{
						Bar d = new Bar();
						d.setColor(Color.parseColor("#99CC00"));
						d.setName(event_name.get(i));
						d.setValue(Integer.parseInt(event_value.get(i))); 
				 		points.add(d);
					}
						g.setBars(points);			
						lable.setText("Steps 1-4 of"+" "+event_name.size());	 
						////on button next click
						next.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								position=position+1; 
								
								g.removeAllLines();//removing all graph
										if(position==total-1){//for secon last
														if((event_name.size()%4)==0){
															for (int i=4*position; i< 4*position+4; i++)
															{ 		Bar d = new Bar();
																	d.setColor(Color.parseColor("#99CC00"));
																	d.setName(event_name.get(i));
																	d.setValue(Integer.parseInt(event_value.get(i))); 
															 		points.add(d);
																 
															}
															int last=4*position+4;
															int first=4*position+1;
															lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());											 
																	g.setBars(points);
																	next.setVisibility(View.INVISIBLE);
														}
														else{
															
															for (int i=4*position; i< 4*position+(event_name.size()%4); i++)
															{ 		Bar d = new Bar();
																	d.setColor(Color.parseColor("#99CC00"));
																	d.setName(event_name.get(i));
																	d.setValue(Integer.parseInt(event_value.get(i))); 
															 		points.add(d);
																 
															}
															int first=4*position+1;
															int last=event_name.size();//
															
															if(first==last){
																lable.setText("Step "+first+" of"+" "+event_name.size());											 

															}
															else{
																lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());											 

															}


																g.setBars(points);	 
																next.setVisibility(View.INVISIBLE);
														}
																						

										}	
										else if (position<total-1){
											for (int i=4*position; i< 4*position+4; i++)
											{ 		Bar d = new Bar();
													d.setColor(Color.parseColor("#99CC00"));
													d.setName(event_name.get(i));
													d.setValue(Integer.parseInt(event_value.get(i))); 
											 		points.add(d);
												 
											}
											int last=4*position+4;
											int first=4*position+1;

											lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());											 

												g.setBars(points);	
										}
											
										previous.setVisibility(View.VISIBLE);////previous on click next

										
							 }
								 
							});					
						
						///////////////////////////////////////////////////////////////////////////////////
					////on previous next click///////////////////////////////////////////////////////////
						previous.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								g.removeAllLines();//removing all graph
								position=position-1; 
								if(position==0){
									previous.setVisibility(View.INVISIBLE);
									next.setVisibility(View.VISIBLE);
								}
								for (int i=4*position; i< 4*position+4; i++)
								{ 		Bar d = new Bar();
										d.setColor(Color.parseColor("#99CC00"));
										d.setName(event_name.get(i));
										d.setValue(Integer.parseInt(event_value.get(i))); 
								 		points.add(d);
									 
								}
								int last=4*position+4;
								int first=4*position+1;
								lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());									
									g.setBars(points);	
								
									next.setVisibility(View.VISIBLE);

							 }
								 
							});					
								
			
		}
	
	
	}
 	 ////////////////////////////////////////////////////////rest activity function
	
	
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
  
	
}
