package com.mixpanel.src;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.MotionEvent;
 
public class Homescreen extends Activity {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
 
        gestureDetector = new GestureDetector(new MyGestureDetector());
        View mainview = (View) findViewById(R.id.mainView);
 
        // Set the touch listener for the main view to be our custom gesture listener
        mainview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });
    }
 
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	    Intent intent = new Intent(Homescreen.this.getBaseContext(), MainActivity.class);
	    Intent intent1 = new Intent(Homescreen.this.getBaseContext(), Webview_graph.class);
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
 
            // right to left swipe
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
    		startActivity(intent);
    		Homescreen.this.overridePendingTransition(
			R.anim.slide_in_right,
			R.anim.slide_out_left
    		);
    	    // right to left swipe
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
    		startActivity(intent1);
    		Homescreen.this.overridePendingTransition(
			R.anim.slide_in_left, 
			R.anim.slide_out_right
    		);
            }
 
            return false;
        }
 
        // It is necessary to return true from onDown for the onFling event to register
        @Override
        public boolean onDown(MotionEvent e) {
	        	return true;
        }
    }
}