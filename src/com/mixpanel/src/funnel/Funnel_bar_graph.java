  package com.mixpanel.src.funnel;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.Event_top;
import com.mixpanel.src.Prefrenceactivity_event_top;
import com.mixpanel.src.R;
import com.mixpanel.src.SampleList;
import com.mixpanel.src.event_final;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;

public class Funnel_bar_graph extends SherlockListActivity implements View.OnClickListener{
	
    public Boolean internt_count=null;// to check the connectvity

	ArrayList<String> event_name;
	ArrayList<String> event_value;
	ArrayList<String> event_overall_conv_ratio;
 	ArrayList<String> event_step_conv_ratio;
 	ArrayList<String> event_avg_time;

 	private static String KEY1= "temp";
	 private static String VALUE1= "temp1";
 	
	ArrayList<Bar> points ;
	BarGraph g;
	int overall_position=0;
	 int position=0;
	 int total=0;
	 Button next;
	 Button previous;
	 TextView lable;
	    private int anmi=0;
	    String funnel_id=null;
	    //////////////graph labling///////////////////////
	    TextView barlable1;
	    TextView barlable2;
	    TextView barlable3;
	    TextView barlable4;
	    
	    ////////////////
		RelativeLayout detail;
		LinearLayout list;
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
					  detail = (RelativeLayout)findViewById(R.id.a22);
					  list = (LinearLayout)findViewById(R.id.a21);
					
					
			 		event_name = new ArrayList<String>();
			 		event_value = new ArrayList<String>();
			 		event_overall_conv_ratio = new ArrayList<String>();
			 		event_step_conv_ratio = new ArrayList<String>();
			 		event_avg_time = new ArrayList<String>();

			 		Intent in = getIntent();
					funnel_id=in.getStringExtra("funnel_id");
			 		event_name=(ArrayList<String>)in.getSerializableExtra("event_name");
			 		event_value=(ArrayList<String>)in.getSerializableExtra("event_value");
			 		
			 		event_overall_conv_ratio=(ArrayList<String>)in.getSerializableExtra("overall_conv_ratio");
			 		event_step_conv_ratio=(ArrayList<String>)in.getSerializableExtra("step_conv_ratio");
			 		event_avg_time=(ArrayList<String>)in.getSerializableExtra("event_avg_time");

