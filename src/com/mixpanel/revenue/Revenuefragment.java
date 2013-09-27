package com.mixpanel.revenue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mixpanel.src.R;


public final class Revenuefragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    TextView  revenue_cust;
    TextView  revenue_avg;
    TextView  revenue_total;
    
    public static Revenuefragment newInstance(String content) {
        Revenuefragment fragment = new Revenuefragment();

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
       // getActivity().setContentView(R.layout.demo);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.revenue_display, container, false);//defing layout
 
 
        return view;
    	
    }


  
	@Override
	public void onResume() {
		revenue_cust =  (TextView)getView().findViewById(R.id.revenue_cust);
		revenue_avg =  (TextView)getView().findViewById(R.id.revenue_avg);
		revenue_total =  (TextView)getView().findViewById(R.id.revenue_total);
    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));////////setting utc time zone
		  	Date date = new Date(); 	
		  	Calendar cal = new GregorianCalendar();
			cal.setTime(date);						
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date date7 = cal.getTime();
 	  		  String date_yesterday=formatter.format(date7);
			String date_today=formatter.format(date);
			  
				try {
					JSONObject  obj1=new JSONObject(Revenue_first.data);
						JSONObject obj2=obj1.getJSONObject("results");

				
				
   		 if(mContent.equals("Today")){
   			
			JSONObject obj3=obj2.getJSONObject(date_today);
			revenue_cust.setText(obj3.getString("paid_count"));
			if(Integer.parseInt(obj3.getString("paid_count"))==0){
				revenue_avg.setText("0");

			}
			else{
				revenue_avg.setText(Float.parseFloat(obj3.getString("amount"))/Float.parseFloat(obj3.getString("paid_count"))+"");

			}
			revenue_total.setText(obj3.getString("amount"));

 
		   }
		   else  if(mContent.equals("Yesterday")){
			   JSONObject obj3=obj2.getJSONObject(date_yesterday);
				revenue_cust.setText(obj3.getString("paid_count"));
				if(Integer.parseInt(obj3.getString("paid_count"))==0){
					revenue_avg.setText("0");

				}
				else{
					revenue_avg.setText(Float.parseFloat(obj3.getString("amount"))/Float.parseFloat(obj3.getString("paid_count"))+"");

				}
				revenue_total.setText(obj3.getString("amount"));


		   }
		   else  if(mContent.equals("This week")){
  
			   
			   Date counter=date;
			   Float amount=0f;
			   int paid_count = 0;
			   for(int i=0;i<7;i++){
				   JSONObject obj3=obj2.getJSONObject(formatter.format(counter));
 
				   amount=amount+Float.parseFloat(obj3.getString("amount"));
				   paid_count=paid_count+Integer.parseInt(obj3.getString("paid_count"));
				   counter= new Date(counter.getTime() - 1 * 24 * 3600 * 1000 );

 			   }
			   Log.i(amount+"",paid_count+"---------");
			   revenue_cust.setText(paid_count+"");
				if(paid_count==0){
					revenue_avg.setText("0");

				}
				else{
					revenue_avg.setText(amount/paid_count+"");

				}
				revenue_total.setText(amount+"");

	 	  	   
   

		   }
		   else  if(mContent.equals("Last week")){
  
			   Date counter=date;
			   counter= new Date(counter.getTime() - 7 * 24 * 3600 * 1000 );

			   Float amount=0f;
			   int paid_count = 0;
			   for(int i=0;i<7;i++){
				   JSONObject obj3=obj2.getJSONObject(formatter.format(counter));
 
				   amount=amount+Float.parseFloat(obj3.getString("amount"));
				   paid_count=paid_count+Integer.parseInt(obj3.getString("paid_count"));
				   counter= new Date(counter.getTime() - 1 * 24 * 3600 * 1000 );

 			   }
			   Log.i(amount+"",paid_count+"---------");
			   revenue_cust.setText(paid_count+"");
				if(paid_count==0){
					revenue_avg.setText("0");

				}
				else{
					revenue_avg.setText(amount/paid_count+"");

				}
				revenue_total.setText(amount+"");

	 	  	   
			   

		   }
		   else  if(mContent.equals("This month")){
  

		   }
		   else  if(mContent.equals("Last month")){
  

		   }
		   else  if(mContent.equals("This year")){
  

		   }
		   else  if(mContent.equals("Last year")){
  

		   }
				} catch (JSONException e) {
 					e.printStackTrace();
				}
 		super.onResume();
	}

	public void callin(){
    	
    	
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
