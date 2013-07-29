package com.mixpanel.src.demo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mixpanel.src.Home;
import com.mixpanel.src.R;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
 
  
        public class Demo extends FragmentActivity {
        	  DemoFragmentAdapter mAdapter;
        	    ViewPager mPager;
        	    PageIndicator mIndicator;
                int position=0;
    
        	       
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.demo);

                mAdapter = new DemoFragmentAdapter(getSupportFragmentManager());

                mPager = (ViewPager)findViewById(R.id.pager);
                mPager.setAdapter(mAdapter);
                CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
                mIndicator = indicator;
                indicator.setViewPager(mPager);

                final float density = getResources().getDisplayMetrics().density;
                indicator.setBackgroundColor(Color.TRANSPARENT);//transparent color
                indicator.setRadius(5 * density);
                indicator.setPageColor(0x880000FF);
                indicator.setFillColor(0xFF888888);
                indicator.setStrokeColor(0xFF000000);
                indicator.setStrokeWidth(2 * density);
                
                
              Button button = (Button) findViewById(R.id.skip);
      		 
             button.setOnClickListener(new OnClickListener() {
    
     			@Override
     			public void onClick(View arg0) {
    
      				 Intent mainIntent = new Intent(Demo.this,Home.class);
     	                startActivity(mainIntent);
       	                finish();
 
      			}
   
     		});	
                
             Button button1 = (Button) findViewById(R.id.next);
      		 
             button1.setOnClickListener(new OnClickListener() {
    
     			@Override
     			public void onClick(View arg0) {
     				position=mPager.getCurrentItem();

     				position=position+1;///moving fragment

     				if(position>7){
     					position=0;
     				}
                    mIndicator.setCurrentItem(position); 
 
      			}
   
     		});	
             
             Button button2 = (Button) findViewById(R.id.previous);
      		 
             button2.setOnClickListener(new OnClickListener() {
    
     			@Override
     			public void onClick(View arg0) {
     				position=mPager.getCurrentItem();

     				if(position<1){
     					position=8;
     				}
     				position=position-1;

                    mIndicator.setCurrentItem(position); 
 
      			}
   
     		});	
                
             
                
                
                
                
                
                
                
                
                
                
                
                
            }
        }