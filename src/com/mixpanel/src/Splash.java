package com.mixpanel.src;

 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mixpanel.src.demo.Demo;

public class Splash extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3300;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
 	 public void onAttachedToWindow() {
			super.onAttachedToWindow();
			Window window = getWindow();
			window.setFormat(PixelFormat.RGBA_8888);
		
		}
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//making full screen

       // setContentView(R.layout.activity_splash);
         setContentView(R.layout.activity_splash);

       
        StartAnimations();
     
       main();
        
    }
    
    public void main(){
    	 prefs = Splash.this.getSharedPreferences("nbRepet", MODE_PRIVATE);      
         final int value = prefs.getInt("nbRepet", 0);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
//            	if(value<1)
//            	{
//            		
////            		  prefs = getSharedPreferences("nbRepet",Context.MODE_PRIVATE);
////            	    	editor = prefs.edit();
////            	    	editor.putInt("nbRepet", 1);
////            	    	editor.commit();
////                Intent mainIntent = new Intent(Splash.this,Demo.class);
////                Splash.this.startActivity(mainIntent);
//            	}
//            	else{
            		if(HomeFragment.check==10){
            			overridePendingTransition(0, 0);
                       // myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        overridePendingTransition(0, 0);            			}
            		else{
            			main();
//            		}
            	}
             }
        }, SPLASH_DISPLAY_LENGHT);
    	
    }
    private void StartAnimations() {// animation of logo
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);

        
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.splash_bg);
        l.clearAnimation();
        l.startAnimation(anim);
        
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim1 = AnimationUtils.loadAnimation(this, R.anim.translate1);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.translate2);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo1);
        iv.clearAnimation();
        iv.startAnimation(anim);
        anim.reset();
        ImageView iv1 = (ImageView) findViewById(R.id.logo2);
        iv1.clearAnimation();
        iv1.startAnimation(anim1);
        anim.reset();
        ImageView iv2 = (ImageView) findViewById(R.id.logo3);
        iv2.clearAnimation();
        iv2.startAnimation(anim2);
        iv.setVisibility(View.INVISIBLE);
        iv1.setVisibility(View.INVISIBLE);
        iv2.setVisibility(View.INVISIBLE);
        
        
    }
   
}