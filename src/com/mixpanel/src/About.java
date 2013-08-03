package com.mixpanel.src;

import net.simonvt.menudrawer.MenuDrawer;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
    	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //open only keyboard when user click

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
        mTitleTextView.setText("About");
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
             colorDrawable.setColor(Color.parseColor("#25d3ee"));//menu 2
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
		feedback();
		Rate();
		share();
		//credit


 				TextView tv1=	(TextView) findViewById(R.id.crd1);
				tv1.setMovementMethod(LinkMovementMethod.getInstance());
		 		tv1.setText(Html.fromHtml(  getResources().getString(R.string.crd1) ) );

				stripUnderlines(tv1);

				((TextView) findViewById(R.id.crd2)).setMovementMethod(LinkMovementMethod.getInstance());
				((TextView) findViewById(R.id.crd2)).setText(Html.fromHtml( getResources().getString(R.string.crd2)));
				stripUnderlines(((TextView) findViewById(R.id.crd2)));

				((TextView) findViewById(R.id.crd3)).setMovementMethod(LinkMovementMethod.getInstance());
				((TextView) findViewById(R.id.crd3)).setText(Html.fromHtml( getResources().getString(R.string.crd3) ));
				stripUnderlines(((TextView) findViewById(R.id.crd3)));

				((TextView) findViewById(R.id.crd4)).setMovementMethod(LinkMovementMethod.getInstance());
				((TextView) findViewById(R.id.crd4)).setText(Html.fromHtml( getResources().getString(R.string.crd4) ));
				stripUnderlines(((TextView) findViewById(R.id.crd4)));

				((TextView) findViewById(R.id.crd5)).setMovementMethod(LinkMovementMethod.getInstance());
				((TextView) findViewById(R.id.crd5)).setText(Html.fromHtml( getResources().getString(R.string.crd5) ));
				stripUnderlines(((TextView) findViewById(R.id.crd5)));

	}
	
	private void stripUnderlines(TextView textView) {
	    Spannable s = (Spannable)textView.getText();
	    URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
	    for (URLSpan span: spans) {
	        int start = s.getSpanStart(span);
	        int end = s.getSpanEnd(span);
	        s.removeSpan(span);
	        span = new URLSpanNoUnderline(span.getURL());
	        s.setSpan(span, start, end, 0);
	    }
	    textView.setText(s);
	}
	public void feedback(){
		  Button button = (Button) findViewById(R.id.submit);
		  
          button.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
					 Intent Email = new Intent(Intent.ACTION_SEND);
					 EditText feedback = (EditText)  	findViewById(R.id.feedback_edit);
					    String feedback_string = feedback.getText().toString(); 
				        Email.setType("text/email");
				        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "29jitender@gmail.com" });
				        Email.putExtra(Intent.EXTRA_SUBJECT, "Mixpanel App Feedback");
				        Email.putExtra(Intent.EXTRA_TEXT, feedback_string);
				        startActivity(Intent.createChooser(Email, "Send Feedback:"));
	 
				}
	 
			});	     
		
	}
	
	
	public void share(){
		TextView share = (TextView) findViewById(R.id.share);
		  
		share.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
						String message = "Text I wan't to share.";
							Intent share = new Intent(Intent.ACTION_SEND);
							share.setType("text/plain");
							share.putExtra(Intent.EXTRA_TEXT, message);

							startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
				}
	 
			});	
		
	}
	


	public void Rate(){
		TextView share = (TextView) findViewById(R.id.rate);
		  
		share.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
					 Intent intent = new Intent(Intent.ACTION_VIEW);
					   intent.setData(Uri.parse("market://details?id=com.android.example"));
					   startActivity(intent);
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
	    		  Intent myIntent = new Intent(About.this ,Home.class);//refreshing
	              myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	              Home.mMenuDrawer.closeMenu();

	              startActivity(myIntent);
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
