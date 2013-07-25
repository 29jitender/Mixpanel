package com.mixpanel.src;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class Temp implements OnItemClickListener{

    private View lastSelectedView = null;

    public void clearSelection()
    {
        if(lastSelectedView != null) lastSelectedView.setBackgroundColor(0xFFF06D2F);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
        clearSelection();
        
        lastSelectedView = view;
        view.setBackgroundDrawable(view.getContext().getResources().getDrawable(R.drawable.round_button1));
    }
}