package com.devroo.donttouchme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.R;

import java.util.List;

public class AdapterDate extends BaseAdapter {

    private List<CDate> listData;
    private LayoutInflater layoutInflater;

    public AdapterDate(Context aContext, List<CDate> listData) {
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
            convertView = layoutInflater.inflate(R.layout.one_list_row, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.reportView = (TextView) convertView.findViewById(R.id.reporter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.headlineView.setText(listData.get(position).getSTime() + " ~ " + listData.get(position).getETime());
        int sH, sM, eH, eM;
        sH = Integer.valueOf(listData.get(position).getSTime().split(":")[0]);
        sM = Integer.valueOf(listData.get(position).getSTime().split(":")[1]);
        eH = Integer.valueOf(listData.get(position).getETime().split(":")[0]);
        eM = Integer.valueOf(listData.get(position).getETime().split(":")[1]);

        int total = (eH-sH) * 60 + (eM-sM);
        String totalStr = null;
        if (total < 60) {
            totalStr = total + " 분 ";
        } else {
            totalStr = total/60 + " 시간 " + total%60 + " 분 ";
        }

        holder.reportView.setText(totalStr);
        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView reportView;
    }
}
