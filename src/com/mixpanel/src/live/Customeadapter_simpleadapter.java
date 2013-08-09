package com.mixpanel.src.live;

import java.util.List;
import java.util.Map;

import android.content.Context;  
import android.database.Cursor;  
import android.graphics.Color;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;  
  
/** 
* @author C'dine 
* A simple cursor adapter. Only variation is that it displays alternate rows 
*  in alternate colors. 
*/  
public class Customeadapter_simpleadapter extends SimpleAdapter{

	public Customeadapter_simpleadapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	  View view = super.getView(position, convertView, parent);
	  int[] colors = new int[] { 0x30ffffff, 0xfff7f7f7 };

	  int colorPos = position % colors.length;
	  view.setBackgroundColor(colors[colorPos]);
	  return view;
	}
} 