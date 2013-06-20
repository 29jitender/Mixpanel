package com.mixpanel.src;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Json_values  {

	
	public static String finalOutput = "";
	 public static  String jsonresult(String[] S1) throws JSONException{
		 
		
			
			  String result = ParseJSON.result;
			  result = result.replaceAll("(\\r|\\n)", ",");
			  result = result.substring(0, result.length() - 1);
			   
			    
				    JSONArray Jarray = new JSONArray("["+result+"]");
				    
				   String add_finaloutput="";
				   for (int i = 0; i < Jarray.length(); i++) {
				   JSONObject Jasonobject = Jarray.getJSONObject(i);	
			       		if (S1.length>1) {
							for (int j = 0; j < S1.length-1; j++) 
							{ // this is to get the jsonboject and get at last string
								Jasonobject = Jasonobject.getJSONObject(S1[j]);
								if (j == S1.length - 2) {
									add_finaloutput = (Jasonobject.getString(S1[++j]) + " ") + "\n";
														}
							}
							
							
						}
			       		else { // this if for just one argument
			       			add_finaloutput = ( Jasonobject.getString(S1[0]) + " " )+ "\n";
			       			
			       		}
			       		
				finalOutput = finalOutput + add_finaloutput+ "\n";
			       
			       Log.i("jsonnew obasdasdasdasd", finalOutput+"");
			    }
			    return finalOutput;
			    //view.setText(finalOutput);
	 }
	
}
