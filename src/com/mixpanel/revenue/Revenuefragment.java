package com.mixpanel.revenue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mixpanel.src.All_api_define;
import com.mixpanel.src.R;


public final class Revenuefragment extends SherlockFragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    TextView  revenue_cust;
    TextView  revenue_avg;
    TextView  revenue_total;
    String last_week;
    String this_month;
    String last_month;
    String this_year;
    String last_year;
    RelativeLayout progress;
    LinearLayout mainlayout;
    
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
    public static String date_call(int i){
   	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
   		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));////////setting utc time zone
   	  	Date date = new Date(); 	
   	  	Calendar cal = new GregorianCalendar();
   		cal.setTime(date);						
   		cal.add(Calendar.DAY_OF_MONTH, -i);
   		Date date7 = cal.getTime();
   		  String date_yesterday=formatter.format(date7);
   		//String date_today=formatter.format(date);
   		  return date_yesterday;
   }
     

    public float roundof(Float f){
    	 BigDecimal bd = new BigDecimal(Float.toString(f));
    	    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    	    return bd.floatValue();
    }
  
	@Override
	public void onResume() {
		revenue_cust =  (TextView)getView().findViewById(R.id.revenue_cust);
		revenue_avg =  (TextView)getView().findViewById(R.id.revenue_avg);
		revenue_total =  (TextView)getView().findViewById(R.id.revenue_total);
		progress =  (RelativeLayout)getView().findViewById(R.id.progress);
		mainlayout =  (LinearLayout)getView().findViewById(R.id.main);

			  
				try {

				
				
   		 if(mContent.equals("Today")){
   			progress.setVisibility(View.VISIBLE);
   			mainlayout.setVisibility(View.GONE);
				JSONObject  obj1=new JSONObject(Revenue_first.data);

				JSONObject obj2=obj1.getJSONObject("results");

			JSONObject obj3=obj2.getJSONObject(date_call(0));//today
			revenue_cust.setText(obj3.getString("paid_count"));
			if(Integer.parseInt(obj3.getString("paid_count"))==0){
				revenue_avg.setText("0");

			}
			else{
				
				revenue_avg.setText(roundof(Float.parseFloat(obj3.getString("amount"))/Float.parseFloat(obj3.getString("paid_count")))+"");

			}
			revenue_total.setText(obj3.getString("amount"));
			progress.setVisibility(View.GONE);
   			mainlayout.setVisibility(View.VISIBLE);
 
		   }
		   else  if(mContent.equals("Yesterday")){
			   progress.setVisibility(View.VISIBLE);
	   			mainlayout.setVisibility(View.GONE);
				JSONObject  obj1=new JSONObject(Revenue_first.data);

				JSONObject obj2=obj1.getJSONObject("results");

			   JSONObject obj3=obj2.getJSONObject(date_call(1));//yesterday
				revenue_cust.setText(obj3.getString("paid_count"));
				if(Integer.parseInt(obj3.getString("paid_count"))==0){
					revenue_avg.setText("0");

				}
				else{
					revenue_avg.setText(roundof(Float.parseFloat(obj3.getString("amount"))/Float.parseFloat(obj3.getString("paid_count")))+"");

				}
				revenue_total.setText(obj3.getString("amount"));

				progress.setVisibility(View.GONE);
	   			mainlayout.setVisibility(View.VISIBLE);
		   }
		   else  if(mContent.equals("This week")){
			   progress.setVisibility(View.VISIBLE);
	   			mainlayout.setVisibility(View.GONE);
				JSONObject  obj=new JSONObject(Revenue_first.data);
				JSONObject  obj1=obj.getJSONObject("results");
				JSONObject obj2=obj1.getJSONObject("$overall");
				revenue_cust.setText(obj2.getString("paid_count"));
				if(Integer.parseInt(obj2.getString("paid_count"))==0){
					revenue_avg.setText("0");

				}
				else{
					revenue_avg.setText(roundof(Float.parseFloat(obj2.getString("amount"))/Float.parseFloat(obj2.getString("paid_count")))+"");

				}
				revenue_total.setText(obj2.getString("amount"));
   
				progress.setVisibility(View.GONE);
	   			mainlayout.setVisibility(View.VISIBLE);
		   }
		   else  if(mContent.equals("Last week")){
			   progress.setVisibility(View.VISIBLE);
	   			mainlayout.setVisibility(View.GONE);
			   if(last_week!=null){
 				   callin_ui(last_week);
 				  progress.setVisibility(View.GONE);
		   			mainlayout.setVisibility(View.VISIBLE);
			   }
			   else{
 
				   AsyncHttpClient client = new AsyncHttpClient();
	      	        client.get(All_api_define.revenu_home(Revenuefragment.date_call(13),Revenuefragment.date_call(7)),  new AsyncHttpResponseHandler() {
	      	        	@Override
	      	            public void onSuccess(String response) {	      	            	
	      	        		last_week=response;
	     				   callin_ui(last_week);
	     				  progress.setVisibility(View.GONE);
	     		   			mainlayout.setVisibility(View.VISIBLE);
	      	             } });

			   }
               
			  
			   

		   }
		   else  if(mContent.equals("This month")){
			   progress.setVisibility(View.VISIBLE);
	   			mainlayout.setVisibility(View.GONE);
			   if(this_month!=null){
 				   callin_ui(this_month);
 				  progress.setVisibility(View.GONE);
		   			mainlayout.setVisibility(View.VISIBLE);
			   }
			   else{
 
				   AsyncHttpClient client = new AsyncHttpClient();
	      	        client.get(All_api_define.revenu_home(Revenuefragment.date_call(30),Revenuefragment.date_call(0)),  new AsyncHttpResponseHandler() {
	      	        	@Override
	      	            public void onSuccess(String response) {	      	            	
	      	        		this_month=response;
	     				   callin_ui(this_month);
	     				  progress.setVisibility(View.GONE);
	     		   			mainlayout.setVisibility(View.VISIBLE);
	      	             } });

			   }

		   }
		   else  if(mContent.equals("Last month")){
			   progress.setVisibility(View.VISIBLE);
	   			mainlayout.setVisibility(View.GONE);
			   if(last_month!=null){
 				   callin_ui(last_month);
 				  progress.setVisibility(View.GONE);
		   			mainlayout.setVisibility(View.VISIBLE);
			   }
			   else{
 
				   AsyncHttpClient client = new AsyncHttpClient();
	      	        client.get(All_api_define.revenu_home(Revenuefragment.date_call(60),Revenuefragment.date_call(31)),  new AsyncHttpResponseHandler() {
	      	        	@Override
	      	            public void onSuccess(String response) {	      	            	
	      	        		last_month=response;
	     				   callin_ui(last_month);
	     				  progress.setVisibility(View.GONE);
	     		   			mainlayout.setVisibility(View.VISIBLE);
	      	             } });

			   }

		   }
		   else  if(mContent.equals("This year")){
			   progress.setVisibility(View.VISIBLE);
	   			mainlayout.setVisibility(View.GONE);
			   if(this_year!=null){
				   Log.i("year",this_year);
 				   callin_ui(this_year);
 				  progress.setVisibility(View.GONE);
		   			mainlayout.setVisibility(View.VISIBLE);
			   }
			   else{
 
				   AsyncHttpClient client = new AsyncHttpClient();
	      	        client.get(All_api_define.revenu_home(Revenuefragment.date_call(364),Revenuefragment.date_call(0)),  new AsyncHttpResponseHandler() {
	      	        	@Override
	      	            public void onSuccess(String response) {	      	            	
	      	        		this_year=response;
 
	     				   callin_ui(this_year);
	     				  progress.setVisibility(View.GONE);
	     		   			mainlayout.setVisibility(View.VISIBLE);
	      	             } });

			   }

		   }
		   else  if(mContent.equals("Last year")){
			   progress.setVisibility(View.VISIBLE);
	   			mainlayout.setVisibility(View.GONE);
			   if(last_year!=null){
 				   callin_ui(last_year);
 				  progress.setVisibility(View.GONE);
		   			mainlayout.setVisibility(View.VISIBLE);
			   }
			   else{
 
				   AsyncHttpClient client = new AsyncHttpClient();
	      	        client.get(All_api_define.revenu_home(Revenuefragment.date_call(730),Revenuefragment.date_call(365)),  new AsyncHttpResponseHandler() {
	      	        	@Override
	      	            public void onSuccess(String response) {	      	            	
	      	        		last_year=response;
	     				   callin_ui(last_year);
	     				  progress.setVisibility(View.GONE);
	     		   			mainlayout.setVisibility(View.VISIBLE);
	      	             } });

			   }

		   }
				} catch (JSONException e) {
 					e.printStackTrace();
				}
 		super.onResume();
	}

	public void callin_ui(String data){
		JSONObject obj;
		try {
			obj = new JSONObject(data);
			JSONObject  obj1=obj.getJSONObject("results");
				JSONObject obj2=obj1.getJSONObject("$overall");
				revenue_cust.setText(obj2.getString("paid_count"));
				if(Integer.parseInt(obj2.getString("paid_count"))==0){
					revenue_avg.setText("0");

				}
				else{
					revenue_avg.setText(roundof(Float.parseFloat(obj2.getString("amount"))/Float.parseFloat(obj2.getString("paid_count")))+"");

				}
				revenue_total.setText(obj2.getString("amount"));
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
