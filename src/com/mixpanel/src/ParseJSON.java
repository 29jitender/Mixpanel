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
 

public class ParseJSON extends Activity {
	/** Called when the activity is first created. */
	
	  
	  static final String TAG ="ParseJSON"; 
	  public static String result="";
	  public static String[] arg = {}; 
	  public static String display="";
	  public static String retrun_stuff="";
	  
	  @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//send_request(All_api_define.event());// what to call
		//pass_values("event_name");
	}
	  
	  public String pass_values(String type_event){ // passing everything
		  
		  
		  if(type_event=="event"){
			  arg = new String[]{"data","values"};
			  //send_request(All_api_define.event());// what to call
			   
			  retrun_stuff=send_request(All_api_define.event());
			return retrun_stuff;// haved to change this
		  }
		  else if(type_event=="event_name"){
			  arg = new String[]{};
			  //send_request(All_api_define.event_name());// what to call
			  
			  retrun_stuff=send_request(All_api_define.event_name());
		  }
		  
		  else if(type_event=="export"){
			  arg = new String[]{};
			  //send_request(All_api_define.event_name());// what to call
			  
			  retrun_stuff=send_request(All_api_define.export());
		  }
		  
		  
		  return retrun_stuff ;
		  
	  }
	  
	  
	  
	  public String send_request(String endpoint){
		  
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
					//TextView view = (TextView) findViewById(R.id.result);
					
					 //in this arry paas what we need from JSONobject
					
					
					if(arg.length==0){// if we are not passing anything
						display= result;
						 
						
		       		}
					else{//
						// a bug it is calling two time please check please please 
						//
						//
						//trying :P
						display = Json_values.jsonresult(arg);// what things we want from jsonobect
						 
					}
					Log.i("temppp", display);
					 //view.setText(display);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return display;
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