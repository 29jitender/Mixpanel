package com.mixpanel.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
 

public class ParseJSON extends Activity {
	/** Called when the activity is first created. */
	
	  
	  static final String TAG ="ParseJSON"; 
	
	  
	  @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		send_request(All_api_define.segmentation_average());// what to call
	}
	  
	  
	  public void send_request(String endpoint){
		  
		  Log.i("check endpoint",endpoint+"");
		  DefaultHttpClient httpclient = new DefaultHttpClient();
//			HttpGet getRequest = new HttpGet(Callapi.funnel());
		  
			HttpGet getRequest = new HttpGet(endpoint);// main request
			
			//Log.d(TAG,"onClicked with bundle:"+ export());		
	 		 getRequest.setHeader("Accept", "application/json");
			// Use GZIP encoding
			getRequest.setHeader("Accept-Encoding", "gzip"); //
			
			 
			try {
				HttpResponse response = (HttpResponse) httpclient.execute(getRequest);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					Header contentEncoding = response
							.getFirstHeader("Content-Encoding");
					if (contentEncoding != null
							&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
						instream = new GZIPInputStream(instream);
					}
					// convert content stream to a String
					String result = readStream(instream);
					instream.close();

					Log.i("JSON", result);
					TextView view = (TextView) findViewById(R.id.result);
					
					//JSONObject jsonObject = new JSONObject(result);
					  result = result.replaceAll("(\\r|\\n)", ",");
					  result = result.substring(0, result.length() - 1);
					    view.setText(result);
	 
	 				    JSONArray Jarray = new JSONArray("["+result+"]");
	 				    String finalOutput = "";
					    for (int i = 0; i < Jarray.length(); i++) {
					      JSONObject Jasonobject = Jarray.getJSONObject(i);	
					       
					       finalOutput = finalOutput + ( Jasonobject.getString("event") + " " )+ "\n";
					       Log.i("jsonnew obasdasdasdasd",finalOutput);
					    }
					    //view.setText(finalOutput);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	private static String readStream(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
 	
}