package com.mixpanel.src;

import java.util.TreeMap;

import android.util.Log;

public class All_api_define {
	
	public static String export(){// this is for export api
		
	  	  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("from_date", new String("2013-06-14"));
	      tm.put("to_date", new String("2013-06-16"));
	      String send_path_first ="https://data.mixpanel.com/api/2.0/export?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      
	      Log.i("check",path_http+"");
		  return path_http;
		  
	  }	
	  
	  public static String event(){// is for event 
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
	      tm.put("type", new String("average"));
	      tm.put("unit", new String("day"));
	      tm.put("interval", new String("7"));
	      //tm.put("format", new String("")); //currently not available
	      String send_path_first ="http://mixpanel.com/api/2.0/events/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String event_top(){// is for event method is TOP
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  //tm.put("limit", new String("Signup Form Submit")); //its optional
	      tm.put("type", new String("unique"));
	        
	      String send_path_first ="http://mixpanel.com/api/2.0/events/top/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String event_name(){// is for event method is name
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("type", new String("average"));
		  //tm.put("limit", new String("Signup Form Submit")); //its optional  
	      String send_path_first ="http://mixpanel.com/api/2.0/events/names/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  
	  public static String event_properties(){// is for event properties not working
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
		  tm.put("name", new String("average"));
		  //tm.put("values", new String("average"));   // this is optional 
		  tm.put("type", new String("average"));
		  tm.put("unit", new String("day"));
		  tm.put("interval", new String("7"));
		  //tm.put("format", new String("average")); //this is optional 
		  //tm.put("limit", new String("average"));	// this is optional
	      String send_path_first ="http://mixpanel.com/api/2.0/properties/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String event_properties_top(){// is for event properties  method is TOP not working
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
		   //tm.put("limit", new String("Signup Form Submit")); // this is optional  
	      String send_path_first ="http://mixpanel.com/api/2.0/properties/top/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	     
	      
	      
	      return path_http;
	  }
	   
	  public static String event_properties_values(){// is for event properties method is values
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
		  tm.put("name", new String(""));
		  //tm.put("limit", new String("[\"Signup Form Submit\"]")); // thi is optional
		  //tm.put("bucket", new String("Signup Form Submit")); // this is optional  
	      String send_path_first ="http://mixpanel.com/api/2.0/properties/values/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  
	  public static String funnels(){// is for funnels
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("funnel_id", new String("274366"));
//		  tm.put("from_date", new String(""));
//		  tm.put("to_date", new String(" "));
//		  tm.put("length", new String(""));
//		  tm.put("interval", new String(" "));
//		  tm.put("unit", new String(""));
//		  tm.put("on", new String(" "));
//		  tm.put("where", new String(""));
//		  tm.put("limit", new String(""));
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
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
		  tm.put("from_date", new String("2013-06-14"));
		  //tm.put("to_date", new String("2013-06-16 "));
//		  tm.put("on", new String(""));
//		  tm.put("unit", new String(" "));
//		  tm.put("where", new String(""));
//		  tm.put("limit", new String(" "));
//		  tm.put("type", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String segmentation_numeric(){// is for segmentation method is numeric
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
		  tm.put("from_date", new String("2013-06-14"));
		//  tm.put("to_date", new String("2013-06-16 "));
		  tm.put("on", new String("number(properties[\"time\"])"));
		  tm.put("buckets", new String("5"));
//		  tm.put("where", new String(""));
//		  tm.put("unit", new String(" "));
//		  tm.put("type", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/numeric/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String segmentation_sum(){// is for segmentation method is sum
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
		  tm.put("from_date", new String("2013-06-14"));
		  //tm.put("to_date", new String("2013-06-16 "));
		  tm.put("on", new String("number(properties[\"time\"])"));
		
//		  tm.put("unit", new String(" "));
//		  tm.put("where", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/sum/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String segmentation_average(){// is for segmentation method is average
		  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		  tm.put("event", new String("[\"Signup Form Submit\"]"));
		  tm.put("from_date", new String("2013-06-14"));
		  //tm.put("to_date", new String("2013-06-16 "));
		  tm.put("on", new String("number(properties[\"time\"])"));
		
//		  tm.put("unit", new String(" "));
//		  tm.put("where", new String(""));

		  String send_path_first ="http://mixpanel.com/api/2.0/segmentation/average/?";
	      String path_http = Newapicall.Calc_sig(tm,send_path_first);
	      return path_http;
	  }
	  
	  public static String retention(){// is for retentation
	  
		  TreeMap<String, String> tm = new TreeMap<String, String>();
		 
		  tm.put("from_date", new String("2013-06-14"));
//		  tm.put("to_date", new String("2013-06-16 "));
//		  tm.put("retention_type", new String(""));
//		  tm.put("born_event", new String(" "));
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
