package com.mixpanel.src;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class HomeFragmentAdapter extends FragmentPagerAdapter   {
    protected static final String[] CONTENT = new String[] { "Today" };//setting title
  
    private int mCount = CONTENT.length;

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HomeFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return HomeFragmentAdapter.CONTENT[position % CONTENT.length];
    }
 

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}