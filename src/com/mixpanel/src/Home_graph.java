package com.mixpanel.src;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

public class Home_graph extends Activity  implements Callback,OnChildClickListener{
	 public static String[] event_name=new String[5];
	 private static String TAG_event = "values";
	 private static String  key = "";	
	 private static String KEY1= "temp";
	 private static String VALUE1= "temp1";
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_fragment_layout_line);
//		Home_graph_call obj =new Home_graph_call();//this is to find top events and send value to all api call
//		obj.tocall();//caling
		ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_top_value");//to get event top value
		ParseJson_object.setListener(this);
		
		//for expandable list
		
		
		
		}
	
	public void calllist(){
		seteventname();
		senteventname_value();
		list_event = (ExpandableListView) findViewById(R.id.eventname_list);

		list_event.setAdapter(new Home_list_adapter(this, groupItem, childItem));

		list_event.setOnChildClickListener(this);

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
		 	 
				
//setting y axis lable 
		 	DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		 	int width = metrics.widthPixels;
		 	 int size= width/61;
		 	TextView text = (TextView) findViewById(R.id.days);
		 	text.setTextSize(size);
		 	String temp="";
		 	for(int i=0;i < 7;i++){
				 try {
					temp=temp+series.getString(i)+"    " ;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 	text.setText(temp);
	  	 
	//	 	 
		 
		 	/**
			* Updating parsed JSON data into graphs
			* */
  	  
		 	
		//1 	
   	   Line graph1 = new Line();
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
		 Line graph2 = new Line();
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
			 Line graph3 = new Line();
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
				 Line graph4 = new Line();
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
					 Line graph5 = new Line();
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
					 float maxrange=0;
					 for (int i=0;i<therange.length;i++){
						 if(therange[i]>maxrange){
							 maxrange=therange[i];
						 }
						 
					 }
					 
		LineGraph li = (LineGraph)findViewById(R.id.linegraph_home);
		li.addLine(graph1);
 		li.addLine(graph2);
		 
		//li.setLineToFill(2);//change filling
		li.addLine(graph3);
		 
		//li.setLineToFill(2);//change filling
		li.addLine(graph4);
		li.addLine(graph5);
		li.setRangeY(0, maxrange);

	 
		calllist();//calling list after graph
		 
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
		Toast.makeText(this, "Clicked On Child" + v.getTag(),
				Toast.LENGTH_SHORT).show();
		return true;
	}
	
		
}
