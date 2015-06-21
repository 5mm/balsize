package com.devroo.donttouchme.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.devroo.donttouchme.Adapter.AdapterSchedule;
import com.devroo.donttouchme.Adapter.AdapterSetting;
import com.devroo.donttouchme.Class.CCalendar;
import com.devroo.donttouchme.Class.CRoom;
import com.devroo.donttouchme.Component.SwipeDismissListViewTouchListener;
import com.devroo.donttouchme.DBHandler.DBRoomHandler;
import com.devroo.donttouchme.DBHandler.DBScheduleHandler;
import com.devroo.donttouchme.FourFragment;
import com.devroo.donttouchme.Popup.PopupMap;
import com.devroo.donttouchme.Popup.PopupSchedule;
import com.devroo.donttouchme.R;
import com.devroo.donttouchme.ThreeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentCalendar extends Fragment implements View.OnClickListener {

    static DBScheduleHandler db = null;
    static List<CRoom> apps;
    static ListView lv1;
    static View initView;
    static TextView topTitle;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    static String iDate;
    static Date resDate;
    static List<CCalendar> cals;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView = inflater.inflate(R.layout.calendar_fragment, container, false);

        ImageButton bButton = (ImageButton) initView.findViewById(R.id.bt_back);
        ImageButton sButton = (ImageButton) initView.findViewById(R.id.bt_save);

        bButton.setOnClickListener(this);
        sButton.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            iDate = bundle.getString("date", null);
            Log.d("rows", iDate);
        }

        if (iDate != null) {
            try {
                resDate = sdf2.parse(iDate);
                Log.d("rows", resDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            resDate = new Date();
        }

        Log.d("rows", resDate.toString());
        String today = sdf.format(resDate);
        topTitle = (TextView) initView.findViewById(R.id.topTitle);
        topTitle.setText(today);

        db = new DBScheduleHandler(getActivity());

        initListview();

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
                                    db.deleteRow(cals.get(position));
                                }
                                initListview();
                            }
                        });
        lv1.setOnTouchListener(touchListener);
        lv1.setOnScrollListener(touchListener.makeScrollListener());

        return initView;
    }

    private void initListview() {
        ArrayList image_details = getListData();
        lv1 = (ListView) initView.findViewById(R.id.listView);
        lv1.setAdapter(new AdapterSchedule(getActivity(), image_details));
    }

    private ArrayList getListData() {
        ArrayList<CCalendar> results = new ArrayList<CCalendar>();

        cals = db.getAllRows(sdf2.format(resDate));

        for (CCalendar cn : cals) {
            results.add(cn);
        }
        return results;
    }

    //ImageButton 클릭 시 fragmentReplace 호출
    @Override
    public void onClick(View v) {
        Fragment newFragment = null;
        FragmentManager fragmentManager = null;
        FragmentTransaction transaction = null;

        switch (v.getId()) {
            case R.id.bt_back:
                newFragment = new ThreeFragment();
                fragmentManager = getFragmentManager();

                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.view_fragment, newFragment);
                transaction.commit();
                break;
            case R.id.bt_save:
                // save logic

                Intent intent = new Intent(getActivity(), PopupSchedule.class);
                intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("dates", sdf2.format(resDate));
                startActivity(intent);
                break;
        }
    }
}

