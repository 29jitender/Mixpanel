package com.mixpanel.src.funnel;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

class Funnel_display_fragment extends FragmentPagerAdapter implements IconPagerAdapter {
	int totalevents=Funnal_final.total;
	
	Float a =(float) (totalevents/4.0);
	int total1=(int)Math.ceil(a);
	


    

   // private int mCount = CONTENT.length;

    public Funnel_display_fragment(FragmentManager fm) {
    	
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	ArrayList<String> title_count = new ArrayList<String>();
    	for(int i=0;i<total1;i++){
    		title_count.add(i+"");
    	}
        return Funnel_display_main.newInstance(title_count.get(position % title_count.size()));
    }

    @Override
    public int getCount() {
        return total1;
    }

    @Override
    public CharSequence getPageTitle(int position) {//adding title
     	ArrayList<String> title = new ArrayList<String>();
     	if(totalevents<4){
     		title.add("Step 1-"+totalevents+" of "+totalevents);
     	}
     	else{
		     	for(int i=0;i<total1;i++){
		     		if(totalevents<(i*4+4)){
		     			int temp=i*4+4-totalevents;	
		     			switch(temp){
		     			case 0:
				     		title.add("Step "+(i*4+1)+ "-"+((i*4+4))+" of "+totalevents);

		     	    		
		     	    		break;
		     	    	case 1:
				     		title.add("Step "+(i*4+1)+ "-"+((i*4+4)-1)+" of "+totalevents);

		     	    		
		     	    		break;
		     	    	case 2:
				     		title.add("Step "+(i*4+1)+ "-"+((i*4+4)-2)+" of "+totalevents);

		     	    		break;
		     	    	case 3:
				     		title.add("Step "+(i*4+1)+" of "+totalevents);

		     	    		
		     	    		break;
		     	     
		     	    	}
		     	     	  
		     		
		     			
		     		}
		     		else{
		     		title.add("Step"+(i*4+1)+ "-"+(i*4+4)+" of "+totalevents);
		     		}
		     		
		     	}
     	}
      //return Funnel_display_fragment.CONTENT[position % CONTENT.length];
     	return title.get(position % title.size());
    }

    

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
        	total1 = count;
            notifyDataSetChanged();
        }
    }

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}