package com.mixpanel.src.demo;

import com.mixpanel.src.R;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;


public final class DemoFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    TextView  text;
    public static DemoFragment newInstance(String content) {
        DemoFragment fragment = new DemoFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getActivity().setContentView(R.layout.demo);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.demofragment, container, false);//defing layout
 
 
        return view;
    	
    }


  
	@Override
	public void onResume() {
    	text =  (TextView)getView().findViewById(R.id.demo_text);
    	ImageView imageview  =  (ImageView)getView().findViewById(R.id.imageView1);

  		 if(mContent.equals("1")){
			    text.setText("This is home Screen. It give top five events of today and there value of last 7 days");
			    imageview.setImageDrawable(getResources().getDrawable(R.drawable.screen1));

		   }
		   else  if(mContent.equals("2")){
			    text.setText("Here you can modify Api key and Secret by clicking Modify in home screen");
			    imageview.setImageDrawable(getResources().getDrawable(R.drawable.screen2));


		   }
		   else  if(mContent.equals("3")){
			    text.setText("By click on event name you can see its values of last 7 days");
			    imageview.setImageDrawable(getResources().getDrawable(R.drawable.screen3));


		   }
		   else  if(mContent.equals("4")){
			    text.setText("This is menu.You can open it by swiping to right from the edge of screen or by clicking the drawer button");
			    imageview.setImageDrawable(getResources().getDrawable(R.drawable.screen4));


		   }
		   else  if(mContent.equals("5")){
			    text.setText("All events");
			    imageview.setImageDrawable(getResources().getDrawable(R.drawable.screen5));


		   }
		   else  if(mContent.equals("6")){
			    text.setText("Event data");
			    imageview.setImageDrawable(getResources().getDrawable(R.drawable.screen6));


		   }
		   else  if(mContent.equals("7")){
			    text.setText("Top Events");
			    imageview.setImageDrawable(getResources().getDrawable(R.drawable.screen7));


		   }
 		super.onResume();
	}

	public void callin(){
    	
    	
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
