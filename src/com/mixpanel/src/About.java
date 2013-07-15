package com.mixpanel.src;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class About extends SherlockActivity {
	ImageButton imageButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		setContentView(R.layout.activity_about);

		String tempString="Open Source Credits";
		  TextView text=(TextView)findViewById(R.id.textView4);
		  SpannableString spanString = new SpannableString(tempString);
		  spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		  spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		  text.setText(spanString);
		
		github();
		gmail();
		play();
	}
	public void github() {
		 
		imageButton = (ImageButton) findViewById(R.id.imageButton1);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				 Uri uri = Uri.parse("https://github.com/29jitender/Mixpanel");
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);
			}
 
		});
 
	}
	public void gmail() {
		 
		imageButton = (ImageButton) findViewById(R.id.imageButton2);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "29jitender@gmail.com"));
				intent.putExtra(Intent.EXTRA_SUBJECT, "Mixpanel Android");
				//intent.putExtra(Intent.EXTRA_TEXT, "your_text");
				startActivity(intent);
			}
 
		});
 
	}
	public void play() {
		 
		imageButton = (ImageButton) findViewById(R.id.imageButton3);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				final String appName = "com.example";
				try {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
				} catch (android.content.ActivityNotFoundException anfe) {
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
				}
			}
 
		});
 
	}
	
	
	
	
	
	
	  
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        //Used to put dark icons on light action bar
	        boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
 
	         
	        menu.add(Menu.NONE, R.id.event_filter, Menu.NONE, "Version 0.1")//defining version in menu
       
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);	

	        return true;
	    }

     
	
	
	
	
	
	
	
	
}
