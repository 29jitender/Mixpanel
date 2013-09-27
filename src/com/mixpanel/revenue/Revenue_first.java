package com.mixpanel.revenue;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.mixpanel.src.ParseJSON;
import com.mixpanel.src.R;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
 
  
        public class Revenue_first extends FragmentActivity implements com.mixpanel.src.Callback {
        	  RevenueFragmentAdapter mAdapter;
        	    ViewPager mPager;
        	    PageIndicator mIndicator;
                int position=0;
                public static String data;
        	       
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.revenue_first); 
                ParseJSON ParseJson_object = new ParseJSON();
        		ParseJson_object.pass_values("revenu_home");
        		ParseJson_object.setListener(this);
                
                
            }


			@Override
			public void methodToCallback(String response) {
 				data=response;
				 mAdapter = new RevenueFragmentAdapter(getSupportFragmentManager());

	                mPager = (ViewPager)findViewById(R.id.pager);
	                mPager.setAdapter(mAdapter);

	                TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
	                mIndicator = indicator;
	                indicator.setViewPager(mPager);

	                final float density = getResources().getDisplayMetrics().density;
	                indicator.setBackgroundColor(0x18FF0000);
	                indicator.setFooterColor(0xFFAA2222);
	                indicator.setFooterLineHeight(1 * density); //1dp
	                indicator.setFooterIndicatorHeight(3 * density); //3dp
	                indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
	                indicator.setTextColor(0xAA000000);
	                indicator.setSelectedColor(0xFF000000);
	                indicator.setSelectedBold(true);
 			}
        }