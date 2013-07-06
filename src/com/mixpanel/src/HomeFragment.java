package com.mixpanel.src;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;

public final class HomeFragment extends Fragment implements Callback{
    private static final String KEY_CONTENT = "TestFragment:Content";
    SharedPreferences prefs;
	public static String API_sceret= "";//defining variable 
	public static String API_key=""; 
	//for pie chart
	private static final String amount = "amount";
	private static final String percent_change = "percent_change";
	private static final String event = "event";
    JSONArray event_data = null;
	static JSONObject json = null;
	 private static final String TAG_event = "events";

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
        ParseJSON ParseJson_object = new ParseJSON();
		ParseJson_object.pass_values("event_top");
		ParseJson_object.setListener(this);
		
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	View view = inflater.inflate(R.layout.home_fragment_layout, container, false);//defing layout
    	 
        return view;        
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
	@Override
	public void methodToCallback(String print) {
		// TODO Auto-generated method stub
 
			// Hashmap for ListView
		 String[] name_value=new String[5];
		  final String[] e_name=new String[5];
		  final Float[] values_amount=new Float[5];
					 
		  Log.i("i am check frag",mContent);
					try {
						json = new JSONObject(print);
						event_data = json.getJSONArray(TAG_event);
						
						// looping through All data
						for(int i = 0; i < event_data.length(); i++){
						    JSONObject c = event_data.getJSONObject(i);
						     
						    // Storing each json item in variable
						    String Amount = c.getString(amount);
						    String parcent_change = c.getString(percent_change);
						    String Event = c.getString(event);
						    
						    e_name[i]=  Event;//name of event
						    float k =Float.parseFloat(Amount);//amount to pass in pie chart
						    values_amount[i]=k;//amount ot pass in pie chart
						    name_value[i]=Amount;
						  
	 
						}
		               // setSupportProgressBarIndeterminateVisibility(false);//after getting result false of loading icon


						//adding text in layout
						final TextView textViewToChange1 = (TextView) getView().findViewById(R.id.textView1);
						textViewToChange1.setText(e_name[0]);
						final TextView textViewToChange2 = (TextView) getView().findViewById(R.id.textView2);
						textViewToChange2.setText(e_name[1]);
						final TextView textViewToChange3 = (TextView) getView().findViewById(R.id.textView3);
						textViewToChange3.setText(e_name[2]);
						final TextView textViewToChange4 = (TextView) getView().findViewById(R.id.textView4);
						textViewToChange4.setText(e_name[3]);
						final TextView textViewToChange5 = (TextView) getView().findViewById(R.id.textView5);
						textViewToChange5.setText(e_name[4]);
 						   	
						
								
								
			
								
					/**
			       * Updating parsed JSON data into pie chart
			       * 
			       * */
				   if(mContent.equals("Today")){
						
						PieGraph pg1 = (PieGraph) getView().findViewById(R.id.piegraph);
		           PieSlice slice1 = new PieSlice();
		           slice1.setColor(Color.parseColor("#99CC00"));
		           slice1.setValue(values_amount[0]+1);//adding 1 to show the graph with value 
		           pg1.addSlice(slice1);
		           slice1 = new PieSlice();
		           slice1.setColor(Color.parseColor("#FFBB33"));
		           slice1.setValue(values_amount[1]+1);
		           pg1.addSlice(slice1);
		           slice1 = new PieSlice();
		           slice1.setColor(Color.parseColor("#AA66CC"));
		           slice1.setValue(values_amount[2]+1);
		           pg1.addSlice(slice1);
		           slice1 = new PieSlice();
		           slice1.setColor(Color.parseColor("#f41212"));
		           slice1.setValue(values_amount[3]+1);
		           pg1.addSlice(slice1);
		           slice1 = new PieSlice();
		           slice1.setColor(Color.parseColor("#25d3ee"));
		           slice1.setValue(values_amount[4]+1);
		           pg1.addSlice(slice1);  
		           
		           pg1.setOnSliceClickedListener(new OnSliceClickedListener(){

                       @Override
                       public void onClick(int index) {
           				Toast.makeText(getActivity(), e_name[index]+" Got Clicked " +Math.round(values_amount[index])+" times" +" today", Toast.LENGTH_SHORT).show();

                               
                       }
                       
               });
				   }
		           
		           
				   else if(mContent.equals("Yesteday")){ // for other tab
		           //yesteday
		           PieGraph pg = (PieGraph)getView().findViewById(R.id.piegraph);
		           PieSlice slice = new PieSlice();
		           slice.setColor(Color.parseColor("#99CC00"));
		           slice.setValue(values_amount[0]+1);//adding 1 to show the graph with value 
		           pg.addSlice(slice);
		           slice = new PieSlice();
		           slice.setColor(Color.parseColor("#FFBB33"));
		           slice.setValue(values_amount[1]+1);
		           pg.addSlice(slice);
		           slice = new PieSlice();
		           slice.setColor(Color.parseColor("#AA66CC"));
		           slice.setValue(values_amount[2]+1);
		           pg.addSlice(slice);
		           slice = new PieSlice();
		           slice.setColor(Color.parseColor("#f41212"));
		           slice.setValue(values_amount[3]+1);
		           pg.addSlice(slice);
		           slice = new PieSlice();
		           slice.setColor(Color.parseColor("#25d3ee"));
		           slice.setValue(values_amount[4]+1);
		           pg.addSlice(slice);  
		           
		           pg.setOnSliceClickedListener(new OnSliceClickedListener(){

                       @Override
                       public void onClick(int index) {
           				Toast.makeText(getActivity(), e_name[index]+" Got Clicked " +Math.round(values_amount[index])+" times" +" today", Toast.LENGTH_SHORT).show();

                               
                       }
                       
               });
		           
		           
				   }
		           
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					}
					
				//if(json.getJSONObject("error")==null){}	


       

}
    
    
    
}
