package com.mixpanel.src;

 
import com.mixpanel.src.demo.Demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splash extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3300;
	
	 public void onAttachedToWindow() {
			super.onAttachedToWindow();
			Window window = getWindow();
			window.setFormat(PixelFormat.RGBA_8888);
		}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//making full screen
        setContentView(R.layout.activity_splash);
        
        
        StartAnimations();
   
       
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,Demo.class);
                Splash.this.startActivity(mainIntent);
             }
        }, SPLASH_DISPLAY_LENGHT);
        
        
//        Button button = (Button) findViewById(R.id.skip);
//		 
//        button.setOnClickListener(new OnClickListener() {
// 
//			@Override
//			public void onClick(View arg0) {
// 
//				 Intent mainIntent = new Intent(Splash.this,Home.class);
//	                Splash.this.startActivity(mainIntent);
//	                Splash.this.finish();
// 
//			}
// 
//		});	     
        
    }
    
   
    
    
    private void StartAnimations() {// animation of logo
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.splash_bg);
        l.clearAnimation();
        l.startAnimation(anim);
        
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo1);
        iv.clearAnimation();
        iv.startAnimation(anim);
        
        
        
    }
   
}