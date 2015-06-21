package com.devroo.donttouchme.Popup;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.devroo.donttouchme.Class.CRoom;
import com.devroo.donttouchme.Class.CTime;
import com.devroo.donttouchme.DBHandler.DBRoomHandler;
import com.devroo.donttouchme.DBHandler.DBTimeHandler;
import com.devroo.donttouchme.MainActivity;
import com.devroo.donttouchme.R;

import java.util.ArrayList;
import java.util.List;

public class PopupTime extends Activity implements View.OnClickListener {

    static DBRoomHandler roomDB = null;
    static DBTimeHandler db = null;
    static List<String> list = new ArrayList<String>();
    static Spinner spinner;
    static Spinner days;
    static Spinner color;
    static String selText = "";
    static List<CRoom> roomList;
    static ArrayList<String> arr;
    static ArrayList<String> arr2;
    private int mSelectedColorCal0 = 0;
    static String update;
    static ArrayAdapter<String> dataAdapter;
    static ArrayAdapter<String> dataAdapter2;
    static ArrayAdapter<String> dataAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_time);

        Button bt_ok = (Button) findViewById(R.id.bt_ok);
        Button bt_cancel= (Button) findViewById(R.id.bt_cancel);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);

        db = new DBTimeHandler(this);
        roomDB = new DBRoomHandler(this);
        roomList = roomDB.getAllRows();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setPrompt("위치 선택");

        list = new ArrayList<String>();
        for (CRoom rm : roomList) {
            list.add(rm.getLocationNm());
        }

        if (list.size() < 1) {
            Toast.makeText(this, "위치를 먼저 등록해주세요.", Toast.LENGTH_LONG).show();
            finish();
        }

        dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown, list);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(mItemSelectedListener);

        days = (Spinner) findViewById(R.id.days);
        days.setPrompt("요일 선택");

        arr = new ArrayList<String>();
        arr.add("월");
        arr.add("화");
        arr.add("수");
        arr.add("목");
        arr.add("금");
        arr.add("토");
        arr.add("일");

        dataAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_dialog, arr);

        days.setAdapter(dataAdapter2);
        days.setOnItemSelectedListener(mItemSelectedListener);

        color = (Spinner) findViewById(R.id.color);
        color.setPrompt("요일 선택");

        arr2 = new ArrayList<String>();
        arr2.add("Blue");
        arr2.add("Red");
        arr2.add("Green");
        arr2.add("Orange");
        arr2.add("Purple");
        arr2.add("Gray");

        dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_dialog, arr2);

        color.setAdapter(dataAdapter3);
        color.setOnItemSelectedListener(mItemSelectedListener);

        Intent intent = getIntent();
        update = intent.getStringExtra("Update");

        if (update != null) {
            CTime cTime = db.getRow(Integer.valueOf(update));

            EditText classes = (EditText) findViewById(R.id.classes);
            EditText lecture = (EditText) findViewById(R.id.lecture);
            EditText title = (EditText) findViewById(R.id.title);
            EditText professor = (EditText) findViewById(R.id.professor);
            EditText room = (EditText) findViewById(R.id.room);

            classes.setText(cTime.getClasses());
            lecture.setText(cTime.getLecture());
            title.setText(cTime.getTitle());
            professor.setText(cTime.getProfessor());
            room.setText(cTime.getRoom());

            if (!cTime.getDays().equals(null)) {
                int spinnerPostion = dataAdapter2.getPosition(cTime.getDays());
                days.setSelection(spinnerPostion);
            }
            if (!cTime.getColor().equals(null)) {
                int spinnerPostion = dataAdapter3.getPosition(cTime.getColor());
                color.setSelection(spinnerPostion);
            }
            if (!cTime.getLocationNm().equals(null)) {
                int spinnerPostion = dataAdapter.getPosition(cTime.getLocationNm());
                spinner.setSelection(spinnerPostion);
            }
        }
    }

    // Theme 적용함수
    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

        theme.applyStyle(android.R.style.Theme_Panel, true);
    }

    // Button Click Event
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                EditText classes = (EditText) findViewById(R.id.classes);
                EditText lecture = (EditText) findViewById(R.id.lecture);
                EditText title = (EditText) findViewById(R.id.title);
                EditText professor = (EditText) findViewById(R.id.professor);
                EditText room = (EditText) findViewById(R.id.room);

                selText = spinner.getSelectedItem().toString();

                if (classes.getText() == null || classes.getText().length() == 0) {
                    Toast.makeText(this, "교시를 입력해주세요.", Toast.LENGTH_LONG).show();
                    break;
                }
                if (lecture.getText() == null || lecture.getText().length() == 0) {
                    Toast.makeText(this, "연강을 입력해주세요.", Toast.LENGTH_LONG).show();
                    break;
                }
                if (title.getText() == null || title.getText().length() == 0) {
                    Toast.makeText(this, "과목명을 입력해주세요.", Toast.LENGTH_LONG).show();
                    break;
                }
                if (professor.getText() == null || professor.getText().length() == 0) {
                    Toast.makeText(this, "교수님을 입력해주세요.", Toast.LENGTH_LONG).show();
                    break;
                }
                if (room.getText() == null || room.getText().length() == 0) {
                    Toast.makeText(this, "강의실을 입력해주세요.", Toast.LENGTH_LONG).show();
                    break;
                }

                CTime ct = new CTime();
                ct.setDays(days.getSelectedItem().toString());
                ct.setClasses(classes.getText().toString());
                ct.setLectrue(lecture.getText().toString());
                ct.setColor(color.getSelectedItem().toString());
                ct.setTitle(title.getText().toString());
                ct.setProfessor(professor.getText().toString());
                ct.setRoom(room.getText().toString());

                if (selText != null && selText != "") {
                    for (CRoom rm : roomList) {
                        if (rm.getLocationNm() != null && rm.getLocationNm().equals(selText)) {
                            ct.setLocationCd(rm.getLocationCd());
                            ct.setLocationNm(rm.getLocationNm());
                        }
                    }

                    db.addRow(ct);

                    if (update != null) {
                        CTime cTime = db.getRow(Integer.valueOf(update));
                        db.deleteRow(cTime);
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("page", "time");
                    startActivity(intent);
                }
                break;
            case R.id.bt_cancel:
                finish();
                break;
        }
    }

    private Spinner.OnItemSelectedListener mItemSelectedListener = new Spinner.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {
        }

        public void onNothingSelected(AdapterView parent) { }
    };

}
