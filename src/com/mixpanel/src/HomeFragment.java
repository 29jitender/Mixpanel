package com.mixpanel.src;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Window;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

public final class HomeFragment extends SherlockFragment implements Callback,OnChildClickListener{
    private static final String KEY_CONTENT = "TestFragment:Content";
	 public static String[] event_name=new String[5];
	 private static String TAG_event = "values";
	 
	 public static String event_interval="";//global 
	 public static String event_unit="";
	 public static String event_type="";		 
	 JSONObject event_data = null;
	 static JSONObject json = null;
	public static String name="";  // defining event name
	public JSONArray series= null;
	 public static String[] e_name=new String[5];
	 float[][] data_map=new float[10][100];//defing array 
	//list	
	 private ExpandableListView list_event;
		ArrayList<String> groupItem = new ArrayList<String>();
		ArrayList<Object> childItem = new ArrayList<Object>();
		//list
 //line graph
	  
	 float rangeY1=0;
	 float rangeY2=0;
	 float rangeY3=0;
	 float rangeY4=0;
	 float rangeY5=0;

	 Line graph1;
	 Line graph2;
	 Line graph3;
	 Line graph4;
	 Line graph5;
	 float maxrange=0;

    public static HomeFragment newInstance(String content) {
        HomeFragment fragment = new HomeFragment();

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
		 getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);//onload show loading

