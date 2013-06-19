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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
 

public class ParseJSON extends Activity {
	/** Called when the activity is first created. */
	
	  
	  static final String TAG ="ParseJSON"; 
	  public static String result="";
	  public static String[] arg = {"data"}; // what to pass
	  
	  @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		send_request(All_api_define.event());// what to call
	}
	  
	  
	  public void send_request(String endpoint){
		  
		  Log.i("check endpoint",endpoint+"");
		  DefaultHttpClient httpclient = new DefaultHttpClient();
		   HttpGet getRequest = new HttpGet(endpoint);// main request
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
					result = readStream(instream);
					instream.close();

					Log.i("JSON", result);
					TextView view = (TextView) findViewById(R.id.result);
					
					 //in this arry paas what we need from JSONobject
					 //public static String[] arg = {"data"};
					String display = Json_values.jsonresult(arg);// what things we want from jsonobect
					//Log.i("temppp", display);
					
					 view.setText(display);
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