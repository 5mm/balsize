package com.devroo.donttouchme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.devroo.donttouchme.Adapter.AdapterDate;
import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.Component.SwipeDismissListViewTouchListener;
import com.devroo.donttouchme.DBHandler.DBDateHandler;
import com.devroo.donttouchme.Popup.PopupDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OneFragment extends Fragment implements View.OnClickListener {

    static DBDateHandler db = null;
    static ListView lv1;
    static View initView;
    static TextView topTitle;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView = inflater.inflate(R.layout.one_fragment, container, false);

        ImageButton button = (ImageButton) initView.findViewById(R.id.bt_add);
        button.setOnClickListener(this);

        String today = sdf.format(new Date());
        topTitle = (TextView) initView.findViewById(R.id.topTitle);
        topTitle.setText(today);

        Date where = null;
        try {
            where = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ImageButton left = (ImageButton) initView.findViewById(R.id.bt_left);
        ImageButton right = (ImageButton) initView.findViewById(R.id.bt_right);

        left.setOnClickListener(this);
        right.setOnClickListener(this);

        db = new DBDateHandler(getActivity());
        initListview(where);

        Log.d("Reading: ", "Reading all Rows..");
        List<CDate> dates = db.getAllRows(where);

        int hour = 0, minute = 0;
        for (CDate cn : dates) {
            String log = "Sn: " + cn.getSN() + " ,S-Time: " + cn.getSTime() + " ,E-Time: " + cn.getETime() + " ,V-Date: " + cn.getVDate();
            // Writing Contacts to log
            Log.d("rows: ", log);

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
            Date date1 = null, date2 = null;
            try {
                date1 = dateFormat.parse(cn.getSTime().toString());
                date2 = dateFormat.parse(cn.getETime().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long duration = date2.getTime() - date1.getTime();
            Log.d("rows", String.valueOf(duration));
            hour += (duration/1000)/(60 * 60);
            minute += ((duration/1000)%(60 * 60))/60;
        }

        if (minute > 60) {
            int other = minute/60;
            hour += other;
            minute -= other * 60;
        }

        EditText tHour = (EditText) initView.findViewById(R.id.hour);
        EditText tMinute = (EditText) initView.findViewById(R.id.minute);

        tHour.setText(String.valueOf(hour));
        tMinute.setText(String.valueOf(minute));

        tHour.setFocusable(false);
        tHour.setClickable(false);
        tMinute.setFocusable(false);
        tMinute.setClickable(false);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lv1,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    db.deleteRow(db.getAllRows().get(position));
                                }

                                try {
                                    initListview(sdf.parse(topTitle.getText().toString()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        lv1.setOnTouchListener(touchListener);
        lv1.setOnScrollListener(touchListener.makeScrollListener());

        return initView;
    }

    private void initListview(Date where) {
        lv1 = (ListView) initView.findViewById(R.id.listView);
        lv1.setAdapter(new AdapterDate(getActivity(), db.getAllRows(where)));

        List<CDate> dates = db.getAllRows(where);

        int hour = 0, minute = 0;
        for (CDate cn : dates) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
            Date date1 = null, date2 = null;
            try {
                date1 = dateFormat.parse(cn.getSTime().toString());
                date2 = dateFormat.parse(cn.getETime().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long duration = date2.getTime() - date1.getTime();
            hour += (duration/1000)/(60 * 60);
            minute += ((duration/1000)%(60 * 60))/60;
        }

        if (minute > 60) {
            int other = minute/60;
            hour += other;
            minute -= other * 60;
        }

        EditText tHour = (EditText) initView.findViewById(R.id.hour);
        EditText tMinute = (EditText) initView.findViewById(R.id.minute);

        tHour.setText(String.valueOf(hour));
        tMinute.setText(String.valueOf(minute));
    }

    //ImageButton 클릭 시 Intent를 이용하여 Popup으로 Activity전환
    @Override
    public void onClick(View v) {
        Date da = null;
        try {
            da = sdf.parse(topTitle.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long todayTime = da.getTime();
        String today = "";

        switch (v.getId()) {
            case R.id.bt_add:
                Intent intent = new Intent(getActivity(), PopupDate.class);
                intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.bt_left:
                da.setTime(todayTime - (1 * 1000 * 60 * 60 * 24));
                today = sdf.format(da);

                topTitle = (TextView) initView.findViewById(R.id.topTitle);
                topTitle.setText(today);

                initListview(da);
                break;
            case R.id.bt_right:
                da.setTime(todayTime + (1 * 1000 * 60 * 60 * 24));
                today = sdf.format(da);

                topTitle = (TextView) initView.findViewById(R.id.topTitle);
                topTitle.setText(today);

                initListview(da);
                break;

        }
    }
}

