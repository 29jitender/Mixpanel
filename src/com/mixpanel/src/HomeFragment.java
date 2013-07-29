package com.mixpanel.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

public final class HomeFragment extends SherlockFragment implements Callback,OnChildClickListener{
    private static final String KEY_CONTENT = "TestFragment:Content";
	 public static String[] event_name=new String[5];
	 private static String TAG_event = "values";
    	LinearLayout linlaHeaderProgress;
    	RelativeLayout line_table;
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
	 LineGraph li;
	 LineGraph li1;
	 LineGraph li2;
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
 
        Home_graph_call1 
        
        obj =new Home_graph_call1();
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
    	 linlaHeaderProgress = (LinearLayout) getView().findViewById(R.id.linlaHeaderProgress1);//progress
    	 line_table = (RelativeLayout) getView().findViewById(R.id.line_table);//progress

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
 
		 	 // this is for the height of graph
 	    	RelativeLayout rl = (RelativeLayout) getView().findViewById(R.id.rel1);
	    	  
	        Display display = getActivity().getWindowManager().getDefaultDisplay(); 		 
	  		int height = display.getHeight();  
    	    rl.getLayoutParams().height = (int) (height/2.2);
   
 
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
					    	li = (LineGraph)getView().findViewById(R.id.linegraph_home);
		            	 li2 = (LineGraph)getView().findViewById(R.id.linegraph_home1);
 
		        		li.addLine(graph1);
		         		li.addLine(graph2);
		        		 
		        		//li.setLineToFill(2);//change filling
		        		li.addLine(graph3);
		        		 
		        		//li.setLineToFill(2);//change filling
		        		li.addLine(graph4);
		        		li.addLine(graph5);
		        		li.setRangeY(0, maxrange);
		        		///for loader
		        	    linlaHeaderProgress.setVisibility(View.GONE);//hiding the loader
		        	    rl.setVisibility(View.VISIBLE);
		        	    line_table.setVisibility(View.VISIBLE);
///for loader
		        	   
		        	    ///////////////graph touch////////////////////////////////////////////
		        	    
		        	    li.setOnPointClickedListener(new OnPointClickedListener(){

		        			@Override
		        			public void onClick(int lineIndex, int pointIndex) { 
		        				 list_event.expandGroup(lineIndex);  //can use this later if want to open list by clicking graph
		                 		 li.setVisibility(View.GONE);//hiding graph

		        			}
		        			
		        		}); 
		        	    
		        	  
		        	    ////////////////////////////////////////////////////////////////////////// 	   
		        
//////////////////////this is for graph labling/////////////////////////////////////////////////////////////////////////////
		        	    
		        	    //series.getString(i)
		        	    TextView lable1=(TextView)getView().findViewById(R.id.hometext1);    
		        	    TextView lable2=(TextView)getView().findViewById(R.id.hometext2);    
		        	    TextView lable3=(TextView)getView().findViewById(R.id.hometext3);    
		        	    TextView lable4=(TextView)getView().findViewById(R.id.hometext4);    
		        	    TextView lable5=(TextView)getView().findViewById(R.id.hometext5);    
		        	    TextView lable6=(TextView)getView().findViewById(R.id.hometext6);    
		        	    TextView lable7=(TextView)getView().findViewById(R.id.hometext7);    
			            String newFormat[]=new String[10];

