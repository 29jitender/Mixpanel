package com.mixpanel.src;

import java.util.TreeMap;

import com.mixpanel.src.funnel.Funnel_activity;

import android.util.Log;

public class All_api_define{
	public static String interval1="";
	public static String event=null;
	public static String[] event_name_array=new String[5];
	///////funnel////////////////
	public static String funnel_id=null;
 
	public static String to_date=null;
	public static String from_date=null;
	public static String interval_funnel=null;

	////////////////////////////
	public static String export(){// this is for export api
		 
	  	  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("from_date", new String("2013-06-14"));
	      tm.put("to_date", new String("2013-06-16"));
	      String send_path_first ="https://data.mixpanel.com/api/2.0/export?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      
	      Log.i("check",path_http+"");
		  return path_http;
		   
	  }	

	 public static String event1(){// is for event coming from event_activity not in use 

		// String event = Event_activity.click_type;//getting type of list click from event activity
		 //now getting the name of event on click
		 String event1= "[\""+event;// converting it into required formet
		 String event2= event1+"\"]";
		 String event3=event2;
		 
		 TreeMap<String, String> tm = new TreeMap<String, String>();
		  //some bug in api when calling watched video in average
		 // Event_activity event_object= new Event_activity();
		   
		  String type = event_final.event_type;
		  String unit = event_final.event_unit;
		  interval1 = event_final.event_interval;
		
		  tm.put("event", event3);
	      tm.put("type", type);
	      tm.put("unit", unit);
	      tm.put("interval", interval1);
	      //tm.put("format", new String("")); //currently not available
	      String send_path_first ="http://mixpanel.com/api/2.0/events/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	

	 public static String event_top_value(){// is for event coming from event_activity not in use 
		 
		String temp="";
		 for(int i=0;i < event_name_array.length;i++){
			 temp=temp+"\""+event_name_array[i]+"\""+",";
		 }
		  temp = temp.substring(0, temp.length() - 1);
		 
		 String event1= "["+temp; 
		 String event2= event1+"]";
		 
		 String event3=event2;
		 Log.i("we are in all api define",event3);
		 TreeMap<String, String> tm = new TreeMap<String, String>();		   
		  tm.put("event", event3); 
	      tm.put("type", "general");
	      tm.put("unit", "day");
	      tm.put("interval", "7");
	      //tm.put("format", new String("")); //currently not available
	      String send_path_first ="http://mixpanel.com/api/2.0/events/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	
	
	 public static String event(){// is for event 

		 //String event = Event_activity.click_type;//getting type of list click from event activity
		
		 String event=Event_top_click.name;	 
		 Log.i("checkinh i am",event);
		 String event1= "[\""+event;// converting it into required formet
		 String event2= event1+"\"]";
		 String event3=event2;
		 String interval=Event_top_click.interval;
		 TreeMap<String, String> tm = new TreeMap<String, String>();
		  //some bug in api when calling watched video in average
//		  Event_activity event_object= new Event_activity();
//		   
//		  String type = event_object.event_type;
//		  String unit = event_object.event_unit;
//		  String interval = event_object.event_interval;
//		
		  tm.put("event", event3);
	      tm.put("type", "general");
	      tm.put("unit", "day");
	      tm.put("interval", interval);
	      //tm.put("format", new String("")); //currently not available
	      String send_path_first ="http://mixpanel.com/api/2.0/events/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	 public static String api_check(){
		 
		 // sending randome reuest to check response
		
		 TreeMap<String, String> tm = new TreeMap<String, String>();
		 tm.put("limit", new String("10")); //its optional
	      tm.put("type", new String("unique"));
	         
	      String send_path_first ="http://mixpanel.com/api/2.0/events/top/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
		 
	 }
	 
	  public static String event_top(){// is for event method is TOP
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("limit", new String("5")); //its optional
	      tm.put("type", new String("unique"));
	         
	      String send_path_first ="http://mixpanel.com/api/2.0/events/top/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  public static String event_top1(){// to call from inside event_top.java
		  String limit=Event_top.limit;
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("limit", new String(limit)); //its optional
	      tm.put("type", new String("unique"));
	         
	      String send_path_first ="http://mixpanel.com/api/2.0/events/top/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String event_name(){// is for event method is name
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("type", new String("general"));
		  //tm.put("limit", new String("Signup Form Submit")); //its optional  
	      String send_path_first ="http://mixpanel.com/api/2.0/events/names/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      Log.i("result in event_name",path_http);
	      return path_http;
	  }
	  
	  public static String event_properties(){// is for event properties 
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String( "Signup Form Submit"));
		  tm.put("name", new String("email_address"));
		  //tm.put("values", new String("average"));   // this is optional 
		  tm.put("type", new String("general"));
		  tm.put("unit", new String("month"));
		  tm.put("interval", new String("2"));
		  //tm.put("format", new String("average")); //this is optional 
		  //tm.put("limit", new String("average"));	// this is optional
	      String send_path_first ="http://mixpanel.com/api/2.0/events/properties/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String event_properties_top(){// is for event properties  method is TOP 
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("Signup Form Submit"));
		   //tm.put("limit", new String("Signup Form Submit")); // this is optional  
	      String send_path_first ="http://mixpanel.com/api/2.0/events/properties/top/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	     
	      
	      
	      return path_http;
	  }
	   
