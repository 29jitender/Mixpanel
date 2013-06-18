package com.mixpanel.src;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import android.util.Log;

	public class Newapicall {

	 
	  
	 
	  
	  public static String Calc_sig(TreeMap<String, String> receive ){
		  
		  receive.put("api_key", Conf_API.API_key);
		  receive.put("expire",  Conf_API.expire);
		  Log.i("hii",receive+"");
		  Set<Entry<String, String>> set = receive.entrySet();
	      // Get an iterator
	      Iterator<Entry<String, String>> i = set.iterator();
	      // Display elements
	      String sig="";
	      String Send_path = "https://data.mixpanel.com/api/2.0/export?";
	      while(i.hasNext()) {
	         Entry<String, String> me = i.next();
	        
	         sig= sig + me.getKey()+"="+ me.getValue();
	         Send_path=Send_path+ me.getKey()+"="+ me.getValue()+"&";
	         
	      }
	        sig= sig+ Conf_API.API_sceret;
	      	
	        sig = md5(sig);
	        Send_path = Send_path + "&sig=" + sig;
	        
		  
		  
		  
		  return Send_path;
		
		  
		  
	
	  }

	  public static String md5(String input) {
    
    String md5 = null;
     
    if(null == input) return null;
     
    try {
         
    //Create MessageDigest object for MD5
    MessageDigest digest = MessageDigest.getInstance("MD5");
     
    //Update input string in message digest
    digest.update(input.getBytes(), 0, input.length());

    //Converts message digest value in base 16 (hex) 
    md5 = new BigInteger(1, digest.digest()).toString(16);

    } catch (NoSuchAlgorithmException e) {

        e.printStackTrace();
    }
    return md5;
}
	
	}