			 	 	bar_graph_call();
					
					
				}
	
	
	public void bar_graph_call(){
		////////////////////////////bar graph////////////////////////////////////
		points = new ArrayList<Bar>();
		g = (BarGraph)findViewById(R.id.bargraph);
		  //////////////find max for range/////////////////
		  int max=0;
			
					  if(event_value.size()>5){
							  for (int i=0; i< 5; i++){
								  
								  
									  max=max+Integer.parseInt(event_value.get(i));
								  
							  }
					  	}
					  else{
						  for (int i=0; i< event_value.size(); i++){
							  
							  
							  max=max+Integer.parseInt(event_value.get(i));
						 
						  
						  
					  }
					  }
					    
		  
          Log.i("rrrrrrrrrrasdasd",max+""); 

		  /////////////////////////
          
          
          
          
          
          
          
 		g.setrange(max);
 		
		  next= (Button)findViewById(R.id.funnel_next);
		  previous= (Button)findViewById(R.id.funnel_previous);
		  lable= (TextView)findViewById(R.id.funnel_text_lable);

		  ///////////////////////showing overall conversion and date////////////////////////////////
		  TextView overall =(TextView)findViewById(R.id.funnel_overall);
		  
		  		 float con=Float.parseFloat(event_overall_conv_ratio.get(1));
		  		 //float width =BarGraph.barWidth;
			  overall.setText(Float.toString(con*100)); 
		  
			  TextView date =(TextView)findViewById(R.id.funnel_date);
			  
			  date.setText( All_api_define.from_date+"-"+ All_api_define.to_date);
		  /////////////////////////////////////////////////////////////////////////////////
		  
		  
		previous.setVisibility(View.INVISIBLE);////no previous initally
		Float a =(float) (event_name.size()/4.0);
		  total=(int)Math.ceil(a);
		  Log.i("total",total+"");
		  Log.i("event size",event_name.size()+"");
		  
		
		if(event_name.size()<4){
				ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
				overall_position=0;
					for (int i=0; i< event_name.size(); i++)
					{
						Bar d = new Bar();
						d.setColor(Color.parseColor("#99CC00"));
						d.setName("");///giving now value
						d.setValue(Integer.parseInt(event_value.get(i))); 
				 		points.add(d);
						HashMap<String, String> map = new HashMap<String, String>();

				 		 map.put(KEY1, event_value.get(i));
				         map.put(VALUE1, event_name.get(i));// all values
						 Event_list.add(map);


					}
					listentry(Event_list);
				
					previous.setVisibility(View.INVISIBLE);
					next.setVisibility(View.INVISIBLE);
					lable.setText("Steps 1-"+event_name.size()+" of"+" "+event_name.size());
					
						g.setBars(points);	
						barclick(g);
						graph_name(position);
						
		}
		else{
			ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
			overall_position=0;
					for (int i=0; i< 4; i++)
					{
						Bar d = new Bar();
						d.setColor(Color.parseColor("#99CC00"));
						d.setName("");///giving now value
						d.setValue(Integer.parseInt(event_value.get(i))); 
				 		points.add(d);
				 		HashMap<String, String> map = new HashMap<String, String>();

				 		 map.put(KEY1, event_value.get(i));
				         map.put(VALUE1, event_name.get(i));// all values
						 Event_list.add(map);
					}
						listentry(Event_list);

						g.setBars(points);	
						barclick(g);
						graph_name(position);

						lable.setText("Steps 1-4 of"+" "+event_name.size());	 
						////on button next click
						next.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								position=position+1; 
								overall_position=overall_position+4;
								g.removeAllLines();//removing all graph
										if(position==total-1){//for secon last
														if((event_name.size()%4)==0){
															ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();

															for (int i=4*position; i< 4*position+4; i++)
															{ 		Bar d = new Bar();
																	d.setColor(Color.parseColor("#99CC00"));
																	d.setName("");///giving now value
																	d.setValue(Integer.parseInt(event_value.get(i))); 
															 		points.add(d);
															 		HashMap<String, String> map = new HashMap<String, String>();

															 		 map.put(KEY1, event_value.get(i));
															         map.put(VALUE1, event_name.get(i));// all values
																	 Event_list.add(map);
																 
															}
															int last=4*position+4;
															int first=4*position+1;
															lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());											 
															listentry(Event_list);
		
															g.setBars(points);
															barclick(g);
															graph_name(position);

																	next.setVisibility(View.INVISIBLE);
														}
														else{
															ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();

															for (int i=4*position; i< 4*position+(event_name.size()%4); i++)
															{ 		Bar d = new Bar();
																	d.setColor(Color.parseColor("#99CC00"));
																	d.setName("");///giving now value
																	d.setValue(Integer.parseInt(event_value.get(i))); 
															 		points.add(d);
															 		HashMap<String, String> map = new HashMap<String, String>();

															 		 map.put(KEY1, event_value.get(i));
															         map.put(VALUE1, event_name.get(i));// all values
																	 Event_list.add(map);
																 
															}
															int first=4*position+1;
															int last=event_name.size();//
															
															if(first==last){
																lable.setText("Step "+first+" of"+" "+event_name.size());											 

															}
															else{
																lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());											 

															}

															listentry(Event_list);

																g.setBars(points);	
																barclick(g);
																graph_name(position);

																next.setVisibility(View.INVISIBLE);
														}
																						

										}	
										else if (position<total-1){
											ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
											
											for (int i=4*position; i< 4*position+4; i++)
											{ 		Bar d = new Bar();
													d.setColor(Color.parseColor("#99CC00"));
													d.setName("");///giving now value
													d.setValue(Integer.parseInt(event_value.get(i))); 
											 		points.add(d);
											 		HashMap<String, String> map = new HashMap<String, String>();

											 		 map.put(KEY1, event_value.get(i));
											         map.put(VALUE1, event_name.get(i));// all values
													 Event_list.add(map);
												 
											}
											int last=4*position+4;
											int first=4*position+1;

											lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());											 
											listentry(Event_list);

												g.setBars(points);
												barclick(g);
												graph_name(position);

										}
											
										previous.setVisibility(View.VISIBLE);////previous on click next

										
							 }
								 
							});					
						
						///////////////////////////////////////////////////////////////////////////////////
					////on previous next click///////////////////////////////////////////////////////////
						previous.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								overall_position=overall_position-4;
								g.removeAllLines();//removing all graph
								position=position-1; 
								if(position==0){
									previous.setVisibility(View.INVISIBLE);
									next.setVisibility(View.VISIBLE);
								}
								ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();

								for (int i=4*position; i< 4*position+4; i++)
								{ 		Bar d = new Bar();
										d.setColor(Color.parseColor("#99CC00"));
										d.setName("");///giving now value
										d.setValue(Integer.parseInt(event_value.get(i))); 
								 		points.add(d);
								 		HashMap<String, String> map = new HashMap<String, String>();

								 		 map.put(KEY1, event_value.get(i));
								         map.put(VALUE1, event_name.get(i));// all values
										 Event_list.add(map);
									 
								}
								int last=4*position+4;
								int first=4*position+1;
								lable.setText("Steps "+first+"-"+last+" of"+" "+event_name.size());									
								listentry(Event_list);
	
								g.setBars(points);	
								barclick(g);
								graph_name(position);

									next.setVisibility(View.VISIBLE);

							 }
								 
							});					
								
			
		}
	
	
	}
	
	
	
	public void writedetail(int i){
		TextView step_name=(TextView)findViewById(R.id.funnel_event_name);
		TextView step_amount=(TextView)findViewById(R.id.textView2);
		TextView step_time=(TextView)findViewById(R.id.textView5);
		TextView step_amount_next=(TextView)findViewById(R.id.textView7);
		TextView step_conv=(TextView)findViewById(R.id.textView9);
		
		step_name.setText(event_name.get(i+overall_position));
		step_amount.setText(event_value.get(i+overall_position));
		step_time.setText(event_avg_time.get(i+overall_position));
		step_conv.setText(event_step_conv_ratio.get(i+overall_position));
		if((i+overall_position)==(event_name.size()-1)){
			step_amount_next.setText("----");
		}
		else{
		step_amount_next.setText(event_value.get(i+overall_position+1));
		}	
			list.setVisibility(View.GONE);
			detail.setVisibility(View.VISIBLE);
			
			
			
			
			
			
			Button showall= (Button)findViewById(R.id.funnel_list_show);//toggel list
			showall.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					list.setVisibility(View.VISIBLE);
					detail.setVisibility(View.GONE);
				 }
					 
				});					

		
	}
	
	public void barclick(BarGraph bar){
		
		bar.setOnBarClickedListener(new OnBarClickedListener(){

            @Override
            public void onClick(int index) {

				writedetail(index);
            }
            
    });
	}
	
	public void listentry(ArrayList<HashMap<String, String>> Event_list){/// add data into list

				ListAdapter adapter = new SimpleAdapter(this, Event_list,  R.layout.funnel_list,
						new String[] {VALUE1,KEY1}, new int[] {
				             R.id.funnel_list_text,R.id.funnel_list_amount});
		 
					setListAdapter(adapter);
			 
				
			
 		ListView lv = getListView();
		
 		lv.setOnItemClickListener(new OnItemClickListener() {
		
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
				        int position, long id) {
					writedetail(position);
					
		 
				}
		});
						 
	}
	
	
	public void graph_name(int i){
		////////////////////////////////////////////////////graph labling////////////////////////////////////// 
		barlable1= (TextView)findViewById(R.id.funneltext1);
		barlable2= (TextView)findViewById(R.id.funneltext2);
		barlable3= (TextView)findViewById(R.id.funneltext3);
		barlable4= (TextView)findViewById(R.id.funneltext4);	 
		barlable1.setWidth((int) BarGraph.barWidth);////getting width from bar graph
		barlable2.setWidth((int) BarGraph.barWidth);
		barlable3.setWidth((int) BarGraph.barWidth);
		barlable4.setWidth((int) BarGraph.barWidth);
		
		try {
			barlable1.setText(event_name.get(i));
		} catch (Exception e) {
			barlable1.setText("");
			e.printStackTrace();
		}
		
		try {
			barlable2.setText(event_name.get(i+1));
		} catch (Exception e) {
			barlable2.setText("");
			e.printStackTrace();
		}
		
		try {
			barlable3.setText(event_name.get(i+2));
		} catch (Exception e) {
			barlable3.setText("");
			e.printStackTrace();
		}
		
		try {
			barlable4.setText(event_name.get(i+3));
		} catch (Exception e) {
			barlable4.setText("");
			e.printStackTrace();
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	  
	    @Override

	    public boolean onCreateOptionsMenu(Menu menu) {
	        //Used to put dark icons on light action bar
	        boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light; 
	        menu.add(Menu.NONE, R.id.event_top_setting, Menu.NONE, R.string.event_top_setting)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	       //  getSherlock().getMenuInflater().inflate(R.menu.event_top, menu);

	        return true;
	    }
	    

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			switch (item.getItemId()){
			
			case R.id.event_top_setting:
				//startService(intentUpdater);
				 
 				Intent myIntent = new Intent(this, Funnel_pref.class);//refreshing
             	myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//ending all activity

    		  	myIntent.putExtra("funnel_id", funnel_id);
    		  	startActivity(myIntent);
				
				overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
		            anmi=2;
				return true;
			 
			 
			default:
				return false;
				
				
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	
}
