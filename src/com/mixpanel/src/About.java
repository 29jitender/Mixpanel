package com.mixpanel.src;

import net.simonvt.menudrawer.MenuDrawer;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class About extends SherlockActivity implements View.OnClickListener {
	ImageButton imageButton;
	//navigation drawer variables
    private static final String STATE_MENUDRAWER = "net.simonvt.menudrawer.samples.WindowSample.menuDrawer";
    private static final String STATE_ACTIVE_VIEW_ID = "net.simonvt.menudrawer.samples.WindowSample.activeViewId";
    private MenuDrawer mMenuDrawer; 
    private int mActiveViewId;
    //navigation
	 private int anmi=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //navigation
        if (savedInstanceState != null) {
            mActiveViewId = savedInstanceState.getInt(STATE_ACTIVE_VIEW_ID);
        }
        
        ////////////////////////////////////////////////////
        // Action bar
          ActionBar mActionBar;
        LayoutInflater mInflater;
        View mCustomView;
         TextView mTitleTextView;
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.menu, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("My Own Title");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        // mActionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_header_bg));
        ImageButton ibItem1 = (ImageButton)  findViewById(R.id.btn_slide);
        ibItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMenuDrawer.toggleMenu();
            }
        });
        
        /////////////////////////////////////////////
        
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
        mMenuDrawer.setMenuView(R.layout.menu_scrollview);// this is the layout for 
        Display display = getWindowManager().getDefaultDisplay(); 		 
  		int width = display.getWidth();  
        mMenuDrawer.setMenuSize(width/3);//size of menu     
        mMenuDrawer.setDropShadow(android.R.color.transparent);//removin showdo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            getActionBar().setDisplayHomeAsUpEnabled(true);
            // this is for the color of title bar
        	 ColorDrawable colorDrawable = new ColorDrawable();
             colorDrawable.setColor(Color.parseColor("#44C19F"));//menu 2
             android.app.ActionBar actionBar = getActionBar();
             actionBar.setBackgroundDrawable(colorDrawable);

        }

       // mContentTextView = (TextView) findViewById(R.id.contentText);

        findViewById(R.id.item1).setOnClickListener(this);
        findViewById(R.id.item2).setOnClickListener(this);
        findViewById(R.id.item3).setOnClickListener(this);
        findViewById(R.id.item4).setOnClickListener(this);


        TextView activeView = (TextView) findViewById(mActiveViewId);
        if (activeView != null) {
            mMenuDrawer.setActiveView(activeView);
            //mContentTextView.setText("Active item: " + activeView.getText());
        } 
    
        //navigation
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

     
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        //This uses the imported MenuItem from ActionBarSherlock
		     
		  		switch(item.getItemId()){
		  		 
		  		 
		  			case android.R.id.home:
		  			    finish();

		  	            return true; 
					default:
						return false;	
		  						
		  		}
		  	
	        
	       
	    }
	
	//rest functinality for of navigation
	    @Override
	    protected void onRestoreInstanceState(Bundle inState) {
	        super.onRestoreInstanceState(inState);
	        mMenuDrawer.restoreState(inState.getParcelable(STATE_MENUDRAWER));
	    }

	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putParcelable(STATE_MENUDRAWER, mMenuDrawer.saveState());
	        outState.putInt(STATE_ACTIVE_VIEW_ID, mActiveViewId);
	    }
	 
	    @Override
	    public void onBackPressed() {
	    	
	        final int drawerState = mMenuDrawer.getDrawerState();
	        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
	            mMenuDrawer.closeMenu();
	            return;
	        }

	        super.onBackPressed();
	    }

	    @Override
	    public void onClick(View v) { // for the click view
	    	

	    	switch(v.getId()){
	    	case R.id.item1:
	    		mMenuDrawer.setActiveView(v);
	  		  	//mMenuDrawer.closeMenu();
	            startActivity(new Intent(this, Home.class));  
	            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

	    		break;
	    	case R.id.item2:
	    		 mMenuDrawer.setActiveView(v);
	   		  //	mMenuDrawer.closeMenu();
	             startActivity(new Intent(this, Event_activity.class));
		            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

	             anmi=1;
	    		break;
	    	case R.id.item3:
	    		mMenuDrawer.setActiveView(v);
	  		  //mMenuDrawer.closeMenu();
	            startActivity(new Intent(this, Event_top.class));    
	            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	            anmi=1;
	    		
	    		break;
	    	case R.id.item4:
	    		
	    		 mMenuDrawer.setActiveView(v);
	      		  mMenuDrawer.closeMenu();
//	              startActivity(new Intent(this, About.class));
//	              overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	              anmi=1;
	    		
	    		break;

	    	}
	     	  
	    	
	    	
	    	
	    	
	    	
	      
	        mActiveViewId = v.getId();
	    }
	//navigaiton ending

	
	
	
	
	
}
