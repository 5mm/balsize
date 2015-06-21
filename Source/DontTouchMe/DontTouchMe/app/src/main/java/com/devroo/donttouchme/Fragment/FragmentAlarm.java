package com.devroo.donttouchme.Fragment;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devroo.donttouchme.Adapter.AdapterAlarm;
import com.devroo.donttouchme.Adapter.AdapterSetting;
import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.Class.CSetting;
import com.devroo.donttouchme.DBHandler.DBSettingHandler;
import com.devroo.donttouchme.FourFragment;
import com.devroo.donttouchme.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlarm extends Fragment implements View.OnClickListener {

    final Long[] soundId = new Long[1000];
    final String[] soundName = new String[1000];
    final String[] soundUri = new String[1000];
    static Ringtone r = null;
    static DBSettingHandler db = null;
    static ListView lv1;
    static ArrayList image_details;
    static int prevPosition = -1;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_fragment, container, false);

        ImageButton bButton = (ImageButton) view.findViewById(R.id.bt_back);
//        ImageButton sButton = (ImageButton) view.findViewById(R.id.bt_save);

        bButton.setOnClickListener(this);
//        sButton.setOnClickListener(this);

        TextView topTitle = (TextView) view.findViewById(R.id.topTitle);
        topTitle.setText("알림음 관리");

        db = new DBSettingHandler(getActivity());

        Log.d("Reading: ", "Reading all Rows..");
        List<CSetting> dates = db.getAllRows();

        CSetting cs = null;
        for (CSetting cn : dates) {
            cs = cn;
            String log = "Sn: " + cn.getSN() + " ,Alarm: " + cn.getAlarm() + " ,AlarmUri: " + cn.getAlarmUri();
            // Writing Contacts to log
            Log.d("rows: ", log);
        }

        image_details = getListData();
        lv1 = (ListView) view.findViewById(R.id.listView);

        if (cs != null) {
            lv1.setAdapter(new AdapterAlarm(getActivity(), image_details, cs.getAlarm()));
        } else {
            lv1.setAdapter(new AdapterAlarm(getActivity(), image_details));
        }
        lv1.setOnItemClickListener(new ListViewItemClickListener());

        if (db.getRowCount() < 1) {
            db.addEmptyRow(new CSetting());
        } else {
            for (int i = 0; i < lv1.getCount(); i++) {
                if (cs.getAlarm() != null && cs.getAlarm().equals(soundName[i])) {
                    prevPosition = i;
                }
            }
        }

        return view;
    }

    private ArrayList getListData() {
//        ArrayList<CSetting> results = new ArrayList<CSetting>();
        ArrayList<String> results = new ArrayList<String>();

        RingtoneManager manager = new RingtoneManager(getActivity());
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        long notificationId = 0;
        String notificationTitle = null;
        String notificationUri = null;
        int i = 0;
        while (cursor.moveToNext()) {

            notificationId = cursor.getLong(RingtoneManager.ID_COLUMN_INDEX);
            notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            soundId[i] = cursor.getLong(RingtoneManager.ID_COLUMN_INDEX);
            soundName[i] = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            soundUri[i] = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            results.add(notificationTitle);
            i++;
        }

//        Uri selectNoti = ContentUris.withAppendedId(Uri.parse(notificationUri), notificationId);
//        r = RingtoneManager.getRingtone(getActivity(), selectNoti);
//        r.play();

        return results;
    }

    @Override
    public void onStop() {
//        r.stop();
        super.onStop();
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
//            case R.id.bt_save:
//                // save logic
//
//                newFragment = new FourFragment();
//                fragmentManager = getFragmentManager();
//
//                transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.view_fragment, newFragment);
//                transaction.commit();
//                break;
        }
    }

    // InnerClass Listener방식으로 구현
    private class ListViewItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CSetting cs = db.getRow(1);
            cs.setAlarm(soundName[position]);
            cs.setAlarmUri(soundUri[position]);
            db.updateRow(cs);

            if (prevPosition != -1) {
                lv1.getChildAt(prevPosition).setBackgroundColor(Color.TRANSPARENT);
            }
            view.setBackgroundColor(Color.rgb(255, 59, 59));
            prevPosition = position;

            Toast.makeText(getActivity(), soundName[position] + "으로 설정되었습니다.", Toast.LENGTH_LONG).show();
        }
    }
}