	  public static String event_properties_values(){// is for event properties method is values
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("Signup Form Submit"));
		  tm.put("name", new String("email_address"));
		  
		  //tm.put("limit", new String("[\"Signup Form Submit\"]")); // thi is optional
		  //tm.put("bucket", new String("Signup Form Submit")); // this is optional  
	      String send_path_first ="http://mixpanel.com/api/2.0/events/properties/values/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  	  
	  public static String funnels(){// is for funnels
  
		 
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("funnel_id", funnel_id);
 		  tm.put("from_date", from_date);
 		  tm.put("to_date", to_date);
//		  tm.put("length", new String(""));
 		  tm.put("interval",interval_funnel);
//		  tm.put("unit", new String(""));
//		  tm.put("on", new String(" "));
//		  tm.put("where", new String(""));
 		 // tm.put("limit", new String("200"));
		  String send_path_first ="http://mixpanel.com/api/2.0/funnels/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String funnels_list(){// is for funnels method is list
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		 
		  String send_path_first ="http://mixpanel.com/api/2.0/funnels/list/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String segmentation(){// is for segmentation
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("signup failed"));
		  tm.put("from_date", new String("2013-06-01"));
		  tm.put("to_date", new String("2013-06-16"));
//		  tm.put("on", new String(""));
//		  tm.put("unit", new String(" "));
//		  tm.put("where", new String(""));
//		  tm.put("limit", new String(" "));
		  tm.put("type", new String("general"));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String segmentation_numeric(){// is for segmentation method is numeric
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("Signup Form Submit"));
		  tm.put("from_date", new String("2013-06-01"));
		  tm.put("to_date", new String("2013-06-16"));
		  tm.put("on", new String("properties[\"distinct_id\"]"));
		  tm.put("buckets", new String("5"));
//		  tm.put("where", new String(""));
//		  tm.put("unit", new String(" "));
//		  tm.put("type", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/numeric/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	   // check sum and average again
	  public static String segmentation_sum(){// is for segmentation method is sum
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("Signup Form Submit"));
		  tm.put("from_date", new String("2013-06-01"));
		  tm.put("to_date", new String("2013-06-16"));
		  tm.put("on", new String("properties[\"distinct_id\"]"));
		
//		  tm.put("unit", new String(" "));
//		  tm.put("where", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/sum/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String segmentation_average(){// is for segmentation method is average
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("Signup Form Submit"));
		  tm.put("from_date", new String("2013-06-01"));
		  tm.put("to_date", new String("2013-06-16"));
		  tm.put("on", new String("properties[\"distinct_id\"]"));
		
//		  tm.put("unit", new String(" "));
//		  tm.put("where", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/average/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String retention(){// is for retentation
	  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		 
		  tm.put("from_date", new String("2013-06-10"));
		  tm.put("to_date", new String("2013-06-16"));
		  tm.put("retention_type", new String("birth"));
		  tm.put("born_event", new String("Signup Form Submit"));
//		  tm.put("event", new String(""));
//		  tm.put("born_where", new String(" "));
//		  tm.put("where", new String(""));
//		  tm.put("interval", new String(""));
//		  tm.put("interval_count", new String(""));
//		  tm.put("unit", new String(" "));
//		  tm.put("on", new String(""));
//		  tm.put("limit", new String(""));
		  String send_path_first ="http://mixpanel.com/api/2.0/retention/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String engage(){// is for funnels
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		 
//		  tm.put("where", new String("2013-06-14"));
//		  tm.put("session_id", new String("2013-06-16 "));
//		  tm.put("page", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/engage/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }

 

}
