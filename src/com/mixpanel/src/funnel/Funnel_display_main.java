package com.mixpanel.src.funnel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.mixpanel.src.All_api_define;
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
		    
		    ////////////////
			RelativeLayout detail;
			LinearLayout list; 
			
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
    	 detail = (RelativeLayout)getView().findViewById(R.id.a22);
		  list = (LinearLayout)getView().findViewById(R.id.a21);
	 	 	bar_graph_call();

		  
 		super.onResume();
	}
    

    
	public void bar_graph_call(){
		////////////////////////////bar graph////////////////////////////////////
		points = new ArrayList<Bar>();
		g = (BarGraph)getView().findViewById(R.id.bargraph);
		  //////////////find max for range/////////////////
 		  int max= Integer.parseInt(event_value.get(0));//max value will be the first value 
		  
          Log.i("rrrrrrrrrrasdasd",max+""); 

		  ///////////////////////// 
 		g.setrange(max); 
		  ///////////////////////showing overall conversion and date////////////////////////////////
		  TextView overall =(TextView)getView().findViewById(R.id.funnel_overall);
		  
		  		 float con=Float.parseFloat(event_overall_conv_ratio.get(event_name.size()-1));
		  		 //float width =BarGraph.barWidth;
			  overall.setText(Float.toString(con*100)); 
		  
			  TextView date =(TextView)getView().findViewById(R.id.funnel_date);
			  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				 Date date_pr1 = null;
				 Date date_pr2 = null;
				try {
					date_pr1 = formatter.parse(All_api_define.from_date);
					   date_pr2 = formatter.parse(All_api_define.to_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMMM yy");
					String new_date_pr1 = formatter1.format(date_pr1);
					String new_date_pr2 = formatter1.format(date_pr2);
			  
			  date.setText( new_date_pr1+" -- "+new_date_pr2 );
		  /////////////////////////////////////////////////////////////////////////////////
		  
		  
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
						 Event_list.add(map);
					}
						listentry(Event_list);

						g.setBars(points);	
						barclick(g);
 						 
					 	overall_position=0;
					 	for(int i=0;i<event_name.size();i++){
					 		 if(Integer.parseInt(mContent)==i){
								 	left(Integer.parseInt(mContent));
									overall_position=overall_position+4*i;
									graph_name(overall_position);

					 		 }
					 	}
					 	
					 	
					 	
		                //Toast.makeTet(getActivity(), "current " + current_position+" next "+temp2+" previous "+temp1, Toast.LENGTH_SHORT).show();
 			
		}
	
	
	}
	
 public void left(int position){
	 //position=position+1; 
		//overall_position=overall_position+4;
		//graph_name(overall_position);

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
							 Event_list.add(map);
						 
					}
					int last=4*position+4;
					int first=4*position+1;

 					listentry(Event_list);

						g.setBars(points);
						barclick(g);
				}
					
 
				
	 }
		 
// public void right(){
//	 	overall_position=overall_position-4;
//		graph_name(overall_position);
//
//		g.removeAllLines();//removing all graph
//		position=position-1; 
//		if(position==0){
//			 
//		}
//		ArrayList<HashMap<String, String>> Event_list = new ArrayList<HashMap<String, String>>();
//
//		for (int i=4*position; i< 4*position+4; i++)
//		{ 		Bar d = new Bar();
//				d.setColor(Color.parseColor("#99CC00"));
//				d.setName("");///giving now value
//				d.setValue(Integer.parseInt(event_value.get(i))); 
//		 		points.add(d);
//		 		HashMap<String, String> map = new HashMap<String, String>();
//
//		 		 map.put(KEY1, event_value.get(i));
//		         map.put(VALUE1, event_name.get(i));// all values
//				 Event_list.add(map);
//			 
//		}
//		int last=4*position+4;
//		int first=4*position+1;
// 		listentry(Event_list);
//
//		g.setBars(points);	
//		barclick(g);
// 	 
//	 
// }
	
	public void writedetail(int i){
		TextView step_name=(TextView)getView().findViewById(R.id.funnel_event_name);
		TextView step_amount=(TextView)getView().findViewById(R.id.textView2);
		TextView step_time=(TextView)getView().findViewById(R.id.textView5);
		TextView step_amount_next=(TextView)getView().findViewById(R.id.textView7);
		TextView step_conv=(TextView)getView().findViewById(R.id.textView9);
		
		step_name.setText(event_name.get(i+overall_position));
		step_amount.setText(event_value.get(i+overall_position));
		
		if(event_avg_time.get(i+overall_position).equals("null")){
			step_time.setText(event_avg_time.get(i+overall_position));
		}
		else{
			int temp=Integer.parseInt(event_avg_time.get(i+overall_position));
			Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",temp+"");
 			if((temp/3600)==0){
							if((temp/60)==0){
								
								step_time.setText(temp+"seconds");
							}
							else{
			 					step_time.setText(BigDecimal.valueOf((temp/60))+"minutes");
			
							}
							
			}
			else{
				float temp1=Integer.parseInt(event_avg_time.get(i+overall_position));
				double d=temp1/3600;
				BigDecimal bd = new BigDecimal(d).setScale(1, RoundingMode.HALF_EVEN);
				d = bd.doubleValue();
					step_time.setText(d+"hours");
 
			}
			
			
		}
		
		float conv =Float.parseFloat(event_step_conv_ratio.get(i+overall_position));
		step_conv.setText(conv*100+"");
		if((i+overall_position)==(event_name.size()-1)){
			step_amount_next.setText("----");
		}
		else{
		step_amount_next.setText(event_value.get(i+overall_position+1));
		}	
			list.setVisibility(View.GONE);
			detail.setVisibility(View.VISIBLE);
		 	
			
			Button showall= (Button)getView().findViewById(R.id.funnel_list_show);//toggel list
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

				ListAdapter adapter = new SimpleAdapter(getActivity(), Event_list,  R.layout.funnel_list,
						new String[] {VALUE1,KEY1}, new int[] {
				             R.id.funnel_list_text,R.id.funnel_list_amount});
		 
					this.setListAdapter(adapter);
			 
				
			
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
