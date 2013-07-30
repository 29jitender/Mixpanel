  package com.mixpanel.src.funnel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.mixpanel.src.R;

public class Funnel_bar_graph extends SherlockActivity{
	ArrayList<String> event_name;
	ArrayList<String> event_value;
	ArrayList<Bar> points ;
	BarGraph g;
	 int position=0;
	 int total=0;
	 Button next;
	 Button previous;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_funnel_final);
 		event_name = new ArrayList<String>();
 		event_value = new ArrayList<String>();

 		Intent in = getIntent();
 		event_name=(ArrayList<String>)in.getSerializableExtra("event_name");
 		event_value=(ArrayList<String>)in.getSerializableExtra("event_value");
  	 
			for (int i=0; i< event_name.size(); i++){
				Log.i("eveeeeeeeeeeent",event_name.get(i));
			}
 	 	bar_graph_call();
	}

	public void bar_graph_call(){
		////////////////////////////bar graph////////////////////////////////////
		points = new ArrayList<Bar>();
		g = (BarGraph)findViewById(R.id.bargraph);
		  next= (Button)findViewById(R.id.funnel_next);
		  previous= (Button)findViewById(R.id.funnel_previous);
		previous.setVisibility(View.GONE);////no previous initally
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
					 
						////on button next click
						next.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								position=position+1; 
								
								g.removeAllLines();//removing all graph
										if(position==total-1){//for secon last
														if((event_name.size()%2)==0){
															for (int i=4*position; i< 4*position+4; i++)
															{ 		Bar d = new Bar();
																	d.setColor(Color.parseColor("#99CC00"));
																	d.setName(event_name.get(i));
																	d.setValue(Integer.parseInt(event_value.get(i))); 
															 		points.add(d);
																 
															}
																g.setBars(points);	 
																next.setVisibility(View.GONE);
														}
														else{
															
															for (int i=4*position; i< 4*position+(event_name.size()%2); i++)
															{ 		Bar d = new Bar();
																	d.setColor(Color.parseColor("#99CC00"));
																	d.setName(event_name.get(i));
																	d.setValue(Integer.parseInt(event_value.get(i))); 
															 		points.add(d);
																 
															}
																g.setBars(points);	 
																next.setVisibility(View.GONE);
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
									previous.setVisibility(View.GONE);
									next.setVisibility(View.VISIBLE);
								}
								for (int i=4*position; i< 4*position+4; i++)
								{ 		Bar d = new Bar();
										d.setColor(Color.parseColor("#99CC00"));
										d.setName(event_name.get(i));
										d.setValue(Integer.parseInt(event_value.get(i))); 
								 		points.add(d);
									 
								}
									g.setBars(points);	
								
									next.setVisibility(View.VISIBLE);

							 }
								 
							});					
								
			
		}
	
	
	}
	
	 
	
}
