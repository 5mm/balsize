package com.devroo.donttouchme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.devroo.donttouchme.Adapter.AdapterSetting;
import com.devroo.donttouchme.Fragment.FragmentApplication;
import com.devroo.donttouchme.Fragment.FragmentRoom;
import com.devroo.donttouchme.Fragment.FragmentSubject;
import com.devroo.donttouchme.Fragment.FragmentTime;

import java.util.ArrayList;

public class FourFragment extends Fragment implements View.OnClickListener {

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.four_fragment, container, false);

        TextView topTitle = (TextView) view.findViewById(R.id.topTitle);
        topTitle.setText("설정");

        ArrayList image_details = getListData();
        final ListView lv1 = (ListView) view.findViewById(R.id.listView);
        lv1.setAdapter(new AdapterSetting(getActivity(), image_details));

        // InnerClass 방식으로 Listener 호출하는 방식
        lv1.setOnItemClickListener( new ListViewItemClickListener() );
        return view;
    }

    // 데이터 가져오기
    private ArrayList getListData() {
        ArrayList<String> results = new ArrayList<String>();

        results.add(new String("과제알림날짜"));
        results.add(new String("시간표 시간설정"));
        results.add(new String("어플리케이션 차단"));
        results.add(new String("강의실 위치관리"));

        return results;
    }

    //ImageButton 클릭 시 fragmentReplace 호출
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                break;
        }
    }

    // InnerClass Listener방식으로 구현
    private class ListViewItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Fragment newFragment = null;
            FragmentManager fragmentManager;
            final FragmentTransaction transaction;

            switch (position) {
                case 0:
                    //D-Days
                    newFragment = new FragmentSubject();
                    break;
                case 1:
                    //Time Schedule
                    newFragment = new FragmentTime();
                    break;
                case 2:
                    //Block Application
                    newFragment = new FragmentApplication();
                    break;
                case 3:
                    //Room Location
                    newFragment = new FragmentRoom();
                    break;
            }

            fragmentManager = getFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.view_fragment, newFragment);
            transaction.commit();
        }
    }
}

