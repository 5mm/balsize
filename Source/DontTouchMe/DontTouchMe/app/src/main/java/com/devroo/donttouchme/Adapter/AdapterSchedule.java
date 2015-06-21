package com.devroo.donttouchme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devroo.donttouchme.Class.CCalendar;
import com.devroo.donttouchme.R;

import java.util.ArrayList;

public class AdapterSchedule extends BaseAdapter {

    private ArrayList<CCalendar> listData;
    private LayoutInflater layoutInflater;

    public AdapterSchedule(Context aContext, ArrayList<CCalendar> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.two_list_row, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.reporter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (listData.get(position).getTypeCd().equals("1")) {
            holder.headlineView.setText(listData.get(position).getTitle());
            holder.reporterNameView.setText(listData.get(position).getSubject());
        } else {
            holder.headlineView.setText("스케줄내용");
            holder.reporterNameView.setText(listData.get(position).getContents());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
    }
}
