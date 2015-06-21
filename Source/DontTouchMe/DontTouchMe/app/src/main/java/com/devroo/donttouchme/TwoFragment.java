package com.devroo.donttouchme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.devroo.donttouchme.Class.CSetting;
import com.devroo.donttouchme.Class.CTime;
import com.devroo.donttouchme.Component.WeekView;
import com.devroo.donttouchme.Component.WeekViewEvent;
import com.devroo.donttouchme.DBHandler.DBSettingHandler;
import com.devroo.donttouchme.DBHandler.DBTimeHandler;
import com.devroo.donttouchme.Popup.PopupTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TwoFragment extends Fragment implements View.OnClickListener, WeekView.MonthChangeListener, WeekView.EventClickListener {

    private WeekView mWeekView;
    static DBTimeHandler db = null;
    static DBSettingHandler setDB = null;
    static int startVal = 9;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_fragment, container, false);

        ImageButton button = (ImageButton) view.findViewById(R.id.bt_add);
        ImageButton button2 = (ImageButton) view.findViewById(R.id.bt_del);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);

        TextView topTitle = (TextView) view.findViewById(R.id.topTitle);
        topTitle.setText("시간표");

        db = new DBTimeHandler(getActivity());
        setDB = new DBSettingHandler(getActivity());
        List<CSetting> setings = setDB.getAllRows();

        CSetting cs = null;
        for (CSetting cn : setings) {
            cs = cn;
            String log = "Sn: " + cn.getSN() + " ,Alarm: " + cn.getAlarm() + " ,AlarmUri: " + cn.getAlarmUri();
            // Writing Contacts to log
            Log.d("rows: ", log);
        }

        if (setDB.getRowCount() < 1) {
            setDB.addEmptyRow(new CSetting());
        } else {
            if (cs.getTimeStart() != null) {
                startVal = Integer.valueOf(cs.getTimeStart().substring(0, 2));
            }
        }

        Log.d("Reading: ", "Reading all Rows..");
        List<CTime> dates = db.getAllRows();

        for (CTime cn : dates) {
            String log = "Sn: "+cn.getSN()+" ,days: " + cn.getDays() + " ,class: " + cn.getClasses() + " ,lecture: " + cn.getLecture()
                    + " ,color: " + cn.getColor() + " ,title: " + cn.getTitle() + " ,prof: " + cn.getProfessor()
                    + " ,room: " + cn.getRoom() + " ,locCd: " + cn.getLocationCd() + " ,locNm: " + cn.getLocationNm();
            // Writing Contacts to log
            Log.d("rows: ", log);
        }

        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);

        return view;
    }

    //ImageButton 클릭 시 Intent를 이용하여 Popup으로 Activity전환
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                Intent intent = new Intent(getActivity(), PopupTime.class);
                intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.bt_del:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("전체삭제")        // 제목 설정
                        .setMessage("시간표를 전체삭제 하시겠습니까?")        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whichButton){
                                List<CTime> ctList = db.getAllRows();
                                for (CTime ct : ctList) {
                                    db.deleteRow(ct);
                                }
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("page", "time");
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        List<CTime> dates = db.getAllRows();

        for (CTime cn : dates) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, startVal + Integer.valueOf(cn.getClasses()) - 1);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth - 1);
            startTime.set(Calendar.YEAR, newYear);
            switch (cn.getDays()) {
                case "월":
                    startTime.set(Calendar.DATE, 4);
                    break;
                case "화":
                    startTime.set(Calendar.DATE, 5);
                    break;
                case "수":
                    startTime.set(Calendar.DATE, 6);
                    break;
                case "목":
                    startTime.set(Calendar.DATE, 7);
                    break;
                case "금":
                    startTime.set(Calendar.DATE, 8);
                    break;
                case "토":
                    startTime.set(Calendar.DATE, 9);
                    break;
                case "일":
                    startTime.set(Calendar.DATE, 10);
                    break;
            }

            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, Integer.valueOf(cn.getLecture()));
            endTime.set(Calendar.MONTH, newMonth - 1);
            String eventTitle = cn.getTitle() + "\n" + cn.getRoom();

            WeekViewEvent event = new WeekViewEvent(Integer.valueOf(cn.getSN()), eventTitle, startTime, endTime);
            if (cn.getColor() != null) {
                switch (cn.getColor()) {
                    case "Blue":
                        event.setColor(getResources().getColor(R.color.event_color_01));
                        break;
                    case "Red":
                        event.setColor(getResources().getColor(R.color.event_color_02));
                        break;
                    case "Green":
                        event.setColor(getResources().getColor(R.color.event_color_03));
                        break;
                    case "Orange":
                        event.setColor(getResources().getColor(R.color.event_color_04));
                        break;
                    case "Purple":
                        event.setColor(Color.parseColor("#aa66cc"));
                        break;
                    case "Gray":
                        event.setColor(Color.parseColor("#888888"));
                        break;
                }
            } else {
                event.setColor(getResources().getColor(R.color.event_color_01));
            }
            events.add(event);
        }

        return events;
    }

    static String selButton = "수정";
    @Override
    public void onEventClick(final WeekViewEvent event, RectF eventRect) {

        selButton = "수정";
        final String items[] = { "수정", "삭제" };
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setTitle("Dialog");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        selButton = items[whichButton];
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        CTime cTime = db.getRow(event.getId());
                        Intent intent = null;
                        switch (selButton) {
                            case "수정":
                                intent = new Intent(getActivity(), PopupTime.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                intent.putExtra("Update", String.valueOf(cTime.getSN()));
                                startActivity(intent);
                                break;
                            case "삭제":
                                db.deleteRow(cTime);

                                intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("page", "time");
                                startActivity(intent);
                                break;
                        }
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        ab.show();
    }

}

