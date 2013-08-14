package com.mixpanel.src.people;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mixpanel.src.R;

public class CustomListAdapter extends BaseAdapter {

	private ArrayList<Item> listData;
	private ArrayList<Item> arraylist;


	private LayoutInflater layoutInflater;

	public CustomListAdapter(Context context, ArrayList<Item> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
		
		this.arraylist = new ArrayList<Item>();
        this.arraylist.addAll(listData);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
 			convertView = layoutInflater.inflate(R.layout.people_first_list, null);
			holder = new ViewHolder();
			holder.headlineView = (TextView) convertView.findViewById(R.id.people_first_name);
			holder.reporterNameView = (TextView) convertView.findViewById(R.id.people_first_email);
			holder.reportedDateView = (TextView) convertView.findViewById(R.id.people_first_time);
			holder.location = (TextView) convertView.findViewById(R.id.people_first_location);
			holder.id = (TextView) convertView.findViewById(R.id.people_first_id);

			holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
			convertView.setTag(holder);
	 

		Item newsItem = (Item) listData.get(position);

		holder.headlineView.setText(newsItem.getHeadline());
		holder.reporterNameView.setText(newsItem.getReporterName());
		holder.reportedDateView.setText(newsItem.getDate());
		holder.location.setText(newsItem.getlocation());
		holder.id.setText(newsItem.getuserid());


		if (holder.imageView != null) {
			new ImageDownloaderTask(holder.imageView).execute(newsItem.getUrl());
		}

		return convertView;
	}

	static class ViewHolder {
		TextView headlineView;
		TextView reporterNameView;
		TextView reportedDateView;
		TextView location;
		TextView id;

		ImageView imageView;
	}
	
	 // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listData.clear();
        if (charText.length() == 0) {
        	listData.addAll(arraylist);
        } else {
            for (Item wp : arraylist) {
                if (wp.getHeadline().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	listData.add(wp);
                }
                else if (wp.getReporterName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	listData.add(wp);
                }
                else if (wp.getlocation().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	listData.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
