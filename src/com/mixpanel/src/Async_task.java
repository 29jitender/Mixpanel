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

import android.os.AsyncTask;
import android.util.Log;

class Async_task extends AsyncTask<String, Void, String> {
	
	public static String result="";
	public  String[] arg = {}; 
	public static String display="";
	public static String finalOutput = "";
	public static String display_arg="";
	protected  String doInBackground(String... params) {// async task thread 
		Log.i("starting of do in back","7"); 
		String temp = (String) params[0];
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(temp);// main request
		getRequest.setHeader("Accept", "application/json");
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
						result = readStream(instream);
						instream.close();
						int temp1 = arg.length;
						Log.i("i am checking arg",temp1+"");
						if(arg.length==0){// if we are not passing anything
							display= result;							
			       		}
						else{
							
							String result1 = result;
							result1 = result1.replaceAll("(\\r|\\n)", ",");
							  result1 = result1.substring(0, result1.length() - 1);
							   
							    
								    JSONArray Jarray = new JSONArray("["+result1+"]");
								    
								   String add_finaloutput="";
								   for (int i = 0; i < Jarray.length(); i++) {
								   JSONObject Jasonobject = Jarray.getJSONObject(i);	
							       		if (arg.length>1) {
											for (int j = 0; j < arg.length-1; j++) 
											{ // this is to get the jsonboject and get at last string
												Jasonobject = Jasonobject.getJSONObject(arg[j]);
												if (j == arg.length - 2) {
													add_finaloutput = (Jasonobject.getString(arg[++j]) + " ") + "\n";
																		}
											}
											
											
										}
							       		
							       		else { // this if for just one argument
							       			add_finaloutput = ( Jasonobject.getString(arg[0]) + " " )+ "\n";
							       			
							       		}
							       		
								finalOutput = finalOutput + add_finaloutput+ "\n";
							       
							       Log.i("jsonnew obasdasdasdasd", finalOutput+"");
							    }
								   
							display= finalOutput;
							
							
							
							 
							
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
		return display;
	}

	Callback callbackInstance;// callback variable

	public void setListener(Callback listener){
	   callbackInstance = listener;
	}
	
	
	//@Override
	protected void onPostExecute(String result1) {
			Log.i("result in postexecute",display);
			callbackInstance.methodToCallback(display);
			
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