        Home_graph_call1 obj =new Home_graph_call1();
        obj.tocall();
       
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
        
        
    }
    
    public void newcall(){
    	 ParseJSON ParseJson_object = new ParseJSON();
			ParseJson_object.pass_values("event_top_value");//to get event top value
			ParseJson_object.setListener(this);
    	
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View view = inflater.inflate(R.layout.home_fragment_layout_line, container, false);//defing layout

        return view;        
    }

	public void calllist(){
	
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
	public void methodToCallback(String print) {
		 
		final String[] key_series= new String[100];
		int range= 7;//converting into float this is the inteval
		 try {
			json = new JSONObject(print);
			 series = json.getJSONArray("series");//taking the series 			
			 event_data = json.getJSONObject(TAG_event);
			 for(int i=0;i<8;i++){
		         	key_series[i]=series.getString(i);// taking the key value from the serioues 
			 		Log.i("and now its home_graph its series",key_series+"");

			 }
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 	 for(int out=0;out<5;out++){
 					 try {
						JSONObject c = event_data.getJSONObject(All_api_define.event_name_array[out]);	
						 	int i = 0;
						 	 
						 	for (Iterator<?> keys = c.keys(); keys.hasNext(); i++) { 	
						 		String mvalue="";
						 		Log.i("and now its home_graph",c+"");
						         mvalue=  c.getString(series.getString(i));				            
						         Log.i("and now its home_graph mvalue",mvalue+"");	
						         float k=0;
						        k =Float.parseFloat(mvalue);//converting the string into float				            
						        data_map[out][i]=k;//storing values into data_maps which will be used in graphs
 							             	 
						 	}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
		 	 }
		 	 getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon

		 	 // this is for the height of graph
		 	int density = getActivity().getResources().getDisplayMetrics().densityDpi ;
	    	RelativeLayout rl = (RelativeLayout) getView().findViewById(R.id.rel1);
	    	if (density == DisplayMetrics.DENSITY_LOW) {
	    	    rl.getLayoutParams().height = (int) 300;

	    	} else if (density == DisplayMetrics.DENSITY_MEDIUM) {
	    	    rl.getLayoutParams().height = (int) 340;

	    	}  else if (density == DisplayMetrics.DENSITY_TV) {
	    	    rl.getLayoutParams().height = (int) 380;

	    	}
	    	 else if (density == DisplayMetrics.DENSITY_HIGH) {
	     	    rl.getLayoutParams().height = (int) 420;

	     	}
	    	 else if (density == DisplayMetrics.DENSITY_XHIGH) {
	     	    rl.getLayoutParams().height = (int) 460;

	     	}
	    	 else if (density == DisplayMetrics.DENSITY_DEFAULT) {
	     	    rl.getLayoutParams().height = (int) 480;

	     	}
 
		 	/**
			* Updating parsed JSON data into graphs
			* */
  	  
		 	
		//1 	
   	     graph1 = new Line();
		LinePoint p = new LinePoint();		
		 for(int x=1;x<=range;x++){
			p = new LinePoint();
			p.setX(x);
			p.setY(data_map[0][x-1]);
			Log.i("data_map",data_map[x-1]+"");
			graph1.addPoint(p);
		}
		
		 //getting the range for y-axis
		 for(int x= 0;x<data_map.length;x++){
			 if(data_map[0][x]>rangeY1){
				 rangeY1=data_map[0][x];
			 }
			 
		 }		 
		 
		 graph1.setColor(Color.parseColor("#99CC00"));
  		 //2
		   graph2 = new Line();
			LinePoint p2 = new LinePoint();		
			 for(int x=1;x<=range;x++){
				p2 = new LinePoint();
				p2.setX(x);
				p2.setY(data_map[1][x-1]);
				Log.i("data_map",data_map[x-1]+"");
				graph2.addPoint(p2);
			}
			
			 //getting the range for y-axis
			 for(int x= 0;x<data_map.length;x++){
				 if(data_map[1][x]>rangeY2){
					 rangeY2=data_map[1][x];
				 }
				 
			 }		 
			 
			 graph2.setColor(Color.parseColor("#FFBB33"));
	  	//3
			   graph3 = new Line();
				LinePoint p3 = new LinePoint();		
				 for(int x=1;x<=range;x++){
					p3 = new LinePoint();
					p3.setX(x);
					p3.setY(data_map[2][x-1]);
					Log.i("data_map",data_map[x-1]+"");
					graph3.addPoint(p3);
				}
				
				 //getting the range for y-axis
				 for(int x= 0;x<data_map.length;x++){
					 if(data_map[2][x]>rangeY3){
						 rangeY3=data_map[2][x];
					 }
					 
				 }		 
				 
				 graph3.setColor(Color.parseColor("#AA66CC"));
				//4
				   graph4 = new Line();
					LinePoint p4 = new LinePoint();		
					 for(int x=1;x<=range;x++){
						p4 = new LinePoint();
						p4.setX(x);
						p4.setY(data_map[3][x-1]);
						Log.i("data_map",data_map[x-1]+"");
						graph4.addPoint(p4);
					}
					
					 //getting the range for y-axis
					 for(int x= 0;x<data_map.length;x++){
						 if(data_map[3][x]>rangeY4){
							 rangeY4=data_map[3][x];
						 }
						 
					 }		 
					 
					 graph4.setColor(Color.parseColor("#f41212"));
				
					//5
					   graph5 = new Line();
						LinePoint p5 = new LinePoint();		
						 for(int x=1;x<=range;x++){
							p5 = new LinePoint();
							p5.setX(x);
							p5.setY(data_map[4][x-1]);
							Log.i("data_map",data_map[x-1]+"");
							graph5.addPoint(p5);
						}
						
						 //getting the range for y-axis
						 for(int x= 0;x<data_map.length;x++){
							 if(data_map[4][x]>rangeY5){
								 rangeY5=data_map[4][x];
							 }
							 
						 }		 
						 
						 graph5.setColor(Color.parseColor("#25d3ee"));	 
					 
					 float[] therange = new float []{rangeY1,rangeY2,rangeY3,rangeY4,rangeY5}; 
					 for (int i=0;i<therange.length;i++){
						 if(therange[i]>maxrange){
							 maxrange=therange[i];
						 }
						 
					 }
					 LineGraph li = (LineGraph)getView().findViewById(R.id.linegraph_home);
		        		li.addLine(graph1);
		         		li.addLine(graph2);
		        		 
		        		//li.setLineToFill(2);//change filling
		        		li.addLine(graph3);
		        		 
		        		//li.setLineToFill(2);//change filling
		        		li.addLine(graph4);
		        		li.addLine(graph5);
		        		li.setRangeY(0, maxrange);
		

//calling list 
		
		seteventname();
		senteventname_value();
		
	    final Button b1=(Button)getView().findViewById(R.id.button11);
	    final Button b2=(Button)getView().findViewById(R.id.button21);
	    final Button b3=(Button)getView().findViewById(R.id.button31);
	    final  Button b4=(Button)getView().findViewById(R.id.button41);
	    final Button b5=(Button)getView().findViewById(R.id.button51);

	    
		list_event = (ExpandableListView) getView().findViewById(R.id.eventname_list);

		list_event.setAdapter(new Home_list_adapter(getActivity(), groupItem, childItem));

		list_event.setOnChildClickListener(this);
        	
		
        list_event.setOnGroupCollapseListener(new OnGroupCollapseListener() {// when it collops
			
			@Override
			public void onGroupCollapse(int arg0) {
 
 				b1.setVisibility(View.VISIBLE);
        		b2.setVisibility(View.VISIBLE);
        		b3.setVisibility(View.VISIBLE);
        		b4.setVisibility(View.VISIBLE);
        		b5.setVisibility(View.VISIBLE);

        		b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button1));
        		b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button2));
        		b3.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button3));
        		b4.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button4));
        		b5.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button5));
        		LineGraph li = (LineGraph)getView().findViewById(R.id.linegraph_home);
        		li.addLine(graph1);
         		li.addLine(graph2);
        		 
         		li.addLine(graph3);
        		 
         		li.addLine(graph4);
        		li.addLine(graph5);
        		li.setRangeY(0, maxrange);
        		
        		
        		
			}
		});
			 
		list_event.setOnGroupExpandListener(new OnGroupExpandListener() { // this to auto close the list when something is already clicked
	        int previousGroup = -1;

	        
	        
	        
	        @Override
	        public void onGroupExpand(int groupPosition) {
  	            if(groupPosition != previousGroup){
 	            	list_event.collapseGroup(previousGroup);
 	 	           

	            previousGroup = groupPosition;
	            
	            }
            	LineGraph li = (LineGraph)getView().findViewById(R.id.linegraph_home);

  	            switch(groupPosition){
	            case 0:
	            	
	        		li.removeAllLines();
	            	li.addLine(graph1);
	        		li.setRangeY(0, maxrange);

	            		b1.setVisibility(View.VISIBLE);
	            		b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button1));
		            	b2.setVisibility(View.GONE);
		            	b3.setVisibility(View.GONE);
		            	b4.setVisibility(View.GONE);
		            	b5.setVisibility(View.GONE);
  	            		break;
	            case 1:
	            	li.removeAllLines();
	            	li.addLine(graph2);
	        		li.setRangeY(0, maxrange);
	            	
            		b1.setVisibility(View.VISIBLE);
            		b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button2));
	            	//b1.setBackgroundColor(getResources().getColor(R.color.c2));
	            	b2.setVisibility(View.GONE);
	            	b3.setVisibility(View.GONE);
	            	b4.setVisibility(View.GONE);
	            	b5.setVisibility(View.GONE);
               		break;
	            case 2:
	            	li.removeAllLines();
	            	li.addLine(graph3);
	        		li.setRangeY(0, maxrange);
	        		
	        		
            		b1.setVisibility(View.VISIBLE);
            		b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button3));
	            	b3.setVisibility(View.GONE);
	            	b2.setVisibility(View.GONE);
	            	b4.setVisibility(View.GONE);
	            	b5.setVisibility(View.GONE);
             		break;
	            case 3:
	            	li.removeAllLines();
	            	li.addLine(graph4);
	        		li.setRangeY(0, maxrange);
	            	
            		b1.setVisibility(View.VISIBLE);
            		b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button4));
	            	b4.setVisibility(View.GONE);
	            	b3.setVisibility(View.GONE);
	            	b2.setVisibility(View.GONE);
	            	b5.setVisibility(View.GONE);
             		break;
	            case 4:
	            	li.removeAllLines();
	            	li.addLine(graph5);
	        		li.setRangeY(0, maxrange);
	            	
            		b1.setVisibility(View.VISIBLE);
            		b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button5));
	            	b5.setVisibility(View.GONE);
	            	b3.setVisibility(View.GONE);
	            	b4.setVisibility(View.GONE);
	            	b2.setVisibility(View.GONE);
             		break;
            	default:
             		b1.setVisibility(View.VISIBLE);
            		b2.setVisibility(View.VISIBLE);
            		b3.setVisibility(View.VISIBLE);
            		b4.setVisibility(View.VISIBLE);
            		b5.setVisibility(View.VISIBLE);

            		b1.setBackgroundColor(getResources().getColor(R.color.c1));
	            	b2.setBackgroundColor(getResources().getColor(R.color.c2));
	            	b3.setBackgroundColor(getResources().getColor(R.color.c3));
	            	b4.setBackgroundColor(getResources().getColor(R.color.c4));
	            	b5.setBackgroundColor(getResources().getColor(R.color.c5));

            		break;
            		
	            }
	        }
	        	
	       
	        
	    });
		
		
		
		ViewTreeObserver vto = list_event.getViewTreeObserver();/// this is to move the icon to right

        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	list_event.setIndicatorBounds(list_event.getRight()- 80, list_event.getWidth());
            }
        });
		
		
		
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		 
	}
	//list 
	
	public void seteventname() {
		for(int i=0;i<5;i++){
			groupItem.add(All_api_define.event_name_array[i]);
			
		}
		 
	}

	 

	public void senteventname_value() {
		/**
		 * Add Data For event1
		 */
		ArrayList<String> child = new ArrayList<String>();
		
			try {
				for(int i=0;i<7;i++){
				child.add(series.getString(i)+"          "+Math.round(data_map[0][i]));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 		 
		childItem.add(child);

		/**
		 * Add Data For event 2
		 */
		child = new ArrayList<String>();
		try {
			for(int i=0;i<7;i++){
			child.add(series.getString(i)+"          "+Math.round(data_map[1][i]));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 		 
	childItem.add(child);
		/**
		 * Add Data For event 3
		 */
		child = new ArrayList<String>();
		try {
			for(int i=0;i<7;i++){
			child.add(series.getString(i)+"          "+Math.round(data_map[2][i]));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		childItem.add(child);

		/**
		 * Add Data For event 4
		 */
		child = new ArrayList<String>();
		try {
			for(int i=0;i<7;i++){
			child.add(series.getString(i)+"          "+Math.round(data_map[3][i]));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		childItem.add(child);

		/**
		 * Add Data For event 5
		 */
		child = new ArrayList<String>();
		try {
			for(int i=0;i<7;i++){
			child.add(series.getString(i)+"          "+Math.round(data_map[4][i]));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		childItem.add(child);

		
	}
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Toast.makeText(getActivity(), "Clicked On Child" + v.getTag(),
				Toast.LENGTH_SHORT).show();
		return true;
	}
	
	 class Home_graph_call1 implements Callback{
			JSONArray event_data = null;
			  JSONObject json = null;
			 private static final String TAG_event = "events";
			 public   String[] e_name=new String[5];
			 
			 public void tocall(){
				 
				 	ParseJSON ParseJson_object = new ParseJSON();
					ParseJson_object.pass_values("event_top");
					ParseJson_object.setListener(this);
			 }
			 
			@Override
			public void methodToCallback(String print) {
				 		 try {
								json = new JSONObject(print);  
								event_data = json.getJSONArray(TAG_event);
								
								// looping through All data
								for(int i = 0; i < event_data.length(); i++){
								    JSONObject c = event_data.getJSONObject(i);		     
								    // Storing each json item in variable
								    String Event = c.getString("event");						    
								    e_name[i]=  Event;//name of event 
									 Log.i("in home graph call",e_name[i]);

								}					
								All_api_define.event_name_array=e_name;//sending this to all api define
								All_api_define.event_top_value();
								newcall();
									
								 	 
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								
								e.printStackTrace();
							}

		}


		}
 
    
}
