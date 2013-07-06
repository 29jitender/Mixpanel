package com.mixpanel.src;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class About extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		setContentView(R.layout.activity_about);
		Typeface type = Typeface.createFromAsset(getAssets(),"ASansBlack.ttf"); 
		TextView tv = (TextView) findViewById(R.id.textView1);		 
		tv.setTypeface(type);
//		//keeping it in center
//		Display display = getWindowManager().getDefaultDisplay(); 
//		int width = display.getWidth();  // deprecated
//		int height = display.getHeight();  // deprecated
//		WindowManager.LayoutParams params = getWindow().getAttributes();  
//		params.x = -20;  
//		params.height = height-100;  
//		params.width = width-100;  
//		params.y = -10;  
//		this.getWindow().setAttributes(params); 
//		
		
		
	}
 

}
