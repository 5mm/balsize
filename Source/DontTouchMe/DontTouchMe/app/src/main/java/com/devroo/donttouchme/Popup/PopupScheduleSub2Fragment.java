package com.devroo.donttouchme.Popup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.devroo.donttouchme.Component.DateTime;
import com.devroo.donttouchme.Component.DateTimePicker;
import com.devroo.donttouchme.Component.SimpleDateTimePicker;
import com.devroo.donttouchme.R;

import java.util.Date;

public class PopupScheduleSub2Fragment extends Fragment implements View.OnClickListener, DateTimePicker.OnDateTimeSetListener {

    static EditText sdate;
    static EditText edate;
    static String stype = null;

    //Fragment의 View 작업을 하는 function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_schedule_sub2, container, false);

        sdate = (EditText) view.findViewById(R.id.sTime);
        edate = (EditText) view.findViewById(R.id.eTime);

        sdate.setInputType(0);
        edate.setInputType(0);

        sdate.setEnabled(false);
        edate.setEnabled(false);

        CheckBox block = (CheckBox) view.findViewById(R.id.Block);
        block.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sdate.setEnabled(true);
                    edate.setEnabled(true);
                } else {
                    sdate.setEnabled(false);
                    edate.setEnabled(false);
                }
            }
        });

        sdate.setOnClickListener(this);
        edate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sTime:
                SimpleDateTimePicker simpleDateTimePicker = SimpleDateTimePicker.make(
                        "Set Time Title",
                        new Date(),
                        this,
                        getFragmentManager()
                );
                simpleDateTimePicker.show();
                stype = "sDate";
                break;
            case R.id.eTime:
                simpleDateTimePicker = SimpleDateTimePicker.make(
                        "Set Time Title",
                        new Date(),
                        this,
                        getFragmentManager()
                );
                simpleDateTimePicker.show();
                stype = "eDate";
                break;
        }
    }

    @Override
    public void DateTimeSet(Date date) {
        DateTime mDateTime = new DateTime(date);
        if (stype != null) {
            switch (stype) {
                case "sDate":
                    sdate.setText(mDateTime.getDateString("HH:mm"));
                    break;
                case "eDate":
                    edate.setText(mDateTime.getDateString("HH:mm"));
                    break;
            }
        }
    }
}

