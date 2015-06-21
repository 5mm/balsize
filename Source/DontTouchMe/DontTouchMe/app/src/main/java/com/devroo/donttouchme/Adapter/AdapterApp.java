package com.devroo.donttouchme.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devroo.donttouchme.Class.CApp;
import com.devroo.donttouchme.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterApp extends BaseAdapter {

    private ArrayList<String> listData;
    private List<CApp> apps;
    private LayoutInflater layoutInflater;

    public AdapterApp(Context aContext, ArrayList<String> listData, List<CApp> apps) {
        this.listData = listData;
        this.apps = apps;
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
            convertView = layoutInflater.inflate(R.layout.one_list_row, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.headlineView.setText(listData.get(position));

        boolean isChecked = false;
        for (CApp ap : apps) {
            if (listData.get(position).equals(ap.getAppNm())) {
                isChecked = true;
            }
        }
        if (isChecked) {
            convertView.setBackgroundColor(Color.rgb(255,59,59));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    // 데이터 구조
    static class ViewHolder {
        TextView headlineView;
    }
}
