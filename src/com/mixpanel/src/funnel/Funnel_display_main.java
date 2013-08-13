package com.mixpanel.src.funnel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.mixpanel.src.R;
 
public final class Funnel_display_main extends ListFragment {
	//graphdata
	int current_position=0;
	  public Boolean internt_count=null;// to check the connectvity
 		ArrayList<String> event_name;
		ArrayList<String> event_value;
		ArrayList<String> event_overall_conv_ratio;
	 	ArrayList<String> event_step_conv_ratio;
	 	ArrayList<String> event_avg_time;

	 	private static String KEY1= "temp";
		 private static String VALUE1= "temp1";
		 private static String VALUE2= "temp2";
	 	
		ArrayList<Bar> points ;
		BarGraph g;
		int overall_position=0;
		 int position=0;
		 int total=0;
		 
 		    private int anmi=0;
		    String funnel_id=null;
		    //////////////graph labling///////////////////////
		    TextView barlable1;
		    TextView barlable2;
		    TextView barlable3;
		    TextView barlable4;
		    Boolean check;
		    Button scroll ;
		    ////////////////
		    ScrollView scrollview;
		    RelativeLayout scrolllist;
 
	/////////////////////////////
    private static final String KEY_CONTENT = "TestFragment:Content";

    public static Funnel_display_main newInstance(String content) {
        Funnel_display_main fragment = new Funnel_display_main();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    	
		event_name = new ArrayList<String>();
		event_value = new ArrayList<String>();
		event_overall_conv_ratio = new ArrayList<String>();
		event_step_conv_ratio = new ArrayList<String>();
		event_avg_time = new ArrayList<String>();
		
 		funnel_id= Funnel_display.funnel_id;
 		event_name= Funnel_display.event_name;
 		event_value= Funnel_display.event_value;
 		
 		event_overall_conv_ratio= Funnel_display.event_overall_conv_ratio;
 		event_step_conv_ratio= Funnel_display.event_step_conv_ratio;
 		event_avg_time= Funnel_display.event_avg_time;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.activity_funnel_final, container, false);//defing layout
    	 
