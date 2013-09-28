package com.mixpanel.src;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import android.util.Log;

	public class Newapicall {

	 
	  
	 
	  /**
	   * 
	   * @param receive this is the values we pass in http request and to calculate sig
	   * @param path getting the initial path for httprequest from parseJSON
	   * @return the sig and the path for http request
	   */
	  public static String Calc_sig(TreeMap<String, String> receive , String path){
		  
		   
		  receive.put("api_key", Home.API_key);
		  receive.put("expire",  "1410949530");
		 
		  Set<Entry<String, String>> set = receive.entrySet();
	      // Get an iterator
	      Iterator<Entry<String, String>> i = set.iterator();
	      // Display elements
	      String sig="";
	      String Send_path = path;// path for diff api
	      while(i.hasNext()) {
	         Entry<String, String> me = i.next();
	         String value = me.getValue();
	         try {
				value =URLEncoder.encode(value, "UTF-8");// encoding the values for url
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          
	         sig= sig + me.getKey()+"="+ me.getValue();
	         Send_path=Send_path+ me.getKey()+"="+ value+"&";
	         //Log.i("check the newew send path",Send_path+"sdad");
	         
	      }
	        sig= sig+ Home.API_sceret;
	      	
	        sig = md5(sig);
	        Send_path = Send_path + "&sig=" + sig;
	        Log.i("hii123",Send_path+"");
		  
		  
		  
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
