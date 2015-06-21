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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devroo.donttouchme.Adapter.AdapterSetting;
import com.devroo.donttouchme.Class.CRoom;
import com.devroo.donttouchme.Class.CSetting;
import com.devroo.donttouchme.Component.SwipeDismissListViewTouchListener;
import com.devroo.donttouchme.Component.SwipeDismissTouchListener;
import com.devroo.donttouchme.DBHandler.DBRoomHandler;
import com.devroo.donttouchme.DBHandler.DBSettingHandler;
import com.devroo.donttouchme.FourFragment;
import com.devroo.donttouchme.Popup.PopupMap;
import com.devroo.donttouchme.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentRoom extends Fragment implements View.OnClickListener {

    static DBRoomHandler roomDB = null;
    static List<CRoom> apps;
    static ListView lv1;
    static View initView;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView = inflater.inflate(R.layout.room_fragment, container, false);

        ImageButton bButton = (ImageButton) initView.findViewById(R.id.bt_back);
        ImageButton sButton = (ImageButton) initView.findViewById(R.id.bt_save);

        bButton.setOnClickListener(this);
        sButton.setOnClickListener(this);

        TextView topTitle = (TextView) initView.findViewById(R.id.topTitle);
        topTitle.setText("위치 관리");

        roomDB = new DBRoomHandler(getActivity());

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
                                    roomDB.deleteRow(apps.get(position));
                                }
                                initListview();
                            }
                        });
        lv1.setOnTouchListener(touchListener);
        lv1.setOnScrollListener(touchListener.makeScrollListener());

        Log.d("Reading: ", "Reading all Rows..");

        apps = roomDB.getAllRows();

        CRoom ap = null;
        for (CRoom cn : apps) {
            ap = cn;
            String log = "Sn: " + cn.getSN() + " ,loCd: " + cn.getLocationCd() + " ,loNm: " + cn.getLocationNm()
                    + " ,lat: " + cn.getLatitude() + " ,lon: " + cn.getLongitude();
            Log.d("rows: ", log);
        }

        return initView;
    }

    private void initListview() {
        ArrayList image_details = getListData();
        lv1 = (ListView) initView.findViewById(R.id.listView);
        lv1.setAdapter(new AdapterSetting(getActivity(), image_details));
    }

    private ArrayList getListData() {
        ArrayList<String> results = new ArrayList<String>();

        List<CRoom> apps = roomDB.getAllRows();

        for (CRoom cn : apps) {
            results.add(cn.getLocationNm());
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
                newFragment = new FourFragment();
                fragmentManager = getFragmentManager();

                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.view_fragment, newFragment);
                transaction.commit();
                break;
            case R.id.bt_save:
                // save logic

                Intent intent = new Intent(getActivity(), PopupMap.class);
                intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
        }
    }
}

