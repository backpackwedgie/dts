package com.downthestreetapp.downthestreet;

import java.util.ArrayList;

import com.downthestreetapp.downthestreet.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter{

	private static ArrayList<Event> searchArrayList;
    private LayoutInflater mInflater;
    public CustomBaseAdapter(Context context, ArrayList<Event> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }
 
    public int getCount() {
        return searchArrayList.size();
    }
 
    public Object getItem(int position) {
        return searchArrayList.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item_row, null);
            holder = new ViewHolder();
            holder.eventtitle = (TextView) convertView.findViewById(R.id.eventtitle);
            holder.hostedBy = (TextView) convertView.findViewById(R.id.hosted_by);
            holder.shdesc = (TextView) convertView.findViewById(R.id.shdesc);
            holder.timetill = (TextView) convertView.findViewById(R.id.time);
            holder.distance = (TextView) convertView.findViewById(R.id.distance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.eventtitle.setText(searchArrayList.get(position).eventtitle);
        holder.hostedBy.setText("Hosted by: " + searchArrayList.get(position).hostedBy);
        holder.shdesc.setText(searchArrayList.get(position).shdesc);
        holder.timetill.setText(searchArrayList.get(position).TimeTill());
        holder.distance.setText(searchArrayList.get(position).placeName);
        return convertView;
    }
 
    static class ViewHolder {
        TextView eventtitle;
        TextView shdesc;
        TextView hostedBy;
        TextView timetill;
        TextView distance;
    }

}
