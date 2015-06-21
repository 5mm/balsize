package com.devroo.donttouchme.Popup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.devroo.donttouchme.Class.CCalendar;
import com.devroo.donttouchme.DBHandler.DBScheduleHandler;
import com.devroo.donttouchme.MainActivity;
import com.devroo.donttouchme.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class PopupSchedule extends FragmentActivity implements View.OnClickListener {

    List<String> list = new ArrayList<String>();
    int mCurrentFragmentIndex = 1;
    static DBScheduleHandler db = null;
    static Spinner spinner;
    int year, month, day, hour, minute;
    static EditText dates;
    static String dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_schedule);

        Button bt_ok = (Button) findViewById(R.id.bt_ok);
        Button bt_cancel = (Button) findViewById(R.id.bt_cancel);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);

        db = new DBScheduleHandler(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setPrompt("Selected Types");

        list.add("과제");
        list.add("스케줄");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(mItemSelectedListener);

        dates = (EditText) findViewById(R.id.date);
        dates.setInputType(0);
        dates.setOnClickListener(this);

        Intent intent = getIntent();
        dt = intent.getStringExtra("dates");

        if (dt != null) {
            dates.setText(dt);
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = String.format("%d-%02d-%02d", year,monthOfYear+1, dayOfMonth);
            dates.setText(msg);
        }
    };

    // fragment 영역에 fragment를 set 해주는 function
    public void fragmentReplace(int reqNewFragmentIndex) {
        Fragment newFragment = getFragment(reqNewFragmentIndex);  // replace fragment

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.view_fragment, newFragment);
        transaction.commit();
    }

    // fragment의 index를 입력받아 해당 fragment 를 반환하는 function
    private Fragment getFragment(int idx) {
        Fragment newFragment = null;
        switch (idx) {
            case 1:
                newFragment = new PopupScheduleSub1Fragment();
                break;
            case 2:
                newFragment = new PopupScheduleSub2Fragment();
                break;
        }
        return newFragment;
    }

    // Theme 적용함수
    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

        theme.applyStyle(android.R.style.Theme_Panel, true);
    }

    // Button Click Event
    public void onClick(View v) {
        CCalendar cCalendar = new CCalendar();
        switch (v.getId()) {
            case R.id.bt_ok:
                if (spinner.getSelectedItem().toString().equals("과제")) {
                    EditText date = (EditText) findViewById(R.id.date);
                    EditText title = (EditText) findViewById(R.id.title);
                    EditText subject = (EditText) findViewById(R.id.subject);

                    if (date.getText() == null || date.getText().length() == 0) {
                        Toast.makeText(this, "일자를 입력해주세요.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (title.getText() == null || title.getText().length() == 0) {
                        Toast.makeText(this, "과목명을 입력해주세요.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (subject.getText() == null || subject.getText().length() == 0) {
                        Toast.makeText(this, "과제명을 입력해주세요.", Toast.LENGTH_LONG).show();
                        break;
                    }

                    cCalendar.setTypeCd("1");
                    cCalendar.setTypeNm("과제");
                    cCalendar.setVDtm(date.getText().toString());
                    cCalendar.setTitle(title.getText().toString());
                    cCalendar.setSubject(subject.getText().toString());
                } else {
                    EditText date = (EditText) findViewById(R.id.date);
                    CheckBox check = (CheckBox) findViewById(R.id.Block);
                    EditText sTime = (EditText) findViewById(R.id.sTime);
                    EditText eTime = (EditText) findViewById(R.id.eTime);
                    EditText contents = (EditText) findViewById(R.id.contents);


                    if (date.getText() == null || date.getText().length() == 0) {
                        Toast.makeText(this, "일자를 입력해주세요.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (contents.getText() == null || contents.getText().length() == 0) {
                        Toast.makeText(this, "스케줄 내용을 입력해주세요.", Toast.LENGTH_LONG).show();
                        break;
                    }

                    cCalendar.setTypeCd("2");
                    cCalendar.setTypeNm("스케줄");
                    cCalendar.setVDtm(date.getText().toString());
                    cCalendar.setOutYn(String.valueOf(check.isChecked()));

                    if (check != null && check.isChecked()) {
                        cCalendar.setSTime(sTime.getText().toString());
                        cCalendar.setETime(eTime.getText().toString());
                    }
                    cCalendar.setContents(contents.getText().toString());
                }
                db.addRow(cCalendar);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("page", "sch");
                startActivity(intent);
                break;
            case R.id.bt_cancel:
                finish();
                break;
            case R.id.date:
                GregorianCalendar calendar = new GregorianCalendar();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day= calendar.get(Calendar.DAY_OF_MONTH);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                new DatePickerDialog(this, dateSetListener, year, month, day).show();
                break;
        }
    }

    private Spinner.OnItemSelectedListener mItemSelectedListener = new Spinner.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {
            mCurrentFragmentIndex = position + 1;
            fragmentReplace(mCurrentFragmentIndex);
        }

        public void onNothingSelected(AdapterView parent) {
        }
    };

}