		        	    for(int i=0;i<7;i++){
					        	    String key = null;
											try {
												key = series.getString(i);
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
					        	    SimpleDateFormat formatter ; 
						            Date date = null ;
						            formatter = new SimpleDateFormat("yyyy-MM-dd");
												try {
													date = formatter.parse(key);
												} catch (ParseException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
									SimpleDateFormat formatter1 = new SimpleDateFormat("E");
								    newFormat[i] = formatter1.format(date);
					        	    
		        	    }
		        	    lable1.setText(newFormat[0]);
		        	    lable2.setText(newFormat[2]);
		        	    lable3.setText(newFormat[2]);
		        	    lable4.setText(newFormat[3]);
		        	    lable5.setText(newFormat[4]);
		        	    lable6.setText(newFormat[5]);
		        	    lable7.setText(newFormat[6]);
		        	    
		        	    
///////////////////////////////graph lablin end//////////////////////////////////////////////////////////////////////////////		        	    
		        	    
		        	    
		        	    
		        	    
		        	    
		        	    

		        	    
//calling list 
		
		seteventname();
		try {
			senteventname_value();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    final TextView b1=(TextView)getView().findViewById(R.id.button11);
	    final TextView b2=(TextView)getView().findViewById(R.id.button21);
	    final TextView b3=(TextView)getView().findViewById(R.id.button31);
	    final  TextView b4=(TextView)getView().findViewById(R.id.button41);
	    final TextView b5=(TextView)getView().findViewById(R.id.button51);

	    
		list_event = (ExpandableListView) getView().findViewById(R.id.eventname_list);

		list_event.setAdapter(new Home_list_adapter(getActivity(), groupItem, childItem));

		list_event.setOnChildClickListener(this);
 
		
        list_event.setOnGroupCollapseListener(new OnGroupCollapseListener() {// when it collops
			
			@Override
			public void onGroupCollapse(int arg0) {
 
				//moving icon on click
				LayoutParams params = new LayoutParams(
            	        LayoutParams.FILL_PARENT,      
            	        LayoutParams.FILL_PARENT
            	);
				int i=dpToPx(6);
				
            	params.setMargins(0, i, 0, 0);            	
            	b1.setLayoutParams(params);
            	//////////////////
				
 				b1.setVisibility(View.VISIBLE);
        		b2.setVisibility(View.VISIBLE);
        		b3.setVisibility(View.VISIBLE);
        		b4.setVisibility(View.VISIBLE);
        		b5.setVisibility(View.VISIBLE);
        		
        		b1.setTextColor(getResources().getColor(R.color.c1));
        		b2.setTextColor(getResources().getColor(R.color.c2));
        		b3.setTextColor(getResources().getColor(R.color.c3));
        		b4.setTextColor(getResources().getColor(R.color.c4));
        		b5.setTextColor(getResources().getColor(R.color.c5));
         		 
        	 
	        	li.setVisibility(View.VISIBLE);
	        	li2.setVisibility(View.GONE);
         	   
        	    
        	  
        	    ////////////////////////////////////////////////////////////////////////// 	   
        		
        		
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
            	LayoutParams params = new LayoutParams(
            	        LayoutParams.FILL_PARENT,      
            	        LayoutParams.FILL_PARENT
            	);
            	int i=dpToPx(14);
            	params.setMargins(0, i, 0, 0);
       		 li.setVisibility(View.GONE);//hiding graph

  	            switch(groupPosition){
	            case 0:

	            	li2.removeAllLines();
 	        		li2.setVisibility(View.VISIBLE); 
 	        		
	            	li2.addLine(graph1);
	        		li2.setRangeY(0, maxrange);

	            		b1.setVisibility(View.VISIBLE);
	            		b1.setTextColor(getResources().getColor(R.color.c1));
		            	b2.setVisibility(View.GONE);
		            	b3.setVisibility(View.GONE);
		            	b4.setVisibility(View.GONE);
		            	b5.setVisibility(View.GONE);
  	            		break;
	            case 1:
	            	li2.removeAllLines();
 	        		li2.setVisibility(View.VISIBLE); 
 	        		
 	            	li2.addLine(graph2);
	        		li2.setRangeY(0, maxrange);
	            	b1.setLayoutParams(params);

            		b1.setVisibility(View.VISIBLE);
            		b1.setTextColor(getResources().getColor(R.color.c2));
 
	            	b2.setVisibility(View.GONE);
	            	b3.setVisibility(View.GONE);
	            	b4.setVisibility(View.GONE);
	            	b5.setVisibility(View.GONE);
               		break;
	            case 2:
	            	li2.removeAllLines();
 	        		li2.setVisibility(View.VISIBLE); 
 	        		
 	            	li2.addLine(graph3);
	        		li2.setRangeY(0, maxrange);
	        		
	            	b1.setLayoutParams(params);

            		b1.setVisibility(View.VISIBLE);
            		b1.setTextColor(getResources().getColor(R.color.c3));
	            	b3.setVisibility(View.GONE);
	            	b2.setVisibility(View.GONE);
	            	b4.setVisibility(View.GONE);
	            	b5.setVisibility(View.GONE);
             		break;
	            case 3:
 	            	li2.removeAllLines();
 	        		li2.setVisibility(View.VISIBLE); 
	            	li2.addLine(graph4);
	        		li2.setRangeY(0, maxrange);
	            	b1.setLayoutParams(params);

            		b1.setVisibility(View.VISIBLE);
            		b1.setTextColor(getResources().getColor(R.color.c4));
	            	b4.setVisibility(View.GONE);
	            	b3.setVisibility(View.GONE);
	            	b2.setVisibility(View.GONE);
	            	b5.setVisibility(View.GONE);
             		break;
	            case 4:
 	            	li2.removeAllLines();
 	        		li2.setVisibility(View.VISIBLE); 
	            	li2.addLine(graph5);
	        		li2.setRangeY(0, maxrange);
	            	b1.setLayoutParams(params);

            		b1.setVisibility(View.VISIBLE);
            		b1.setTextColor(getResources().getColor(R.color.c5));
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

            		b1.setTextColor(getResources().getColor(R.color.c1));
            		b2.setTextColor(getResources().getColor(R.color.c2));
            		b3.setTextColor(getResources().getColor(R.color.c3));
            		b4.setTextColor(getResources().getColor(R.color.c4));
            		b5.setTextColor(getResources().getColor(R.color.c5));
            		break;
            		
	            }
  	            
  	            
    ///////////////graph touch//////////////////////////////////////////// 
        	    
        	    li2.setOnPointClickedListener(new OnPointClickedListener(){
        	    	
        			@Override
        			public void onClick(int lineIndex, int pointIndex) { 
        				//list_event.expandGroup(lineIndex);  //can use this later if want to open list by clicking graph
        				for(int i=0;i<5;i++){
        				list_event.collapseGroup(i);} //to collopas the graph on click
        				Log.i("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",lineIndex+"");

        			}
        			
        		}); 
        	    
        	  
        	    ////////////////////////////////////////////////////////////////////////// 	   
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
    
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
        return px;
    }
    
	//list 
	
	public void seteventname() {
		for(int i=0;i<5;i++){
			groupItem.add(All_api_define.event_name_array[i]);
			
		}
		 
	}

	 

	public void senteventname_value() throws JSONException {
		
		 String newFormat[]=new String[10];

 	    for(int i=0;i<7;i++){
		        	    String key = null;
								try {
									key = series.getString(i);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        	    SimpleDateFormat formatter ; 
			            Date date = null ;
			            formatter = new SimpleDateFormat("yyyy-MM-dd");
									try {
										date = formatter.parse(key);
										 System.out.printf("%s %tB %<te, %<tY", 
						                         "Due date:", date);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
						SimpleDateFormat formatter1 = new SimpleDateFormat("E dd ' of ' MMMM");
					    newFormat[i] = formatter1.format(date);
		        	    
 	    }
		
		
		/**
		 * Add Data For event1
		 */
		ArrayList<String> child = new ArrayList<String>();
		
			 
				for(int i=0;i<7;i++){
				child.add(newFormat[i]+"          "+Math.round(data_map[0][i]));
				}
			  
		childItem.add(child);

		/**
		 * Add Data For event 2
		 */
		child = new ArrayList<String>();
		 
			for(int i=0;i<7;i++){
			child.add(newFormat[i]+"                    "+Math.round(data_map[1][i]));
			}
	 
	 		 
	childItem.add(child);
		/**
		 * Add Data For event 3
		 */
		child = new ArrayList<String>();
		for(int i=0;i<7;i++){
		child.add(newFormat[i]+"                   "+Math.round(data_map[2][i]));
		}
		childItem.add(child);

		/**
		 * Add Data For event 4
		 */
		child = new ArrayList<String>();
		for(int i=0;i<7;i++){
		child.add(newFormat[i]+"                    "+Math.round(data_map[3][i]));
		}
		childItem.add(child);

		/**
		 * Add Data For event 5
		 */
		child = new ArrayList<String>();
		for(int i=0;i<7;i++){
		child.add(newFormat[i]+"                    "+Math.round(data_map[4][i]));
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
