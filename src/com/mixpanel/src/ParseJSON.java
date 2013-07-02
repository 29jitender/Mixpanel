package com.mixpanel.src;

import android.util.Log;
 
interface Callback {
    void methodToCallback(String response);
}

public class ParseJSON  implements Callback{
	/** Called when the activity is first created. */


	  static final String TAG ="ParseJSON"; 
	  public static String result="";
	  public static String[] arg = {}; 
	  public static String display="";
	  public static String retrun_stuff="";

//	  @Override
//	 public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main1);
//		//send_request(All_api_define.event());// what to call
//		//pass_values("event_name");
//	}
	  public void methodToCallback(String display1) {
		 	display=display1;
		 	callbackInstance1.methodToCallback(display);
	  		}


	  public String pass_values(String type_event){ // passing everything


		  if(type_event=="event"){
			  Async_task asyncRequest = new Async_task();
			  asyncRequest.arg=new String[]{"data"};// pasing the depth
			  asyncRequest.execute(All_api_define.event());
			  asyncRequest.setListener(this);


			  retrun_stuff=display;
		  }
 
		  else if(type_event=="event1"){
 
			  Async_task asyncRequest = new Async_task();
			  asyncRequest.arg=new String[]{"data"};// pasing the depth
			  asyncRequest.execute(All_api_define.event1());
			  asyncRequest.setListener(this);


			  retrun_stuff=display;
		  }

		  else if(type_event=="event_name"){

			  //send_request(All_api_define.event_name());// what to call
			  Async_task asyncRequest = new Async_task();
			  asyncRequest.arg = new String[]{};
			  asyncRequest.execute(All_api_define.event_name());
			  Log.i("are you working",retrun_stuff);
			  asyncRequest.setListener(this);

			  retrun_stuff=display;
			  Log.i("plese check",retrun_stuff);
		  }

		  else if(type_event=="event_top"){

			  Async_task asyncRequest = new Async_task();
			  asyncRequest.arg = new String[]{};
			  asyncRequest.execute(All_api_define.event_top());
			  asyncRequest.setListener(this);

			  retrun_stuff=display;
		  }


		  return retrun_stuff ;

	  }
	  Callback callbackInstance1;// callback variable

		public void setListener(Callback listener){
		   callbackInstance1 = listener;
		}



}