        return view;
    }
    @Override
	public void onResume() {     
    	scrollview = (ScrollView) getView().findViewById(R.id.scr);  
    	scrolllist =(RelativeLayout) getView().findViewById(R.id.scrolllist);
  	 	 	bar_graph_call();

  	 	 scroll =(Button) getView().findViewById(R.id.funnel_scroll);
	     check = false;

	scroll.setOnClickListener(new View.OnClickListener() {

		  @Override
		  public void onClick(View v) {
			  if(check==true){

				  scrolllist.setVisibility(View.GONE);
				  scrollview.post(new Runnable() {

					    @Override
					    public void run() {
					         
					       scrollview.fullScroll(ScrollView.FOCUS_UP);
					       check=false;
						    scroll.setText(R.string.down);

					    }
					});
				  }else{

					  scrolllist.setVisibility(View.VISIBLE);
					  scrollview.post(new Runnable() {

						    @Override
						    public void run() {
						        // to scroll down
						    	TableRow tab = (TableRow) getView().findViewById(R.id.tableRow6);
						    	scrollview.scrollTo(0, tab.getBottom());

						        //scrollview.fullScroll(ScrollView.FOCUS_DOWN);
							    check=true;
							    scroll.setText(R.string.up);

						    }
						});
	            }
			  
			 
			  }

		});

	
 		super.onResume();
	}
    

    
	public void bar_graph_call(){
		////////////////////////////bar graph////////////////////////////////////
		points = new ArrayList<Bar>();
		g = (BarGraph)getView().findViewById(R.id.bargraph);
		  //////////////find max for range/////////////////
 		  int max= Integer.parseInt(event_value.get(0));//max value will be the first value 
		  
 
		  ///////////////////////// 
 		g.setrange(max); 
		   
		  
 		Float a =(float) (event_name.size()/4.0);
		  total=(int)Math.ceil(a);
		  Log.i("total",total+"");
		  Log.i("event size",event_name.size()+"");
		  
		
		if(event_name.size()<4){
				ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
				overall_position=0;
				graph_name(overall_position);

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
				
				 
					
						g.setBars(points);	
						barclick(g);
						
		}
		else{
			ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
			overall_position=0;
			graph_name(overall_position);

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
				         
				         String time;
				         if(event_avg_time.get(i+overall_position).equals("null")){
				 			time =event_avg_time.get(i+overall_position);
				 		}
				 		else{
				 			int temp=Integer.parseInt(event_avg_time.get(i+overall_position));
 				  			if((temp/3600)==0){
				 							if((temp/60)==0){
				 								time =temp+"seconds";
				 								//step_time.setText(temp+"seconds");
				 							}
				 							else{
				 								time =BigDecimal.valueOf((temp/60))+"minutes";
				 			 					//step_time.setText(BigDecimal.valueOf((temp/60))+"minutes");
				 			
				 							}
				 							
				 			}
				 			else{
				 				float temp1=Integer.parseInt(event_avg_time.get(i+overall_position));
				 				double d1=temp1/3600;
				 				BigDecimal bd = new BigDecimal(d1).setScale(1, RoundingMode.HALF_EVEN);
				 				d1 = bd.doubleValue();
				 				time=d1+"hours";
				 					//step_time.setText(d+"hours");
				  
				 			}
				 			
				 			
				 		}
				         
				         Log.i("timeeeeeeeeeeeeeeee",time);
				 		 map.put(VALUE2, time);
				         Log.i("timeeeeeeeeeeeeeeee",map+"");


				           
				         
						 Event_list.add(map);
					}
						listentry(Event_list);

						g.setBars(points);	
						
						barclick(g);
 						
						  
						
					 	overall_position=0;
					 	for(int i=0;i<event_name.size();i++){///adding data
					 		 if(Integer.parseInt(mContent)==i){
								 	left(Integer.parseInt(mContent));
									overall_position=overall_position+4*i;
									graph_name(overall_position);

					 		 }
					 	}
					 	
							  
		}
	
	
	}
	 
		
	 
 public void left(int position){
	 
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
									         
									         String time;
									         if(event_avg_time.get(i+overall_position).equals("null")){
									 			time =event_avg_time.get(i+overall_position);
									 		}
									 		else{
									 			int temp=Integer.parseInt(event_avg_time.get(i+overall_position));
					 				  			if((temp/3600)==0){
									 							if((temp/60)==0){
									 								time =temp+"seconds";
									 								//step_time.setText(temp+"seconds");
									 							}
									 							else{
									 								time =BigDecimal.valueOf((temp/60))+"minutes";
									 			 					//step_time.setText(BigDecimal.valueOf((temp/60))+"minutes");
									 			
									 							}
									 							
									 			}
									 			else{
									 				float temp1=Integer.parseInt(event_avg_time.get(i+overall_position));
									 				double d1=temp1/3600;
									 				BigDecimal bd = new BigDecimal(d1).setScale(1, RoundingMode.HALF_EVEN);
									 				d1 = bd.doubleValue();
									 				time=d1+"hours";
									 					//step_time.setText(d+"hours");
									  
									 			}
									 			
									 			
									 		}
									         
 									 		 map.put(VALUE2, time);
 
 									 		 Event_list.add(map);
										 
									}
									int last=4*position+4;
									int first=4*position+1;
									
 									listentry(Event_list);

									g.setBars(points);
									barclick(g);
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
									         
									         
									         String time;
									         if(event_avg_time.get(i+overall_position).equals("null")){
									 			time =event_avg_time.get(i+overall_position);
									 		}
									 		else{
									 			int temp=Integer.parseInt(event_avg_time.get(i+overall_position));
					 				  			if((temp/3600)==0){
									 							if((temp/60)==0){
									 								time =temp+"seconds";
									 								//step_time.setText(temp+"seconds");
									 							}
									 							else{
									 								time =BigDecimal.valueOf((temp/60))+"minutes";
									 			 					//step_time.setText(BigDecimal.valueOf((temp/60))+"minutes");
									 			
									 							}
									 							
									 			}
									 			else{
									 				float temp1=Integer.parseInt(event_avg_time.get(i+overall_position));
									 				double d1=temp1/3600;
									 				BigDecimal bd = new BigDecimal(d1).setScale(1, RoundingMode.HALF_EVEN);
									 				d1 = bd.doubleValue();
									 				time=d1+"hours";
									 					//step_time.setText(d+"hours");
									  
									 			}
									 			
									 			
									 		}
									         
 									 		 map.put(VALUE2, time);
 
 
									         
											 Event_list.add(map);
										 
									}
									int first=4*position+1;
									int last=event_name.size();//
									
									 
									listentry(Event_list);

										g.setBars(points);	
										barclick(g);
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
					         String time;
					         if(event_avg_time.get(i+overall_position).equals("null")){
					 			time =event_avg_time.get(i+overall_position);
					 		}
					 		else{
					 			int temp=Integer.parseInt(event_avg_time.get(i+overall_position));
	 				  			if((temp/3600)==0){
					 							if((temp/60)==0){
					 								time =temp+"seconds";
					 								//step_time.setText(temp+"seconds");
					 							}
					 							else{
					 								time =BigDecimal.valueOf((temp/60))+"minutes";
					 			 					//step_time.setText(BigDecimal.valueOf((temp/60))+"minutes");
					 			
					 							}
					 							
					 			}
					 			else{
					 				float temp1=Integer.parseInt(event_avg_time.get(i+overall_position));
					 				double d1=temp1/3600;
					 				BigDecimal bd = new BigDecimal(d1).setScale(1, RoundingMode.HALF_EVEN);
					 				d1 = bd.doubleValue();
					 				time=d1+"hours";
					 					//step_time.setText(d+"hours");
					  
					 			}
					 			
					 			
					 		}
					         
 					 		 map.put(VALUE2, time);
 
 
 					         
					         
							 Event_list.add(map);
						 
					}
					int last=4*position+4;
					int first=4*position+1;

 					listentry(Event_list);

						g.setBars(points);
						barclick(g);
				}
					
 
				
	 }
 
	 
	public void barclick(BarGraph bar){
		bar.setOnBarClickedListener(new OnBarClickedListener(){

            @Override
            public void onClick(int index) {
            	
 
  	           
            }
            
    });
	}
	
	public void listentry(ArrayList<HashMap<String, String>> Event_list){/// add data into list

				ListAdapter adapter = new SimpleAdapter(getActivity(), Event_list,  R.layout.funnel_list,
						new String[] {VALUE1,KEY1,VALUE2}, new int[] {
				             R.id.funnel_list_text,R.id.funnel_list_amount,R.id.funnel_list_time});
		 
					this.setListAdapter(adapter);
			 
				
			
 		ListView lv = getListView();
		
 		 				 
	}
	
	
	public void graph_name(int i){
		////////////////////////////////////////////////////graph labling////////////////////////////////////// 
		barlable1= (TextView)getView().findViewById(R.id.funneltext1);
		barlable2= (TextView)getView().findViewById(R.id.funneltext2);
		barlable3= (TextView)getView().findViewById(R.id.funneltext3);
		barlable4= (TextView)getView().findViewById(R.id.funneltext4);	 
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
	
	
    
 ////////////////////////graph over//////////////////////////////////////////////////////////
    
    
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
