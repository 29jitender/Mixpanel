package com.mixpanel.src.demo;

import android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

class DemoFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "1", "2", "3", "4", "5","6","7",};
    protected static final int[] ICONS = new int[] {
    	 R.drawable.alert_dark_frame,
         R.drawable.alert_dark_frame,
         R.drawable.alert_dark_frame,
         R.drawable.alert_dark_frame
    };

    private int mCount = CONTENT.length;

    public DemoFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DemoFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return DemoFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}